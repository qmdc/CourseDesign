package com.qiandao.coursedesign.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiandao.coursedesign.constant.Constant;
import com.qiandao.coursedesign.constant.Keywords;
import com.qiandao.coursedesign.pojo.Item;
import com.qiandao.coursedesign.service.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author konan
 * @since 2022-06-05
 */
@Api("商品索检")
@RestController
@RequestMapping("/core/item")
//@Transactional      //开启事物，最后在指定的方法上开启
public class ItemController {

    @Autowired
    private ItemService itemService;

    @ApiOperation("使用elasticsearch服务查找")
    @ResponseBody
    @GetMapping("/search/{keyword}/{pageIndex}/{pageSize}")
    public List<Map<String, Object>> parse(@PathVariable("keyword") String keyword,
                                           @PathVariable("pageIndex") Integer pageIndex,
                                           @PathVariable("pageSize") Integer pageSize) {

        List<Map<String, Object>> search = itemService.search(keyword, pageIndex, pageSize);
        //search.forEach(System.out::println);
        return search;
    }

    @ApiOperation("使用mysql模糊查询")
    @ResponseBody
    @GetMapping("/searchbysql/{keyword}/{ge}/{le}")
    public List<Map<String, String >> parseBySql(@PathVariable("keyword") String keyword,
                                                @PathVariable("ge") Integer ge,
                                                @PathVariable("le") Integer le) {
        Page<Item> page = new Page<>(Constant.pageIndex, Constant.pageSize);
        QueryWrapper<Item> wrapper = new QueryWrapper<>();

//        System.out.println(">>>>>>>>"+keyword);
        String decode = URLDecoder.decode(keyword, StandardCharsets.UTF_8); //解码取出
//        System.out.println(decode);

        if (decode.equals("qiandao")) {
            int i = (int) (Math.random() * Keywords.keywords2.length);
//            System.out.println(Keywords.keywords[i]);

            wrapper.like("itemDes", Keywords.keywords2[i].substring(1));
        } else {
            wrapper.like("itemDes", decode);
        }
        if (ge != null) {
            wrapper.ge("itemprice", ge);
        }
        if (le != null) {
            wrapper.le("itemprice", le);
        }
        wrapper.orderByDesc("create_time");
        Page<Item> pages = itemService.page(page, wrapper);
        List<Item> records = pages.getRecords();

        List<Map<String, String >> items = new ArrayList<>();
        for (Item record : records) {
            Map<String, String > map = new HashMap<>();
            map.put("name", record.getItemDes());
            map.put("img", record.getItemImg());
            map.put("price", "¥" + record.getItemprice());
            map.put("id", record.getItemId().toString());
            items.add(map);
        }
        return items;
    }

}

