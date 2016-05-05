package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.table;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderPosition;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.CommonSelectable;


public class TableRowAdapter extends RecyclerView.Adapter<TableRowViewHolder> {

    private TableItemClickListener listener;
    private TableRowViewHolder etvh;

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


        etvh = new TableRowViewHolder(tableOrderItemView,name, size,price);
        return etvh;
    }

    @Override
    public void onBindViewHolder(TableRowViewHolder holder, int position) {
        final CommonSelectable<OrderPosition> selectable = orderItems.get(position);

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
    public ArrayList<OrderPosition> getSelectedOrderPositions(){
        ArrayList<OrderPosition> list = new ArrayList<>();
        for (CommonSelectable<OrderPosition> sel: orderItems){
            if (sel.isSelected()){
                list.add(sel.getItem());
            }
        }
        return list;
    }
}
