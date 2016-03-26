package ch.hsr.edu.sinv_56082.gastroginiapp.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.fragments.BestellenTischFragment;

public class BestellenTischActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bestellen_tisch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Bundle args = getIntent().getExtras();
        toolbar.setTitle("Bestellen " + args.get("title").toString());
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        BestellenTischFragment btFragment = new BestellenTischFragment();
        fragmentTransaction.replace(R.id.recyclerViewContainer_bestellenTisch,btFragment);
        fragmentTransaction.commit();

    }

}
