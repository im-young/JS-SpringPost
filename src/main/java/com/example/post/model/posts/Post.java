package com.example.post.model.posts;

import java.time.LocalDateTime;

import com.example.post.model.users.User;

import lombok.Data;
@Data
public class Post {
//# 1.
//1-1.필드 정의 : html(create 의 태그 name과 같은지 봐야함)
	private Long id;		//게시글 id :// id는 서비스가 줄수 없음 글 저장 전에 생성
	private String title;	// 제목
	private String content;	//내용
//	private String username;//작성자
//	private String password;//비밀번호
	private User user; // 회원가입 or 로그인한 사용자의 정보를 갖오오기 -> 위의 작성자 비밀변호 필요없음
	private int views;		//조회수 // 0부터 시작해서 손볼것이 없음
	private LocalDateTime createTime;	//작성일 // 이 친구는 값을 세팅 해줘야함
	
//1-2조회수 증가
	public void incrementViews() {
		this.views++;
	}
}
