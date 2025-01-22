package com.example.post.service;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.post.model.posts.Post;
import com.example.post.model.users.User;
import com.example.post.repository.PostRepository;
import com.example.post.repository.PostRepositoryImpl;

import lombok.RequiredArgsConstructor;
//#4.
@Service
@RequiredArgsConstructor
public class PostService {
// 4-1. 의존성 주입
	//필드 선언 방식
	//PostService 객체 생성 시점에 스프링 컨테이너가 자동으로 의존성을 주입(Dependency Injection)해 준다.
	private final PostRepository postRepository;
			//final은 한번 반드시 초기회를 시켜줘야함 -> 초기화 이후엔 변경 불가능
			//PostRepository는 인터페이스라서 구현체가 상관 없음
//4-2. (실제 작동하는)메서드 생성
	//글 저장

	public Post savePost(
			Post post) {
		//6-1. dataTime 값 설정해주기 (saveService 완성)
		post.setCreateTime(LocalDateTime.now()); // 
		postRepository.savePost(post);
		return post;
	}
	// 글 젖체 조회
	public List<Post> getAllPosts(){
		return postRepository.findAllPosts();
	}
	// 아이디로 글 조회
	public Post getPostById(Long postId) {
		
		Post findPost = postRepository.findPostById(postId);
		//조회수 올라가게
		findPost.incrementViews();
		return  findPost;
	}
	//글 삭제 : 로그인 안 하고 삭제
	public void removePost(Long postId) {
		//게시글 조회 후 id 같은지 확인하기
		Post findPost = postRepository.findPostById(postId);
		//로그인 안 하고 삭제
//		if(findPost != null && findPost.getPassword().equals(password)) { // model.post에서 password 삭제해서
//			postRepository.removePost(postId);
//		}
		postRepository.removePost(postId);
	}
			
}
