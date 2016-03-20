package ch.hsr.edu.sinv_56082.gastroginiapp.domain.Event;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;
import java.util.UUID;

@Table(name = "WorkAssignments")
public class WorkAssignment extends Model{

    @Column(unique = true)
    public UUID uuid;

    @Column
    public Date startTime;

    @Column
    public Date endTime;

    @Column
    public Person person;

    @Column
    public Event event;
}
