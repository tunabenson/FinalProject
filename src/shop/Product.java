package shop;

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
        StringBuilder builder = new StringBuilder();
        builder.append("Product: ").append(name).append("\n")
                .append("Category: ").append(category).append("\n")
                .append("Price: $").append(String.format("%.2f", price)).append("\n")
                .append("Stock: ").append(stockQuantity).append(" units\n")
                .append("Description: ").append(description).append("\n");
        return builder.toString();
    }


    public void updateStock(int stock) {
        this.stockQuantity += stock;
    }
}

