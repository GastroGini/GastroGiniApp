package ch.hsr.edu.sinv_56082.gastroginiapp.domain.Orders;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.Event.EventTable;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.Event.Person;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.UUIDModel;

@Table(name = "Orders")
public class EventOrder extends UUIDModel{

    @Column
    public EventTable eventTable;

    @Column
    public Date orderTime;

    @Column
    public Person createdBy;

    public List<OrderPosition> orderPositions(){
        return getMany(OrderPosition.class, "eventOrder");
    }
}
