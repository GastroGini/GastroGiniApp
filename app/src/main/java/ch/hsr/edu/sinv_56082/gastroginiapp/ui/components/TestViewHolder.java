package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;


public class TestViewHolder extends RecyclerView.ViewHolder {
    public View itemView;
    public ImageView deleter;
    public TestViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        deleter = (ImageView)itemView.findViewById(R.id.delete_button);
    }
}
