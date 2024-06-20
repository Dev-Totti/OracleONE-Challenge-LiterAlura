package com.tottidev.literalura.repository;

import com.tottidev.literalura.model.Book;
import com.tottidev.literalura.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByLanguage(Language language);
    List<Book> findTop10ByOrderByNumDownloadsDesc();
}
