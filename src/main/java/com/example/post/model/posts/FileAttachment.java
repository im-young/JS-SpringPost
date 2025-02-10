package com.example.post.model.posts;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class FileAttachment {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String originalFilename; // 원본 파일명
	private String storedFilename; 	// 서버에 저장된 파일명
	private long size; 				//파일 크기
	
	@OneToOne // 양방향
	@JoinColumn(name = "post_id") // FK 컬럼의 이름을 정의
	private Post post;

	@Override
	public String toString() {
		return "FileAttachment{" +
				"id=" + id +
				", originalFilename='" + originalFilename + '\'' +
				", storedFilename='" + storedFilename + '\'' +
				", size=" + size +
				'}';
	}
}
