package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.event;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.event.EventListActivity;

public class EventsAdapter extends RecyclerView.Adapter<EventsViewHolder> implements Serializable {
    final private ItemClickListener mListener;
    private int identifier;
    private List<Event> eventList = new ArrayList<>();
    private boolean editMode = false;

    public EventsAdapter(ItemClickListener mListener, List<Event> eventList, int identifier){
        this.mListener = mListener;
        this.eventList = eventList;
        this.identifier = identifier;
    }

    @Override
    public EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View columnViewFeste = LayoutInflater.from(parent.getContext()).inflate(R.layout.column_row_events, parent, false);
        TextView columnRowEventTitle = (TextView) columnViewFeste.findViewById(R.id.columnRowEventTitle);
        ImageView columnRowEventDelete = (ImageView) columnViewFeste.findViewById(R.id.columnRowEventDeleteIcon);
        EventsViewHolder mevh = new EventsViewHolder(columnViewFeste,columnRowEventTitle,columnRowEventDelete);
        return mevh;
    }

    @Override
    public void onBindViewHolder(EventsViewHolder holder, int position) {
        final int pos = position;
        holder.getTitleTextView().setText(eventList.get(position).getTitle());
        if(getEditMode()){
            holder.getDeleteIconView().setVisibility(View.VISIBLE);
        }else{
            holder.getDeleteIconView().setVisibility(View.INVISIBLE);
        }
        if(mListener != null){
            holder.getDeleteIconView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eventList.remove(pos);
                    ((EventListActivity)mListener).checkIfEventListEmpty();
                    notifyDataSetChanged();
                }
            });
        }
        if(mListener !=null){
            holder.getEventView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(eventList.get(pos).getTitle(),pos,identifier);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public void changeEditMode(){
        this.editMode = !this.editMode;
    }

    private boolean getEditMode(){
        return editMode;
    }
}
