package com.irm.bendan.web.admin;


import com.irm.bendan.po.Type;
import com.irm.bendan.service.BenDansService;
import com.irm.bendan.service.TypeService;
import com.irm.bendan.vo.BenDanQuery;
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
public class TypeShowController {

    @Autowired
    private BenDansService benDansService;

    @Autowired
    private TypeService typeService;

    @GetMapping("/types/{id}")
    public String type(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                       @PathVariable Long id, Model model){
        List<Type> types = typeService.listType();
        if (id == -1) {
            id = types.get(0).getId();
        }
        BenDanQuery benDanQuery = new BenDanQuery();
        benDanQuery.setTypeId(id);
        model.addAttribute("types",types);
        model.addAttribute("page",benDansService.listBendan(pageable,benDanQuery));
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}
