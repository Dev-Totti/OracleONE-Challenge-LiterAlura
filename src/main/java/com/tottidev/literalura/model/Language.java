package com.tottidev.literalura.model;

public enum Language {
    ENGLISH("en"),
    SPANISH("es"),
    FRENCH("fr"),
    ITALIAN("it"),
    PORTUGUESE("pt"),
    GERMAN("de"),
    RUSSIAN("ru");

    
    private final String language;
    
    Language(String language) {
        this.language = language;
    }
    
    public String getLanguage() {
        return language;
    }
    
    public static Language fromString(String text) {
        for (Language language : Language.values()) {
            if (language.language.equalsIgnoreCase(text)) {
                return language;
            }
        }
        return null;
    }
}
