package ch.hsr.edu.sinv_56082.gastroginiapp.backup;


import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;
import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.UUIDModel;

@Table(name = "Persons")
public class Person extends UUIDModel{

    @Column
    public String firstName;

    @Column
    public String lastName;

    public Person(String firstName, String lastName){
        super();
        init(firstName, lastName);
    }

    public Person(UUID uuid, String firstName, String lastName){
        super(uuid);
        init(firstName, lastName);
    }

    private void init(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }

    public List<WorkAssignment> workAssignments(){
        return getMany(WorkAssignment.class, "person");
    }

    public List<Event> eventsHosted(){
        return getMany(Event.class, "host");
    }

}
