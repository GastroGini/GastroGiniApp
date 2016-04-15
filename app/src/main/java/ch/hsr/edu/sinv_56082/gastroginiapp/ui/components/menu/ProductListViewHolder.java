package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.menu;

import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.TestViewHolder;

public class ProductListViewHolder extends TestViewHolder {
    @Bind(R.id.menucardRowItem) public TextView menucardRowItem;
    public ProductListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
