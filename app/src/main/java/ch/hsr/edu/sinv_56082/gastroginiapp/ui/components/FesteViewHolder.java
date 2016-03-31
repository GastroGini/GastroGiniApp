package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class FesteViewHolder extends RecyclerView.ViewHolder {
    private View festView;
    private TextView festTitle;
    private CheckBox festeBox;
    public FesteViewHolder(View festView, TextView festTitle, CheckBox festeBox) {
        super(festView);
        this.festView = festView;
        this.festTitle = festTitle;
        this.festeBox = festeBox;
    }

    public TextView getFestTitle(){
        return festTitle;
    }
    public View getView(){return festView;};
    public CheckBox getFesteBox(){return festeBox;};
}
