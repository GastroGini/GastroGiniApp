package ch.hsr.edu.sinv_56082.gastroginiapp.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;

import java.util.UUID;

public class UUIDModel extends Model {

    @Column(unique = true)
    public UUID uuid;

    public UUIDModel(){
        this.uuid = UUID.randomUUID();
    }

    public UUIDModel(UUID uuid){
        this.uuid = uuid;
    }

    public UUIDModel(String uuidString){
        this.uuid = UUID.fromString(uuidString);
    }

}
