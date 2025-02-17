package com.example.post.model.posts;

import com.example.post.model.users.User;
import jakarta.persistence.*;
import lombok.*;

@Data
@Setter
@Entity // db에 저장
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Lob
    @Column(length = 1000)
    private String content; // 댓글 내용

    // 단방향 메핑:
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;  // 댓글이 속한 게시글

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 댓글 작성자

    //Dto ㅊ가
    public CommentResponseDto toResponseDto() {
        return CommentResponseDto.builder()
                .id(this.id)
                .content(this.content)
                .postId(this.post.getId())
                .userId(this.user.getId())
                .username(this.user.getUsername())
                .build();
    }
    
}
