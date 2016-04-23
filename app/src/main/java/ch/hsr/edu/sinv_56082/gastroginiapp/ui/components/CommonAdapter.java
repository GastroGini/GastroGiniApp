package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class CommonAdapter<ET,VH extends CommonViewHolder> extends RecyclerView.Adapter<VH> {

    public void setList(List<ET> items) {
        this.items = items;
        notifyDataSetChanged();
    }
    public List<ET> getList(){
        return items;
    }

    public interface Listener<ET>{
        void onItemClick(ET item);
        void onDelete(ET item);
    }

    private int resourceFile;
    public Listener<ET> listener;
    List<ET> items;

    private boolean editMode = false;

    public CommonAdapter(int resourceFile, List<ET> items, Listener<ET> listener){
        this.resourceFile = resourceFile;
        this.listener = listener;
        this.items = items;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resourceFile, parent, false);
        return createItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        final ET item = items.get(position);

        if(isEditMode()){
            holder.delete_button.setVisibility(View.VISIBLE);
        }else{
            holder.delete_button.setVisibility(View.GONE);
        }

        if (listener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });

            holder.delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDelete(item);
                }
            });
        }

        bindViewHolder(holder, item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
        notifyDataSetChanged();
    }

    public abstract VH createItemViewHolder(View view);
    public abstract void bindViewHolder(VH holder, ET item);




}
