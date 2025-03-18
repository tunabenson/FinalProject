
public class Product {

    private String name;
    private String category;
    private double price;
    private String description;
    private int stockQuantity;


    public Product(String name, String category, double price, String description, int stockQuantity){
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.stockQuantity = stockQuantity;
    }
    public Product(String name){
        this.name = name;
        this.category = "unknown";
        this.description = null;
        this.price = 0;
        this.stockQuantity = 0;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }


    @Override
    public String toString() {
        return null;
    }
}
