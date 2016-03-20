package ch.hsr.edu.sinv_56082.gastroginiapp.domain.Orders;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;
import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.products.Product;


@Table(name = "OrderPositions")
public class OrderPosition extends Model{

    @Column(unique = true)
    public UUID uuid;

    @Column
    public Date payTime;

    @Column
    public OrderState orderState;

    @Column
    public Product product;

    @Column
    public EventOrder eventOrder;
}
