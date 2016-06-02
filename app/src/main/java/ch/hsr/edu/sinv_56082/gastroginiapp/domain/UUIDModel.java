package ch.hsr.edu.sinv_56082.gastroginiapp.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;

import java.util.UUID;

public abstract class UUIDModel extends Model {

    public UUID getUuid() {
        return UUID.fromString(uuid_column);
    }

    public void setUuid(UUID uuid) {
        this.uuid_column = uuid.toString();
    }

    @Column(name = "uuid",unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public String uuid_column;

    public UUIDModel(){
        setUuid(UUID.randomUUID());
    }

    public UUIDModel(UUID uuid){
        setUuid(uuid);
    }

}
