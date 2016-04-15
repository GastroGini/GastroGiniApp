package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.menu;

import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.TestViewHolder;


public class ProductViewHolder extends TestViewHolder {
    @Bind(R.id.columnRowProductDescription) public TextView columnRowProductDescription;
    @Bind(R.id.columnRowProductVolume) public TextView columnRowProductVolume;
    @Bind(R.id.columnRowProductPrice) public TextView columnRowProductPrice;

    public ProductViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
