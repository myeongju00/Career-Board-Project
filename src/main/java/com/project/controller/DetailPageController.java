package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.project.model.*;
import com.project.model.response.LoginUserResponse;

import jakarta.servlet.http.*;

@Controller
public class DetailPageController {

	@Autowired
	private PostDao postDao;
	@Autowired
	private PostSO postSO;
	@Autowired
	private ReplySO replySO;
	@Autowired
	private ReplyDao replyDao;


	@GetMapping("/detailPageProcess")
	public String detailPageView(@RequestParam long post_id, @RequestParam long commentCount,Model model) {
		PostDO postInfo = postDao.getPostById(post_id);
		model.addAttribute("postInfo", postInfo);
		
		List<ReplyDO> repliesList = replySO.getRepliesByPostId(post_id);
		model.addAttribute("repliesList", repliesList);

		model.addAttribute("commentCount", commentCount);

		model.addAttribute("postList", postSO.updateViewCount(post_id).getPostList());
		return "detailPage";
	}
	
	@PostMapping("/submitReply")
	public String submitReply(ReplyDO reply, @RequestParam long commentCount, HttpSession session, Model model) {

		LoginUserResponse auth = (LoginUserResponse) session.getAttribute("auth");
		String user_id = auth.getUser_id();
		if(user_id == null) {
			return "redirect:/login";
		}
		else {
			replyDao.insertReply(reply);

			return "redirect:/detailPageProcess?post_id=" + reply.getPost_id() + "&commentCount=" + commentCount;
		}
		
	}
	
//	@GetMapping("/detailTest")
//	public String detailPageView() {
//		return "detailPage";
//	}
	
	
}
