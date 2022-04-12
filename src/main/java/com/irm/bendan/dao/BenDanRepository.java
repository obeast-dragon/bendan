package com.irm.bendan.dao;

import com.irm.bendan.po.BenDan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BenDanRepository extends JpaRepository<BenDan,Long>, JpaSpecificationExecutor<BenDan>, PagingAndSortingRepository<BenDan,Long> {

    @Query("select b from BenDan b where b.recommend= true")
    List<BenDan> findTop(Pageable pageable);

    //select * from t_bendan where title like '%内容%'
    //搜索title和content
    @Query("select b from BenDan b where b.title like ?1 or b.content like ?1")
    Page<BenDan> findByQuery(String query,Pageable pageable);

    @Transactional
    @Modifying
    @Query("update BenDan b set b.views = b.views+1 where b.id = ?1")
    int updateViews(Long id);

    @Query("select function('date_format',b.updateTime,'%Y') as year from BenDan b group by year order by function('date_format',b.updateTime,'%Y') desc " )
    List<String> findGroupYear();


    @Query("select b from BenDan b where function('date_format',b.updateTime,'%Y') = ?1")
    List<BenDan> findByYear(String year);
}






