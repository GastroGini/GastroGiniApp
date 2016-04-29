package ch.hsr.edu.sinv_56082.gastroginiapp.app;


import android.content.Context;
import android.content.SharedPreferences;

import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.Consumer;

public class SharedPrefs {

    private SharedPreferences preferences;

    public SharedPrefs(String prefEditor){
        preferences = App.getApp().getSharedPreferences(prefEditor, Context.MODE_PRIVATE);
    }

    public SharedPreferences getPreferences(){
        return preferences;
    }

    public void savePreferences(Consumer<SharedPreferences.Editor> editorConsumer){
        SharedPreferences.Editor editor = preferences.edit();
        editorConsumer.consume(editor);
        editor.commit();
    }


}
