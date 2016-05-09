package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;


public class Serializer {
    public static Gson get(){
        return new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC, Modifier.PRIVATE)
                .serializeNulls()
                .enableComplexMapKeySerialization()
                .create();
    }
}
