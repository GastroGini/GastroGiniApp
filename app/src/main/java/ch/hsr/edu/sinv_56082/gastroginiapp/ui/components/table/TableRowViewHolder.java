package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.table;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


public class TableRowViewHolder extends RecyclerView.ViewHolder {
    View tableOrderItemView;


    TextView name;
    TextView size;
    TextView price;

    public TableRowViewHolder(View tableOrderItemView, TextView name,TextView size,TextView price) {
        super(tableOrderItemView);
        this.tableOrderItemView = tableOrderItemView;
        this.name = name;
        this.size = size;
        this.price = price;
    }

    public View getEventTablesView(){
        return tableOrderItemView;
    }

    public TextView getNameTextView(){  return name; }
    public TextView getSizeTextView(){
        return size;
    }
    public TextView getPriceTextView(){
        return price;
    }

}
