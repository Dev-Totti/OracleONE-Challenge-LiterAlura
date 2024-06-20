package com.tottidev.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("id") Long id,
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAutor> autores,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("download_count") Integer numDescargas
) {
    @Override
    public String toString() {
        return  "ID: " + id +
                "\nTítulo: " + titulo +
                "\nAutores: " + autores +
                "\nIdiomas: " + idiomas +
                "\nNúmero de descargas: " + numDescargas;
    }
}
