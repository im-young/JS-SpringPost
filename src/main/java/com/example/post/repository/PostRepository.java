package com.example.post.repository;

import java.util.List;

import com.example.post.model.Post;
//#2.
public interface PostRepository {
//2-1. 메서드 생성
	//글 등록
	void savePost(Post post); // 접근제어자 안붙어 있음 : 인터페이스는 모든 메서드가 다 퍼블릭이다
	// 글 전체 조회
	List<Post> findAllPosts();
	// 아이디로 글 조회
	Post findPostById(Long postId);
	//글 수정
	void updatePost(Post updatePosst);
	// 글 삭제
	void removePost(Long postId);
}
