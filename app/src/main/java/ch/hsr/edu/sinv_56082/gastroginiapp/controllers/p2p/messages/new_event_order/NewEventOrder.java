package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.messages.new_event_order;

import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventOrder;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderPosition;

public class NewEventOrder {

    public NewEventOrder(EventOrder order, List<OrderPosition> orderPositions) {
        this.order = order;
        this.orderPositions = orderPositions;
    }

    public EventOrder order;
    public List<OrderPosition> orderPositions;

}
