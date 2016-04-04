package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.event;

import android.view.View;

import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.Event;

public interface EventClickListener {
    void onClick(Event event, int position, int identifier);
}
