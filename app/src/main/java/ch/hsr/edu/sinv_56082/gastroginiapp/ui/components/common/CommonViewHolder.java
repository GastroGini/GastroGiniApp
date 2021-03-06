package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.common;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;


public class CommonViewHolder extends RecyclerView.ViewHolder {
    public View itemView;
    @Bind(R.id.delete_button)
    public ImageView delete_button;
    public CommonViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        ButterKnife.bind(this, itemView);
    }
}
