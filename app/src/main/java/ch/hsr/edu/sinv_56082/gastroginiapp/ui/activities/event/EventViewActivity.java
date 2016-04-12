package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.event;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.activeandroid.query.Select;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.app.LocalData;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Person;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductList;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.connection.StartEventActivity;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.DateHelpers;

public class EventViewActivity extends AppCompatActivity {

    private Event event;
    private boolean isNewEvent = false;

    private EditText eventTitle;
    private EditText amountOfTables;
    private Button executionDate ;
    private Spinner productList;
    private Button eventViewSaveButton;
    private Button eventViewStartButton;
    private EventViewActivity eventViewActivity;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        eventViewActivity = this;



        Bundle args = getIntent().getExtras();
        if(args != null){
            isNewEvent = false;
            event = Event.get(UUID.fromString(args.getString("event-uuid")));
        }else{
            isNewEvent = true;
            UUID localUser = ((LocalData) getApplication()).getLocalUser();
            event = new Event(new ProductList("Unused List"), "", new Date(), new Date(), Person.get(localUser));
        }
        setTitle(event.name);


        eventTitle = (EditText) findViewById(R.id.eventViewTitleInput);
        amountOfTables = (EditText) findViewById(R.id.eventViewAnzahlTischeInput);
        executionDate = (Button) findViewById(R.id.eventViewDatumInput);
        productList = (Spinner) findViewById(R.id.eventViewProduktListeSpinner);
        eventViewSaveButton = (Button) findViewById(R.id.eventViewSaveButton);
        eventViewStartButton = (Button) findViewById(R.id.eventViewStartButton);


        List<ProductList> productLists = new Select().from(ProductList.class).execute();
        ArrayAdapter<ProductList> spinnerAdapter = new ArrayAdapter<ProductList>(this,android.R.layout.simple_spinner_dropdown_item,productLists);
        productList.setAdapter(spinnerAdapter);

        eventTitle.setText(event.name);

        if(!isNewEvent) {
            amountOfTables.setText(String.valueOf(event.eventTables().size()));
        } else {
            amountOfTables.setText("0");
        }
        executionDate.setText(DateHelpers.dateToString(this,event.startTime));

        for(int i = 0;i < productLists.size();i++){
            if(productLists.get(i).name.equals(event.productList.name)){
                productList.setSelection(i);
            }
        }

        /*
        if(event.getTitle().isEmpty() || event.getAmountOfTables() == 0){
            eventViewSaveButton.setClickable(false);
        }
        */


        executionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DateHelpers.Picker(eventViewActivity, new DateHelpers.Callback() {
                    @Override
                    public void onSet(Date date) {
                        event.startTime = date;
                        executionDate.setText(DateHelpers.dateToString(eventViewActivity, date));
                    }
                });
            }
        });


        eventViewSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                event.name = eventTitle.getText().toString();
                event.productList = (ProductList)productList.getSelectedItem();
                event.save();
                setResult(RESULT_OK);
                finish();
            }
        });

        eventViewStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(eventViewActivity, StartEventActivity.class);
                intent.putExtra("event-uuid",event.getUuid().toString());
                startActivity(intent);
            }
        });

    }

}
