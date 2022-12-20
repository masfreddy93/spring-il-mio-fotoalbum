package org.spring.italy.demo.repo;

import java.util.List;

import org.spring.italy.demo.pojo.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoRepo extends JpaRepository<Foto, Integer>{
	
	public List<Foto> findByTitleContainingIgnoreCase(String title);
}
