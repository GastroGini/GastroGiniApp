package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.serialization;

import android.util.Log;

import com.google.gson.Gson;

import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.Consumer;
import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.Supplier;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.view.ViewController;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.UUIDModel;

public class ModelHolder<M extends UUIDModel> {

    private M model;
    private Gson gson;
    private Class<M> type;

    public ModelHolder(Class<M> type){
        this.type = type;
        gson = Serializer.get();
    }

    public String toJSON(){
        return gson.toJson(model);
    }

    public ModelHolder<M> fromJSON(String json){
        model = gson.fromJson(json, type);
        return this;
    }

    public M getModel() {
        return model;
    }

    public ModelHolder<M> setModel(M model) {
        this.model = model;
        return this;
    }


    public ModelHolder<M> updateOrSave(Consumer<M> onUpdate){
        if (model == null) return this;

        ViewController<M> controller = new ViewController<>(type);
        M oldModel = controller.get(model.getUuid());
        if (oldModel != null) {
            controller.update(model, onUpdate);
        } else {
            M newModel = controller.prepare(new Supplier<M>() {
                @Override
                public M supply() {
                    return model;
                }
            });
            controller.update(newModel, onUpdate);
        }
        this.model = controller.get(model.getUuid());
//      Log.d("asdf", "updateOrSave: "+model.getId()+model+model.getUuid());
        return this;
    }
}
