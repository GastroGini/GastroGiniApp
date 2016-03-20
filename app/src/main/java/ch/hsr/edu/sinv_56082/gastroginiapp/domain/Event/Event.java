package ch.hsr.edu.sinv_56082.gastroginiapp.domain.Event;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.UUIDModel;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.products.ProductList;

@Table(name = "Events")
public class Event extends UUIDModel{

    @Column
    public String name;

    @Column
    public Date startTime;

    @Column
    public Date endTime;

    @Column
    public Person host;

    @Column
    public ProductList productList;



    public Event(String name, Date startTime, Date endTime, Person host, ProductList productList){
        super();
        init(name, startTime, endTime, host, productList);
    }

    public Event(UUID uuid, String name, Date startTime, Date endTime, Person host, ProductList productList){
        super(uuid);
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
