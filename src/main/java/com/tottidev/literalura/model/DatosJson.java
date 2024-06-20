package com.tottidev.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosJson(
        @JsonAlias("count") Integer count,
        @JsonAlias("results") List<DatosLibro> results
) {}
