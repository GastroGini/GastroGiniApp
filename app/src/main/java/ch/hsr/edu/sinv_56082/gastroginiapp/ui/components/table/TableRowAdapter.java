package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.table;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderPosition;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.common.CommonSelectable;


public class TableRowAdapter extends RecyclerView.Adapter<TableRowViewHolder> {

    private TableItemClickListener listener;

    public interface TableItemClickListener {
        void onClick(OrderPosition orderPosition);
    }
    private List<CommonSelectable<OrderPosition>> orderItems;

    TableRowAdapter adapter;

    public TableRowAdapter(List<CommonSelectable<OrderPosition>> orderItems, TableItemClickListener listener){
        adapter = this;
        this.listener = listener;
        this.orderItems=orderItems;
    }

    @Override
    public TableRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View tableOrderItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.column_row_order_item, parent, false);

        TextView name = (TextView) tableOrderItemView.findViewById(R.id.product_item_name);
        TextView size = (TextView) tableOrderItemView.findViewById(R.id.product_item_size);
        TextView price = (TextView) tableOrderItemView.findViewById(R.id.product_item_price);
        ImageView subtractAmount = (ImageView) tableOrderItemView.findViewById(R.id.subtractAmount);
        TextView amountCounter = (TextView) tableOrderItemView.findViewById(R.id.amountCounter);
        ImageView addAmount = (ImageView) tableOrderItemView.findViewById(R.id.addAmount);

        subtractAmount.setVisibility(View.GONE);
        amountCounter.setVisibility(View.GONE);
        addAmount.setVisibility(View.GONE);


        TableRowViewHolder etvh = new TableRowViewHolder(tableOrderItemView, name, size, price,amountCounter);
        return etvh;
    }

    @Override
    public void onBindViewHolder(TableRowViewHolder holder, int position) {
        final CommonSelectable<OrderPosition> selectable = orderItems.get(position);

        if(selectable.isSelected()){
            holder.getEventTablesView().setBackgroundColor(Color.LTGRAY);
        }else{
            holder.getEventTablesView().setBackgroundColor(Color.WHITE);
        }

        holder.getNameTextView().setText(selectable.getItem().product.productDescription.name);
        holder.getSizeTextView().setText(selectable.getItem().product.volume);
        holder.getPriceTextView().setText(selectable.getItem().product.price + "");



        holder.getEventTablesView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(selectable.getItem());
                selectable.toggleSelected();
                v.setSelected(selectable.isSelected());

                Log.d("SELECTED", "onClick: "+selectable.isSelected());

                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public ArrayList<String> getSelectedUUIDs(){
        ArrayList<String> list = new ArrayList<>();
        for (CommonSelectable<OrderPosition> sel: orderItems){
            if (sel.isSelected()){
                list.add(sel.getItem().getUuid().toString());
            }
        }
        return list;
    }
    public ArrayList<CommonSelectable<OrderPosition>> getSelectedOrderPositions(){
        ArrayList<CommonSelectable<OrderPosition>> list = new ArrayList<>();
        for (CommonSelectable<OrderPosition> sel: orderItems){
            if (sel.isSelected()){
                list.add(sel);
            }
        }
        return list;
    }
}
