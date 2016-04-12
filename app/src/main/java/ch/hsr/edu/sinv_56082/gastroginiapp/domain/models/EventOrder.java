package ch.hsr.edu.sinv_56082.gastroginiapp.domain.models;


import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.UUIDModel;

@Table(name = "Orders")
public class EventOrder extends UUIDModel{

    public EventOrder(){}

    public EventOrder(EventTable eventTable, Date orderTime) {
        this.eventTable = eventTable;
        this.orderTime = orderTime;
    }

    @Column
    public EventTable eventTable;

    @Column
    public Date orderTime;

    public List<OrderPosition> orderPositions(){
        return getMany(OrderPosition.class, "eventOrder");
    }

    public static EventOrder get(UUID uuid) {
        return new Select().from(EventOrder.class).where("uuid=?", uuid.toString()).executeSingle();
    }
}
