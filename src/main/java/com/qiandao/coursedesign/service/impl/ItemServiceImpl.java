package com.qiandao.coursedesign.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.qiandao.coursedesign.pojo.Item;
import com.qiandao.coursedesign.mapper.ItemMapper;
import com.qiandao.coursedesign.service.ItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现T
 * </p>
 *
 * @author konan
 * @since 2022-06-05
 */
@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    public List<Map<String,Object>> search(String keyword, Integer pageIndex, Integer pageSize){
        if (pageIndex<0){pageIndex=0;}

        SearchResponse<Item> search = null;
        try {
            Integer finalPageIndex = pageIndex;
            search = elasticsearchClient.search(s -> s
                            .index("jd_shops")
                            //查询name字段包含hello的document(不使用分词器精确查找)
                            .query(q -> q
                                    .term(t -> t
                                            .field("itemDes")
                                            .value(v -> v.stringValue(keyword))
                                    ))
                            //分页查询，从第0页开始查询n个document   //按age降序排序  .sort(f->f.field(o->o.field("age").order(SortOrder.Desc)))
                            .from(finalPageIndex)
                            .size(pageSize)
                            .highlight(h->h.fields("name",f->f.preTags("<span style='color'>").postTags("</span>"))),
                    Item.class
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(search);

        List<Map<String,Object>> items = new ArrayList<>();
        assert search != null;
        for (Hit<Item> hit : search.hits().hits()) {
            Item source = hit.source();

            Map<String, Object> map = new HashMap<>();
            assert source != null;
            map.put("name",source.getItemDes());
            map.put("img",source.getItemImg());
            map.put("price","¥"+source.getItemprice());
            items.add(map);
        }

        return items;
    }

}
