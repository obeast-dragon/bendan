package com.obeast.bendan;


import com.obeast.bendan.dao.BenDanRepository;
import com.obeast.bendan.service.BenDansService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


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
