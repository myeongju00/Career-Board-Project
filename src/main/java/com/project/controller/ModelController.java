package com.project.controller;

import com.project.exception.UnExpectedAccessException;
import com.project.model.*;
import com.project.model.response.LoginUserResponse;

import com.project.model.ReplyDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.model.PostDao;
import com.project.model.PostSO;
import com.project.model.ReplyDO;
import com.project.model.ReplyDao;
import com.project.model.ReplySO;
import com.project.model.UserSO;
import com.project.model.UserDO;
import com.project.model.UserDao;
import com.project.model.response.LoginUserResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class ModelController {
	
	@Autowired
	private UserSO userSO;
	
	@Autowired
	private PostDao postDao;
	
	@Autowired
	private PostSO postSO;	
	
	@Autowired
	private ReplyDao replyDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ReplySO replySO;

	@GetMapping("/changePasswd")
	public String changePasswdHandler() {
		return "changePasswd";
	}
	
	@PostMapping("/changePasswdProcess")
	public String changePasswdProcessHandler(UserDO userDO, HttpSession session) {
		try {
			LoginUserResponse user = (LoginUserResponse)session.getAttribute("auth");
			String user_id = user.getUser_id();
			userSO.changePassword(user_id, userDO.getOldPasswd(), userDO.getNewPasswd());
			return "redirect:/main";
		}
		catch(Exception e) {
			return "redirect:/changePasswd";
		}

	}
	
	@GetMapping("/postForm")	 
	public String postFormHandler(Model model) {
		model.addAttribute("hotPostList", postDao.hotPost());
		return "postForm";
	}
	
	@GetMapping("/postModify")
	public String postModifyHandler(@RequestParam(value="post_id") long post_id, HttpSession session, Model model) {
		LoginUserResponse user = (LoginUserResponse)session.getAttribute("auth");
		String user_id = user.getUser_id();
		PostDO post = postDao.getPostById(post_id);
		if(user_id != null && user_id.equals(post.getUser_id())) {
			model.addAttribute("postInfo", post);
			model.addAttribute("hotPostList", postDao.hotPost());
			return "postForm";	
		}
		return "redirect:/detailPageProcess?post_id=" + post_id + "&commentCount=0";
		
	}
	
	@PostMapping("/postFormProcess")
	public String postFormProcessHandler(PostDO postDO, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(session != null) {
			LoginUserResponse user = (LoginUserResponse)session.getAttribute("auth");
			postDao.insertPost(postDO, user.getUser_id());
			int postCount = postSO.countPostCountByUserId(user.getUser_id()); 
	        session.setAttribute("postCount", postCount);
		}
		return "redirect:/main";
	}
	

	@PostMapping("/getUserID")
	public String getUserID(@RequestParam(value = "name") String name, @RequestParam(value = "email") String email) {
		if(this.userSO.checkIsUserID(name, email) == false) {
			return "redirect:/findID";
		}
		
		return "/findID";
	}
	
	@PostMapping("/checkAgreement")
	public String checkAgreement(@RequestParam(value = "checkBox") boolean isChecked) {
		if(isChecked == true) {
			return "/signup";
		}
		
		return "/agreement";
	}
	
	@PostMapping("/findUserID")
	public String findUserID(String name, String email, Model model) {
		String id = this.userDao.findID(name, email);
		
		if(id == null || id.equals("")) {
			return "/findID";
		}
			
		model.addAttribute("user_id", id);
		
		return "/findID";
	}
	
	

	@PostMapping("/postUpdate")
	public String postUpdateHandler(PostDO postDO, HttpServletRequest request) {
		postDao.updatePost(postDO);
		return "redirect:/detailPageProcess?post_id=" + postDO.getPost_id() + "&commentCount=0";
	}
	

	@GetMapping("/findID")
	public String findIDHandler() {
		return "findID";
	}
	
	@GetMapping("/agreement")
	public String agreementHandler() {
		return "agreement";
	}
	
	@GetMapping("/postDelete")
	public String postDeleteHandler(@RequestParam(value="post_id") long post_id, HttpSession session) {
		LoginUserResponse user = (LoginUserResponse)session.getAttribute("auth");
		String user_id = user.getUser_id();
		try {
			postSO.deletePostService(post_id, user_id);	
			int postCount = postSO.countPostCountByUserId(user.getUser_id()); 
	        session.setAttribute("postCount", postCount);
			return "redirect:/main";
		}
		catch(UnExpectedAccessException e) {
			return "detailPageProcess?post_id=" + post_id + "&commentCount=0";
		}
	}
	
	@GetMapping("/replyModify")
	public String replyModifyHandler(ReplyDO reply, HttpSession session) {
		LoginUserResponse user = (LoginUserResponse)session.getAttribute("auth");
		String user_id = user.getUser_id();
		ReplyDO replyDO = replyDao.getReplyById(reply.getReply_id());
		if(user_id != null && user_id.equals(replyDO.getUser_id())) {
			return "redirect:/detailPageProcess?post_id=" + reply.getPost_id() + "&commentCount=0&reply_id=" + replyDO.getReply_id();	
		}
		return "redirect:/detailPageProcess?post_id=" + reply.getPost_id() + "&commentCount=0";
	}
	
	@PostMapping("/replyUpdate")
	public String replyUpdateHandler(ReplyDO reply, HttpSession session, @RequestParam(value="commentCount") long commentCount) {
		LoginUserResponse auth = (LoginUserResponse) session.getAttribute("auth");
		
		if(auth.getUser_id() != null && auth.getUser_id().equals(reply.getUser_id())) {
			replyDao.updateReply(reply);
			return "redirect:/detailPageProcess?post_id=" + reply.getPost_id() + "&commentCount=" + commentCount;
		}
		return "redirect:/detailPageProcess?post_id=" + reply.getPost_id() + "&commentCount=" + commentCount;
	}		
	
	
	@GetMapping("/replyDelete")
	public String replyDeleteHandler(ReplyDO reply, HttpSession session) {
		LoginUserResponse user = (LoginUserResponse)session.getAttribute("auth");
		String user_id = user.getUser_id();
		try {
			replySO.deleteReplyService(reply.getReply_id(), user_id);
			int replyCount = replySO.countReplyCountByUserId(user.getUser_id());
			session.setAttribute("replyCount", replyCount);
		}
		catch(UnExpectedAccessException e) {
			e.printStackTrace();
		}
		return "redirect:/detailPageProcess?post_id=" + reply.getPost_id() + "&commentCount=0";
	}
	
}
