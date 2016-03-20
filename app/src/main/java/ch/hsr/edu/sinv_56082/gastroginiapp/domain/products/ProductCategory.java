package ch.hsr.edu.sinv_56082.gastroginiapp.domain.products;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;
import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.UUIDModel;

@Table(name = "ProductCategories")
public class ProductCategory extends UUIDModel{

    @Column
    public String name;

    public List<ProductDescription> productDescriptions(){
        return getMany(ProductDescription.class, "productCategory");
    }
}
