package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.order;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Product;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.CommonSelectable;


public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder>{
    private ProductItemClickListener listener;
    private ProductViewHolder productViewHolder;

    public interface ProductItemClickListener {
        void onClick(Product product);
    }
    private List<CommonSelectable<Product>> orderItems;

    ProductAdapter adapter;

    public ProductAdapter(List<Product> orderItems, ProductItemClickListener listener){
        adapter = this;
        this.orderItems = new ArrayList<>();
        this.listener = listener;
        for (Product pos: orderItems){
            this.orderItems.add(new CommonSelectable<Product>(pos));
        }
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View ProductItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.column_row_order_item, parent, false);

        TextView name = (TextView) ProductItemView.findViewById(R.id.product_item_name);
        TextView size = (TextView) ProductItemView.findViewById(R.id.product_item_size);
        TextView price = (TextView) ProductItemView.findViewById(R.id.product_item_price);


        productViewHolder = new ProductViewHolder(ProductItemView,name, size,price);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        final CommonSelectable<Product> selectable = orderItems.get(position);
        Product item  =   selectable.getItem();

        String name =  (item != null && item.productDescription != null && item.productDescription.name != null)
                        ? item.productDescription.name
                        : "";


        holder.getNameTextView().setText(name);
        holder.getSizeTextView().setText(selectable.getItem().volume);
        holder.getPriceTextView().setText(selectable.getItem().price + "");

        holder.getEventTablesView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(selectable.getItem());
            }
        });

    }
    @Override
    public int getItemCount() {
        return orderItems.size();
    }
}
