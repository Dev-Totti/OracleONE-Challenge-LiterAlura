package com.tottidev.literalura.service;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DataConverter implements IDataConverter {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T getData(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json, clase);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while converting data");
        }
    }
}
