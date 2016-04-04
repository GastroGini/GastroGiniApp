package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.event;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class EventsViewHolder extends RecyclerView.ViewHolder {
    View myEventView;
    TextView columnRowEventTitle;
    TextView columnRowAmountOfTables;
    TextView columnRowStartDate;
    ImageView columnRowEventDelete;

    public EventsViewHolder(View myEventView, TextView columnRowEventTitle, ImageView columnRowEventDelete,
                            TextView columnRowAmountOfTables, TextView columnRowStartDate) {
        super(myEventView);
        this.myEventView = myEventView;
        this.columnRowEventTitle = columnRowEventTitle;
        this.columnRowEventDelete = columnRowEventDelete;
        this.columnRowAmountOfTables = columnRowAmountOfTables;
        this.columnRowStartDate = columnRowStartDate;
    }

    public View getEventView(){
        return myEventView;
    }

    public TextView getTitleTextView(){
        return columnRowEventTitle;
    }

    public TextView getAmountOfTablesView(){
        return columnRowAmountOfTables;
    }

    public TextView getStartDateView(){
        return columnRowStartDate;
    }

    public ImageView getDeleteIconView(){
        return columnRowEventDelete;
    }
}
