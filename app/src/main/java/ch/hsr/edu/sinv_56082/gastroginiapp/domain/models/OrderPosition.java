package ch.hsr.edu.sinv_56082.gastroginiapp.domain.models;


import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.UUIDModel;


@Table(name = "OrderPositions")
public class OrderPosition extends UUIDModel{

    public OrderPosition(){}

    public OrderPosition(Date payTime, OrderState orderState, Product product, EventOrder eventOrder) {
        this.payTime = payTime;
        this.orderState = orderState;
        this.product = product;
        this.eventOrder = eventOrder;
    }

    @Column
    public Date payTime;

    @Column
    public OrderState orderState;

    @Column
    public Product product;

    @Column
    public EventOrder eventOrder;
}
