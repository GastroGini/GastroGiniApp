package ch.hsr.edu.sinv_56082.gastroginiapp.app;

import android.content.SharedPreferences;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;


import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Person;

public class LocalData extends Application {


    private UUID localUser;
    private SharedPreferences preferences;


    LocalData application;
    public P2pHandler p2p;


    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        ActiveAndroid.initialize(this);
        initLocalUser();

        p2p = new P2pHandler(application);
    }

    private void initLocalUser() {
        preferences = getSharedPreferences("LocalUserData", MODE_PRIVATE);
        if (preferences.getString("local-user-uuid", null) != null){
            localUser = UUID.fromString(preferences.getString("local-user-uuid", null));
            Log.d("LocalData", "onCreate: loaded User: " + localUser.toString());
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

    public UUID getLocalUser() {
        return localUser;
    }
}
