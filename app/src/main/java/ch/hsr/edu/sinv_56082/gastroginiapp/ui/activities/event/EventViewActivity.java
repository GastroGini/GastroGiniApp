package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.Consumer;
import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.Supplier;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.app.UserController;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.view.ViewController;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventTable;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductList;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.CommonActivity;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.connection.StartEventActivity;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.DateHelpers;

public class EventViewActivity extends CommonActivity {

    private Event event;

    @Bind(R.id.eventViewTitleInput) EditText eventViewTitleInput;
    @Bind(R.id.eventViewTableNumberInput) EditText eventViewTableNumberInput;
    @Bind(R.id.eventViewDateInput) Button eventViewDateInput;
    @Bind(R.id.eventViewProductListSpinner) Spinner eventViewProductListSpinner;
    @Bind(R.id.eventViewSaveButton) Button eventViewSaveButton;
    @Bind(R.id.eventViewStartButton) Button eventViewStartButton;

    private EventViewActivity eventViewActivity;
    private int oldTableCount;

    @Bind(R.id.toolbar) Toolbar toolbar;
    private ViewController<Event> eventController;
    private Date eventDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        eventController = new ViewController<>(Event.class);

        eventViewActivity = this;



        Bundle args = getIntent().getExtras();
        boolean isNewEvent = false;
        if(args != null){
            isNewEvent = false;
            event = eventController.get(args.getString("event-uuid"));
        }else{
            isNewEvent = true;
            event = eventController.prepare(new Supplier<Event>() {
                @Override
                public Event supply() {
                    return new Event(new ProductList("Unused List"), "", new Date(), new Date(), new UserController().getUser());
                }
            });
        }
        setTitle(event.name);





        List<ProductList> productLists = new ViewController<>(ProductList.class).getModelList();
        ArrayAdapter<ProductList> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, productLists);
        eventViewProductListSpinner.setAdapter(spinnerAdapter);

        eventViewTitleInput.setText(event.name);

        if(!isNewEvent) {
            eventViewTableNumberInput.setText(String.valueOf(event.eventTables().size()));
            oldTableCount = event.eventTables().size();
        } else {
            eventViewTableNumberInput.setText("0");
            oldTableCount = 0;

        }
        eventViewDateInput.setText(DateHelpers.dateToString(event.startTime));
        eventDate = event.startTime;

        for(int i = 0;i < productLists.size();i++){
            if(productLists.get(i).name.equals(event.productList.name)){
                eventViewProductListSpinner.setSelection(i);
            }
        }

        /*
        if(event.getTitle().isEmpty() || event.getAmountOfTables() == 0){
            eventViewSaveButton.setClickable(false);
        }
        */

        if(isNewEvent){
            eventViewStartButton.setVisibility(View.INVISIBLE);
        }

        eventViewDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DateHelpers.Picker(eventViewActivity, new DateHelpers.Callback() {
                    @Override
                    public void onSet(Date date) {
                        eventDate = date;
                        eventViewDateInput.setText(DateHelpers.dateToString(date));
                    }
                });
            }
        });


        eventViewSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                eventController.update(event, new Consumer<Event>() {
                    @Override
                    public void consume(Event saveEvent) {
                        saveEvent.startTime = eventDate;
                        saveEvent.name = eventViewTitleInput.getText().toString();
                        saveEvent.productList = (ProductList) eventViewProductListSpinner.getSelectedItem();
                    }
                });

                int newTableCount = Integer.parseInt(eventViewTableNumberInput.getText().toString());
                if(newTableCount > oldTableCount){
                    for (int i = oldTableCount + 1; i <= newTableCount; i++){
                        final int finalI = i;
                        new ViewController<>(EventTable.class).create(new Supplier<EventTable>() {
                            @Override
                            public EventTable supply() {
                                return new EventTable(finalI, "Tisch " + finalI, event);
                            }
                        });
                        Log.d("aaaaaaaaa", "onClick: new table");
                    }
                }
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
