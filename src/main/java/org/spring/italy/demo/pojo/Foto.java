package org.spring.italy.demo.pojo;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Table
@Entity
public class Foto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull(message = "title can't be null")
	@NotBlank(message = "title can't be null")
	@Column(unique = true)
	private String title;
	
	@Lob
	private String description;
	
	@NotNull
	@NotBlank (message = "url can't be null")
	private String url;
	
	@Column
	private String tag;
	
	@NotNull(message = "visibility must be define")
	private boolean visible;
	
	@ManyToMany
	@JsonIgnore
	private List<Category> categories;
	
	@OneToMany(mappedBy = "foto", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<Comment> comments;

	
	@Column
	private LocalDateTime dateTime;
	
	
	public Foto() {}
	public Foto(String title, String description, String url, String tag, boolean visible) {
		
		setTitle(title);
		setDescription(description);
		setUrl(url);
		setTag(tag);
		setVisible(visible);
		setDateTime(LocalDateTime.now());
	}
	public Foto(String title, String description, String url, String tag, boolean visible, List<Category> categories) {
		
		this(title, description, url, tag, visible);
		setCategories(categories);
		setDateTime(LocalDateTime.now());
	}
	public Foto(String title, String description, String url, String tag, boolean visible, List<Category> categories, List<Comment> comments) {
		
		this(title, description, url, tag, visible, categories);
		setComments(comments);
		setDateTime(LocalDateTime.now());
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	public List<Category> getCategories() {
		return categories;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	
	public void addCategories(Category category) {
		
		boolean finded = false;
		for (Category i : getCategories()) 
			if (i.getId() == category.getId())
				finded = true;
		
		if (!finded) 
			getCategories().add(category);
	}
	
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	
	@Override
	public String toString() {
		
		return "(" + getId() + ")"
				+ " " + getTitle();
	}
	
	
	

}
