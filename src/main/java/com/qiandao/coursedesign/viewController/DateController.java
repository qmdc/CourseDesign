package com.qiandao.coursedesign.viewController;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import com.qiandao.coursedesign.constant.Keywords;
import com.qiandao.coursedesign.pojo.Item;
import com.qiandao.coursedesign.service.ItemService;
import com.qiandao.coursedesign.utils.HtmlParseUtil;
import com.qiandao.coursedesign.utils.Uuid;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api("数据获取")
@Slf4j
@RestController
public class DateController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @ApiOperation("批量爬取京东商城数据")
    @RequestMapping("/parses")
    public String parses() {

        long start = System.currentTimeMillis();

        for (int i=0;i< Keywords.keywords.length;i++){
            String word = Keywords.keywords[i];
            String keyword2 = word.substring(1);
            String type2 = word.substring(0,1);

            List<Item> items = null;
            try {
                log.info("解析数据关键词:"+keyword2+" 类型："+type2);
                items = HtmlParseUtil.parseJD(keyword2,type2);

                try {
                    itemService.saveBatch(items);
                } catch (Exception e) {
                    log.info("数据添加至数据库异常:"+"关键词:"+keyword2+" 类型："+type2);
                }

                //添加至es索引
                List<BulkOperation> bulkOperations = new ArrayList<>();
                assert items != null;
                items.forEach(a ->
                        bulkOperations.add(BulkOperation.of(b ->
                                b.index(c ->
                                        c.id("" + Uuid.getUUID() + 1).document(a)
                                )
                        ))
                );
                BulkResponse jd_shops = null;
                try {
                    jd_shops = elasticsearchClient.bulk(x -> x.index("jd_shops").operations(bulkOperations));
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                assert jd_goods != null;
//                jd_goods.items().forEach(e-> System.out.println("i:"+e.result()));
//                System.out.println("d_goods.errors():"+jd_goods.errors());

            } catch (Exception e) {
                log.info("爬取数据异常："+keyword2);
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        long end = System.currentTimeMillis();

        long time = start - end;

        System.out.println("OK!结束！"+" Time:"+time+" ms"+",wait for "+Keywords.keywords.length*1000+" ms!");
        return "OK!"+" Time:"+time+" ms"+",wait for "+Keywords.keywords.length*1000+" ms!";
    }

    @ApiOperation("爬取京东商城数据")
    @RequestMapping("/parse/{type}/{keyword}")
    public String parse(@PathVariable("keyword") String keyword,@PathVariable("type") String type) {

        List<Item> items = null;
        try {
            log.info("解析数据关键词:"+keyword+" 类型："+type);
            items = HtmlParseUtil.parseJD(keyword,type);

            try {
                itemService.saveBatch(items);
            } catch (Exception e) {
                log.info("数据添加至数据库异常:"+"关键词:"+keyword+" 类型："+type);
            }

        } catch (Exception e) {
            log.info("爬取数据异常："+keyword);
        }

        return "OK!";
    }
}
