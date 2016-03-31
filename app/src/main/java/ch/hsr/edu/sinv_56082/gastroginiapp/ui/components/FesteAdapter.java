package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.Fest;

public class FesteAdapter extends RecyclerView.Adapter<FesteViewHolder> {
    private List<Fest> festeList;
    private ItemClickListener mListener;
    //temporary static, for prototype pres.
    public static List<Boolean> checkBoxStates;
    private boolean editMode = false;

    public FesteAdapter(ItemClickListener mListener, List<Fest> festeList){
        this.mListener = mListener;
        this.festeList = festeList;
        checkBoxStates = new ArrayList<>();
        for(int i = 0; i < festeList.size();i++){
            checkBoxStates.add(false);
        }
    }

    @Override
    public FesteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View columnViewFeste = LayoutInflater.from(parent.getContext()).inflate(R.layout.column_row_feste, parent, false);
        TextView festTitle = (TextView) columnViewFeste.findViewById(R.id.column_festText);
        CheckBox festeBox = (CheckBox) columnViewFeste.findViewById(R.id.festeListCheckBox);
        FesteViewHolder fvh = new FesteViewHolder(columnViewFeste,festTitle, festeBox);
        return fvh;
    }

    @Override
    public void onBindViewHolder(FesteViewHolder holder, final int position) {
        final int pos = position;
        boolean checked = checkBoxStates.get(position);
        holder.getFesteBox().setChecked(checked);
        if(editMode){
            holder.getFesteBox().setVisibility(View.VISIBLE);
        }else{
            holder.getFesteBox().setVisibility(View.INVISIBLE);
        }
        holder.getFesteBox().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxStates.set(pos, !checkBoxStates.get(pos));
            }
        });
        holder.getFestTitle().setText(festeList.get(position).getTitle());
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.onItemClicked(festeList.get(pos), position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return festeList.size();
    }
    public void setCheckBox(boolean state){
        editMode = state;
    }

    public List<Boolean> getSelectedPositions() {
        return checkBoxStates;
    }

    public void clearSelections(){
        for(Boolean pos: checkBoxStates){
            pos = false;
        }
    }

    public boolean getEditMode(){
        return editMode;
    };

    public void refreshList(){
        notifyDataSetChanged();
    }
}
