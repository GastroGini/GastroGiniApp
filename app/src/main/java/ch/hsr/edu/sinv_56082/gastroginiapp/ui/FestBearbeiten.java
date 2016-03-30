package ch.hsr.edu.sinv_56082.gastroginiapp.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.ApplicationObject;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.Fest;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.FesteAdapter;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.FesteBearbeitenCallback;

public class FestBearbeiten extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fest_bearbeiten);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final EditText title = (EditText)findViewById(R.id.festBearbeitenTitle);
        Button save_btn = (Button) findViewById(R.id.speichern_btn);
        Button new_btn = (Button) findViewById(R.id.neuesFest_btn);
        Bundle args = getIntent().getExtras();
        final int position = (int)args.get("position");
        title.setText(args.get("title").toString());

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationObject.getFesteList().get(position).setTitle(title.getText().toString());
                finish();
            }
        });

        new_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationObject.getFesteList().add(new Fest(title.getText().toString()));
                FesteAdapter.checkBoxStates.add(false);
                finish();
            }
        });
    }

}
