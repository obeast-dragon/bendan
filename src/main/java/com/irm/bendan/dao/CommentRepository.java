package com.irm.bendan.dao;

import com.irm.bendan.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByBendansIdAndParentCommentNull(Long bendanId, Sort sort);
}
