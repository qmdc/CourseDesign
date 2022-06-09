package com.qiandao.coursedesign;

import com.qiandao.coursedesign.pojo.Item;
import com.qiandao.coursedesign.service.ItemService;
import com.qiandao.coursedesign.utils.HtmlParseUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class keyword {

    @Autowired
    private ItemService itemService;

    @Test
    void parse() {
        parse2("诺贝尔作品","A");
    }

    @Test
    void parse2(String keyword,String type) {

        List<Item> items;
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

        System.out.println("OK^^^^^^^^");
    }
}
