package com.es1.myreceitamedicaservlets.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;

public class JsonUtils {

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    public JsonUtils() {

    }

    public String convertEntityToJson(Object object) {
        return gson.toJson(object);
    }

    public <T> T convertJsonToEntity(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public String lerBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                sb.append(linha);
            }
        }
        return sb.toString();
    }
}
