package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.order;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Product;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.common.CommonSelectable;


public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder>{
    private ProductItemClickListener listener;
    Map<Product,List<Product>> mappedProducts;

    public interface ProductItemClickListener {
        void onClick(Product product);
        void onDelete(Product product);
    }
    private List<CommonSelectable<Product>> orderItems;

    ProductAdapter adapter;

    public ProductAdapter(List<Product> orderItems, ProductItemClickListener listener){
        adapter = this;
        this.listener = listener;
        setOrUpdateContainers(orderItems);
    }

    private void setOrUpdateContainers(List<Product> orderItems) {
        mappedProducts = new HashMap<>();
        this.orderItems = new ArrayList<>();
        createProductMap(orderItems, mappedProducts);
        for (Product pos: orderItems){
            this.orderItems.add(new CommonSelectable<>(pos));
        }
    }

    private void createProductMap(List<Product> orderItems, Map<Product, List<Product>> mappedProducts) {
        for(Product item : orderItems){
            if(!mappedProducts.containsKey(item)){
                mappedProducts.put(item,new ArrayList<Product>());
                mappedProducts.get(item).add(item);
            }else{
                mappedProducts.get(item).add(item);
            }
        }
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View ProductItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.column_row_order_item, parent, false);

        TextView name = (TextView) ProductItemView.findViewById(R.id.product_item_name);
        TextView size = (TextView) ProductItemView.findViewById(R.id.product_item_size);
        TextView price = (TextView) ProductItemView.findViewById(R.id.product_item_price);
        ImageView subtractAmount = (ImageView) ProductItemView.findViewById(R.id.subtractAmount);
        TextView amountCounter = (TextView) ProductItemView.findViewById(R.id.amountCounter);
        ImageView addAmount = (ImageView) ProductItemView.findViewById(R.id.addAmount);


        ProductViewHolder productViewHolder = new ProductViewHolder(ProductItemView, name, size, price,
                subtractAmount,amountCounter,addAmount);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        final CommonSelectable<Product> selectable = this.orderItems.get(position);
        Product item  =   selectable.getItem();

        String name =  (item != null && item.productDescription != null && item.productDescription.name != null)
                        ? item.productDescription.name
                        : "";
        holder.getNameTextView().setText(name);
        holder.getSizeTextView().setText(selectable.getItem().volume);
        holder.getPriceTextView().setText(selectable.getItem().price + "");

//        for(CommonSelectable<Product> orderItem :orderItems){
//            if(orderItem.getItem().equals(item)){
//                holder.setCount(holder.getCount() + 1);
//            }
//        }

//        holder.getEventTablesView().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.increaseCount();
//                holder.getAmountCounterView().setText(holder.getCount() + "");
//                listener.onClick(selectable.getItem());
//            }
//        });

        holder.getSubtractAmountView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.decreaseCount();
                holder.getAmountCounterView().setText(holder.getCount() + "");
                listener.onDelete(selectable.getItem());
            }
        });

        holder.getAddAmountView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.increaseCount();
                holder.getAmountCounterView().setText(holder.getCount() + "");
                listener.onClick(selectable.getItem());
            }
        });
    }
    @Override
    public int getItemCount() {
        return this.orderItems.size();
    }
}
