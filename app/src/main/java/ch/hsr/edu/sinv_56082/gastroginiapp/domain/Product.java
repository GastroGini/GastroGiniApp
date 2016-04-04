package ch.hsr.edu.sinv_56082.gastroginiapp.domain;

public class Product {
    private double price;
    private String volume;
    private ProductDescription productDescription;

    public Product(double price, String volume, ProductDescription productDescription){
        this.price = price;
        this.volume = volume;
        this.productDescription = productDescription;
    }

    public Product(double price, String volume){
        this.price = price;
        this.volume = volume;
        this.productDescription = new ProductDescription("Product", "It's a product");
    }

    public Product(double price){
        this.price = price;
        this.volume = "N/A";
        this.productDescription = new ProductDescription("Product", "It's a product");
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public String getVolume(){
        return volume;
    }

    public void setVolume(String volume){
        this.volume = volume;
    }

    public ProductDescription getProductDescription(){
        return productDescription;
    }

    public void setProductDescription(ProductDescription productDescription){
        this.productDescription = productDescription;
    }
}
