package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.messages.authenticate;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Person;

public class Authenticate {

    public Person user;
    public String password;

    public Authenticate(Person user, String password) {
        this.user = user;
        this.password = password;
    }
}
