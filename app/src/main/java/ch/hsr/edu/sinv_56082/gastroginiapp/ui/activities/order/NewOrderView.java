package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventOrder;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventTable;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderPosition;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderState;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Product;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductCategory;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.event.EventViewActivity;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.order.NewOrderAdapter;

public class NewOrderView extends AppCompatActivity implements NewOrderAdapter.OnClickListener{

    private List<OrderPosition> myNewOrderPositionList = new ArrayList<>();
    //private RecyclerView myNewOrdersRecyclerView;
    private EventTable eventTable;

    private List<Product> categoryOne = new ArrayList<>();
    private List<Product> categoryTwo = new ArrayList<>();
    private List<Product> categoryThree = new ArrayList<>();
    private List<Product> categoryFour = new ArrayList<>();
    private List<Product> categoryFive = new ArrayList<>();

    private final static int MYCATEGORYONE_IDENTIFIER = 1;
    private final static int MYCATEGORYTWO_IDENTIFIER = 2;
    private final static int MYCATEGORYTHREE_IDENTIFIER = 3;
    private final static int MYCATEGORYFOUR_IDENTIFIER = 4;
    private final static int MYCATEGORYFIVE_IDENTIFIER = 5;

    private RecyclerView myCategoryOneRecyclerView;
    private RecyclerView myCategoryTwoRecyclerView;
    private RecyclerView myCategoryThreeRecyclerView;
    private RecyclerView myCategoryFourRecyclerView;
    private RecyclerView myCategoryFiveRecyclerView;

    private NewOrderAdapter myCategoryOneAdapter;
    private NewOrderAdapter myCategoryTwoAdapter;
    private NewOrderAdapter myCategoryThreeAdapter;
    private NewOrderAdapter myCategoryFourAdapter;
    private NewOrderAdapter myCategoryFiveAdapter;

    private boolean categoryOneCollapsedState = true;
    private boolean categoryTwoCollapsedState = true;
    private boolean categoryThreeCollapsedState = true;
    private boolean categoryFourCollapsedState = true;
    private boolean categoryFiveCollapsedState = true;

    EventOrder temp = new EventOrder(eventTable, new Date(System.currentTimeMillis()));
    OrderState offen=new Select().from(OrderState.class).where("Name = ?", "Unbezahlt").executeSingle();

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

    //TODO: Necessary? -> @Override
    public void onClick(Product product) {
        myNewOrderPositionList.add(new OrderPosition(null, offen, product, temp));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle args = getIntent().getExtras();
        eventTable = new Select().from(EventTable.class).where
                ("uuid = ?", UUID.fromString(args.getString("eventTable-uuid"))).executeSingle();
        Event event = eventTable.event;
        setTitle("GastroGini - Event: " + event.name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final AppCompatActivity activity = this;

        myCategoryOneRecyclerView = (RecyclerView) findViewById(R.id.myProductCategoryOneRecyclerView);
        myCategoryTwoRecyclerView = (RecyclerView) findViewById(R.id.myProductCategoryTwoRecyclerView);
        myCategoryThreeRecyclerView = (RecyclerView) findViewById(R.id.myProductCategoryThreeRecyclerView);
        myCategoryFourRecyclerView = (RecyclerView) findViewById(R.id.myProductCategoryFourRecyclerView);
        myCategoryFiveRecyclerView = (RecyclerView) findViewById(R.id.myProductCategoryFiveRecyclerView);

        myCategoryOneAdapter = new NewOrderAdapter(this, categoryOne, MYCATEGORYONE_IDENTIFIER);
        myCategoryTwoAdapter = new NewOrderAdapter(this, categoryTwo, MYCATEGORYTWO_IDENTIFIER);
        myCategoryThreeAdapter = new NewOrderAdapter(this, categoryThree, MYCATEGORYTHREE_IDENTIFIER);
        myCategoryFourAdapter = new NewOrderAdapter(this, categoryFour, MYCATEGORYFOUR_IDENTIFIER);
        myCategoryFiveAdapter = new NewOrderAdapter(this, categoryFive, MYCATEGORYFIVE_IDENTIFIER);

        myCategoryOneRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myCategoryTwoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myCategoryThreeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myCategoryFourRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myCategoryFiveRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        myCategoryOneRecyclerView.setAdapter(myCategoryOneAdapter);
        myCategoryTwoRecyclerView.setAdapter(myCategoryTwoAdapter);
        myCategoryThreeRecyclerView.setAdapter(myCategoryThreeAdapter);
        myCategoryFourRecyclerView.setAdapter(myCategoryFourAdapter);
        myCategoryFiveRecyclerView.setAdapter(myCategoryFiveAdapter);

        myCategoryOneRecyclerView.setHasFixedSize(true);
        myCategoryTwoRecyclerView.setHasFixedSize(true);
        myCategoryThreeRecyclerView.setHasFixedSize(true);
        myCategoryFourRecyclerView.setHasFixedSize(true);
        myCategoryFiveRecyclerView.setHasFixedSize(true);

        final ImageView categoryOneExpandCollapseIcon = (ImageView) findViewById(R.id.productCategoryOneExpandCollapseIcon);
        final ImageView categoryTwoExpandCollapseIcon = (ImageView) findViewById(R.id.productCategoryTwoExpandCollapseIcon);
        final ImageView categoryThreeExpandCollapseIcon = (ImageView) findViewById(R.id.productCategoryThreeExpandCollapseIcon);
        final ImageView categoryFourExpandCollapseIcon = (ImageView) findViewById(R.id.productCategoryFourExpandCollapseIcon);
        final ImageView categoryFiveExpandCollapseIcon = (ImageView) findViewById(R.id.productCategoryFiveExpandCollapseIcon);

        categoryOneExpandCollapseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!categoryOneCollapsedState){
                    myCategoryOneRecyclerView.setVisibility(View.GONE);
                }else{
                    myCategoryOneRecyclerView.setVisibility(View.VISIBLE);
                }
                categoryOneCollapsedState = !categoryOneCollapsedState;
            }
        });

        categoryTwoExpandCollapseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!categoryTwoCollapsedState) {
                    myCategoryTwoRecyclerView.setVisibility(View.GONE);
                } else {
                    myCategoryTwoRecyclerView.setVisibility(View.VISIBLE);
                }
                categoryTwoCollapsedState = !categoryTwoCollapsedState;
            }
        });
        categoryThreeExpandCollapseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!categoryThreeCollapsedState){
                    myCategoryThreeRecyclerView.setVisibility(View.GONE);
                }else{
                    myCategoryThreeRecyclerView.setVisibility(View.VISIBLE);
                }
                categoryThreeCollapsedState = !categoryThreeCollapsedState;
            }
        });

        categoryFourExpandCollapseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!categoryFourCollapsedState) {
                    myCategoryFourRecyclerView.setVisibility(View.GONE);
                } else {
                    myCategoryFourRecyclerView.setVisibility(View.VISIBLE);
                }
                categoryFourCollapsedState = !categoryFourCollapsedState;
            }
        });
        categoryFiveExpandCollapseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!categoryFiveCollapsedState){
                    myCategoryFiveRecyclerView.setVisibility(View.GONE);
                }else{
                    myCategoryFiveRecyclerView.setVisibility(View.VISIBLE);
                }
                categoryFiveCollapsedState = !categoryFiveCollapsedState;
            }
        });

    }


}
