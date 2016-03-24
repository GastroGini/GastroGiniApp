package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.Fest;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.Tisch;

public class TischeAdapter extends RecyclerView.Adapter<TischeViewHolder> {
    private List<Tisch> tischeList;
    private ItemClickListener mListener;

    public TischeAdapter(ItemClickListener mListener, List<Tisch> tischeList){
        this.mListener = mListener;
        this.tischeList = tischeList;
    }

    @Override
    public TischeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View columnViewTische = LayoutInflater.from(parent.getContext()).inflate(R.layout.column_row_tische, parent, false);
        TextView tischTitle = (TextView) columnViewTische.findViewById(R.id.column_tischText);
        TischeViewHolder tvh = new TischeViewHolder(columnViewTische,tischTitle);
        return tvh;
    }

    @Override
    public void onBindViewHolder(TischeViewHolder holder, int position) {
        final int pos = position;
        holder.getTischDescription().setText(tischeList.get(position).getTischDescription());
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.onItemClicked(tischeList.get(pos));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tischeList.size();
    }
}
