package user;

import shop.Order;
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

        myCurrentOrders = new LinkedList<>();
        myPastOrders = new LinkedList<>();
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

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public void setCity(String city){
        this.city = city;
    }

    public void setState(String state){
        this.state = state;
    }

    public void setZip(String zip){
        this.zip = zip;
    }

    // GETTERS
    public String getFirstName(String firstName){
        return firstName;
    }

    public String getLastName(String lastName){
        return lastName;
    }

    public String getEmail(String email){
        return email;
    }

    public String getPassword(String password){
        return password;
    }

    public String getAddress(String address){
        return address;
    }

    public String getCity(String city){
        return city;
    }

    public String getState(String state){
        return state;
    }

    public String getZip(String zip){
        return zip;
    }



    public void displayUnshippedOrders(){
        System.out.println(myCurrentOrders.numberedListString());
    }
    public void displayShippedOrders(){
        System.out.println(myPastOrders.numberedListString());
    }

    public void setOrderToShip(Order orderToShip) {
        int index = myCurrentOrders.findIndex(orderToShip);
        myCurrentOrders.advanceIteratorToIndex(index);
        myCurrentOrders.removeIterator();

        myPastOrders.addLast(orderToShip);
    }


    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        else if(!(obj instanceof Customer)) return false;
        Customer customer = (Customer) obj;
        if(customer.email.equals(this.email) &&
                (passwordMatch(customer.password)) || customer.password.equals(Employee.EMPLOYEE_BYPASS)) return true;
        return false;
    }

    @Override
    public String toString(){
        String toString = "";
        toString += firstName + " " + lastName + "\n";
        toString += address + "," + city + "," + state + "," + zip + "\n";
        toString += email + "\n";
        toString += password + "\n";
        return toString;
    }
}
