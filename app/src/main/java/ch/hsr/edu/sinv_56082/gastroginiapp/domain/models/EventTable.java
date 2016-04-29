package ch.hsr.edu.sinv_56082.gastroginiapp.domain.models;


import com.activeandroid.annotation.*;
import com.activeandroid.query.Select;

import java.util.List;
import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.UUIDModel;

@Table(name = "EventTables")
public class EventTable extends UUIDModel{

    public EventTable(){}

    public EventTable(int number, String name, Event event) {
        this.number = number;
        this.name = name;
        this.event = event;
    }

    @Column
    public int number;

    @Column
    public String name;

    @Column(onDelete = Column.ForeignKeyAction.CASCADE)
    public Event event;

    public List<EventOrder> orders(){
        return getMany(EventOrder.class, "eventTable");
    }

}
