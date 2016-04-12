package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.order;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderPosition;

/**
 * Created by Phil on 07.04.2016.
 */
public class OrderPositionAdapter extends RecyclerView.Adapter<OrderPositionAdapter.ViewHolder> {

    public interface OnClickListener {
        void onClick(OrderPosition myOrderPositionList);
    }
    OnClickListener listener;
    List<OrderPosition> myOrderPositionList = new ArrayList<>();

    public OrderPositionAdapter(OnClickListener view, List<OrderPosition> myOrderPositionList){
        this.myOrderPositionList = myOrderPositionList;
        this.listener = view;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.column_row_table_order, parent, false);
        return new ViewHolder(view, (TextView) view.findViewById(R.id.orderPositionName));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.getTextView().setText(myOrderPositionList.get(position).product.productDescription.name+
                "    "+String.valueOf(myOrderPositionList.get(position).product.price));
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(myOrderPositionList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return myOrderPositionList.size();
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