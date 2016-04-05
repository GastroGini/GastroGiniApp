package ch.hsr.edu.sinv_56082.gastroginiapp.domain;


import com.activeandroid.query.Select;

import java.util.UUID;



public final class DataHandler {

    public <T extends UUIDModel> T queryByUUID(Class type, UUID uuid){
        return new Select().from(type).where("uuid = ?", uuid).executeSingle();
    }

    public <T extends UUIDModel> T queryByUUID(Class type, String uuid){
        return queryByUUID(type, UUID.fromString(uuid));
    }

}
