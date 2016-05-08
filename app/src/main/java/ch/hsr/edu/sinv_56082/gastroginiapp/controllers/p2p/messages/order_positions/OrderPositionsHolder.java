package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.messages.order_positions;

import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderPosition;

public class OrderPositionsHolder {
    public OrderPositionsHolder(List<OrderPosition> orderPositions) {
        this.orderPositions = orderPositions;
    }

    public List<OrderPosition> orderPositions;
}
