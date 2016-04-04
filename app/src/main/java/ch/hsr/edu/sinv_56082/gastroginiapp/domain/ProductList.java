package ch.hsr.edu.sinv_56082.gastroginiapp.domain;

import java.util.ArrayList;
import java.util.List;

public class ProductList {
    private List<Product> products = new ArrayList<>();
    private String name;

    public ProductList(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public void addProduct(Product newProduct){
        products.add(newProduct);
    }

    public Product getProduct(int position){
        return products.get(position);
    }
}
