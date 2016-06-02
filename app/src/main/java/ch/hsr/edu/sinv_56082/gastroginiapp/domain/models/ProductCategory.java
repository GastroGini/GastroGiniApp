package ch.hsr.edu.sinv_56082.gastroginiapp.domain.models;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.UUIDModel;

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

}
