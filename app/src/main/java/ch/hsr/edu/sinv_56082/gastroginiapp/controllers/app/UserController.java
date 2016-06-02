package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.app;

import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.view.ViewController;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Person;

public class UserController {

    Person localPerson;

    public UserController() {
        localPerson = new ViewController<>(Person.class).get(App.getApp().getLocalUser());
    }

    public Person getUser(){
        return localPerson;
    }

    public void saveUser(String name){
        localPerson.firstName = name;
        localPerson.save();
    }
}
