package com.qiandao.coursedesign.utils;

import com.qiandao.coursedesign.pojo.Item;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class HtmlParseUtil {

    public static List<Item> parseJD(String keyword,String type) throws IOException {
        System.out.println("进入这里"+keyword);
        ArrayList<Item> items = null;
        try {
            String url = "https://search.jd.com/Search?keyword="+keyword;
            System.out.println(url);
            Document document = Jsoup.parse(new URL(url), 10000);
            Element j_goodsList = document.getElementById("J_goodsList");
            assert j_goodsList != null;
            Elements lis = j_goodsList.getElementsByTag("li");
//            System.out.println("====="+lis);

            items = new ArrayList<>();
            for (Element li : lis) {
                if (li.attr("class").equalsIgnoreCase("gl-item")) {
                    String img = li.getElementsByTag("img").eq(0).attr("data-lazy-img");
                    String price = li.getElementsByClass("p-price").eq(0).text();
                    String title = li.getElementsByClass("p-name").eq(0).text();
                    Item item = new Item();
                    item.setItemId(new Random().nextLong());
                    item.setItemImg(img);
                    item.setItemDes(title);
                    item.setItemType(type);

                    int indexOf = price.indexOf(".");
                    String price02 = price.substring(1,indexOf+3);

//                    System.out.println("价格"+price02);
                    item.setItemprice(BigDecimal.valueOf(Double.parseDouble(price02)));
                    items.add(item);
    //                System.out.println("img:"+img);
    //                System.out.println("price:"+price);
    //                System.out.println("title:"+title);
    //                System.out.println("===========================");
                }
            }
        } catch (Exception e) {
            log.info("<<<<<<<异常行为>>>>>>>");
        }
        return items;
    }
}
