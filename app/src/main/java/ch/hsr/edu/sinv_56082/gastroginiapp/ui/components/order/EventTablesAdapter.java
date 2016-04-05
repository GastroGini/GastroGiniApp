package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.order;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventTable;

public class EventTablesAdapter extends RecyclerView.Adapter<EventTablesViewHolder> {
    private List<EventTable> tables;
    private EventTableClickListener mListener;
    public EventTablesAdapter(EventTableClickListener mListener,List<EventTable> tables){
        this.tables = tables;
        this.mListener = mListener;
    }

    @Override
    public EventTablesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View columnRowEventTablesView = LayoutInflater.from(parent.getContext()).inflate(R.layout.column_row_event_tables, parent, false);
        TextView columnRowEventTableTitleText = (TextView) columnRowEventTablesView.findViewById(R.id.eventTableTitleText);
        EventTablesViewHolder etvh = new EventTablesViewHolder(columnRowEventTablesView,columnRowEventTableTitleText);
        return etvh;
    }

    @Override
    public void onBindViewHolder(EventTablesViewHolder holder, int position) {
        holder.getEventTableTextView().setText(tables.get(position).name);
        holder.getEventTablesView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Implement click logic for list item
            }
        });
    }

    @Override
    public int getItemCount() {
        return tables.size();
    }
}
