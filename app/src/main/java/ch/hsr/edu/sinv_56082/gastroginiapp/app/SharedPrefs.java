package ch.hsr.edu.sinv_56082.gastroginiapp.app;


import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {

    private SharedPreferences preferences;

    public SharedPrefs(String prefEditor){
        preferences = App.getApp().getSharedPreferences(prefEditor, Context.MODE_PRIVATE);
    }

    public SharedPreferences getPreferences(){
        return preferences;
    }

    public void savePreferences(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("local-user-uuid", localUser.toString());
        editor.commit();
    }


}
