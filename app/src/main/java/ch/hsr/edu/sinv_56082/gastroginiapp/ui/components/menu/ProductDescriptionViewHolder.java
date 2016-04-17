package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.menu;

import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.TestViewHolder;


public class ProductDescriptionViewHolder extends TestViewHolder{
    @Bind(R.id.productDescriptionTitle) public TextView productDescriptionName;
    @Bind(R.id.productDescriptionDescription) public TextView productDescriptionDesc;

    public ProductDescriptionViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
