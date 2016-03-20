package ch.hsr.edu.sinv_56082.gastroginiapp.domain.Event;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.products.ProductList;

@Table(name = "Events")
public class Event extends Model{

    @Column(unique = true)
    public UUID uuid;

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

    public List<EventTable> eventTables(){
        return getMany(EventTable.class, "event");
    }

    public List<WorkAssignment> workAssignments(){
        return getMany(WorkAssignment.class, "event");
    }

}
