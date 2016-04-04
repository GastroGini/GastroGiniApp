package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.order;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class EventTablesViewHolder extends RecyclerView.ViewHolder {
    View columnRowEventTablesView;
    TextView columnRowEventTableTitleText;
    public EventTablesViewHolder(View columnRowEventTablesView, TextView columnRowEventTableTitleText) {
        super(columnRowEventTablesView);
        this.columnRowEventTablesView = columnRowEventTablesView;
        this.columnRowEventTableTitleText = columnRowEventTableTitleText;
    }

    public View getEventTablesView(){
        return columnRowEventTablesView;
    }

    public TextView getEventTableTextView(){
        return columnRowEventTableTitleText;
    }
}
