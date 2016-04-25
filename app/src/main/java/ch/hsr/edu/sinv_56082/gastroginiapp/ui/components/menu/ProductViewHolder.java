package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.menu;

import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.CommonViewHolder;


public class ProductViewHolder extends CommonViewHolder {
    @Bind(R.id.productTitle) public TextView productTitle;
    @Bind(R.id.productDescription) public TextView productDescription;
    @Bind(R.id.productVolume) public TextView productVolume;
    @Bind(R.id.productPrice) public TextView productPrice;

    public ProductViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
