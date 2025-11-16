package com.Main.Ecommerce.comment;

import com.Main.Ecommerce.auth.dto.response.Response;
import com.Main.Ecommerce.auth.model.Customer;
import com.Main.Ecommerce.comment.dto.CommentDto;
import com.Main.Ecommerce.comment.dto.CreateRequest;
import com.Main.Ecommerce.comment.service.CommentServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentServiceImpl commentService;
    private final ModelMapper modelMapper;

    @PostMapping("/createComment/{id}")
    public ResponseEntity<Response> createComment(Authentication authentication, @PathVariable("id") Long id, @Valid @RequestBody CreateRequest createRequest) {
        String email=(String) authentication.getPrincipal();

        Comment comment = commentService.createComment(id,email,createRequest);
        CommentDto commentDto=modelMapper.map(comment,CommentDto.class);
        return ResponseEntity.ok().body(new Response("نظر ثبت شد", commentDto));

    }

}
