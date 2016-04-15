package ch.hsr.edu.sinv_56082.gastroginiapp.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;


import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Person;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.P2pHandler;

public class App extends Application {


    private static App context;


    private UUID localUser;
    private SharedPreferences preferences;


    public P2pHandler p2p;


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        ActiveAndroid.initialize(this);
        initLocalUser();

        p2p = new P2pHandler(context);
    }

    private void initLocalUser() {
        preferences = getSharedPreferences("LocalUserData", MODE_PRIVATE);
        if (preferences.getString("local-user-uuid", null) != null){
            localUser = UUID.fromString(preferences.getString("local-user-uuid", null));
            Log.d("App", "onCreate: loaded User: " + localUser.toString());
        }else{
            Person localUserPerson = new Person("local", "user");
            localUserPerson.save();
            setLocalUser(localUserPerson.getUuid());
        }
    }


    public void setLocalUser(UUID localUser) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("local-user-uuid", localUser.toString());
        editor.commit();
        this.localUser = localUser;
    }

    public static App getApp(){
        return context;
    }

    public UUID getLocalUser() {
        return localUser;
    }
}
