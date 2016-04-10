package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.event;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.event.EventListActivity;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.DateHelpers;

public class EventsAdapter extends RecyclerView.Adapter<EventsViewHolder> implements Serializable {
    final private EventClickListener mListener;
    private int identifier;
    private Context context;
    private List<Event> eventList = new ArrayList<>();
    private boolean editMode = false;

    public EventsAdapter(EventClickListener mListener, List<Event> eventList, int identifier, Context context){
        this.mListener = mListener;
        this.eventList = eventList;
        this.identifier = identifier;
        this.context = context;
    }

    @Override
    public EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View columnViewFeste = LayoutInflater.from(parent.getContext()).inflate(R.layout.column_row_events, parent, false);
        return new EventsViewHolder(
                columnViewFeste,
                (TextView) columnViewFeste.findViewById(R.id.columnRowEventTitle),
                (ImageView) columnViewFeste.findViewById(R.id.columnRowEventDeleteIcon),
                (TextView) columnViewFeste.findViewById(R.id.columnRowAmountOfTables),
                (TextView) columnViewFeste.findViewById(R.id.columnRowStartDate)
        );
    }

    @Override
    public void onBindViewHolder(EventsViewHolder holder, int position) {
        final int pos = position;
        holder.getAmountOfTablesView().setText(eventList.get(pos).eventTables().size() + "");
        holder.getStartDateView().setText(DateHelpers.dateToString(context,eventList.get(pos).startTime));
        holder.getTitleTextView().setText(eventList.get(position).name);
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
                    mListener.onClick(eventList.get(pos),identifier);
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
