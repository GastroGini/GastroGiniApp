package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.menu;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.CommonViewHolder;

public class ProductCategoryViewHolder extends CommonViewHolder {
    @Bind(R.id.menuCardProductListHeaderTitle) public TextView menuTitle;
    @Bind(R.id.productDescriptionRecyclerView) public RecyclerView productRecycler;
    @Bind(R.id.menuCardProductListHeaderEditMenuIcon) public ImageView editIcon;
    @Bind(R.id.expandCollapseIcon) public ImageView expandCollapseIcon;
    public ProductCategoryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
