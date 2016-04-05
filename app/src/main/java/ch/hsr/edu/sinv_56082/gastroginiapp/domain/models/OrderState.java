package ch.hsr.edu.sinv_56082.gastroginiapp.domain.models;


import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

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

}
