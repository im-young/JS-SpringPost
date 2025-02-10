package com.example.post.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.post.model.posts.FileAttachment;
import com.example.post.model.posts.Post;
import com.example.post.repository.PostRepository;
import com.example.post.util.FileAttachmentUtil;

import lombok.RequiredArgsConstructor;
//#4.
@Transactional(readOnly = true) // 클레스에 해당하는 모든 메서드들이 이 걸 수행함
				// readOnly = true : 읽기 전용으로 가져온다 -> 안하면 이거 안붙은 글 전체조회도 영속성 컨텍스트(트렌젝에 넣어놓음
												//	-> 이거 하면 영속성 컨텍스트를 안불러 옴(한 단계 건너뛰어서 속도에도 용이) !! 이거 다시 보기
@Service
@RequiredArgsConstructor
public class PostService {
	// 파일 업로드
	//업로드 경로 
	@Value("${file.upload.path}")
	private String uploadPath; // 파일 업로드 경로
	
// 4-1. 의존성 주입
	//필드 선언 방식
	//PostService 객체 생성 시점에 스프링 컨테이너가 자동으로 의존성을 주입(Dependency Injection)해 준다.
	private final PostRepository postRepository;
			//final은 한번 반드시 초기회를 시켜줘야함 -> 초기화 이후엔 변경 불가능
			//PostRepository는 인터페이스라서 구현체가 상관 없음
//4-2. (실제 작동하는)메서드 생성
	//글 저장
	@Transactional // 없어도 동작을 하긴 하지만 커밋,롤백이 필요한 작업이 있으면 해놓는게 좋음
	public Post savePost(
			Post post, MultipartFile file  ) {// 파일 업로드를 위해서
		//6-1. dataTime 값 설정해주기 (saveService 완성)
		post.setCreateTime(LocalDateTime.now()); 

//= 파일 업로드Day0207============================================
		// 첨부파일이 존재하는 지 확인한기 

		if (file!=null && !file.isEmpty()) {
			
			//  첨부파일을 저장
			String storedFilename = FileAttachmentUtil.uploadFile(file,uploadPath);
			// attachment에 파일 정보 넣기
			FileAttachment fileAttachment = new FileAttachment();
			fileAttachment.setOriginalFilename(file.getOriginalFilename());
			fileAttachment.setStoredFilename(storedFilename);
			fileAttachment.setSize(file.getSize());
			fileAttachment.setPost(post);

			// 양방향이라서 post에도 정보 버장 
			post.setFileAttachment(fileAttachment);
		}
		postRepository.save(post);
		return post;
	}
	// 글 젖체 조회
	public Page<Post> getAllPosts(int page, int size){
		
//		return postRepository.findAllPosts();// 내림차순
		Pageable pageable=PageRequest.of(page, size);
		return postRepository.findAl1ByOrderByCreateTimeDesc(pageable);// 내림차순

	}

	
	@Transactional // 여기서는 이거 없으면 작동안함 
//	@Override
	// 아이디로 글 조회
	public Post getPostById(Long postId) {
		// 자동 repository
		Optional<Post> findPost = postRepository.findById(postId);
//		// Optional return 방법 1
//		if(findPost.isPresent()) { // Optional은 값이 있는지 없는지 확인해야함. (이거 이나면 onElse?? 같은것도 있었는데 기억안남)
//			//조회수 올라가게
//			findPost.get().incrementViews();
//			return  findPost;
//		}
//		return null;
		 // 방법2. 값이 없으면 예외를 던짐
		Post post = findPost.orElseThrow(// null이면 예외를 던지고 아니면 post를 리턴함.
				() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));


//	    Post post = findPost.get();

		post.incrementViews(); // 변경감지는 같은 트렌젝션 안에서만 가능 -> 같은 트렌젝션아에 안들어 와있어서 db에 반영 안됨
		// 쿼리가 두번 실행됨 -> select(findPost) 와 update (post) -> 이걸 하나의 트렌젝션으로 묶어야함 -> 하는 방법 : @Transactioal(spring꺼) 붙여주기

		//		findPost.orElse(null); //이것도 될듯 . 값이 없으면 null 출력
		
		return post;
				
	}
	
	//글 삭제 : 로그인 안 하고 삭제
	@Transactional
	public void removePost(Long postId) {
		//게시글 조회 후 id 같은지 확인하기
		Optional<Post> findPost = postRepository.findById(postId);
		//로그인 안 하고 삭제
//		if(findPost != null && findPost.getPassword().equals(password)) { // model.post에서 password 삭제해서
//			postRepository.removePost(postId);
//		}
		
		// 자동으로 레포지토리 생성 할 때 레포지토리일 때 삭제하기
		findPost = postRepository.findById(postId);
		if (findPost.isPresent()) {
			Post post = findPost.get();
			postRepository.delete(post);
		}		
	}
	
	//update
	@Transactional
	public Post updatePost(Long postId, String title, String content) {
		Optional<Post> findPost = postRepository.findById(postId);
	    Post post = findPost.get();
	    post.setTitle(title);
	    post.setContent(content);
	    postRepository.save(post);
	    return post;
	}

}
