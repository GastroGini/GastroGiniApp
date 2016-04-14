package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.menu;

import android.view.View;
import android.widget.TextView;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.TestViewHolder;

public class DescViewHolder extends TestViewHolder{
        public TextView name;
        public TextView desc;
        public DescViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.productDescriptionName);
            desc = (TextView)itemView.findViewById(R.id.productDescriptionDesc);
        }

}
