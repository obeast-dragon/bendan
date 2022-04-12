package com.irm.bendan.service;

import com.irm.bendan.po.BenDan;
import com.irm.bendan.vo.BenDanQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BenDansService {
    BenDan getBendan(long id);

    BenDan getAndConvert(long id);

    Page<BenDan> listBendan(Pageable pageable, BenDanQuery bendan);

    Page<BenDan> listBendan(Pageable pageable);

    Page<BenDan> listBendan(Long tagId,Pageable pageable);

    Page<BenDan> listBendan(String query,Pageable pageable);

    List<BenDan> listRecommendBendanTop(Integer size);

    Map<String, List<BenDan>> archivesBendan();

    Long countBendan();

    BenDan saveBenban(BenDan bendan);

    BenDan updateBendan(Long id, BenDan bendan);

    void deleteBendan(Long id);


}
