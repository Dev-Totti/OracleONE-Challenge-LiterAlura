package com.tottidev.literalura.service;

public interface IDataConverter {
    <T> T getData(String json, Class<T> clase);
}
