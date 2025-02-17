package com.example.post.model.posts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
// 작성한 댓글에 대한 응답 객체
public class CommentResponseDto {
    private Long id;
    private String content;
    private Long postId;
    private Long userId;
    private String username;
    
}
