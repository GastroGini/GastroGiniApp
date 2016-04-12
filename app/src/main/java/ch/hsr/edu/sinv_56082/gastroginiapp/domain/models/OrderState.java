package ch.hsr.edu.sinv_56082.gastroginiapp.domain.models;


import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;
import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.UUIDModel;

@Table(name = "OrderStates")
public class OrderState extends UUIDModel{

    public OrderState(){}
    
    public OrderState(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Column
    public String name;

    @Column
    public String description;

    public List<OrderPosition> orderPositions(){
        return getMany(OrderPosition.class, "orderState");
    }

    public static OrderState get(UUID uuid) {
        return new Select().from(OrderState.class).where("uuid=?", uuid.toString()).executeSingle();
    }
}
