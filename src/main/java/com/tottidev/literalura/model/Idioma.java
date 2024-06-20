package com.tottidev.literalura.model;

public enum Idioma {
    ESPAÑOL("es"),
    INGLES("en"),
    FRANCES("fr"),
    ITALIANO("it"),
    PORTUGUES("pt"),
    ALEMAN("de"),
    RUSO("ru");

    
    private final String idioma;
    
    Idioma(String idioma) {
        this.idioma = idioma;
    }
    
    public String getIdioma() {
        return idioma;
    }
    
    public static Idioma fromString(String text) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.idioma.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        return null;
    }
}
