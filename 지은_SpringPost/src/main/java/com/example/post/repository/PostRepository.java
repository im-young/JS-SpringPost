package com.example.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.post.model.posts.Post;
//import com.example.post.model.users.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	
}
