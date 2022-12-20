package org.spring.italy.demo.pojo;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull(message = "Name can't be null")
	@NotBlank(message = "Name can't be null")
	private String name;
	
	@ManyToMany(mappedBy = "categories")
	private List<Foto> fotos;
	
	public Category() {}
	public Category(String name) {
		
		setName(name);
	}
	public Category(String name, List<Foto> fotos) {
		
		this(name);
		setFotos(fotos);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Foto> getFotos() {
		return fotos;
	}
	public void setFotos(List<Foto> fotos) {
		this.fotos = fotos;
	}
	
	//metodo per evitare che alla rimozione della categoria venga eliminata anche la foto
	@PreRemove
	private void removeCategoriesFromFotos() {
	    for (Foto f : getFotos())
	        f.getCategories().remove(this);
	}
	
	
	@Override
	public String toString() {
		
		return "(" + getId() + ")"
				+ "" + getName();
	}
	
}
