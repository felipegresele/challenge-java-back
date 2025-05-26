package com.mottu.mottu.repository;

import com.mottu.mottu.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

//Qual entidade vai usar e o tipo do id da entidade
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
