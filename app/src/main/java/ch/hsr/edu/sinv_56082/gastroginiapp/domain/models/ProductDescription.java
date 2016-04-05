package ch.hsr.edu.sinv_56082.gastroginiapp.domain.models;


import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.UUIDModel;

@Table(name = "ProductDescriptions")
public class ProductDescription extends UUIDModel{

    public ProductDescription(){}

    public ProductDescription(String name, String description, ProductCategory productCategory){
        super();
        this.name = name;
        this.description = description;
        this.productCategory = productCategory;
    }

    @Column
    public String name;

    @Column
    public String description;

    @Column
    public ProductCategory productCategory;


    public List<Product> products(){
        return getMany(Product.class, "productDescription");
    }

}
