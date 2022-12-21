package org.spring.italy.demo.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.spring.italy.demo.pojo.Foto;
import org.spring.italy.demo.serv.FotoServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1/fotos")
@CrossOrigin("*")
public class FotoApiController {
	
	@Autowired FotoServ fotoServ;
	
	@GetMapping("/all")
	public List<Foto> getFotos(){
		
		List<Foto> fotos = fotoServ.findAll();
		List<Foto> visibleFotos = new ArrayList<>();
		
		for(Foto foto : fotos) {
			
			if(foto.isVisible())
				visibleFotos.add(foto);
		}
		
		return visibleFotos;
	}
}
