package com.Main.Ecommerce.comment.service;
import com.Main.Ecommerce.comment.Comment;
import com.Main.Ecommerce.comment.dto.CreateRequest;


import java.util.List;

public interface CommentService {

    Comment publishComment(Long id);
    Comment createComment (Long id ,String email, CreateRequest createRequest);
    Comment unpublishComment(Long id);
    void deleteComment(Long id);
    List<Comment> getAllComments();

}
