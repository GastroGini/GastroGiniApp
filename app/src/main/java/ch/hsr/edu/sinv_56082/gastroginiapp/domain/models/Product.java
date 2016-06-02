package ch.hsr.edu.sinv_56082.gastroginiapp.domain.models;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.UUIDModel;

@Table(name = "Products")
public class Product extends UUIDModel implements Serializable{
    @Column
    public double price;

    @Column
    public String volume;

    @Column(onDelete = Column.ForeignKeyAction.CASCADE)
    public ProductDescription productDescription;

    @Column(onDelete = Column.ForeignKeyAction.CASCADE)
    public ProductList productList;


    public Product(){}

    public Product(ProductDescription productDescription,
                   ProductList productList,
                   double price,
                   String volume){
        super();
        this.productDescription = productDescription;
        this.productList = productList;
        this.price = price;
        this.volume = volume;
    }

    public List<OrderPosition> orderPositions(){
        return getMany(OrderPosition.class, "product");
    }


}