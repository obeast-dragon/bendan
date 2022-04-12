package com.irm.bendan.web.admin;

import com.irm.bendan.po.BenDan;
import com.irm.bendan.po.Type;
import com.irm.bendan.po.User;
import com.irm.bendan.service.BenDansService;
import com.irm.bendan.service.TagService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BenDanController {

    public static final String INPUT = "admin/bendans-input";
    public static final String LIST = "admin/bendans";
    public static final String REDIRECT_LIST = "redirect:admin/bendans";


    @Autowired
    private BenDansService benDansService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/bendans")
    public String BenDan(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC)Pageable pageable, BenDanQuery benDanQuery, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",benDansService.listBendan(pageable,benDanQuery));
        return LIST;
    }

    @PostMapping("/bendans/search")
    public String search(@PageableDefault(size = 5,sort = {"updateTime"}, direction = Sort.Direction.DESC)Pageable pageable,
                         BenDanQuery benDanQuery, Model model){
        model.addAttribute("page",benDansService.listBendan(pageable,benDanQuery));
        return "admin/bendans :: bendanList";
    }

    @GetMapping("/bendans/input")
    public String input(Model model){
        setTypeAndTag(model);
        model.addAttribute("bendan",new BenDan());
        return INPUT;
    }

    private void setTypeAndTag(Model model){
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("types",typeService.listType());
    }

    @GetMapping("/bendans/{id}/input")
    public String editInput(@PathVariable  Long id, Model model){
        setTypeAndTag(model);
        BenDan benDan = benDansService.getBendan(id);
        //初始化tagIds
        benDan.init();
        model.addAttribute("bendan",benDansService.getBendan(id));
        return INPUT;
    }

    @PostMapping("/bendans")
    public String post(BenDan benDan, RedirectAttributes attributes, HttpSession session){
        benDan.setUser((User) session.getAttribute("user"));
        benDan.setType(typeService.getType(benDan.getType().getId()));
        benDan.setTags(tagService.listTag(benDan.getTagIds()));
        BenDan b;
        if (benDan.getId() == null) {
            b = benDansService.saveBenban(benDan);
        }else {
            b = benDansService.updateBendan(benDan.getId(), benDan);
        }

        if (b == null){
            attributes.addFlashAttribute("message", "操作失败");
        }else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:bendans";
    }

    @GetMapping("/bendans/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        benDansService.deleteBendan(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/bendans";

    }

}

