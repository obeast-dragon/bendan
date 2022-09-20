package com.obeast.bendan.web;


import com.obeast.bendan.po.Comment;
import com.obeast.bendan.po.User;
import com.obeast.bendan.service.BenDansService;
import com.obeast.bendan.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BenDansService benDansService;

    @Value("${comment.avatar}")
    private String avatar;


    @GetMapping("/comments/{bendanId}")
    public String comments(@PathVariable Long bendanId, Model model){
        model.addAttribute("comments",commentService.listCommentByBendanId(bendanId));
        return "bendan :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        Long bendanId = comment.getBendans().getId();
        comment.setBendans(benDansService.getBendan(bendanId));
        User user = (User) session.getAttribute("user");
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.getParentComment();
        } else {
            comment.setAvatar(avatar);
        }
        comment.setAvatar(avatar);
        commentService.saveComment(comment);
        return "redirect:/comments/" + bendanId;
    }

}
