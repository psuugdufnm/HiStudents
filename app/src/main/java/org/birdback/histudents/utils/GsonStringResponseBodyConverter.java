package org.birdback.histudents.utils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.birdback.histudents.entity.ResponseEntity;
import org.birdback.histudents.net.HttpServer;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

class GsonStringResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    GsonStringResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        T t = null;
        try {
            if (String.class.equals(type)) {
                t = (T) value.string();
            } else {
                String json = value.string();
                Bitch bitch = gson.fromJson(json, Bitch.class);

                if (bitch.getOk() == HttpServer.SUCCESS) {
                    JsonReader jsonReader = new JsonReader(new StringReader(json));
                    TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
                    t = (T) adapter.read(jsonReader);
                } else {
                    ResponseEntity responseEntity = new ResponseEntity();
                    responseEntity.setOk(bitch.getOk());
                    responseEntity.setMessage(bitch.getMessage());
                    t = (T) responseEntity;
                }
            }
        } finally {
            value.close();
        }
        return t;
    }
}