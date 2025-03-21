
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

    }

    public Product(String category, int stockQuantity){
        this.category = category;
        this.stockQuantity = stockQuantity;
    }


    public String getName() {
        return name;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public String getCategory() {
        return category;
    }


    @Override
    public String toString() {
        return null;
    }





}

