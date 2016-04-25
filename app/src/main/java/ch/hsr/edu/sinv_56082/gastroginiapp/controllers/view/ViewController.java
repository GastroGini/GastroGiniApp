package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.view;


import com.activeandroid.query.Select;

import java.util.List;
import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.Functions;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.Controller;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.UUIDModel;

public class ViewController<M extends UUIDModel> extends Controller {

    public List<M> getModelList() {
        return new Select().from(type).execute();
    }

    private Class<M> type;

    public ViewController(Class<M> clazz){
        type = clazz;
    }

    public M get(UUID uuid){
        return get(uuid.toString());
    }

    public M get(String uuid){
        return new Select().from(type).where("uuid=?", uuid).executeSingle();
    }

    public M create(Functions.Supplier<M> supplier){
        M model = supplier.supply();
        model.save();
        return model;
    }

    public void update(M newModel, Functions.Consumer<M> updater){
        M model = get(newModel.getUuid());
        updater.consume(model);
        model.save();
    }

    public void delete(M oldModel){
        M model = get(oldModel.getUuid());
        model.delete();
    }
}
