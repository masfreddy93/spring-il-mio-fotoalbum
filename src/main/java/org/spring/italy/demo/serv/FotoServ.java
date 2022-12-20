package org.spring.italy.demo.serv;

import java.util.List;

import org.spring.italy.demo.pojo.Foto;
import org.spring.italy.demo.repo.FotoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FotoServ {
	
	@Autowired FotoRepo fotoRepo;
	
	public void save(Foto foto) {
		
		fotoRepo.save(foto);
	}
	public void delete(Foto foto) {
		
		fotoRepo.delete(foto);
	}
	public Foto findById(int id) {
		
		return fotoRepo.findById(id).get();
	}
	public List<Foto> findAll(){
		
		return fotoRepo.findAll();
	}
}
