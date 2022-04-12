package com.irm.bendan.web;



import com.irm.bendan.dao.UserRepository;
import com.irm.bendan.po.User;
import com.irm.bendan.service.BenDansService;
import com.irm.bendan.service.TagService;
import com.irm.bendan.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BenDansService benDansService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;


    @GetMapping("/")
    public String Index(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        HttpSession session,
                        Model model){

        model.addAttribute("page",benDansService.listBendan(pageable));
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("recommendBendans",benDansService.listRecommendBendanTop(8));
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model){
        //"%"+query+"%"匹配原生sql语句
        model.addAttribute("page",benDansService.listBendan("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/bendan/{id}")
    public String bendan(@PathVariable Long id, Model model){
        model.addAttribute("bendan",benDansService.getAndConvert(id));
        return "bendan";
    }

}
