package ch.hsr.edu.sinv_56082.gastroginiapp.domain.Concreate;

import android.util.Log;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provider;

import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.Abstract.IPersonRepository;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventOrder;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Person;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.WorkAssignment;

/**
 * Created by Dogan on 06.05.16.
 */
public class PersonRepository implements IPersonRepository  {
    @Inject
    Person p;


    @Override
    public String getFullName() {
        return p.getFullName();
    }

    @Override
    public List<WorkAssignment> workAssignments() {
        return p.workAssignments();
    }

    @Override
    public List<Event> eventsHosted() {
        return p.eventsHosted();
    }

    @Override
    public List<EventOrder> ordersCreated() {
        return p.ordersCreated();
    }
}