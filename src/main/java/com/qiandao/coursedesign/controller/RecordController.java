package com.qiandao.coursedesign.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.qiandao.coursedesign.pojo.Item;
import com.qiandao.coursedesign.pojo.Record;
import com.qiandao.coursedesign.pojo.User;
import com.qiandao.coursedesign.service.ItemService;
import com.qiandao.coursedesign.service.RecordService;
import com.qiandao.coursedesign.service.UserService;
import com.qiandao.coursedesign.utils.IntegralVip;
import com.qiandao.coursedesign.utils.SimpleDate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.Date;


/**
 * @author konan
 * @since 2022-06-05
 */

@Api("商品")
@Slf4j
@Controller
public class RecordController {

    @Autowired
    private RecordService recordService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;


    @ApiOperation("用户点击购买商品")
    @GetMapping("/shop/shopping/{itemId}/{integral}")
    @Transactional(rollbackFor = Exception.class)  //开启事物，异常回滚
    public String shopping(@PathVariable("itemId") String itemId,@PathVariable("integral") int integral) {

        System.out.println("购买的商品id是：{},使用的积分数量：{}"+itemId+"---"+integral);
        log.info("购买的商品id是：{},使用的积分数量：{}",itemId,integral);
        Item item = itemService.getById(Long.valueOf(itemId));

        //如果商品卖完了
        if (item.getItemNums()<=0) {return "item/item-null.html";}

        int integral1 = IntegralVip.integral(item.getItemprice().toString());   //增加的积分

        BigDecimal money = BigDecimal.valueOf(0);      //折扣的金钱
        if (integral!=0) {money = IntegralVip.money(integral);}

        boolean flag = false;
        User loginsuccess = (User) SecurityUtils.getSubject().getSession().getAttribute("loginsuccess");

        Integer userGrade = loginsuccess.getUserGrade();    //用户的vip等级
        double discount = IntegralVip.discount(userGrade);  //折扣等级

        try {
            //填入销售记录表
            Record record = new Record();
            record.setItemId(Long.valueOf(itemId));
            record.setUserId(loginsuccess.getUserId());
            record.setRecordMoney(item.getItemprice().subtract(money));
            record.setUseBranch(integral);
            record.setRecordTime(SimpleDate.nowtime());
            record.setItemDes(item.getItemDes());
            record.setItemprice(item.getItemprice());
            record.setItemImg(item.getItemImg());
            recordService.save(record);
            //修改商品信息表
            UpdateWrapper<Item> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("itemId",Long.valueOf(itemId));
            updateWrapper.setSql("itemNums = itemNums - 1");
            updateWrapper.set("update_time",new Date());
            itemService.update(updateWrapper);
            //修改个人信息表
            UpdateWrapper<User> updateWrapper1 = new UpdateWrapper<>();
            updateWrapper1.eq("userId",loginsuccess.getUserId());
            updateWrapper1.setSql("userCount = userCount + 1");

            //先算折扣再算积分抵消的金额
            updateWrapper1.setSql("userMoney = UserMoney -"+item.getItemprice().multiply(BigDecimal.valueOf(discount))+"+"+money);
            updateWrapper1.setSql("userSpend = userSpend +"+item.getItemprice().multiply(BigDecimal.valueOf(discount))+"-"+money);
            updateWrapper1.setSql("userBranch = userBranch -"+integral+"+"+integral1);

            updateWrapper1.set("update_time",new Date());   //修改时间，有偏差
            updateWrapper1.set("userGrade",
                    IntegralVip.vip(Double.parseDouble(loginsuccess.getUserSpend().toString())));    //判断用户等级

            userService.update(updateWrapper1);
            log.info("购买成功");
        } catch (Exception e) {
            flag = true;
            log.info("购买失败");
            throw new RuntimeException();
        }

        if (flag) {
            return "item/item-fail.html";   //商品购买失败
        }

        //更新用户session
        User user = userService.getById(loginsuccess.getUserId());
        Session session = SecurityUtils.getSubject().getSession();
        session.removeAttribute("loginsuccess");
        session.setAttribute("loginsuccess",user);

        return "forward:/shop/recording";
    }

}

