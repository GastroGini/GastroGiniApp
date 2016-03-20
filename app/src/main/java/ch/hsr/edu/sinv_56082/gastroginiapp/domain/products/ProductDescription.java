package ch.hsr.edu.sinv_56082.gastroginiapp.domain.products;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;
import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.UUIDModel;

@Table(name = "ProductDescriptions")
public class ProductDescription extends UUIDModel{


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
