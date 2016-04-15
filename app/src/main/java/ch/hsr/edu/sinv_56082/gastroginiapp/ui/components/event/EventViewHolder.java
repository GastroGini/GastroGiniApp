package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.event;

import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.TestViewHolder;


public class EventViewHolder extends TestViewHolder {
    @Bind(R.id.columnRowEventTitle) public TextView columnRowEventTitle;
    @Bind(R.id.columnRowStartDate) public TextView columnRowStartDate;
    @Bind(R.id.columnRowAmountOfTables) public TextView columnRowAmountOfTables;

    public EventViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
