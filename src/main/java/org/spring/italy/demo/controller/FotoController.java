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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/fotos")
public class FotoController {
	
	@Autowired FotoServ fotoServ;
	@Autowired CategoryServ categoryServ;

	@GetMapping
	public String getIndex(Model model) {
		
		List<Foto> fotos = fotoServ.findAll();
		model.addAttribute("fotos", fotos);
		
		return "fotos/index";
	}
	
	@GetMapping("/foto/{id}")
	public String showFoto(@PathVariable("id") int id, Model model) {
		
		Foto foto = fotoServ.findById(id);
		model.addAttribute("foto", foto);
		
		return "fotos/foto";
	}
	
	
	@GetMapping("/create")
	public String createFoto(Model model) {
		
		Foto f = new Foto();
		model.addAttribute("foto", f);
		List<Category> categories = categoryServ.findAll();
		model.addAttribute("categories", categories);
		
		return "fotos/foto-create";
	}
	
		
	@PostMapping("/create")
	public String storeFoto(@Valid Foto foto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		if(bindingResult.hasErrors()) {
						
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			
			return "redirect:/fotos/create";
		}

		
		try {
			
			fotoServ.save(foto);
			
		} catch (Exception e) {
			
			final String msg = e.getMessage();
			redirectAttributes.addFlashAttribute("catchError", msg);
			
			return "redirect:/fotos/create";
		}
		
		return "redirect:/fotos";
	}
	
	
	@GetMapping("/update/{id}")
	public String editFoto(@PathVariable("id") int id, Model model) {
		
		Foto foto = fotoServ.findById(id);
		model.addAttribute("foto", foto);
		List<Category> categories = categoryServ.findAll();
		model.addAttribute("categories", categories);
		
		return "fotos/foto-update";
	}
	
	@PostMapping("/store")
	public String updateFoto(@Valid Foto foto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		if(bindingResult.hasErrors()) {
			
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			
			return "redirect:/fotos/update/" + foto.getId();
			
		}

		
		try {
			
			fotoServ.save(foto);
			
		} catch (Exception e) {
			
			final String msg = e.getMessage();
			redirectAttributes.addFlashAttribute("catchError", msg);
			
			return "redirect:/fotos/update/" + foto.getId();
		}
		
		return "redirect:/fotos";
	}
	
	
	
	@GetMapping("/delete/{id}")
	public String deleteFoto(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
		
		try {
			
			Foto foto = fotoServ.findById(id);
			fotoServ.delete(foto);
			
		} catch(Exception e) {
			
			final String msg = e.getMessage();
			
			System.err.println(msg);
			redirectAttributes.addFlashAttribute("catchError", msg);
		}
		
		return "redirect:/fotos";
	}
	
	
	@GetMapping("/search")
	public String getSearchFotoByName(Model model, @RequestParam(name = "q", required = false) String query) {
		
		List<Foto> fotos = null;
		if (query == null) {
			
			fotos = fotoServ.findAll();
			
		} else {
			
			fotos = fotoServ.findByTitleOrTag(query, query);
		}
		
		
		model.addAttribute("fotos", fotos);
		model.addAttribute("query", query);
		
		
		return "fotos/foto-search";
	}
	
	
	
}
