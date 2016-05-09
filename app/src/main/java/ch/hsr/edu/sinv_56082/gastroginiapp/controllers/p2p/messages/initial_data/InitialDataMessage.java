package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.messages.initial_data;

import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventTable;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Product;

public class InitialDataMessage {
    public InitialDataMessage(Event event, List<Product> products, List<EventTable> tables) {
        this.event = event;
        this.products = products;
        this.tables = tables;
    }

    public Event event;
    public List<Product> products;
    public List<EventTable> tables;
}
