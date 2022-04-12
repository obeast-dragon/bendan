package com.irm.bendan.service;

import com.irm.bendan.po.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentByBendanId(Long bendanId);

    Comment saveComment(Comment comment);
}
