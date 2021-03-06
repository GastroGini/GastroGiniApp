package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.menu;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.Consumer;
import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.HintMessage;
import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.Supplier;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.view.ViewController;
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
    CommonActivity activity;
    @Bind(R.id.toolbar) Toolbar toolbar;
    private ViewController<ProductDescription> productDescriptionController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_product_description_edit);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity=this;

        initializeProductDescription();
        productDescriptionEditName.setText(productDescription.name);
        productDescriptionEditDesc.setText(productDescription.description);
        ArrayAdapter<ProductCategory> arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item,
                new ViewController(ProductCategory.class).getModelList());

        productDescriptionCategorySelect.setAdapter(arrayAdapter);

        int position = arrayAdapter.getPosition(productDescription.productCategory);
        productDescriptionCategorySelect.setSelection(position);
        arrayAdapter.notifyDataSetChanged();

        productDescriptionSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(fieldIsEmpty() == true){
                    new HintMessage(activity, "Fehler", "Name oder beschreibung sind leer!");
                }
                else{
                    productDescriptionController.update(productDescription, new Consumer<ProductDescription>() {
                        @Override
                        public void consume(ProductDescription description) {
                            productDescription.name = productDescriptionEditName.getText().toString();
                            productDescription.description = productDescriptionEditDesc.getText().toString();
                            productDescription.productCategory = (ProductCategory) productDescriptionCategorySelect.getSelectedItem();
                        }
                    });
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });

    }

    public boolean fieldIsEmpty(){

        boolean isNameEmpty =  productDescriptionEditName.getText().toString().trim().isEmpty()  ? true : false;
        boolean isDescEmpty =  productDescriptionEditDesc.getText().toString().trim().isEmpty()   ? true : false;
        return (isNameEmpty == false && isDescEmpty == false) ? false : true;
    }
    private void initializeProductDescription() {
        productDescriptionController = new ViewController<>(ProductDescription.class);

        Bundle extras = getIntent().getExtras();
        if(extras==null) isNewProductDescription = true;
        productDescription = productDescriptionController.prepare(new Supplier<ProductDescription>() {
            @Override
            public ProductDescription supply() {
                return new ProductDescription("","",null);
            }
        });
        if(!isNewProductDescription){
            productDescription = productDescriptionController.get(extras.getString("productDescriptionSelect-uuid"));
        }
    }

}
