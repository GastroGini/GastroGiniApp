package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.menu;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.CommonViewHolder;


public class ProductDescriptionViewHolder extends CommonViewHolder {
    @Bind(R.id.productDescriptionTitle) public TextView productDescriptionName;
    @Bind(R.id.productDescriptionDescription) public TextView productDescriptionDesc;
    @Bind(R.id.delete_button) public ImageView deleteButton;

    public ProductDescriptionViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
