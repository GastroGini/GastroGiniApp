package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.order;

import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.common.CommonViewHolder;

public class EventTableViewHolder extends CommonViewHolder {
    @Bind(R.id.eventTableTitleText) public TextView eventTableTitleText;
    public EventTableViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
