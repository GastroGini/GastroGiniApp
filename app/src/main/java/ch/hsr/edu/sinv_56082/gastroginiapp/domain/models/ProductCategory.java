package ch.hsr.edu.sinv_56082.gastroginiapp.domain.models;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;
import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.*;

@Table(name = "ProductCategories")
public class ProductCategory extends UUIDModel{

    public ProductCategory(){}

    public ProductCategory(String name){
        super();
        this.name = name;
    }

    @Column
    public String name;

    public List<ProductDescription> productDescriptions(){
        return getMany(ProductDescription.class, "productCategory");
    }

    @Override
    public String toString() {
        return name;
    }


    public static ProductCategory get(UUID uuid){
        return new Select().from(ProductCategory.class).where("uuid = ?", uuid.toString()).executeSingle();
    }

    public static ProductCategory get(String uuid){
        return get(UUID.fromString(uuid));
    }
}
