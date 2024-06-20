package com.tottidev.literalura.repository;

import com.tottidev.literalura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByYearBirthLessThanAndYearDeathGreaterThan(int yearBirth, int yearDeath);

    List<Author> findByNameContainingIgnoreCase(String nombre);
}
