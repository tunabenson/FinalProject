import structs.LinkedList;

public class Order {

    public enum ShippingType {
        STANDARD,
        PRIORITY,
        OVERNIGHT
    }
    private static int order_seed = 1000000;
    private LinkedList<Product> orderContent;
    private int orderId;

    private ShippingType shippingType;
    private int priority;


    public Order(){
        this.orderId = ++orderId;
        this.orderContent = new LinkedList<>();
        this.shippingType = null;
    }

    public Order(ShippingType shippingType, Product... products) {
        this.orderContent = new LinkedList<>(products);
        orderId = ++order_seed;
        this.shippingType = shippingType;
    }

    public boolean isEmpty(){
        return orderContent.isEmpty();
    }

    public void addToOrder(Product product){
        this.orderContent.addLast(product);
    }

    public void setShippingType(int shippingType){

    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Order of: \n").append(orderContent.numberedListString());
        str.append("\n");
        str.append("Using ").append(getShippingType()).append("\n");
        return str.toString();
    }

    public String getShippingType(){
        switch (shippingType){
            case STANDARD: return "Standard Shipping";
            case PRIORITY: return "Rush Shipping";
            case OVERNIGHT: return "Overnight Shipping";
        }
        return null; //should not happen
    }


}
