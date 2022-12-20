package org.spring.italy.demo.controller;

import java.util.List;

import org.spring.italy.demo.pojo.Category;
import org.spring.italy.demo.pojo.Foto;
import org.spring.italy.demo.serv.CategoryServ;
import org.spring.italy.demo.serv.FotoServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {

	@Autowired CategoryServ categoryServ;
	@Autowired FotoServ fotoServ;
	
	@GetMapping
	public String getIndex(Model model) {
		
		List<Category> categories = categoryServ.findAll();
		model.addAttribute("categories", categories);
		
		return "categories/index";
	}
	
	@GetMapping("/create")
	public String createCategory(Model model) {
		
		Category c = new Category();
		model.addAttribute("category", c);
		List<Foto> fotos = fotoServ.findAll();
		model.addAttribute("fotos", fotos);
		
		return "categories/category-create";
	}
	
		
	@PostMapping("/create")
	public String storeCategory(@Valid Category category, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		List<Foto> fotos = category.getFotos();
		for(Foto foto : fotos)
			foto.getCategories().add(category);
		
		
		if(bindingResult.hasErrors()) {
						
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			
			return "redirect:/categories/create";
		}

		
		try {
			
			categoryServ.save(category);
			
		} catch (Exception e) {
			
			final String msg = e.getMessage();
			redirectAttributes.addFlashAttribute("catchError", msg);
			
			return "redirect:/categories/create";
		}
		
		return "redirect:/categories";
	}
	
	
	@GetMapping("/update/{id}")
	public String editCategory(@PathVariable("id") int id, Model model) {
		
		Category c = categoryServ.findById(id);
		model.addAttribute("category", c);
		List<Foto> fotos = fotoServ.findAll();
		model.addAttribute("fotos", fotos);
		
		return "categories/category-update";
	}
	
	@PostMapping("/store/{id}")
	public String updateCategory(
			@PathVariable("id") int id,
			@Valid Category category, 
			BindingResult bindingResult, 
			RedirectAttributes redirectAttributes) {
		
		
		Category oldCategory = categoryServ.findById(id);
		
		for(Foto foto : oldCategory.getFotos())
			foto.getCategories().remove(oldCategory);
		
		for(Foto foto : category.getFotos())
			foto.addCategories(category);
		
		
		if(bindingResult.hasErrors()) {
			
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			
			return "redirect:/categories/update/" + category.getId();
			
		}

		
		try {
			
			categoryServ.save(category);
			
		} catch (Exception e) {
			
			final String msg = e.getMessage();
			redirectAttributes.addFlashAttribute("catchError", msg);
			
			return "redirect:/categories/update/" + category.getId();
		}
		
		return "redirect:/categories";
	}
	
	
	@GetMapping("/delete/{id}")
	public String deleteIngrediente(@PathVariable("id") int id,
			RedirectAttributes redirectAttributes) {
		
		try {
			
			Category c = categoryServ.findById(id);
			categoryServ.delete(c);			
			
		} catch (Exception e) {
			
			final String msg = e.getMessage();
			
			System.err.println(msg);
			redirectAttributes.addFlashAttribute("catchError", msg);
		}
		
		
		return "redirect:/categories";
	}
	
}
