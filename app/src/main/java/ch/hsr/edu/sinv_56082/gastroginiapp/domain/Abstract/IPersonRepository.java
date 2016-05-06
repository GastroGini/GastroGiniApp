package ch.hsr.edu.sinv_56082.gastroginiapp.domain.Abstract;

import com.activeandroid.annotation.Column;

import java.util.List;
import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.Concreate.PersonRepository;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventOrder;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.WorkAssignment;

/**
 * Created by Dogan on 06.05.16.
 */
public interface IPersonRepository {


    public String getFullName();

    public List<WorkAssignment> workAssignments();

    public List<Event> eventsHosted();

    public List<EventOrder> ordersCreated();

}
