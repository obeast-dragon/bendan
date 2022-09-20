package com.obeast.bendan.service.Impl;

import com.obeast.bendan.exception.NotFoundException;
import com.obeast.bendan.dao.BenDanRepository;
import com.obeast.bendan.po.BenDan;
import com.obeast.bendan.po.Type;
import com.obeast.bendan.service.BenDansService;
import com.obeast.bendan.util.MarkdownUtils;
import com.obeast.bendan.util.MyBeanUtils;
import com.obeast.bendan.vo.BenDanQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class BenDanServiceImpl implements BenDansService {

    @Autowired
    private BenDanRepository bendanRepository;

    @Override
    public BenDan getBendan(long id) {
        return bendanRepository.getOne(id);
    }

    @Override
    public BenDan getAndConvert(long id) {
        BenDan benDan = bendanRepository.getOne(id);
        if (benDan == null) {
            throw new NotFoundException("博客不存在");
        }
        BenDan b = new BenDan();
        BeanUtils.copyProperties(benDan,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return b;
    }

    @Override
    public Page<BenDan> listBendan(Pageable pageable, BenDanQuery bendan) {
        return bendanRepository.findAll(new Specification<BenDan>() {
            @Override

            public Predicate toPredicate(Root<BenDan> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                //动态搜索条件
                if (!"".equals(bendan.getTitle()) && bendan.getTitle() != null){
                    predicates.add(cb.like(root.<String>get("title"),"%"+bendan.getTitle()+"%"));
                }
                if (bendan.getTypeId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"),bendan.getTypeId()));

                }

                if (bendan.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"),bendan.isRecommend()));

                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                
                return null;

            }
        },pageable);
    }


    @Override
    public Page<BenDan> listBendan(Pageable pageable) {
        return bendanRepository.findAll(pageable);
    }

    @Override
    public Page<BenDan> listBendan(Long tagId, Pageable pageable) {
        return bendanRepository.findAll(new Specification<BenDan>() {
            @Override
            public Predicate toPredicate(Root<BenDan> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public Page<BenDan> listBendan(String query, Pageable pageable) {
        return bendanRepository.findByQuery(query,pageable);
    }

    @Override
    public List<BenDan> listRecommendBendanTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0,size,sort);
        return bendanRepository.findTop(pageable);
    }

    @Override
    public Map<String, List<BenDan>> archivesBendan() {
        List<String> years = bendanRepository.findGroupYear();
        Map<String,List<BenDan>> map = new HashMap<>();
        for (String year: years) {
            map.put(year,bendanRepository.findByYear(year));

        }
        return map;
    }

    @Override
    public Long countBendan() {
        return bendanRepository.count();
    }

    @Transactional
    @Override
    public BenDan saveBenban(BenDan bendan) {

        if (bendan.getId() == null) {
            bendan.setCreateTime(new Date());
            bendan.setUpdateTime(new Date());
            bendan.setViews(0);
        }
        else {
            bendan.setUpdateTime(new Date());
        }
        return bendanRepository.save(bendan);
    }

    @Transactional
    @Override
    public BenDan updateBendan(Long id, BenDan bendan) {
        BenDan b = bendanRepository.getOne(id);
        if (b == null){
            throw  new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(bendan,b, MyBeanUtils.getNullPropertNames(bendan));
        b.setUpdateTime(new Date());
        return bendanRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteBendan(Long id) {
        BenDan b = bendanRepository.getOne(id);
        bendanRepository.delete(b);
    }
}
