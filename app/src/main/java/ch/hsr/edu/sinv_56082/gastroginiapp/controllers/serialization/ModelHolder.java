package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.serialization;

import com.google.gson.Gson;

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

    public void fromJSON(String json){
        model = gson.fromJson(json, type);
    }

    public M getModel() {
        return model;
    }

    public void setModel(M model) {
        this.model = model;
    }
}
