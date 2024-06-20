package com.tottidev.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record AuthorData(
        @JsonAlias("name") String name,
        @JsonAlias("birth_year") Integer yearBirth,
        @JsonAlias("death_year") Integer yearDeath
) {}
