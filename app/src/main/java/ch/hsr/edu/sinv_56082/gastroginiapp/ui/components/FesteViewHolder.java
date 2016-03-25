package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class FesteViewHolder extends RecyclerView.ViewHolder {
    private View festView;
    private TextView festTitle;
    public FesteViewHolder(View festView, TextView festTitle) {
        super(festView);
        this.festView = festView;
        this.festTitle = festTitle;
    }

    public TextView getFestTitle(){
        return festTitle;
    }
    public View getView(){return festView;};
}
