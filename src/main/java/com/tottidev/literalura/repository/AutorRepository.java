package com.tottidev.literalura.repository;

import com.tottidev.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findByYearNacimientoLessThanAndYearFallecimientoGreaterThan(int yearNacimiento, int yearFallecimiento);

    List<Autor> findByNombreContainingIgnoreCase(String nombre);
}
