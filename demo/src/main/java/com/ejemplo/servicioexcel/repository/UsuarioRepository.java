package com.ejemplo.servicioexcel.repository;
import com.ejemplo.servicioexcel.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
