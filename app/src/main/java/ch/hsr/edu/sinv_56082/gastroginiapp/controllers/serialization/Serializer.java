package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

/**
 * Created by tobias on 02.05.2016.
 */
public class Serializer {
    public static Gson get(){
        return new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .serializeNulls()
                .create();
    }
}
