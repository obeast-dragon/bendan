package com.irm.bendan.web;


import com.irm.bendan.service.BenDansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArchivesShowController {

    @Autowired
    private BenDansService benDansService;


    @GetMapping("/archives")
    public String archives(Model model){
        model.addAttribute("archiveMap",benDansService.archivesBendan());
        model.addAttribute("bendanCount",benDansService.countBendan());
        return "archives";
    }
}
