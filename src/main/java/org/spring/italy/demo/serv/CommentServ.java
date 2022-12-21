package org.spring.italy.demo.serv;

import java.util.List;

import org.spring.italy.demo.pojo.Comment;
import org.spring.italy.demo.repo.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServ {

	@Autowired CommentRepo commentRepo;
	
	public void save(Comment comment) {
		
		commentRepo.save(comment);
	}
	public List<Comment> findAll(){
		
		return commentRepo.findAll();
	}
}

