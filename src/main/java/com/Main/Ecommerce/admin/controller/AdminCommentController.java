package com.Main.Ecommerce.admin.controller;
import com.Main.Ecommerce.auth.dto.response.Response;
import com.Main.Ecommerce.comment.Comment;
import com.Main.Ecommerce.comment.dto.CommentDto;
import com.Main.Ecommerce.comment.service.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/comment")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminCommentController {

    private final CommentServiceImpl commentService;
    private  final ModelMapper mapper;

    @DeleteMapping("/delete")
    public ResponseEntity<Response> deleteComment(Long id) {
       commentService.deleteComment(id);
        return ResponseEntity.ok().body(new Response("پاک شد",null));
    }

    @PutMapping("/publishComment")
    public ResponseEntity<Response> publishComment(Long id) {
        Comment comment=commentService.publishComment(id);
        CommentDto commentDto=mapper.map(comment,CommentDto.class);

        return ResponseEntity.ok().body(new Response("موفقیت آمیز",commentDto));
    }

    @PutMapping("/unpublishComment")
    public ResponseEntity<Response> unpublishComment(Long id) {
        Comment comment=commentService.unpublishComment(id);
        CommentDto commentDto=mapper.map(comment,CommentDto.class);
        return ResponseEntity.ok().body(new Response("موفقیت آمیز",commentDto));
    }

}
