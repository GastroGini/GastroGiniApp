package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.menu;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductList;


public class MenuProductListAdapter extends RecyclerView.Adapter<MenuProductListAdapter.ViewHolder> {

    public interface OnClickListener {
        void onClick(ProductList productList);
    }

    OnClickListener listener;
    List<ProductList> productLists = new ArrayList<>();

    public MenuProductListAdapter(OnClickListener view, List<ProductList> productLists){
        this.productLists = productLists;
        this.listener = view;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.column_row_menucard, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.menucardRowItem);
        return new ViewHolder(view, textView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.getTextView().setText(productLists.get(position).name);
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(productLists.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return productLists.size();
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
