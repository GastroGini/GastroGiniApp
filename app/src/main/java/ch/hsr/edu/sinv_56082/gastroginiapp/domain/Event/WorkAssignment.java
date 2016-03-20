package ch.hsr.edu.sinv_56082.gastroginiapp.domain.Event;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;
import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.UUIDModel;

@Table(name = "WorkAssignments")
public class WorkAssignment extends UUIDModel{

    @Column
    public Date startTime;

    @Column
    public Date endTime;

    @Column
    public Person person;

    @Column
    public Event event;
}
