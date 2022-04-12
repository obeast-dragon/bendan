package com.irm.bendan;


import com.irm.bendan.dao.BenDanRepository;
import com.irm.bendan.po.BenDan;
import com.irm.bendan.service.BenDansService;
import com.irm.bendan.web.IndexController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import javax.annotation.Resource;
import java.util.List;


@SpringBootTest
class BenDanApplicationTests  {
    @Autowired
    private BenDansService benDansService;

    @Autowired
    private BenDanRepository benDanRepository;


    @Test
    void testType() {

    }

    @Test
    void testBendan() {
//

    }
}
