package ch.hsr.edu.sinv_56082.gastroginiapp.domain;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "ProductLists")
public class ProductList extends Model{

    @Column
    public String name;

    public List<ProductListProduct> productListProducts(){
        return getMany(ProductListProduct.class, "productList");
    }

}
