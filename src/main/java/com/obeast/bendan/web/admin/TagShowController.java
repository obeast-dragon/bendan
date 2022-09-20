package com.obeast.bendan.web.admin;


import com.obeast.bendan.po.Tag;
import com.obeast.bendan.service.BenDansService;
import com.obeast.bendan.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TagShowController {

    @Autowired
    private BenDansService benDansService;

    @Autowired
    private TagService tagService;

    @GetMapping("/tags/{id}")
    public String type(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                       @PathVariable Long id, Model model){
        List<Tag> tags = tagService.listTag();
        if (id == -1) {
            id = tags.get(0).getId();
        }
        model.addAttribute("tags",tags);
        model.addAttribute("page",benDansService.listBendan(id,pageable));
        model.addAttribute("activeTagId",id);
        return "tags";
    }
}
