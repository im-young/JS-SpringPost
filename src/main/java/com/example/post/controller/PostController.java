package com.example.post.controller;


import java.net.MalformedURLException;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.example.post.util.PageNavigator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import com.example.post.model.posts.Post;
import com.example.post.model.users.User;
import com.example.post.service.PostService;

import lombok.RequiredArgsConstructor;


//5. 컨트롤러 만들기
@RequiredArgsConstructor // 의존성 주입
@Controller
@Slf4j
public class PostController {
	// 페이지 네비게이션
	// 한 페이지 당 보여줄 게시글의 수
	@Value("${page.countPerPage}")
	private int countPerPage;
	// 한 그룹당 보여줄 페이지의 수
	@Value("${page.pagePerGroup}")
	private int pagePerGroup;
	//파일 업로드 경로 	
	@Value("${file.upload.path}") // file.upload.path : application.properties의 업로드 경로 변수명
	private String uploadPath;
	
	
//5-1. 의존성 주입(필드 생성방식)
	private final PostService postService;

//메서드 작성
	// 5-2. 게시글 작성 페이지 이동
	@GetMapping(path = "posts/create") // 링크로 클릭해서 이동하는 것은 get방식
	public String createPostForm(
			/*
			 * 세선을 받아올 수 있는 방법3가지 1. servlet 2. http 3. session 어노테이션 1. reqest객체안에 세션이 들어있음
			 * : HttpServletRequest request 2.HttpSession session
			 */
			// 3. 세션에 저장된 데이터를 조회한다./ required : 세선에 null이라도 에러 나지 않게 하기 위해서
			@SessionAttribute(name = "loginUser", required = false) User loginUser) {
		// 사용자가 로그인 했는지 체크
		log.info("loginUser:{}", loginUser);
//		if (loginUser == null) {
//			// 로그인을 하지 않았으면 로그인 페이지로 리다이렉트
//			return "redirect:/users/login";
//		}
		// 게시글 작성 페이지의 뷰 이름을 리턴
		return "posts/create";
	}

	public String createPostForm() {
		// 게시글 작성 페이지의 뷰 이름을 리턴
		return "posts/create";
	}

	// 5-3. 게시글 등록
	@PostMapping("posts")
	public String savePost(
//			Model model,
			@ModelAttribute Post post, @SessionAttribute(name = "loginUser", required = false) User loginUser,
			// ============ Day0206 파일 업로드 =================
			@RequestParam(name="file",required = false) MultipartFile file )throws IllegalArgumentException { // MultipartFile: 첨부파일을 받을 대 사용하는 데이터 타입
		// 파일 정보 log로 찍기
		log.info("file.getName():{}",file.getName());
		log.info("file.getOriginalFilename():{}",file.getOriginalFilename());
		log.info("file.getSize():{}",file.getSize());
		// 파일 업로드 경로에 저장 // -> @value 로 해결(필드)
//		String uploadPath = "C:\\workspace\\JavaSpring\\post프로젝트_파일업로드";
//		file.transferTo(new File(uploadPath + file.getOriginalFilename()));
		
		// ---------- 사용자가 저장한 파일 명과 서버에 저장되는 파일명이 달라야함(파일명 중복문제) -> 파일이 저장되는 정보가 필요 (Model.Post.FileAttachment <c>) ---------- 
		
		// ===== 파일 업로드 끝 ====== 
		
		// log로 먼저 찍어보기
		log.info("post(savePost): {}", post);

		// 세션에서 로그인 정보를 가지고 와서 post객체의 user에 저장하기
		post.setUser(loginUser);
		// 사용자가 입력한 값이 아니라서 id, view, crateTime 은 default값으로 들어옴
		// 6 화인
		Post savedPost = postService.savePost(post, file); // 이 때 첨부파일이 있는지 확인 -> service의 savepost 수정
	
		log.info("savePost(savePost):{}", savedPost);

		return "redirect:/posts"; // redirect 사용(아직 list 안 만들어서 에러남)
	}

	// 5-4. 게시글 목록 가기 (조회)
	@GetMapping("posts") // 이 값이 다른 메서드랑 같아도 get과 post면 상관 없음
	public String listPosts(
			@SessionAttribute(name = "loginUser",required = false) User loginUser, //로그인 상태인지 확인
			Model model,
			@RequestParam(name= "page", defaultValue = "0" ) int page) {
			// 로그인 안되어 있으면 로그인 페이지로 가기
//		if (loginUser == null) {
//			return "redirect:/usesr/login";
//		}
		//목록보기, 글쓰기, 수정하기 삭제하기 모두 로그인 체크 해야함(수정 페이지로 갈 때도 체크 해야함) -> 
		
//		List<Post> posts = postService.getAllPosts();
		if(page <0) page =0;
		Page<Post> posts = postService.getAllPosts(page ,countPerPage);
												// page: 사용자가 선택할 페이지
		PageNavigator navi = new PageNavigator(countPerPage, pagePerGroup, posts.getTotalPages(), posts.getNumber(), posts.getTotalElements());
		log.info("post.user(listPosts):{}", posts);
		model.addAttribute("posts", posts);
		model.addAttribute("navi",navi);
		
//		
		return "posts/list";
	}

