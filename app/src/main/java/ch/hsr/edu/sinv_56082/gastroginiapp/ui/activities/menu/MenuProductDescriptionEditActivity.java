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

import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductCategory;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductDescription;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.TestActivity;

public class MenuProductDescriptionEditActivity extends TestActivity {

    @Bind(R.id.productDescriptionEditName)TextView productName;
    @Bind(R.id.productDescriptionEditDesc)TextView productDesc;
    @Bind(R.id.productDescriptionCategorySelect)Spinner productCategory;
    @Bind(R.id.productDescriptionSaveButton)Button saveButton;

    ProductDescription productDescription;
    boolean isNewProductDescription = false;

    @Bind(R.id.toolbar) Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_product_description_edit);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        initializeProductDescription();

        productName.setText(productDescription.name);
        productDesc.setText(productDescription.description);

        productCategory.setAdapter(new ArrayAdapter<ProductCategory>(
                        this, android.R.layout.simple_spinner_dropdown_item,
                        new Select().from(ProductCategory.class).<ProductCategory>execute())
        );

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productDescription.name = productName.getText().toString();
                productDescription.description = productDesc.getText().toString();
                productDescription.productCategory = (ProductCategory) productCategory.getSelectedItem();
                productDescription.save();

                setResult(RESULT_OK);
                finish();
            }
        });

    }


    private void initializeProductDescription() {
        Bundle extras = getIntent().getExtras();
        if(extras==null) isNewProductDescription = true;
        productDescription = new ProductDescription("","",null);
        if(!isNewProductDescription){
            productDescription = ProductDescription.get(extras.getString("productDescription-uuid"));
        }
    }

}
