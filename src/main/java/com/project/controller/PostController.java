package com.project.controller;

import com.project.model.PostDao;
import com.project.model.PostSO;
import com.project.model.ReplySO;
import com.project.model.response.PageResponse;
import com.project.model.response.Post;
import com.project.model.response.PostMainResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostController {

    private final PostSO postSO;
    private final ReplySO replySO;

    @Autowired
    public PostController(PostSO postSO, ReplySO replySO) {
        this.postSO = postSO;
        this.replySO = replySO;
    }

    @GetMapping("/main")
    public String mainPagingHandler(@RequestParam(value = "postpageCount", defaultValue = "1") int postpageCount,
                                    Model model) {
        PageResponse<Post> postList = postSO.getPaginatedPost(postpageCount);
        postList.getContent().forEach(post -> {
            post.setReply_count(replySO.getReplyCount(post.getPost_id()));
        });
        model.addAttribute("hotPostList", postSO.getHotPost());
        model.addAttribute("postList", postList.getContent());
        model.addAttribute("postpageCount", postpageCount);
        model.addAttribute("totalPages", postList.getTotalPages());
        return "/main";
    }

    @GetMapping("/search")
    public String searchPost(String keyword, @RequestParam(value = "postpageCount", defaultValue = "1") int postpageCount, Model model) {
        PageResponse<Post> postList = postSO.searchPaginatedPost(keyword, postpageCount);
        postList.getContent().forEach(post -> {
            post.setReply_count(replySO.getReplyCount(post.getPost_id()));
        });

        model.addAttribute("keyword", keyword);

        model.addAttribute("hotPostList", postSO.getHotPost());
        model.addAttribute("postList", postList.getContent());
        model.addAttribute("postpageCount", postpageCount);
        model.addAttribute("totalPages", postList.getTotalPages());

        return "/main";
    }

    @GetMapping("/search-position")
    public String searchPosition(String position,
                                 @RequestParam(value = "postpageCount", defaultValue = "1") int postpageCount,
                                 Model model) {
        PageResponse<Post> postList = postSO.searchPositionPaginatedPost(position, postpageCount);

        postList.getContent().forEach(post -> {
            post.setReply_count(replySO.getReplyCount(post.getPost_id()));
        });

        model.addAttribute("keyword", position);
        model.addAttribute("postList", postList.getContent());
        model.addAttribute("totalPages", postList.getTotalPages());
        model.addAttribute("postpageCount", postpageCount);
        return "/main";
    }

}
