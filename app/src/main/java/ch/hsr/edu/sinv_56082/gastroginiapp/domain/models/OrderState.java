package ch.hsr.edu.sinv_56082.gastroginiapp.domain.models;


import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;
import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.UUIDModel;

@Table(name = "OrderStates")
public class OrderState extends UUIDModel{

    public static OrderState STATE_OPEN;
    public static OrderState STATE_PAYED;

    public static void loadOrderStates(){
        STATE_OPEN = new OrderState("Open", "unpayed");
        STATE_PAYED = new OrderState("Payed", "Payed");
        STATE_OPEN.save();
        STATE_PAYED.save();
    }

    public OrderState(){}
    
    public OrderState(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Column(unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public String name;

    @Column
    public String description;

    public List<OrderPosition> orderPositions(){
        return getMany(OrderPosition.class, "orderState");
    }
}
