package com.example.post.model.posts;

import java.time.LocalDateTime;

import com.example.post.model.users.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Data;
@Data
@Entity
public class Post {
//# 1.
//1-1.필드 정의 : html(create 의 태그 name과 같은지 봐야함)
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) //프라이머리 키값을 안쓰기 위해서? 
	private Long id;		//게시글 id :// id는 서비스가 줄수 없음 글 저장 전에 생성
	private String title;	// 제목
	@Lob// (엄청 길게 가능) or @Column(columnDefinition = "text")
	private String content;	//내용(기본:varchar 255)
	
	
//	private String username;//작성자
//	private String password;//비밀번호
	
	//주인 입장 : 폴인키를 갖고 있는것, 1(유저):N(포스트) => 포스트가 주인 
	@ManyToOne(fetch = FetchType.LAZY) 
	// 클래스 입장이 N인지 1인지에 따라 결정됨, 
				//기본(패치타입) 로딩전략: 즉시 로딩 -> post를 조회하면 user도 같이 조회한다. -> 나는 포스트만 로딩하고 싶은데 연관된 것도 같이 로딩하면서 의도치 않은 테이블도 불러옴
					// => 페치타입 LAZY
	@JoinColumn(name="user_id")
	private User user; // 회원가입 or 로그인한 사용자의 정보를 갖오오기 -> 위의 작성자 비밀변호 필요없음
	private int views;		//조회수 // 0부터 시작해서 손볼것이 없음
	private LocalDateTime createTime;	//작성일 // 이 친구는 값을 세팅 해줘야함
	
//1-2조회수 증가
	public void incrementViews() {
		this.views++;
	}
}
