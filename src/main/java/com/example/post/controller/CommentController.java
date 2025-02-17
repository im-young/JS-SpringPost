package com.example.post.controller;

import com.example.post.model.posts.Comment;
import com.example.post.model.posts.CommentResponseDto;
import com.example.post.model.posts.Post;
import com.example.post.model.users.User;
import com.example.post.service.CommentService;
import com.example.post.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController // controller
@RequiredArgsConstructor //생성자 주입
@Slf4j // log.info
@RequestMapping("api") // 공통 url
public class CommentController {
    private final CommentService commentService;
    private final PostService postService;


    /*
    * HTTP 요청 방식
    * get : 조회
    * post : 저장, 등록
    * put: 교체(원본 수정 -> 기존걸 없애고 완전히 바꾸겠다.)
    * patch : 일부 교체, 수정
    * delete: 삭제
    * */
    //댓글 작성(저장 : post)
    @PostMapping("posts/{postId}/comments")
    public ResponseEntity<CommentResponseDto> addComment(
            @PathVariable(name= "postId") Long postId,
            //postid를 경로 변수값으로 받기 
            @RequestBody Comment comment,
            //자바스트립을 통해서 요청을 보낼꺼기 떄문에(request param 사용X)
            @SessionAttribute(name="loginUser") User loginUser) throws JsonMappingException, JsonProcessingException {
        log.info("comment:{}",comment);
        //@RequestBody String comment 일 때 사용
//        ObjectMapper objectMapper = new ObjectMapper(); // 내가 받고 싶은 파일이 임시로 처리하고 싶을 때 사용(@RequestBody String comment 으로 사용할 때)
//        Comment value = objectMapper.readValue(comment, Comment.class);
//        log.info("value:{}",value);
//        commentService.addComment(comment);

        //  어떤 게시글에 대한 댓글인지
        Post post = postService.getPostById(postId);
        //누가 썻는지를 세션에서 받아온다.
        comment.setUser(loginUser);
        comment.setPost(post);
        log.info("add comment:{}",comment);
        // service로 넘겨주기
        commentService.addComment(comment);

        CommentResponseDto commentResponseDto = comment.toResponseDto();

        return ResponseEntity.ok(commentResponseDto);

    }


    //댓글 조회(읽기 : get)
    @GetMapping("posts/{postId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getComment(@PathVariable(name= "postId") Long postId){
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
        for(Comment comment : comments){
            commentResponseDtos.add(comment.toResponseDto());
        }
        return ResponseEntity.ok(commentResponseDtos);
    }


    // 댓글 수정(put, patch)
    @PutMapping("posts/{postId}/comments/{commentsId}")
    public ResponseEntity<Comment> editComment(@PathVariable(name= "postId") Long postId, @PathVariable(name= "commentsId") Long commentsId, @RequestBody String comment){
        log.info("edit comment:{}",comment);
        return ResponseEntity.ok(null);
    }
    // 댓글 삭제(delete)
    @DeleteMapping("posts/{postId}/comments/{commentsId}")
    public ResponseEntity<String> deleteComment(){
        return ResponseEntity.ok(null);
    }


}
