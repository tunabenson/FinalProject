import structs.LinkedList;

public class Customer extends User {

    private String address;

    private String city;

    private String state;

    private String zip;

    //Shipped orders
    private LinkedList<Order> myPastOrders;
    //Unshipped orders
    private LinkedList<Order> myCurrentOrders;



    public Customer(String firstName, String lastName, String email, String password,
    String address, String city, String state, String zip){
        super(firstName,lastName, email, password);
        this.address = address;
        this.state = state;
        this.zip = zip;
        this.city = city;
    }

    public Customer (String email, String password){
        super(email, password);
    }

    public Customer() {
         this.myCurrentOrders = new LinkedList<>();
    }

    public void addOrder(Order order){
        myCurrentOrders.addLast(order);
    }


    public void displayUnshippedOrders(){
        System.out.println(myCurrentOrders.numberedListString());
    }
    public void displayShippedOrders(){
        System.out.println(myPastOrders.numberedListString());
    }
}
