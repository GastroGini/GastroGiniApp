package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.menu;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.Functions;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.view.ViewController;
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
    private ViewController<Product> productController;

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
                new ViewController(ProductDescription.class).getModelList());
        productDescriptionSelect.setAdapter(adapter);

        final int position = adapter.getPosition(product.productDescription);
        productDescriptionSelect.setSelection(position);
        adapter.notifyDataSetChanged();

        productEditSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                productController.update(product, new Functions.Consumer<Product>() {
                    @Override
                    public void consume(Product product) {
                        product.price = Double.valueOf(productEditPrice.getText().toString());
                        product.volume = productEditVolume.getText().toString();
                        product.productDescription = (ProductDescription) productDescriptionSelect.getSelectedItem();
                    }
                });

                adapter.notifyDataSetChanged();
                setResult(RESULT_OK);
                finish();
            }
        });

    }

    private void initializeProductDescription() {
        productController = new ViewController<>(Product.class);
        final Bundle extras = getIntent().getExtras();
        if(extras.getString("product-uuid")==null) isNewProduct = true;
        product = productController.prepare(new Functions.Supplier<Product>() {//TODO musst be diff
            @Override
            public Product supply() {
                return new Product(null, new ViewController<>(ProductList.class).get(extras.getString("menucardRowItem-uuid")), 0.0,"");
            }
        });
        if(!isNewProduct){
            product = productController.get(extras.getString("product-uuid"));
        }
    }

}
