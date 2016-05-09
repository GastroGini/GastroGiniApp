package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.order;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderPosition;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Product;
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
        View tableOrderItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.column_row_order_pay_item, parent, false);

        TextView name = (TextView) tableOrderItemView.findViewById(R.id.product_item_name_order_pay);
        TextView size = (TextView) tableOrderItemView.findViewById(R.id.product_item_size_order_pay);
        TextView price = (TextView) tableOrderItemView.findViewById(R.id.product_item_price_order_pay);
        TextView amountCounter = (TextView) tableOrderItemView.findViewById(R.id.amount_counter_order_pay);
        TableRowViewHolder orderPayViewHolder = new TableRowViewHolder(tableOrderItemView, name, size, price, amountCounter);
        return orderPayViewHolder;
    }

    @Override
    public void onBindViewHolder(TableRowViewHolder holder, int position) {
        final OrderPosition selectable = orderItems.get(position);

        holder.getNameTextView().setText(selectable.product.productDescription.name);
        holder.getSizeTextView().setText(selectable.product.volume);
        holder.getPriceTextView().setText(selectable.product.price + "");
        holder.getAmountCounterView().setText("1");
        Log.d("OrderPayAdapter", selectable.orderState.name);
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }
}