	// 게시글 조회
	@GetMapping("posts/{postId}")
	public String veiw(@PathVariable(name = "postId") Long postId, Model model) {
		Post findPost = postService.getPostById(postId);
		model.addAttribute("post", findPost);
		log.info("veiw:findpost : {}", findPost);
		return "posts/view";
	}

	// 게시글 삭제 : 로그인 하고 삭제
	@GetMapping("posts/remove/{postId}")
	public String removePost(
			// id와 비밀번호를 같이 갖고 와야한다
			@PathVariable(name = "postId") Long postId, @SessionAttribute(name = "loginUser") User loginUser

	) {
//			post.setUser(loginUser);
		// 삭제하려고 하는 게시글이 로그인 사용자가 작성한 글인지 확인
		Post findPost = postService.getPostById(postId);
		if (findPost == null || findPost.getUser().getId() != loginUser.getId()) {
			return "redirect:/posts";
		}
		postService.removePost(postId);
		return "redirect:/posts";
	}

	// 게시글 수정 =========================================
	//게시글 수정 이동 --------------------------------
	// get : 수정 대상 게시글 데이터를 뷰로 전달
	@GetMapping("posts/edit/{postId}")
	public String updatePost(
			@PathVariable(name = "postId") Long postId,
			Model model) {
		// 원래 입력한 택스트 보여주기
		Post findPost = postService.getPostById(postId);
		model.addAttribute("post", findPost);
		return "posts/edit";
	}

	// Post : 전달된 데이터를 받아 게시글을 수정한 뒤 저장
	@PostMapping("posts/edit/{postId}")
	public String updatePost(
			@SessionAttribute(name = "loginUser") User loginUser, // 로그인 정보 가지고 오기
			@PathVariable(name = "postId") Long postId, // {postId} 가지고 오기
			// 값 받이오는 방법 1 : param
//			@RequestParam(name = "title") String title,
//			@RequestParam(name = "content") String content
			// 값 받아오는 방법 2: modelatrribute
			@ModelAttribute Post updatePost
			
	) {
		//방법1
//		postService.updatePost(postId, title, content);
		
		//방법2
		// 수정하려는 게시글이 존재하고 로긍니 사용자와 게시글의 작성자가 같은지 확인
		Post post = postService.getPostById(postId);
		if (post.getUser().getId() == loginUser.getId()) {
			postService.updatePost(postId, updatePost.getTitle(), updatePost.getContent());
		}
		return "redirect:/posts"; // 수정 완료 후 게시글 목록으로 이동
	}
	
	// ==== Day 0207 파일 다운로드 =========
	
	@GetMapping("posts/{postId}/download/{fileAttachmentId}")
	// 파일 다운시 return 값은 resource타입
	public ResponseEntity<Resource> download(
			@PathVariable(name = "postId") Long postId,
			@PathVariable(name="fileAttachmentId") Long fileAttachmentId
			) throws MalformedURLException{
		Post post = postService.getPostById(postId);
		if(post == null) {
			return ResponseEntity.notFound().build(); 
 		}
		//첨부파일이 저장된 전체 경로값을 만든다
		String fullPath = uploadPath + "/" + post.getFileAttachment().getStoredFilename();
		UrlResource resource = new UrlResource("file:" + fullPath);
		
		//다운로드 되는 파일의 이름을 지정한다.
			// 1.인코딩하기(일본어와 한국어로 작성된 제목은 못 불러와서 
												// 사용자가 작성한 파일 이름 갖고 오기// 인코딩 형식 지정
		String encodedFilename = UriUtils.encode(post.getFileAttachment().getOriginalFilename(), StandardCharsets.UTF_8);
		
			// 2.헤더 정보에 들어갈 헤더 값을 만들기
		String contentDisposition = "attachment; filename=\""+  encodedFilename+"\"";
		
		return ResponseEntity.ok()
				.header("content-Disposition", contentDisposition)
				.body(resource);
		
		// 다음은 viewh.html 가서 링크 만들어 주기
		
	}

}