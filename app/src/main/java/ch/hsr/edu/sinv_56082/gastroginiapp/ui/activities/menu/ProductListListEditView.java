package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.menu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.HintMessage;
import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.Supplier;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.view.ViewController;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductList;

public class ProductListListEditView extends AppCompatActivity {
    AppCompatActivity activity;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.productListEditViewTextInput) EditText productListNameInput;
    @Bind(R.id.productListEditViewSaveButton) Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list_list_edit_view);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        activity=this;

        productListNameInput = (EditText) findViewById(R.id.productListEditViewTextInput);
        saveButton = (Button)findViewById(R.id.productListEditViewSaveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFieldEmpty(productListNameInput) == true) {
                    new HintMessage(activity, "Fehler", "Name ist leer!");
                } else {
                    new ViewController<>(ProductList.class).create(new Supplier<ProductList>() {
                        @Override
                        public ProductList supply() {
                            return new ProductList(productListNameInput.getText().toString());
                        }
                    });
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });

    }
    public boolean isFieldEmpty(EditText newProductListName){

        boolean isNameEmpty =  newProductListName.getText().toString().trim().isEmpty()  ? true : false;

        return (isNameEmpty == false) ? false : true;
    }

}
