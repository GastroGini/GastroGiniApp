package ch.hsr.edu.sinv_56082.gastroginiapp.domain.models;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.UUIDModel;

@Table(name = "Events")
public class Event extends UUIDModel{

    public Event(){}

    public Event(ProductList productList, String name, Date startTime, Date endTime, Person host) {
        super();
        this.productList = productList;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.host = host;
    }

    @Column
    public String name;

    @Column
    public Date startTime;

    @Column
    public Date endTime;

    @Column(onDelete = Column.ForeignKeyAction.RESTRICT)
    public Person host;

    @Column(onDelete = Column.ForeignKeyAction.RESTRICT)
    public ProductList productList;



    public Event(String name, Date startTime, Date endTime, Person host, ProductList productList){
        super();
        init(name, startTime, endTime, host, productList);
    }

    public Event(UUID uuid, String name, Date startTime, Date endTime, Person host, ProductList productList){
        super();
        init(name,  startTime,  endTime,  host,  productList);
    }

    private void init(String name, Date startTime, Date endTime, Person host, ProductList productList) {
        this.name = name;
        this.host = host;
        this.productList = productList;
        this.endTime = endTime;
        this.startTime = startTime;
    }

    public List<EventTable> eventTables(){
        return getMany(EventTable.class, "event");
    }

    public List<WorkAssignment> workAssignments(){
        return getMany(WorkAssignment.class, "event");
    }

}
