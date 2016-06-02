package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.order;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderPosition;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Product;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.common.CommonSelectable;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.table.TableRowViewHolder;

public class OrderPayAdapter extends RecyclerView.Adapter<TableRowViewHolder> {
    private OrderItemClickListener listener;
    private Map<Product,List<Product>> mappedProducts = new HashMap();
    private List<CommonSelectable<Product>> productList = new ArrayList<>();
    public interface OrderItemClickListener {
        void onClick(OrderPosition orderPosition);
    }
    private List<OrderPosition> orderItems;
    OrderPayAdapter adapter;

    public OrderPayAdapter(List<OrderPosition> orderItems, OrderItemClickListener listener){
        adapter = this;
         this.listener = listener;
        createOrderPositionMap(orderItems,mappedProducts);
        Iterator<Product> iterator = mappedProducts.keySet().iterator();
        while(iterator.hasNext()){
            productList.add(new CommonSelectable<>(iterator.next()));
        }
        this.orderItems=orderItems;
    }

    private void createOrderPositionMap(List<OrderPosition> orderItems, Map<Product, List<Product>> mappedOrderPositions) {
        for(OrderPosition orderPosition : orderItems){
            Product item = orderPosition.product;
            if(!mappedProducts.containsKey(item)){
                mappedProducts.put(item,new ArrayList<Product>());
                mappedProducts.get(item).add(item);
            }else{
                mappedProducts.get(item).add(item);
            }
        }
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
        final CommonSelectable<Product> selectable = productList.get(position);

        holder.getNameTextView().setText(selectable.getItem().productDescription.name);
        holder.getSizeTextView().setText(selectable.getItem().volume);
        holder.getPriceTextView().setText(selectable.getItem().price + "");
        holder.getAmountCounterView().setText(mappedProducts.get(selectable.getItem()).size()+"");
        Log.d("OrderPayAdapter", selectable.getItem().productDescription.name);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
