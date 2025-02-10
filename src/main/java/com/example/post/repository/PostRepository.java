package com.example.post.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.post.model.posts.Post;
import org.springframework.data.jpa.repository.Query;

//#2.
public interface PostRepository extends JpaRepository<Post, Long> {
    //구현체는 java가 알아서 만들어 줌 				// T: 어떤 엔티티 타입을 관리 할꺼냐, id : user에 있는 id 타입
// 게시판 글 작성일 순으로 내림차순
    //방법1 : jpal을 사용하는 방법
    @Query("select p from Post p order by p.createTime desc") // sql 문법
    List<Post> findAllPosts();

    //방법2. 쿼리 메소드를 사용하는 방법
    Page<Post> findAl1ByOrderByCreateTimeDesc(Pageable pageable);
}
