package ch.hsr.edu.sinv_56082.gastroginiapp.domain.models;


import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;
import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.UUIDModel;

@Table(name = "Orders")
public class EventOrder extends UUIDModel{

    public EventOrder(){}

    public EventOrder(EventTable eventTable, Date orderTime, Person createdBy) {
        this.eventTable = eventTable;
        this.orderTime = orderTime;
        this.createdBy = createdBy;
    }

    @Column(onDelete = Column.ForeignKeyAction.CASCADE)
    public Person createdBy;

    @Column(onDelete = Column.ForeignKeyAction.CASCADE)
    public EventTable eventTable;

    @Column
    public Date orderTime;

    public List<OrderPosition> orderPositions(){
        return getMany(OrderPosition.class, "eventOrder");
    }

}
