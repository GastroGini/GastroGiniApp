package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.menu;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductDescription;


public class MenuProductDescriptionAdapter extends RecyclerView.Adapter<MenuProductDescriptionAdapter.ViewHolder> {

    public interface OnClickListener {
        void onClick(ProductDescription productList);
    }

    OnClickListener listener;
    List<ProductDescription> productDescs = new ArrayList<>();

    public MenuProductDescriptionAdapter(OnClickListener view, List<ProductDescription> productDescs){
        this.productDescs = productDescs;
        this.listener = view;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.column_row_product_description, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.productDescriptionName);
        TextView desc = (TextView) view.findViewById(R.id.productDescriptionDesc);
        return new ViewHolder(view, textView, desc);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.getTextView().setText(productDescs.get(position).name);
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(productDescs.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return productDescs.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView textView;
        TextView desc;
        public ViewHolder(View view, TextView textView, TextView desc) {
            super(view);
            this.view = view;
            this.textView = textView;
            this.desc = desc;
        }
        public View getView(){
            return view;
        }
        public TextView getTextView(){
            return textView;
        }
        public TextView getDesc(){
            return desc;
        }
    }

}
