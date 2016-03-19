package ch.hsr.edu.sinv_56082.gastroginiapp.domain.Orders;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.UUID;

@Table(name = "OrderStates")
public class OrderState extends Model{

    @Column(unique = true)
    public UUID uuid;

    @Column
    public String name;

    @Column
    public String description;

}
