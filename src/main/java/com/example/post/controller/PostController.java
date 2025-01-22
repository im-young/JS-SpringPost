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
	@GetMapping(path ="posts/create") // 링크로 클릭해서 이동하는 것은 get방식
	public String createPostForm(
			/*세선을 받아올 수 있는 방법3가지 1. servlet 2. http 3. session 어노테이션
			 * 1.  reqest객체안에 세션이 들어있음 : HttpServletRequest request
			 * 2.HttpSession session*/
			// 3. 세션에 저장된 데이터를 조회한다./ required : 세선에 null이라도 에러 나지 않게 하기 위해서
			@SessionAttribute(name="loginUser" ,required = false) User loginUser){
	// 사용자가 로그인 했는지 체크
	log.info("loginUser:{}",loginUser);
	if(loginUser == null) {
	// 로그인을 하지 않았으면 로그인 페이지로 리다이렉트
		return "redirect:/users/login";
		}
	// 게시글 작성 페이지의 뷰 이름을 리턴
		return "posts/create";
	}
	public String createPostForm() {
		//게시글 작성 페이지의 뷰 이름을 리턴
		return "posts/create";
	}
	//5-3. 게시글 등록
	@PostMapping("posts")
	public String savePost(
//			Model model,
			@ModelAttribute Post post,
			@SessionAttribute(name="loginUser" ,required = false) User loginUser
			) {
		//log로 먼저 찍어보기
		log.info("post(savePost): {}",post);
		
		//세션에서 로그인 정보를 가지고 와서 post객체의 user에 저장하기
		 post.setUser(loginUser);
		//사용자가 입력한 값이 아니라서 id, view, crateTime 은 default값으로 들어옴
		//6 화인
		Post savedPost = postService.savePost(post);
		log.info("savePost(savePost):{}",savedPost);
		
//		model.addAttribute("posts", savedPost);
		
		return "redirect:/posts"; //redirect 사용(아직 list 안 만들어서 에러남)
	}
	//5-4. 게시글 목록 가기 (조회)
	@GetMapping("posts") // 이 값이 다른 메서드랑 같아도 get과 post면 상관 없음
	public String listPosts(Model model) {
		List<Post> posts = postService.getAllPosts();
		model.addAttribute("posts", posts);
//		log.info("post.user(listPosts):{}",posts.user);
//		
		return "posts/list";
	}
	// 게시글 조회
		@GetMapping("posts/{postId}")
		public String veiw(
				@PathVariable (name = "postId") Long postId,
				Model model) {
			Post findPost = postService.getPostById(postId);
			model.addAttribute("post", findPost);
			return "posts/view";
		}

		//게시글 삭제 : 로그인 하고 삭제
		@GetMapping("posts/remove/{postId}")
		public String removePost(
				//id와 비밀번호를 같이 갖고 와야한다
				@PathVariable(name = "postId")Long postId,
				@SessionAttribute(name="loginUser") User loginUser
				) {
			
			// 삭제하려고 하는 게시글이 로그인 사용자가 작성한 글인지 확인
			Post findPost = postService.getPostById(postId);
			if(findPost == null || findPost.getUser().getId() != loginUser.getId()) {
				return "redirect:/posts";
			}
			postService.removePost(postId);
			return "redirect:/posts";
		}
}


