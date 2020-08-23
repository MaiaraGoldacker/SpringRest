package com.example.demo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.domain.model.Comentario;

public interface ComentarioRepository  extends JpaRepository<Comentario, Long>{

}
