package com.tottidev.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "authors")
public class Author {
    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private int yearBirth;

    private int yearDeath;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> books;

    // Constructors
    public Author() {
    }

    public Author(AuthorData authorData) {
        this.name = authorData.name();
        this.yearBirth = authorData.yearBirth();
        this.yearDeath = authorData.yearDeath();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String nombre) {
        this.name = nombre;
    }

    public int getYearBirth() {
        return yearBirth;
    }

    public void setYearBirth(int yearNacimiento) {
        this.yearBirth = yearNacimiento;
    }

    public int getYearDeath() {
        return yearDeath;
    }

    public void setYearDeath(int yearFallecimiento) {
        this.yearDeath = yearFallecimiento;
    }

    public List<Book> getLibros() {
        return books;
    }

    public void setLibros(List<Book> books) {
        this.books = books;
        books.forEach(book -> book.setAutor(this));
    }

    @Override
    public String toString() {
        return "ID: " + id + " | " + name + " (" + yearBirth + "-" + yearDeath + ")";
    }
}
