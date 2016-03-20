package ch.hsr.edu.sinv_56082.gastroginiapp.domain.Orders;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;
import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.UUIDModel;

@Table(name = "OrderStates")
public class OrderState extends UUIDModel{


    @Column
    public String name;

    @Column
    public String description;

    public List<OrderPosition> orderPositions(){
        return getMany(OrderPosition.class, "orderState");
    }

}
