package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.connection;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.ConnectionStateView;
import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.DoIt;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.app.App;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.app.ConnectionController;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.ConnectionState;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.CommonActivity;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.HomeScreenActivity;

public abstract class ConnectionActivity extends CommonActivity {


    private Menu menu;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_connstat:
                Intent intent = new Intent(this, StatusActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ConnectionController.getInstance().addClientConnectionListener(new DoIt() {
            @Override
            public void doIt() {
                Toast.makeText(App.getApp(), "Disconnect from Server!",
                        Toast.LENGTH_LONG).show();
                backHome();
            }
        });

        ConnectionController.getInstance().addConnectionStateListener(new DoIt(){
            @Override
            public void doIt() {
                Drawable icon = menu.findItem(R.id.action_connstat).getIcon();
                switch (ConnectionController.getInstance().getConnectionState()){
                    case DISCONNECTED:
                        icon.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                        break;
                    case CONNECTED:
                        icon.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
                        break;
                    case RECONNECTING:
                        icon.setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
                        break;
                    default:
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        this.menu = menu;

        Drawable icon = menu.findItem(R.id.action_connstat).getIcon();
        icon.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);

        ConnectionController c = ConnectionController.getInstance();
        c.setConnectionState(c.getConnectionState());

        return true;
    }



    @Override
    protected void onPause() {
        super.onPause();
        ConnectionController.getInstance().removeConnectionStateListener();
        ConnectionController.getInstance().removeClientConnectionListener();
    }

    protected void backHome(){
        Intent intent = new Intent(App.getApp(), HomeScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public abstract ConnectionActivity getActivity();
}
