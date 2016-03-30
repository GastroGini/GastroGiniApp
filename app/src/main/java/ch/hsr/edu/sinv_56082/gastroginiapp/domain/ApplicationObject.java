package ch.hsr.edu.sinv_56082.gastroginiapp.domain;

import android.app.Application;
import android.content.res.Configuration;

import java.util.ArrayList;
import java.util.List;

public class ApplicationObject extends Application {
    private static List<Fest> feste  = new ArrayList<>();

    public ApplicationObject(){
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static List<Fest> getFesteList(){
        return feste;
    }
}
