package ch.hsr.edu.sinv_56082.gastroginiapp.domain.Event;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;
import java.util.UUID;

@Table(name = "Persons")
public class Person extends Model{

    @Column(unique = true)
    public UUID uuid;

    @Column
    public String name;

    @Column
    public String email;

    @Column
    public String phone;

    public List<WorkAssignment> workAssignments(){
        return getMany(WorkAssignment.class, "person");
    }

    public List<Event> eventsHosted(){
        return getMany(Event.class, "host");
    }

}
