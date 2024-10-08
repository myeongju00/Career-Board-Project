package com.project.model;

import com.project.model.response.PageResponse;
import com.project.model.response.Post;
import com.project.exception.*;
import com.project.model.response.PostMainResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostSO {

	@Autowired
	private PostDao postDao;
	
	@Autowired
	private ReplyDao replyDao;
	
	public PostSO(PostDao postDao) {
		this.postDao = postDao;
	}

	public PostMainResponse getAllPost() {
		return new PostMainResponse(postDao.selectAllPost());

	}

	public PageResponse<Post> getPaginatedPost(int page) {
		PageResponse<PostDO> postPage = postDao.selectPaginatedPost(page);

		List<Post> postList = postPage.getContent().stream().map(Post::new).toList();

		return new PageResponse<>(
				postList,
				postPage.getCurrentPage(),
				postPage.getSize(),
				postPage.getTotalElements()
		);
	}

//	public PostDO getPostById(long post_id) {
//		return postDao.selectPostById(post_id);
//	}

	public PostMainResponse searchPosition(String position) {
		List<PostDO> search = postDao.searchPosition(position);
		return new PostMainResponse(search);
	}


	public PostMainResponse updateViewCount(long postId) {
		List<PostDO> postList = postDao.updateViewCount(postId);

		return new PostMainResponse(postList);
	}
	
    @Transactional
	public void deletePostService(long post_id, String user_id) {
		PostDO post = postDao.getPostById(post_id);

		if(user_id != null && user_id.equals(post.getUser_id())) {
			replyDao.deleteReplyByPostId(post_id);
			postDao.deletePost(post_id);
		} else {
			throw new UnExpectedAccessException();
		}
	}

	public List<PostDO> getHotPost() {
		return postDao.hotPost();
	}

	public int countPostCountByUserId(String user_id) {
		return postDao.countPostByUserId(user_id);
	}

	public PageResponse<Post> searchPaginatedPost(String keyword, int page) {
		PageResponse<PostDO> postPage = postDao.searchPaginatedPost(keyword, page);

		List<Post> postList = postPage.getContent().stream().map(Post::new).toList();

		return new PageResponse<>(
				postList,
				postPage.getCurrentPage(),
				postPage.getSize(),
				postPage.getTotalElements()
		);
	}

	public PageResponse<Post> searchPositionPaginatedPost(String position, int page) {
		PageResponse<PostDO> postPage = postDao.searchPositionPaginatedPost(position, page);

		List<Post> postList = postPage.getContent().stream().map(Post::new).toList();

		return new PageResponse<>(
				postList,
				postPage.getCurrentPage(),
				postPage.getSize(),
				postPage.getTotalElements()
		);
	}
}
