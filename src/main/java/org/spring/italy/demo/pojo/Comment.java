package org.spring.italy.demo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = true)
	private String author;
	
	@NotNull(message = "Text can't be null")
	@NotBlank(message = "Text can't be empty")
	@Lob
	private String text;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	@JsonIgnore
	private Foto foto;
	
	public Comment() {	}
	public Comment(String author, String text) {
		
		setAuthor(author);
		setText(text);
	}
	public Comment(String author, String text, Foto foto) {
		
		this(author, text);
		setFoto(foto);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Foto getFoto() {
		return foto;
	}
	public void setFoto(Foto foto) {
		this.foto = foto;
	}
	
	
	@Override
	public String toString() {
		
		return "(" + getId() + ")"
				+ " " + getAuthor()
				+ ": " + getText();
	}
}
