package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.menu;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.activeandroid.query.Select;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductCategory;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductDescription;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.CommonActivity;

public class MenuProductDescriptionEditActivity extends CommonActivity {

    @Bind(R.id.productDescriptionEditName)TextView productDescriptionEditName;
    @Bind(R.id.productDescriptionEditDesc)TextView productDescriptionEditDesc;
    @Bind(R.id.productDescriptionCategorySelect)Spinner productDescriptionCategorySelect;
    @Bind(R.id.productDescriptionSaveButton)Button productDescriptionSaveButton;

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
        productDescriptionEditName.setText(productDescription.name);
        productDescriptionEditDesc.setText(productDescription.description);
        ArrayAdapter<ProductCategory> arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item,
                new Select().from(ProductCategory.class).<ProductCategory>execute());

        productDescriptionCategorySelect.setAdapter(arrayAdapter);

        int position = arrayAdapter.getPosition(productDescription.productCategory);
        productDescriptionCategorySelect.setSelection(position);
        arrayAdapter.notifyDataSetChanged();

        productDescriptionSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productDescription.name = productDescriptionEditName.getText().toString();
                productDescription.description = productDescriptionEditDesc.getText().toString();
                productDescription.productCategory = (ProductCategory) productDescriptionCategorySelect.getSelectedItem();
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
            productDescription = ProductDescription.get(extras.getString("productDescriptionSelect-uuid"));
        }
    }

}
