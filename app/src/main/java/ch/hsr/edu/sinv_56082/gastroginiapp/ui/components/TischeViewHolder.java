package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class TischeViewHolder extends RecyclerView.ViewHolder {
    private View tischView;
    private TextView tischDescription;
    public TischeViewHolder(View tischView, TextView tischDescription) {
        super(tischView);
        this.tischView = tischView;
        this.tischDescription = tischDescription;
    }

    public TextView getTischDescription(){
        return tischDescription;
    }
    public View getView(){return tischView;};
}
