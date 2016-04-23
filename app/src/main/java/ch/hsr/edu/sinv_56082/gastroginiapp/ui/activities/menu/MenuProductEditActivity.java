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
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Product;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductDescription;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductList;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.CommonActivity;

public class MenuProductEditActivity extends CommonActivity {



    @Bind(R.id.productEditPrice)TextView productEditPrice;
    @Bind(R.id.productEditVolume)TextView productEditVolume;
    @Bind(R.id.productDescriptionSelect)Spinner productDescriptionSelect;
    @Bind(R.id.productEditSaveButton)Button productEditSaveButton;

    Product product;
    boolean isNewProduct = false;

    @Bind(R.id.toolbar) Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_product_edit);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeProductDescription();

        productEditPrice.setText(String.valueOf(product.price));
        productEditVolume.setText(product.volume);
        final ArrayAdapter<ProductDescription> adapter = new ArrayAdapter<ProductDescription>(
                this, android.R.layout.simple_spinner_dropdown_item,
                new Select().from(ProductDescription.class).<ProductDescription>execute());
        productDescriptionSelect.setAdapter(adapter);

        int position = adapter.getPosition(product.productDescription);
        productDescriptionSelect.setSelection(position);
        adapter.notifyDataSetChanged();

        productEditSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product.price = Double.valueOf(productEditPrice.getText().toString());
                product.volume = productEditVolume.getText().toString();
                product.productDescription = (ProductDescription) productDescriptionSelect.getSelectedItem();
                product.save();
                adapter.notifyDataSetChanged();
                setResult(RESULT_OK);
                finish();
            }
        });

    }

    private void initializeProductDescription() {
        Bundle extras = getIntent().getExtras();
        if(extras.getString("product-uuid")==null) isNewProduct = true;
        ProductList productList = ProductList.get(extras.getString("menucardRowItem-uuid"));
        product = new Product(null, productList, 0.0,"");
        if(!isNewProduct){
            product = Product.get(extras.getString("product-uuid"));
        }
    }

}
