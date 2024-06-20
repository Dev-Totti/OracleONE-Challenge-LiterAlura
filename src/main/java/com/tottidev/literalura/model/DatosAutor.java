package com.tottidev.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DatosAutor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") Integer yearNacimiento,
        @JsonAlias("death_year") Integer yearFallecimiento
) {
    @Override
    public String toString() {
        return nombre + " (" + yearNacimiento + "-" + yearFallecimiento + ")";
    }
}
