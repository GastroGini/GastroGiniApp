package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.event;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.connection.JoinEventActivity;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.connection.StartEventActivity;

public class EventViewActivity extends AppCompatActivity {
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EventViewActivity eva = this;

        Bundle args = getIntent().getExtras();
        final Event event = (Event)args.get("event");
        final int position = args.getInt("pos");
        final int identifier = args.getInt("identifier");
        setTitle(event.getTitle());
        date = event.getStartTime();

        final EditText eventTitle = (EditText) findViewById(R.id.eventViewTitleInput);
        final EditText amountOfTables = (EditText) findViewById(R.id.eventViewAnzahlTischeInput);
        final EditText executionDate = (EditText) findViewById(R.id.eventViewDatumInput);
        final Spinner productList = (Spinner) findViewById(R.id.eventViewProduktListeSpinner);
        final Button eventViewSaveButton = (Button) findViewById(R.id.eventViewSaveButton);
        final Button eventViewStartButton = (Button) findViewById(R.id.eventViewStartButton);

        eventTitle.setText(event.getTitle());
        amountOfTables.setText(event.getAmountOfTables() + "");
        executionDate.setText(event.getStartTime());

        if(event.getTitle().isEmpty() || event.getAmountOfTables() == 0){
            eventViewSaveButton.setClickable(false);
        }

        if(identifier == EventListActivity.getForeigneventlistIdentifier()){
            eventTitle.setClickable(false);
            eventTitle.setFocusable(false);
            amountOfTables.setClickable(false);
            amountOfTables.setFocusable(false);
            executionDate.setClickable(false);
            executionDate.setFocusable(false);
            productList.setClickable(false);
            productList.setFocusable(false);
            eventViewSaveButton.setClickable(false);
            eventViewSaveButton.setVisibility(View.INVISIBLE);
            eventViewStartButton.setText("Beitreten");
            eventViewStartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(eva, JoinEventActivity.class);
                    startActivity(intent);
                }
            });
        }else{
            eventTitle.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    setTitle(s.toString());
                    event.setTitle(s.toString());
                }
            });

            amountOfTables.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    event.setAmountOfTables(Integer.parseInt(s.toString()));
                }
            });

            executionDate.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    date = s.toString();
                }
            });

            eventViewSaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: Persistency Logic
                    event.setStartTime(date);
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("title",event.getTitle());
                    resultIntent.putExtra("amountOfTables", event.getAmountOfTables());
                    resultIntent.putExtra("executionDate",event.getStartTime());
                    resultIntent.putExtra("position",position);
                    setResult(Activity.RESULT_OK,resultIntent);
                    finish();
                }
            });

            eventViewStartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(eva, StartEventActivity.class);
                    startActivity(intent);
                }
            });

        }
    }

}
