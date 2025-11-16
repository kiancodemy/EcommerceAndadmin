package com.Main.Ecommerce.comment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentDto {
    private Long id;

    private boolean isPublished;
    private String text;
    private String writer;
    private LocalDateTime createdAt;
}
