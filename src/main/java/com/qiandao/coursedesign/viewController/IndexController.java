package com.qiandao.coursedesign.viewController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiandao.coursedesign.pojo.Item;
import com.qiandao.coursedesign.pojo.Record;
import com.qiandao.coursedesign.pojo.User;
import com.qiandao.coursedesign.service.ItemService;
import com.qiandao.coursedesign.service.RecordService;
import com.qiandao.coursedesign.service.UserService;
import com.qiandao.coursedesign.utils.MD5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Api("视图控制")
@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private RecordService recordService;

    @ApiOperation("新用户注册")
    @RequestMapping("/openup")
    public String openup() {
        return "open-up/openup.html";
    }

    @ApiOperation("新用户注册表单提交处")
    @PostMapping("/openup/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("number") String number,
                           @RequestParam("address") String address,
                           @RequestParam("word") String word,
                           @RequestParam("sex") int sex,
                           Model model) {

        User user = new User();
        user.setUserName(username);
        user.setUserPwd(MD5Util.MD5(password));
        user.setUserPhone(number);
        user.setUserAddress(address);
        user.setUserWork(word);
        user.setUserSex(sex);
        System.out.println(user);

        try {
            userService.save(user);
            log.info("新用户" + username + "注册成功～～～");
            model.addAttribute("register", "恭喜您 " + username + " 注册成功！");
            return "sign-in/signin.html";
        } catch (Exception e) {
//            e.printStackTrace();
            log.info("新用户" + username + "注册失败！！！");
            model.addAttribute("register", "很遗憾 " + username + " 注册失败！");
            return "open-up/openup.html";
        }
    }

    @ApiOperation("点击商品后")
    @GetMapping("/first/order/{numbers}")
    public String firstOrder(@PathVariable("numbers") String numbers, HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isPermitted("user:primary") || subject.isPermitted("user:root")) {
            Item item0 = itemService.getById(Long.valueOf(numbers));
            HashMap<String, Object> item = new HashMap<>();
            item.put("itemImg", item0.getItemImg());
            item.put("itemprice", item0.getItemprice());
            item.put("itemDes", item0.getItemDes());
            item.put("itemId", item0.getItemId().toString());
            request.setAttribute("item", item);
            return "order/order.html";  //有相应权限直接进入下单页面
        } else {
            request.setAttribute("numbers", numbers);
            return "forward:/userlogin";    //没有跳转至登录页面
        }
    }

    @ApiOperation("购物记录-视图跳转")
    @RequestMapping("/shop/recording")
    public String recording() {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("loginsuccess");
        return "forward:/shop/record/"+user.getUserId();
    }

    @ApiOperation("购物记录")
    @RequestMapping("/shop/record/{userid}")
    public String record(@PathVariable("userid") Integer userid,HttpServletRequest request) {
        QueryWrapper<Record> wrapper = new QueryWrapper<>();
        wrapper.eq("userId",userid);
        wrapper.orderByDesc("recordId");
        List<Record> recordlist = recordService.list(wrapper);
        request.setAttribute("recordlist",recordlist);
//        recordlist.forEach(System.out::println);
        return "user/record.html";
    }

}
