package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.menu;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.activeandroid.query.Select;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Product;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductCategory;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductDescription;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductList;

public class MenuProductEditActivity extends AppCompatActivity {


    TextView price;
    TextView volume;
    Spinner productDescription;
    Button saveButton;

    Product product;
    boolean isNewProduct = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_product_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeProductDescription();
        loadViews();

        price.setText(String.valueOf(product.price));
        volume.setText(product.volume);

        productDescription.setAdapter(new ArrayAdapter<>(
                        this, android.R.layout.simple_spinner_dropdown_item,
                        new Select().from(ProductDescription.class).<ProductDescription>execute())
        );

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product.price = Double.valueOf(price.getText().toString());
                product.volume = volume.getText().toString();
                product.productDescription = (ProductDescription) productDescription.getSelectedItem();
                product.save();

                setResult(RESULT_OK);
                finish();
            }
        });

    }

    private void loadViews() {
        price = (TextView)findViewById(R.id.productEditPrice);
        volume = (TextView)findViewById(R.id.productEditVolume);
        productDescription = (Spinner)findViewById(R.id.productDescriptionSelect);
        saveButton = (Button)findViewById(R.id.productEditSaveButton);
    }

    private void initializeProductDescription() {
        Bundle extras = getIntent().getExtras();
        if(extras.getString("product")==null) isNewProduct = true;
        ProductList productList = ProductList.get(extras.getString("productList-uuid"));
        product = new Product(null, productList, 0.0,"");
        if(!isNewProduct){
            product = Product.get(extras.getString("product-uuid"));
        }
    }


}
