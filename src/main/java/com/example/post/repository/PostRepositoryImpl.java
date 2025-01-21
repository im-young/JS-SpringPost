package com.example.post.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.example.post.model.posts.Post;
//# 3.
@Repository // 이거 등록 안하면 bin으로 등록 안하기 때문에 쓸수 없음
public class PostRepositoryImpl implements PostRepository {
	//필드
	private static Map<Long, Post> posts = new HashMap<>();
	private static long sequence = 0;
	@Override
	public void savePost(Post post) {
		//6-2. id 값 세팅
		post.setId(++sequence);
		posts.put(post.getId(),post);
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<Post> findAllPosts() {
		// TODO Auto-generated method stub
		return new ArrayList<>(posts.values());
	}
	@Override
	public Post findPostById(Long postId) {
		// TODO Auto-generated method stub
		Post result = posts.get(postId);// 원본임 -> setter 를 통해서 원본 변경가능함->위험함
//		Post result = new Post(); 
//		result = posts.get(postId);
		return result;
	}
	@Override
	public void updatePost(Post updatePosst) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removePost(Long postId) {
		// TODO Auto-generated method stub
		posts.remove(postId);
	}
	
	
	

}
