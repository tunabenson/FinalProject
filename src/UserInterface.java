import shop.Order;
import shop.Product;
import structs.BST;
import structs.HashTable;
import structs.PriorityQueue;
import user.Customer;
import user.Employee;
import user.User;

import java.util.Comparator;
import java.util.Scanner;

public class UserInterface {
    //upon sign in, depending on if customer or employee one hashtable will be populated
    //other is null
    static HashTable<Customer> customerTable = null;
    static HashTable<Employee> employeeTable = null;
    static BST<Product> productsByName = null;
    static BST<Product> productsByCategory = null;

    private static PriorityQueue<Order> orders = null; // Loads unshipped orders


    static User current = null;
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        productsByName = FileHandler.loadProductFile(new NameComparator());
        productsByCategory = FileHandler.loadProductFile(new CategoryComparator());
        customerTable = FileHandler.loadCustomerFile();
        System.out.println("Welcome to the Skate Shop!");
        handleSignIn(input);
        if(current instanceof Employee){
            orders = FileHandler.loadOrderFile(customerTable, productsByName);
        }
        handleMainInterface(input);

    }


    private static void handleMainInterface(Scanner input){
        //depending on role, provide different interfaces
        if(current instanceof Customer){
            Customer customer = (Customer) current;
            customerInterface(input , customer);
        }
        else if(current instanceof Employee && !((Employee) current).isManager()){
            Employee employee = (Employee) current; //may want to use this for something

            employeeInterface(input);

        }
        else {
            managerInterface(input);
        }


    }

    private static void managerInterface(Scanner input) {

    }



    private static void employeeInterface(Scanner input) {
        boolean quit = false;
        while (!quit) {
            System.out.println("\n====== user.Employee Menu ======");
            System.out.println("A: Search for an Order");
            System.out.println("   (1: Search by Order ID");
            System.out.println("    2: Search by user.Customer First and Last Name)");
            System.out.println("B: View Order with Highest Priority");
            System.out.println("C: View All Orders Sorted by Priority (Heap Sort)");
            System.out.println("D: Ship an Order");
            System.out.println("X: Quit");
            System.out.print("Enter your choice: ");
            String choice = input.nextLine().toUpperCase();
            input.nextLine();

            switch (choice) {
                case "A":
                    searchForOrder(input);
                    break;
                case "B":
                    viewHighestPriorityOrder();
                    break;
                case "C":
                    viewAllOrdersSorted();
                    break;
                case "D":
                    shipAnOrder(input);
                    break;
                case "X":
                    quit = true;
                    System.out.println("Exiting user.Employee Interface.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }


    private static void searchForOrder(Scanner input) {
        System.out.println("\n--- Search for an Order ---");
        System.out.println("Choose search method:");
        System.out.println("1: Search by Order ID");
        System.out.println("2: Search by Customer First and Last Name");
        System.out.print("Enter your choice: ");
        int searchOption = Integer.parseInt(input.nextLine());
        Order foundOrder = null;

        if (searchOption == 1) {
            System.out.print("Enter Order ID: ");
            int orderId = Integer.parseInt(input.nextLine());
            // Call the static search method from shop.Order class.
            foundOrder = Order.searchByOrderID(orders, orderId);
        } else if (searchOption == 2) {
            System.out.print("Enter Customer First Name: ");
            String firstName = input.nextLine().trim();
            System.out.print("Enter Customer Last Name: ");
            String lastName = input.nextLine().trim();
            // Call the static search method from shop.Order class.
            foundOrder = Order.searchByCustomerName(orders, firstName, lastName);
        } else {
            System.out.println("Invalid search option.");
            return;
        }

        if (foundOrder != null) {
            System.out.println("Order found:");
            System.out.println(foundOrder);
        } else {
            System.out.println("No matching order found.");
        }
    }

    // Option B: View the shop.Order with the Highest Priority using the Heap's dedicated method.
    private static void viewHighestPriorityOrder() {
        System.out.println("\n--- Order with Highest Priority ---");
        if (orders.isEmpty()) {
            System.out.println("No orders available.");
            return;
        }
        Order topOrder = orders.peek();
        System.out.println(topOrder);
    }

    private static void viewAllOrdersSorted() {
        System.out.println("\n--- All Orders Sorted by Priority ---");
        // Call the existing sorting method from your Heap class.
        // Assuming heapSort() returns an ArrayList<shop.Order> sorted by priority.
        System.out.println(orders.numberedOrderList());
    }

    // Option D: Ship an shop.Order by removing the highest priority order and updating the user.Customer's order lists.
    private static void shipAnOrder(Scanner input) {
        System.out.println("\n--- Ship an Order ---");
        if (orders.isEmpty()) {
            System.out.println("No orders available to ship.");
            return;
        }

        Order orderToShip = orders.dequeue();
        if (orderToShip != null) {
            //this is insecure, must make sure user is an employee to see user as full

            Customer cust = orderToShip.getCustomer();

            // Update the customer's order lists: remove from unshipped and add to shipped.
            cust.setOrderToShip(orderToShip);
            System.out.println("Order shipped successfully:");
            System.out.println(orderToShip);
        } else {
            System.out.println("Error: Unable to ship order.");
        }
    }


    private static void customerInterface(Scanner input, Customer current) {
        System.out.println("Nice to see you " + current.getFirstName() + " !");


        boolean quit = false;

        while (!quit) {
            System.out.println("Please enter the letter corresponding with the following options:");
            System.out.println("A: Search for a product");
            System.out.println("B: List available products");
            System.out.println("C: Place an Order");
            System.out.println("D: View Purchases");
            System.out.println("X: Quit");
            String choice = input.nextLine().toUpperCase();

            switch (choice) {
                case "A":
                    System.out.println("Please select one:");
                    System.out.println("1. Search by name");
                    System.out.println("2. Search by category");
                    int searchBy = Integer.parseInt(input.nextLine());
                    Product searched = searchForProduct(searchBy, input);
                    if(searched == null){
                        System.out.println("Sorry, the product could not be found");
                    }
                    else{
                        System.out.println(searched);
                    }
                    break;
                case "B":
                    displayProducts(input);
                    break;
                case "C":
                     makeOrder(input, current);
                     break;
                case "D":
                    displayUserOrders(input, current);

                case "X":
                    quit = true;
                    break;
                default:
                    System.out.println("Sorry, your choice was not a valid option. Try again!");
            }
        }
        System.out.println("Goodbye " + current.getFirstName() + "!" );
    }

    private static void makeOrder(Scanner input, Customer current){
        boolean doneShopping = false;
        Order order = new Order(current);
        while(!doneShopping) {
            System.out.println("Search for the product you would like to purchase, or 3, to continue to Checkout");
            System.out.println("Please select one:");
            System.out.println("1. Search by name");
            System.out.println("2. Search by category");
            System.out.println("3. go to checkout");
            int in = Integer.parseInt(input.nextLine());
            if (in != 3) {
                Product toOrder = searchForProduct(in, input);
                if (toOrder == null) {
                    System.err.println("Sorry, we don't have the product you are looking for");
                    break;
                }
                System.out.println("Please enter the quantity desired: ");
                int quantity = Integer.parseInt(input.nextLine());
                if (toOrder.getStockQuantity() >= quantity) {
                    System.out.println("1. Standard Shipping");
                    System.out.println("2. Rush Shipping");
                    System.out.println("3. Overnight Shipping");
                    System.out.print("Please select the type of shipping desired: ");
                    int shippingType = Integer.parseInt(input.nextLine());
                    current.addOrder(order);
                }
            }
        }
    }


    private static void displayUserOrders(Scanner input, Customer customer){
        System.out.println("1. View Unshipped Orders");
        System.out.println("2. View Shipped Orders");
        System.out.print("Please enter selection:");
        int choice = Integer.parseInt(input.nextLine());
        switch (choice){
            case 1:
                customer.displayUnshippedOrders();
                break;
            case 2:
                customer.displayShippedOrders();
                break;
        }
    }


    private static void displayProducts(Scanner input){
        System.out.println("Please select one:");
        System.out.println("1. Display by name");
        System.out.println("2. Display by category\n");;
        int display = Integer.parseInt(input.nextLine());
        if(display == 1 ){
            System.out.println(productsByName.inOrderString());
        }
        else if(display == 2){
            System.out.println(productsByCategory.inOrderString());
        }
    }

    private static Product searchForProduct(int searchBy, Scanner input){

        System.out.print("Enter your search: ");
        String searchFor = input.nextLine();
        System.out.println("\nsearching... ");
        Product product = null;
        if(searchBy == 1){
            product = productsByName.search(new Product(searchFor), new NameComparator());

        }
        else {
            product = productsByCategory.search(new Product(searchFor, -1), new CategoryComparator());
        }
        return product;
    }


    private static void handleSignIn(Scanner input){
        System.out.println("Please select sign in as " +
                "\nguest (1)" +
                "\ncustomer (2)" +
                "\nemployee (3) " +
                "\nmanager (4)" +
                "\ncreate account (5)");
        int signInType = Integer.parseInt(input.nextLine());

        boolean authorized = false;

        switch (signInType){
            case 1,2,3,4:
                while(!authorized) {
                    authorized = signIn(signInType, input);
                }
                break;
            case 5:
                while(!authorized) {
                    authorized = signUp(input);
                }
                break;
            default:
                System.out.println(signInType + " Is not a valid option, try again!");
        }
    }

    private static boolean signUp(Scanner input) {
        System.out.println("\n--- Sign Up ---");


        System.out.print("First Name: ");
        String firstName = input.nextLine().trim();

        System.out.print("\nLast Name: ");
        String lastName = input.nextLine().trim();

        System.out.print("\nEmail: ");
        String email = input.nextLine().trim();

        System.out.print("\nPassword: ");
        String password = input.nextLine().trim();


        // Check if email already exists
        if (customerTable.contains(new Customer(email, password))) {
            System.out.println("An account with these credentials already exists.");
            return false;
        }


        System.out.print("\nAddress: ");
        String address = input.nextLine().trim();

        System.out.print("\nCity: ");
        String city = input.nextLine().trim();

        System.out.print("\nState: ");
        String state = input.nextLine().trim();

        System.out.print("\nZIP Code: ");
        String zip = input.nextLine().trim();

        if(firstName.isBlank() || lastName.isBlank() || email.isBlank() ||
        password.isBlank() || address.isBlank() || city.isBlank() || state.isBlank()
                || zip.isBlank()){
            System.out.println("Please fill all fields, do not leave anything blank!");
            return false;
        }

        // Create and add the new customer
        Customer newCustomer = new Customer(firstName, lastName, email, password, address, city, state, zip);
        customerTable.add(newCustomer);


        System.out.println("Account created successfully! Welcome, " + firstName + ".");
        current = newCustomer;
        return true;
    }

    /**
     * helper method for handleSignIn
     * @param role role of sign in user
     * @param input user input scanner
     * @return true if sign in worked, false if failed
     */
    private static boolean signIn(int role, Scanner input){
        if(role == 1){ // 1 is guest
            current = new Customer();
            return true;
        }
        System.out.println("Please enter your email: ");
        String email = input.nextLine();
        System.out.println("Please enter your password: ");
        String password = input.nextLine();
        if(role == 2){ //2 is customer
            current = customerTable.get(new Customer(email, password));
            if(current != null) return true;
            System.err.println("Sorry, your credentials are invalid or incorrect");
            return false;
        }
        else {
            employeeTable = FileHandler.loadWorkerFile();
            current = employeeTable.get(new Employee(email, password));
            if(current != null) return true;
            System.err.println("Sorry, your credentials are invalid or incorrect");
            return false;
        }

    }
}




 class NameComparator implements Comparator<Product> {
     @Override
     public int compare(Product o1, Product o2) {
         return o1.getName().compareTo(o2.getName());
     }
 }

 class CategoryComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return o1.getCategory().compareTo(o2.getCategory());
    }
 }


 class OrderComparator implements Comparator<Order> {
     @Override
     public int compare(Order o1, Order o2) {
         return o1.getPriority().compareTo(o2.getPriority());
     }
 }
