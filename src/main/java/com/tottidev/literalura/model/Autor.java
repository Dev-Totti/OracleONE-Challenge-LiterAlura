package com.tottidev.literalura.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;
    private int yearNacimiento;
    private int yearFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Libro> libros;

    // Constructors
    public Autor() {
    }

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.yearNacimiento = datosAutor.yearNacimiento();
        this.yearFallecimiento = datosAutor.yearFallecimiento();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getYearNacimiento() {
        return yearNacimiento;
    }

    public void setYearNacimiento(int yearNacimiento) {
        this.yearNacimiento = yearNacimiento;
    }

    public int getYearFallecimiento() {
        return yearFallecimiento;
    }

    public void setYearFallecimiento(int yearFallecimiento) {
        this.yearFallecimiento = yearFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
        libros.forEach(libro -> libro.setAutor(this));
    }

    @Override
    public String toString() {
        return "ID: " + id + "\nNombre: " + nombre + "\nAño de nacimiento: " + yearNacimiento + "\nAño de fallecimiento: " + yearFallecimiento;
    }
}
