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
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.TestSelectable;


public class TableRowAdapter extends RecyclerView.Adapter<TableRowViewHolder> {

    private TableItemClickListener listener;
    private TableRowViewHolder etvh;

    public interface TableItemClickListener {
        void onClick(OrderPosition orderPosition);
    }
    private List<TestSelectable<OrderPosition>> orderItems;

    TableRowAdapter adapter;

    public TableRowAdapter(List<OrderPosition> orderItems, TableItemClickListener listener){
        adapter = this;
        this.orderItems = new ArrayList<>();
        this.listener = listener;
        for (OrderPosition pos: orderItems){
            this.orderItems.add(new TestSelectable(pos));
        }
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
        final TestSelectable<OrderPosition> selectable = orderItems.get(position);

        holder.getNameTextView().setText(selectable.getItem().product.productDescription.name);
        holder.getSizeTextView().setText(selectable.getItem().product.volume);
        holder.getPriceTextView().setText(selectable.getItem().product.price + "");

        holder.getEventTablesView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(selectable.getItem());
            }
        });

        holder.getEventTablesView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                selectable.toggleSelected();
                v.setSelected(selectable.isSelected());

                Log.d("SELECTED", "onLongClick: "+selectable.isSelected());

                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public ArrayList<String> getSelectedUUIDs(){
        ArrayList<String> list = new ArrayList<>();
        for (TestSelectable<OrderPosition> sel: orderItems){
            if (sel.isSelected()){
                list.add(sel.getItem().getUuid().toString());
            }
        }
        return list;
    }
}
