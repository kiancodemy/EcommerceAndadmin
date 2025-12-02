package com.Main.Ecommerce.admin.controller;
import com.Main.Ecommerce.auth.dto.response.Response;
import com.Main.Ecommerce.comment.Comment;
import com.Main.Ecommerce.comment.dto.CommentDto;
import com.Main.Ecommerce.comment.service.CommentServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/comment")
@Tag(name = "admin-Comment-handler", description = "APIs for comment by admin")
/////@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminCommentController {

    private final CommentServiceImpl commentService;
    private  final ModelMapper mapper;

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteComment(@PathVariable("id") Long id) {
       commentService.deleteComment(id);
        return ResponseEntity.ok().body(new Response("پاک شد",null));
    }

    @PutMapping("/publishComment/{id}")
    public ResponseEntity<Response> publishComment(@PathVariable Long id) {
        Comment comment=commentService.publishComment(id);
        CommentDto commentDto=mapper.map(comment,CommentDto.class);

        return ResponseEntity.ok().body(new Response("موفقیت آمیز",commentDto));
    }

    @PutMapping("/unpublishComment/{id}")
    public ResponseEntity<Response> unpublishComment(@PathVariable Long id) {
        Comment comment=commentService.unpublishComment(id);
        CommentDto commentDto=mapper.map(comment,CommentDto.class);
        return ResponseEntity.ok().body(new Response("موفقیت آمیز",commentDto));
    }
    @GetMapping("/allComment")
    public ResponseEntity<Response> allComment() {
        List<Comment> comments=commentService.getAllComments();
        List<CommentDto> commentDto=comments.stream().map(c->mapper.map(c,CommentDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(new Response("موفقیت آمیز",commentDto));
    }

}
