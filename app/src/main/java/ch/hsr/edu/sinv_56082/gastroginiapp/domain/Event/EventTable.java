package ch.hsr.edu.sinv_56082.gastroginiapp.domain.Event;


import com.activeandroid.Model;
import com.activeandroid.annotation.*;

import java.util.List;
import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.Orders.EventOrder;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.UUIDModel;

@Table(name = "EventTables")
public class EventTable extends UUIDModel{

    @Column
    public int number;

    @Column
    public String name;

    @Column
    public Event event;

    public List<EventOrder> orders(){
        return getMany(EventOrder.class, "eventTable");
    }
}
