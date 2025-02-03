package com.example.post.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.post.model.posts.Post;
import com.example.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true) // 읽기 전용
@RequiredArgsConstructor
@Service
public class PostService {
	// PostService 객체 생성 시점에 스프링 컨테이너가 
	// 자동으로 의존성을 주입(Dependency Injection)해 준다.
	private final PostRepository postRepository;

    // 글 저장
	@Transactional
	public Post savePost(Post post) {
		post.setCreateTime(LocalDateTime.now());
//		postRepository.savePost(post);
//		return post;
		return postRepository.save(post);
	}
	
	// 글 전체 조회
	public List<Post> getAllPosts() {
		return postRepository.findAll();
	}
	
	// 게시글 읽기 
	@Transactional
	public Post readPost(Long postId) {
		Post post = getPostById(postId);
		post.incrementViews();
		
		return post;
	}
	
	// 아이디로 글 조회
	@Transactional
	public Post getPostById(Long postId) {
		Optional<Post> findPost = postRepository.findById(postId);
//		if (findPost.isPresent()) {
//			// 조회수 증가
//	//			findPost.get().incrementViews();
//	//			return findPost.get();
//			
//			Post post = findPost.get();
//			post.incrementViews();
//			return post;
//		}
//		throws new IllegalArgumentException("게시글을 찾을 수 없습니다.");
		
		Post post = findPost.orElseThrow(
				() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
		return post;
	
	}
	
	@Transactional
	public Post getPostById2(Long postId) {
		Optional<Post> findPost = postRepository.findById(postId);
		Post post = findPost.orElseThrow(
				() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
		return post;
	}
	
	// 글 삭제
	@Transactional
	public void removePost(Long postId) {
		// 게시글 조회
//		Post findPost = postRepository.findPostById(postId);
//		if (findPost != null && findPost.getUser().equals(password)) {
//		Optional<Post> findPost = postRepository.findById(postId);	
//		if (findPost.isPresent()) {
			Post post = getPostById(postId);
			postRepository.delete(post);
		
	}
	
	// 글 수정
	@Transactional
	public void updatePost(Long postId, Post updatePost) {
			Post post = getPostById(postId);
			post.setTitle(updatePost.getTitle());
			post.setContent(updatePost.getContent());
		}

}

