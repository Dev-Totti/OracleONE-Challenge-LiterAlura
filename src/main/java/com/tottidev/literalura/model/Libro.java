package com.tottidev.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    private long id;
    private String titulo;
    @ManyToOne
    private Autor autor;
    private String idioma;
    private int numDescargas;

    // Constructors
    public Libro() {
    }

    public Libro(DatosLibro datosLibro) {
        this.id = datosLibro.id();
        this.titulo = datosLibro.titulo();
//        this.autor = datosLibro.autores();
//        this.idioma = datosLibro.idiomas()
        this.numDescargas = datosLibro.numDescargas();
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public int getNumDescargas() {
        return numDescargas;
    }

    public void setNumDescargas(int numDescargas) {
        this.numDescargas = numDescargas;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\nTítulo: " + titulo + "\nAutor: " + autor + "\nIdioma: " + idioma + "\nNúmero de descargas: " + numDescargas;
    }
}
