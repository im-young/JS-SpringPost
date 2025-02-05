package com.example.post.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.post.model.posts.Post;
import com.example.post.model.users.User;
import com.example.post.service.PostService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//5. 컨트롤러 만들기
@RequiredArgsConstructor // 의존성 주입
@Controller
@Slf4j

public class PostController {
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
			@ModelAttribute Post post, @SessionAttribute(name = "loginUser", required = false) User loginUser) {
		// log로 먼저 찍어보기
		log.info("post(savePost): {}", post);

		// 세션에서 로그인 정보를 가지고 와서 post객체의 user에 저장하기
		post.setUser(loginUser);
		// 사용자가 입력한 값이 아니라서 id, view, crateTime 은 default값으로 들어옴
		// 6 화인
		Post savedPost = postService.savePost(post);
		log.info("savePost(savePost):{}", savedPost);

		return "redirect:/posts"; // redirect 사용(아직 list 안 만들어서 에러남)
	}

	// 5-4. 게시글 목록 가기 (조회)
	@GetMapping("posts") // 이 값이 다른 메서드랑 같아도 get과 post면 상관 없음
	public String listPosts(
			@SessionAttribute(name = "loginUser") User loginUser, //로그인 상태인지 확인
			Model model) {
			// 로그인 안되어 있으면 로그인 페이지로 가기
//		if (loginUser == null) {
//			return "redirect:/usesr/login";
//		}
		//목록보기, 글쓰기, 수정하기 삭제하기 모두 로그인 체크 해야함(수정 페이지로 갈 때도 체크 해야함) -> 
		
		List<Post> posts = postService.getAllPosts();
		model.addAttribute("posts", posts);
//		log.info("post.user(listPosts):{}",posts.user);
//		
		return "posts/list";
	}

	// 게시글 조회
	@GetMapping("posts/{postId}")
	public String veiw(@PathVariable(name = "postId") Long postId, Model model) {
		Post findPost = postService.getPostById(postId);
		model.addAttribute("post", findPost);
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

}