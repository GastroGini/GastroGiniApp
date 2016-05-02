package ch.hsr.edu.sinv_56082.gastroginiapp.p2p;

import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Product;

public class InitialDataMessage {

    public InitialDataMessage(Event event, List<Product> productList) {
        this.event = event;
        this.productList = productList;
    }

    Event event;
    List<Product> productList;

}
