package ch.hsr.edu.sinv_56082.gastroginiapp.domain.models;


import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;
import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.UUIDModel;

@Table(name = "ProductDescriptions")
public class ProductDescription extends UUIDModel{
    @Column
    public String name;


    @Column
    public String description;

    @Column(onDelete = Column.ForeignKeyAction.CASCADE)
    public ProductCategory productCategory;


    public ProductDescription(){}

    public ProductDescription(String name, String description, ProductCategory productCategory){
        super();
        this.name = name;
        this.description = description;
        this.productCategory = productCategory;
    }

    public static ProductDescription get(UUID uuid){
        return new Select().from(ProductDescription.class).where("uuid = ?", uuid.toString()).executeSingle();
    }

    public static ProductDescription get(String uuid){
        return get(UUID.fromString(uuid));
    }

    public List<Product> products(){
        return getMany(Product.class, "productDescription");
    }

    @Override
    public String toString(){
        return name;
    }

}
