package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.menu;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductList;


public class MenuProductListAdapter extends RecyclerView.Adapter<MenuProductListAdapter.ViewHolder> {
    boolean isMenuCardListEditable ;

    MenuProductListClickListener listener;
    List<ProductList> productLists = new ArrayList<>();

    public MenuProductListAdapter(MenuProductListClickListener view, List<ProductList> productLists, boolean isMenuCardListEditable){
        this.productLists = productLists;
        this.listener = view;
        this.isMenuCardListEditable = isMenuCardListEditable;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.column_row_product_list, parent, false);


        ImageView edit = (ImageView) view.findViewById(R.id.columnRowEventEditIcon) ;
        ImageView delete = (ImageView) view.findViewById(R.id.delete_button) ;
        TextView textView = (TextView) view.findViewById(R.id.menucardRowItem);



        return new ViewHolder(view, textView,edit, delete);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.getTextView().setText(productLists.get(position).name);


        holder.getDeleteImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.deleteItem(productLists.get(position));
            }
        });


        holder.getEditImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.editItem(productLists.get(position));
            }
        });

        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(productLists.get(position));

            }
        });

        if(this.isMenuCardListEditable == false){

            holder.getEditImageView().setVisibility(View.GONE);
            holder.getDeleteImageView().setVisibility(View.GONE);
        }
        else{
            holder.getEditImageView().setVisibility(View.VISIBLE);
            holder.getDeleteImageView().setVisibility(View.VISIBLE);

        }




    }

    @Override
    public int getItemCount() {
        return productLists.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView textView;
        ImageView delete;
        ImageView edit;
        public ViewHolder(View view, TextView textView, ImageView edit, ImageView delete) {
            super(view);
            this.view = view;
            this.textView = textView;
            this.edit = edit;
            this.delete = delete;
        }
        public View getView(){
            return view;
        }
        public TextView getTextView(){
            return textView;
        }
        public ImageView getDeleteImageView(){
            return delete;
        }
        public ImageView getEditImageView(){
            return edit;
        }
    }

}
