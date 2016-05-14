package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.order;

import android.content.Intent;
import android.widget.Toast;

import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.DoIt;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.app.App;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.app.ConnectionController;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.CommonActivity;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.HomeScreenActivity;

public class ConnectionActivity extends CommonActivity {

    @Override
    protected void onResume() {
        super.onResume();
        ConnectionController.getInstance().addClientConnectionListener(new DoIt(){
            @Override
            public void doIt() {
                Toast.makeText(App.getApp(), "Disconnect from Server!",
                        Toast.LENGTH_LONG).show();
                backHome();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        ConnectionController.getInstance().removeClientConnectionListener();
    }

    protected void backHome(){
        Intent intent = new Intent(App.getApp(), HomeScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
