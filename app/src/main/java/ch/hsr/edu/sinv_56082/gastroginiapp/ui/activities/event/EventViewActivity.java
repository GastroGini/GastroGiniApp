package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.event;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.app.LocalData;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.UUIDModel;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventTable;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Person;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductList;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.connection.JoinEventActivity;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.connection.StartEventActivity;

public class EventViewActivity extends AppCompatActivity implements View.OnClickListener{
    private String date;

    private Event event;

    private EditText eventTitle;
    private EditText amountOfTables;
    private EditText executionDate ;
    private Spinner productList;
    private Button eventViewSaveButton;
    private Button eventViewStartButton;

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

        final EventViewActivity eventViewActivity = this;



        Bundle args = getIntent().getExtras();
        if(args != null){
            event = new Select().from(Event.class).where("uuid = ?", UUID.fromString(args.getString("event-uuid"))).executeSingle();
        }else{
            //event = new Select().from(Event.class).where("uuid = ?", UUID.fromString(args.getString("event-uuid"))).executeSingle();
            UUID localUser = ((LocalData) getApplication()).getLocalUser();
            Log.d("TEST", localUser.toString());
            Person user = new Select().from(Person.class).where("uuid = ?", localUser).executeSingle();
            event = new Event(new ProductList("Unused List"), "Test", new Date(), new Date(), user);
        }

        setTitle(event.name);
        date = event.startTime.toString(); // TODO FORMAT

        eventTitle = (EditText) findViewById(R.id.eventViewTitleInput);
        amountOfTables = (EditText) findViewById(R.id.eventViewAnzahlTischeInput);
        executionDate = (EditText) findViewById(R.id.eventViewDatumInput);
        productList = (Spinner) findViewById(R.id.eventViewProduktListeSpinner);
        eventViewSaveButton = (Button) findViewById(R.id.eventViewSaveButton);
        eventViewStartButton = (Button) findViewById(R.id.eventViewStartButton);

        /*if(event.getTitle().isEmpty() || event.getAmountOfTables() == 0){
            eventViewStartButton.setVisibility(View.INVISIBLE);
        }*/

        /* dummy data */
/*
        List<ProductList> productLists = new ArrayList<>();
        productLists.add(new ProductList("ProductList 1"));
        productLists.add(new ProductList("ProductList 2"));
        productLists.add(new ProductList("ProductList 3"));
        productLists.add(new ProductList("ProductList 4"));
*/
        /* dummy data */
        List<ProductList> productLists = new Select().from(ProductList.class).execute();
        ArrayAdapter<ProductList> spinnerAdapter = new ArrayAdapter<ProductList>(this,android.R.layout.simple_spinner_dropdown_item,productLists);
        productList.setAdapter(spinnerAdapter);

        eventTitle.setText(event.name);

        if(args != null) {
            amountOfTables.setText(event.eventTables().size() + ""); //TODO mal schaun
        } else {
            amountOfTables.setText("0"); //TODO mal schaun
        }
        executionDate.setText(event.startTime.toString()); // TODO FORMAT

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



        eventViewSaveButton.setOnClickListener(this);

        eventViewStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(eventViewActivity, StartEventActivity.class);
                intent.putExtra("event-uuid",event.uuid.toString());
                startActivity(intent);
            }
        });

        /*
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
                    Intent intent = new Intent(eventViewActivity, JoinEventActivity.class);
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
                    event.setAmountOfTables(s.toString());
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



        }*/
    }


    @Override
    public void onClick(View v) {
        //TODO: Persistency Logic
        event.name = eventTitle.getText().toString();
        event.productList = (ProductList)productList.getSelectedItem();
        event.save();
        Intent resultIntent = new Intent();

        setResult(Activity.RESULT_OK,resultIntent);
        finish();
    }

}
