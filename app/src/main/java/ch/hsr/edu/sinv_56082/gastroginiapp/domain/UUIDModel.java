package ch.hsr.edu.sinv_56082.gastroginiapp.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.UUID;

public class UUIDModel extends Model {

    @Column(unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public UUID uuid;

    public UUIDModel(){
        this.uuid = UUID.randomUUID();
    }

    public UUIDModel(UUID uuid){
        this.uuid = uuid;
    }

}
