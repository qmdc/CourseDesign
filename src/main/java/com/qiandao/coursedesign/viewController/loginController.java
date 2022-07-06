package com.qiandao.coursedesign.viewController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiandao.coursedesign.pojo.Item;
import com.qiandao.coursedesign.pojo.User;
import com.qiandao.coursedesign.service.ItemService;
import com.qiandao.coursedesign.service.UserService;
import com.qiandao.coursedesign.utils.MD5Util;
import com.qiandao.coursedesign.utils.SimpleDate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Api("登录请求视图控制")
@Slf4j
@Controller
public class loginController {

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @ApiOperation("首页")
    @GetMapping (value = {"/","/index"})
    public String login() {
        return "index.html";
    }

    @ApiOperation("登录")
    @GetMapping  ("/userlogin")
    public String userlogin(HttpServletRequest request) {
        String numbers = (String) request.getAttribute("numbers");
        if (numbers!=null) {    //判断是否是因为下单而请求的
            SecurityUtils.getSubject().getSession().setAttribute("numbers2",numbers);
        }
        return "sign-in/signin.html";
    }

    @ApiOperation("个人中心")
    @GetMapping  ("/shop/usercenter")
    public String usercenter() {
        return "user/center.html";
    }

    @ApiOperation("登录页表单提交处理处")
    @RequestMapping("/loginto")
    public String loginUser(String username, String password, Model model,HttpSession session, HttpServletRequest request) {
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户的登录信息
        String password2 = null;
        if (password!=null) {
            password2 = MD5Util.MD5(password);
        }else {
            return "sign-in/signin.html";
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username, password2);

        try {
//            token.setRememberMe(true);  //设置记住我
            subject.login(token);   //登录请求

            log.info(username+"登录成功。。。。。");
            System.out.println(SimpleDate.nowtime()+username+"登录成功。。。。。");

            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("userName",username);
            User user = userService.getOne(wrapper);
            session.setAttribute("loginsuccess",user);  //登录成功的用户信息存入session

            String numbers = (String) session.getAttribute("numbers2"); //判断是否要进入商品下单页面还是个人中心
            session.removeAttribute("numbers2");
            if (numbers!=null) {
                Item item1 = itemService.getById(Long.valueOf(numbers));
                HashMap<String, Object> item = new HashMap<>();
                item.put("itemImg",item1.getItemImg());
                item.put("itemprice",item1.getItemprice());
                item.put("itemDes",item1.getItemDes());
                item.put("itemId",item1.getItemId().toString());
                request.setAttribute("item",item);
                return "order/order.html";
            }else {
                return "user/center.html";
            }
        } catch (Exception e) {
            model.addAttribute("msg", "用户名或密码错误");
            return "sign-in/signin.html";
        }
    }


    @ApiOperation("当前用户没有访问权限")
    @GetMapping("/Unauthorized")
    public String Unauthorized() {
        return "error/unauthorized";
    }

}
