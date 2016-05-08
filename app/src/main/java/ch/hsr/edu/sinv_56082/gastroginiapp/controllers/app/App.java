package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.app;

import android.content.SharedPreferences;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;

import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.Consumer;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Person;

public class App extends Application {


    private static App context;


    private UUID localUser;
    private SharedPrefs prefs;


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        ActiveAndroid.initialize(this);
        initLocalUser();
    }

    private void initLocalUser() {
        prefs = new SharedPrefs("LocalUserData");
        if (prefs.getPreferences().getString("local-user-uuid", null) != null){
            localUser = UUID.fromString(prefs.getPreferences().getString("local-user-uuid", null));
            Log.d("App", "onCreate: loaded User: " + localUser.toString());
        }else{
            Person localUserPerson = new Person("local", "user");
            localUserPerson.save();
            setLocalUser(localUserPerson.getUuid());
        }
    }


    public void setLocalUser(final UUID localUser) {
        prefs.savePreferences(new Consumer<SharedPreferences.Editor>() {
            @Override
            public void consume(SharedPreferences.Editor editor) {
                editor.putString("local-user-uuid", localUser.toString());
            }
        });
        this.localUser = localUser;
    }

    public static App getApp(){
        return context;
    }

    public UUID getLocalUser() {
        return localUser;
    }
}
