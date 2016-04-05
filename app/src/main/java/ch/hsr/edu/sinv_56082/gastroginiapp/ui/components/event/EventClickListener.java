package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.event;


import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;


public interface EventClickListener {
    void onClick(Event event, int identifier);
}
