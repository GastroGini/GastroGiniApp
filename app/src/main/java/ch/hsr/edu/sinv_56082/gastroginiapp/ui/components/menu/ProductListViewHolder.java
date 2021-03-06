package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.menu;

import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.common.CommonViewHolder;

public class ProductListViewHolder extends CommonViewHolder {
    @Bind(R.id.productDescriptionTitle) public TextView menucardRowItemDescriptionTitle;
    @Bind(R.id.productDescriptionDescription) public TextView menucardRowItemDescription;
    public ProductListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
