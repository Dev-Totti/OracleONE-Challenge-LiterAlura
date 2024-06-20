package com.tottidev.literalura.repository;

import com.tottidev.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro, Long> {

}
