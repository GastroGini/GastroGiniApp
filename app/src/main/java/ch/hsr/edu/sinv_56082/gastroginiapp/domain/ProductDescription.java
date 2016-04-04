package ch.hsr.edu.sinv_56082.gastroginiapp.domain;

public class ProductDescription {
    private String name;
    private String description;
    private ProductCatalog productCatalog;

    public ProductDescription(String name){
        this.name = name;
        this.description = "No description available";
        productCatalog = new ProductCatalog("Unassigned");
    }

    public ProductDescription(String name, String description){
        this.name = name;
        this.description = description;
        productCatalog = new ProductCatalog("Unassigned");
    }

    public ProductDescription(String name, String description, ProductCatalog productCatalog){
        this.name = name;
        this.description = description;
        this.productCatalog = productCatalog;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public ProductCatalog getProductCatalog(){
        return productCatalog;
    }

    public void setProductCatalog(ProductCatalog productCatalog){
        this.productCatalog = productCatalog;
    }
}
