package ch.hsr.edu.sinv_56082.gastroginiapp.domain.models;


import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.UUIDModel;

@Table(name = "WorkAssignments")
public class WorkAssignment extends UUIDModel{

    public WorkAssignment(){}

    public WorkAssignment(Date startTime, Date endTime, Person person, Event event) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.person = person;
        this.event = event;
    }

    @Column
    public Date startTime;

    @Column
    public Date endTime;

    @Column
    public Person person;

    @Column
    public Event event;
}
