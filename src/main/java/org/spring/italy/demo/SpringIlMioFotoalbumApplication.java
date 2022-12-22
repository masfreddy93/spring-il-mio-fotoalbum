package org.spring.italy.demo;

import java.util.Arrays;
import java.util.List;

import org.spring.italy.demo.pojo.Category;
import org.spring.italy.demo.pojo.Comment;
import org.spring.italy.demo.pojo.Foto;
import org.spring.italy.demo.pojo.Role;
import org.spring.italy.demo.pojo.User;
import org.spring.italy.demo.serv.CategoryServ;
import org.spring.italy.demo.serv.CommentServ;
import org.spring.italy.demo.serv.FotoServ;
import org.spring.italy.demo.serv.RoleServ;
import org.spring.italy.demo.serv.UserServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringIlMioFotoalbumApplication implements CommandLineRunner {

	@Autowired FotoServ fotoServ;
	@Autowired CategoryServ categoryServ;
	@Autowired RoleServ roleServ;
	@Autowired UserServ userServ;
	@Autowired CommentServ commentServ;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringIlMioFotoalbumApplication.class, args);
	}
	
	
	public void run(String... args) throws Exception{
		
	
		//FOTO
		//create some photos
		Foto f1 = new Foto("Foto 1", "desc1", 
				"https://edu.inaf.it/wp-content/uploads/2018/02/sole_senza_macchie.jpg", 
				"#insta #yo #fun", true);
		Foto f2 = new Foto("Foto 2", "desc2", ".....url", "#takethat #enjoy", false);
		Foto f3 = new Foto("Foto 3", "desc3", 
				"https://www.nieddittas.it/wp-content/uploads/2021/12/qual-e-il-mare-piu-bello-della-sardegna.jpg", 
				null, true);
		
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
		Foto f4 = new Foto("Foto 4", "desc 4", 
				"https://media-assets.wired.it/photos/6336eae909f48deb0c26a936/16:9/w_2560%2Cc_limit/ganapathy-kumar-ve_uN9V8xqU-unsplash.jpg", 
				"#saywhat #mylife", true, catList1);
		fotoServ.save(f4);
		System.err.println(f4);
		System.err.println(f4.getCategories());
		
		//relaz 2
		List<Category> catList2 = Arrays.asList(new Category[] {
				c2,
				c4
		});
		Foto f5 = new Foto("Foto 5", "desc 5", 
				"https://static2-viaggi.corriereobjects.it/wp-content/uploads/2022/05/iStock-1174299397-1080x720.jpg", 
				"#cmon #go #run", true, catList2);
		fotoServ.save(f5);
		System.err.println(f5);
		System.err.println(f5.getCategories());
		
		

		//AUTENTICAZIONE
		//ruoli
		Role admin = new Role("ADMIN");
		
		roleServ.save(admin);
		
		List<Role> roles = roleServ.findAll();
		System.err.println("-----------------------");
		System.err.println("Ruoli");
		System.err.println(roles);
		
		
		//utenti
		User u1 = new User("admin", "{noop}admin", admin);
				
		userServ.save(u1);

		List<User> users = userServ.findAll();
		System.err.println("-----------------------");
		System.err.println("Utenti");
		System.err.println(users);
		
		
		
//		//COMMENTS
		
		//prova
//		Comment coProva = new Comment("Carla", "prova prova");
//		commentServ.save(coProva);
		
		//create
		Comment co1 = new Comment("anonymous", "nice pic!", f5);
		Comment co2 = new Comment(null, "yo", f1);
		Comment co3 = new Comment("anonymous", "cool", f3);
		Comment co4 = new Comment("carla", "awesome", f3);
		Comment co5 = new Comment("roby", "are you crazy?", f5);
		
		//add to DB
		commentServ.save(co1);
		commentServ.save(co2);
		commentServ.save(co3);
		commentServ.save(co4);
		commentServ.save(co5);
		
		//read in terminal
		List<Comment> comments = commentServ.findAll();
		System.err.println(comments);
	}
}
