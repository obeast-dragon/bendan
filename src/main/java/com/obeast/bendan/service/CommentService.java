package com.obeast.bendan.service;

import com.obeast.bendan.po.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentByBendanId(Long bendanId);

    Comment saveComment(Comment comment);
}
