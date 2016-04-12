package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.menu.MenuCardView;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.activeandroid.query.Select;

import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductList;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.menu.MenuCardAdapter;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.menu.MenuProductListClickListener;




public class EditMenuEntity extends AppCompatActivity   {

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
        setContentView(R.layout.activity_menucard_general_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);




        List<ProductList> productLists = new Select().from(ProductList.class).execute();

        String editableName = "";
        Bundle extras = getIntent().getExtras();

        String intentedPage = extras.getString("page-name");

        switch(intentedPage){
            case "add-menu-list":
                toolbar.setTitle("New menu");
            break;
            case "edit-menu-list":
                toolbar.setTitle("Edit menu");
            break;
            case "add-new-menu-item":
                toolbar.setTitle("Add menu item ");
            break;
            case "edit-new-menu-item":
                toolbar.setTitle("Edit menu item ");
            break;

        }


        if (extras != null) {
            editableName = extras.getString("product-list-number");
            EditText name = (EditText) findViewById(R.id.productEditPrice);
            name.setText(editableName);

        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }



}
