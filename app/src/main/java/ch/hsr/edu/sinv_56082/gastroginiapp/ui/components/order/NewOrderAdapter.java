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

public class NewOrderAdapter extends RecyclerView.Adapter<NewOrderAdapter.ViewHolder> {

    final private OnClickListener mListener;
    private int identifier;
    private List<Product> productList = new ArrayList<>();

    public interface OnClickListener {
        void onClick(Product product);
    }

    public NewOrderAdapter(OnClickListener mListener, List<Product> productList, int identifier){
        this.mListener = mListener;
        this.productList = productList;
        this.identifier = identifier;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View columnViewNewOrder = LayoutInflater.from(parent.getContext()).inflate(R.layout.column_row_product_description, parent, false);
        TextView textView = (TextView) columnViewNewOrder.findViewById(R.id.productDescriptionName);
        return new ViewHolder(columnViewNewOrder, textView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.getTextView().setText(productList.get(position).productDescription.name);
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick((Product) productList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView textView;
        public ViewHolder(View view, TextView textView) {
            super(view);
            this.view = view;
            this.textView = textView;
        }
        public View getView(){
            return view;
        }
        public TextView getTextView(){
            return textView;
        }
    }
}
