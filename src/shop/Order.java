package shop;

import structs.LinkedList;
import structs.PriorityQueue;
import user.Customer;

public class Order {


    public static Order searchByOrderID(PriorityQueue<Order> orderHeap, int orderId) {
        return null;

    }

    public static Order searchByCustomerName(PriorityQueue<Order> orderHeap, String firstName, String lastName) {
        return null;
    }



    public enum ShippingType {
        STANDARD,
        PRIORITY,
        OVERNIGHT
    }
    public static int order_seed = 1000000; //will be set to value of last order in unshipped order file upon initialization
    private LinkedList<Product> orderContent;
    private int orderId;

    private Customer shopper;

    private ShippingType shippingType;
    private int priority;

    public Order(Customer customer, int orderId, int shippingType) {
        this.shopper = customer;
        this.orderContent = new LinkedList<>();
        this.orderId = orderId;
        setShippingType(shippingType);
    }

    public Order(Customer shopper) {
        this.shopper = shopper;
        this.orderContent = new LinkedList<>();
        orderId = ++order_seed;
    }



    public Customer getCustomer() {
        return this.shopper;
    }

    public boolean isEmpty(){
        return orderContent.isEmpty();
    }

    public void addToOrder(Product product, int quantity){
        if(product.getStockQuantity() < quantity ) throw new IllegalArgumentException("cannot sell this quantity, not enough in stock");
        for (int i = 0; i < quantity; i++) {
            this.orderContent.addLast(product);
        }
        product.updateStock(-quantity);
    }

    private void setShippingType(int shippingType){
        priority = shippingType;

        switch (priority){
            case 1:
                this.shippingType = ShippingType.OVERNIGHT;
                break;
            case 2:
                this.shippingType = ShippingType.PRIORITY;
                break;
            case 3:
                this.shippingType = ShippingType.STANDARD;
                break;
        }

    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("orders contents: \n").append(orderContent.numberedListString());
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



    public Integer getPriority() {
        return priority;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }



}
