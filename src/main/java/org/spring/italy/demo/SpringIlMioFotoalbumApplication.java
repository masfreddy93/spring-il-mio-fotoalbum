package org.spring.italy.demo;

import java.util.Arrays;
import java.util.List;

import org.spring.italy.demo.pojo.Category;
import org.spring.italy.demo.pojo.Foto;
import org.spring.italy.demo.serv.CategoryServ;
import org.spring.italy.demo.serv.FotoServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringIlMioFotoalbumApplication implements CommandLineRunner {

	@Autowired FotoServ fotoServ;
	@Autowired CategoryServ categoryServ;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringIlMioFotoalbumApplication.class, args);
	}
	
	
	public void run(String... args) throws Exception{
		
	
		//FOTO
		//create some photos
		Foto f1 = new Foto("Foto 1", "desc1", ".....url", "beach", true);
		Foto f2 = new Foto("Foto 2", "desc2", ".....url", "mojito", false);
		Foto f3 = new Foto("Foto 3", "desc3", ".....url", null, true);
		
		//add to database
		fotoServ.save(f1);
		fotoServ.save(f2);
		fotoServ.save(f3);
		
		//read in terminal
		List<Foto> fotos = fotoServ.findAll();
		System.err.println(fotos);
		
		
		
		//CATEGORIES
		//create some categories
		Category c1 = new Category("Cinema");
		Category c2 = new Category("Sport");
		Category c3 = new Category("Musica");
		Category c4 = new Category("Tempo libero");
		
		//add to database
		categoryServ.save(c1);
		categoryServ.save(c2);
		categoryServ.save(c3);
		categoryServ.save(c4);
		
		//read in terminal
		List<Category> categories = categoryServ.findAll();
		System.err.println(categories);
		
		
		
		//MANY-TO-MANY fotos-categories
		//relaz 1
		List<Category> catList1 = Arrays.asList(new Category[] {
				c1,
				c3,
				c4
		});
		Foto f4 = new Foto("Foto 4", "desc 4", "....url", "tag4", true, catList1);
		fotoServ.save(f4);
		System.err.println(f4);
		System.err.println(f4.getCategories());
		
		//relaz 2
		List<Category> catList2 = Arrays.asList(new Category[] {
				c2,
				c4
		});
		Foto f5 = new Foto("Foto 5", "desc 5", "....url", "tag5", false, catList2);
		fotoServ.save(f5);
		System.err.println(f5);
		System.err.println(f5.getCategories());
	}
}
