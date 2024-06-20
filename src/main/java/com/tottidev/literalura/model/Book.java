package com.tottidev.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    private long id;

    private String title;

    @ManyToOne
    private Author author;

    @Enumerated(EnumType.STRING)
    private Language language;

    private int numDownloads;

    // Constructors
    public Book() {
    }

    public Book(BookData bookData) {
        this.id = bookData.id();
        this.title = bookData.title();
        this.numDownloads = bookData.numDownloads();
        this.language = Language.fromString(bookData.languages().getFirst());
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String titulo) {
        this.title = titulo;
    }

    public Author getAutor() {
        return author;
    }

    public void setAutor(Author author) {
        this.author = author;
    }

    public Language getIdioma() {
        return language;
    }

    public void setIdioma(Language language) {
        this.language = language;
    }

    public int getNumDownloads() {
        return numDownloads;
    }

    public void setNumDownloads(int numDescargas) {
        this.numDownloads = numDescargas;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | " + title + " (" + author.getName() + ") | Lang: " + language + " | Downloads: " + numDownloads;
    }
}
