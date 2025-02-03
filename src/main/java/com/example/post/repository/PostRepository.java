package com.example.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.post.model.posts.Post;
import com.example.post.model.users.User;
//#2.
public interface PostRepository extends JpaRepository<Post, Long> {
	//구현체는 jpa가 알아서 만들어 줌 				// T: 어떤 엔티티 타입을 관리 할꺼냐, id : user에 있는 id 타입
	


}
