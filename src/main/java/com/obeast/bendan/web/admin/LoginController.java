package com.obeast.bendan.web.admin;

import com.obeast.bendan.po.User;
import com.obeast.bendan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage(){
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        User user = userService.checkUser(username,password);
        if(user != null){
            user.setPassword(null);//不传密码
            session.setAttribute("user",user);
            return "admin/index";//登录成功
        }else {
            attributes.addFlashAttribute("message","用户名或密码错误");
            return "redirect:/admin";
        }

    }

//    @PostMapping("/login")
//    public String post(@Valid User user,
//                       BindingResult result,
//                       RedirectAttributes attributes){
//        User user1 = userService.checkUserNickname(user.getNickname());
//        if (user1 != null) {
//            result.rejectValue("name","nameError","昵称已存在");
//        }
//        if (result.hasErrors()) {
//            return "admin/login-input";
//        }
//        User u = userService.saveUser(user);
//        if (u == null) {
//            attributes.addFlashAttribute("message", "新增失败");
//        }else {
//            attributes.addFlashAttribute("message","新增成功");
//        }
//        return "redirect:/admin";
//
//    }



//    @GetMapping("/login/input")
//    public String logadd(@Valid User user,Model model,RedirectAttributes attributes){
//        if (user.getId() == null){
//            userService.saveUser(user);
//            attributes.addFlashAttribute("message","新增成功");
//            return "/admin/login";
//        }
//
////        model.addAttribute("user",new User());
//        return "/admin/login-input";
//
//    }

    //注销
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }

}
