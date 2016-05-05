package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.order;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderPosition;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.table.TableRowViewHolder;

public class OrderPayAdapter extends RecyclerView.Adapter<TableRowViewHolder> {

    public interface OrderItemClickListener {
        void onClick(OrderPosition orderPosition);
    }
    private List<OrderPosition> orderItems;

    OrderPayAdapter adapter;

    public OrderPayAdapter(List<OrderPosition> orderItems, OrderItemClickListener listener){
        adapter = this;
        OrderItemClickListener listener1 = listener;
        this.orderItems=orderItems;
    }
    @Override
    public TableRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View tableOrderItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.column_row_order_item, parent, false);

        TextView name = (TextView) tableOrderItemView.findViewById(R.id.product_item_name);
        TextView size = (TextView) tableOrderItemView.findViewById(R.id.product_item_size);
        TextView price = (TextView) tableOrderItemView.findViewById(R.id.product_item_price);


        TableRowViewHolder orderPayViewHolder = new TableRowViewHolder(tableOrderItemView, name, size, price);
        return orderPayViewHolder;
    }

    @Override
    public void onBindViewHolder(TableRowViewHolder holder, int position) {
        final OrderPosition selectable = orderItems.get(position);

        holder.getNameTextView().setText(selectable.product.productDescription.name);
        holder.getSizeTextView().setText(selectable.product.volume);
        holder.getPriceTextView().setText(selectable.product.price + "");
        Log.d("OrderPayAdapter", selectable.orderState.name);
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }
}
