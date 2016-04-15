package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.TestActivity;

public class MenuMain extends TestActivity {

    AppCompatActivity activity;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.menuCardButton) Button menuCardButton;
    @Bind(R.id.productsButton) Button productsButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_menu_main);
        ButterKnife.bind(this);



        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        menuCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ProductListListView.class);
                startActivity(intent);
            }
        });


        productsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ProductDescriptionListActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        //((App)getApplication()).p2p.removeLocalServie();
    }
}
