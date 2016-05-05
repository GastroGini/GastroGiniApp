package ch.hsr.edu.sinv_56082.gastroginiapp.p2p;

import java.util.Date;
import java.util.UUID;

public class TransferEvent {

    public UUID uuid;
    public String name;
    public Date startDate;

    public TransferEvent(UUID uuid, String name, Date startDate) {
        this.uuid = uuid;
        this.name = name;
        this.startDate = startDate;
    }
}
