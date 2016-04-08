package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.table;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventTable;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Product;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.order.TableOrderView;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.order.EventTableClickListener;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.order.EventTablesViewHolder;

/**
 * Created by Dogan on 08.04.16.   EventTable???
 */


public class TableRowAdapter extends RecyclerView.Adapter<TableRowViewHolder> {
    private List<Product> orderItems;
    private TableOrderView activity;
    public TableRowAdapter(TableOrderView activity,List<Product> orderItems){
        this.orderItems = orderItems;
        this.activity = activity;
    }

    @Override
    public TableRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View tableOrderItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.column_row_order_item, parent, false);




        TextView name = (TextView) tableOrderItemView.findViewById(R.id.product_item_name);
        TextView size = (TextView) tableOrderItemView.findViewById(R.id.product_item_size);
        TextView price = (TextView) tableOrderItemView.findViewById(R.id.product_item_price);


        TableRowViewHolder etvh = new TableRowViewHolder(tableOrderItemView,name, size,price);
        return etvh;
    }

    @Override
    public void onBindViewHolder(TableRowViewHolder holder, int position) {
        holder.getNameTextView().setText(orderItems.get(position).getProductDescription().getName());
        holder.getSizeTextView().setText(orderItems.get(position).getVolume());
        holder.getPriceTextView().setText(orderItems.get(position).getPrice()+"");
        holder.getEventTablesView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Implement click logic for list item
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }
}
