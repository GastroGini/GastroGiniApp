package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.order;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.app.ConnectionController;

public class StatusActivity extends ConnectionActivity {

    private StatusActivity activity;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.leave) Button leaveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        ButterKnife.bind(this);
        activity = this;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        leaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionController.getInstance().disconnect();
                backHome();
            }
        });

    }

}
