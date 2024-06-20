package com.tottidev.literalura.repository;

import com.tottidev.literalura.model.Idioma;
import com.tottidev.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByTituloContainingIgnoreCase(String titulo);
    List<Libro> findByIdioma(Idioma idioma);
}
