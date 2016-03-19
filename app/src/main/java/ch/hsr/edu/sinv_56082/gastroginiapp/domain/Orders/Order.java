package ch.hsr.edu.sinv_56082.gastroginiapp.domain.Orders;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;
import java.util.UUID;

@Table(name = "Orders")
public class Order extends Model{

    @Column(unique = true)
    public UUID uuid;

    @Column
    public Table table;

    @Column
    public Date orderTime;
}
