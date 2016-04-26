package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.app;

import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.Controller;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.view.ViewController;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Person;

public class UserController extends Controller{

    Person localPerson;

    public UserController() {
        localPerson = new ViewController<>(Person.class).get(app.getLocalUser());
    }

    public Person getUser(){
        return localPerson;
    }

    public void saveUser(String name){
        localPerson.firstName = name;
        localPerson.save();
    }
}
