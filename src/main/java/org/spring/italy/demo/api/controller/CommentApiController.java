package org.spring.italy.demo.api.controller;

import java.util.List;

import org.spring.italy.demo.pojo.Comment;
import org.spring.italy.demo.pojo.Foto;
import org.spring.italy.demo.serv.CommentServ;
import org.spring.italy.demo.serv.FotoServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/1/comments")
@CrossOrigin("*")
public class CommentApiController {

	@Autowired FotoServ fotoServ;
	@Autowired CommentServ commentServ;
	
	@GetMapping("/by/foto/{id}")
	public List<Comment> getCommentsByFoto(@PathVariable("id") int id){
		
		Foto foto = fotoServ.findById(id);
		List<Comment> comments = foto.getComments();
		
		return comments;
	}
	
	@PostMapping("/create/{id}")
	public Comment createComment(
			@PathVariable("id") int id,
			@Valid @RequestBody Comment comment) {
		
		Foto foto = fotoServ.findById(id);
		Comment newComment = new Comment(comment.getAuthor(), comment.getText(), foto);
		
		commentServ.save(newComment);
		
		System.err.println("-------");
		System.err.println("CREATED COMMENT");
		System.err.println(newComment);
		
		return newComment;
	}
}
