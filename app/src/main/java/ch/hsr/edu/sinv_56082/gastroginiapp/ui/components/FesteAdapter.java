package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.Fest;

public class FesteAdapter extends RecyclerView.Adapter<FesteViewHolder> {
    private List<Fest> festeList;
    private ItemClickListener mListener;

    public FesteAdapter(ItemClickListener mListener, List<Fest> festeList){
        this.mListener = mListener;
        this.festeList = festeList;
    }

    @Override
    public FesteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View columnViewFeste = LayoutInflater.from(parent.getContext()).inflate(R.layout.column_row_feste, parent, false);
        TextView festTitle = (TextView) columnViewFeste.findViewById(R.id.column_festText);
        FesteViewHolder fvh = new FesteViewHolder(columnViewFeste,festTitle);
        return fvh;
    }

    @Override
    public void onBindViewHolder(FesteViewHolder holder, int position) {
        final int pos = position;
        holder.getFestTitle().setText(festeList.get(position).getTitle());
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.onItemClicked(festeList.get(pos));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return festeList.size();
    }
}
