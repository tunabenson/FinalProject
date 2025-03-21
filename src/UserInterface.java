import structs.BST;
import structs.HashTable;

import java.util.Comparator;
import java.util.Scanner;

public class UserInterface {
    //upon sign in, depending on if customer or employee one hashtable will be populated
    //other is null
    static HashTable<Customer> customerTable = null;
    static HashTable<Employee> employeeTable = null;
    static BST<Product> productsByName = null;
    static BST<Product> productsByCategory = null;

    static User current = null;
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        productsByName = FileHandler.loadProductFile(new ProductComparator());
        productsByCategory = FileHandler.loadProductFile(new CategoryComparator());
        System.out.println("Welcome to the Skate Shop!");
        handleSignIn(input);
        handleMainInterface(input);

    }


    private static void handleMainInterface(Scanner input){
        //depending on role, provide different interfaces
        if(current instanceof Customer){
            Customer customer = (Customer) current;
            customerInterface(input , customer);
        }
        else if(current instanceof Employee && !((Employee) current).isManager()){
            employeeInterface(input);

        }
        else {
            managerInterface(input);
        }


    }

    private static void managerInterface(Scanner input) {

    }

    private static void employeeInterface(Scanner input) {

    }

    private static void customerInterface(Scanner input, Customer current) {
        System.out.println("Nice to see you " + current.getFirstName() + " !");
        System.out.println("Please enter the letter corresponding with the following options:");
        System.out.println("A: Search for a product");
        System.out.println("B: List available products");
        System.out.println("C: Place an Order");
        System.out.println("D: View Purchases");
        System.out.println("X: Quit");
        String choice = input.next().toUpperCase();

        boolean quit = false;

        while (!quit) {
            switch (choice) {
                case "A":
                    Product searched = searchForProduct(input);
                    if(searched == null){
                        System.out.println("Sorry, the product could not be found");
                    }
                    else{
                        System.out.println(searched);
                    }
                    break;
                case "B":
                    System.out.println("Please select one:");
                    System.out.println("1. Display by name");
                    System.out.println("2. Display by category");
                    int display = Integer.parseInt(input.next());
                    if(display == 1 ){
                        System.out.println(productsByName.inOrderString());
                    }
                    else if(display == 2){
                        System.out.println(productsByCategory.inOrderString());
                    }
                    break;
                case "C":
                    boolean doneShopping = false;
                    Order order = new Order();
                    while(!doneShopping) {
                        System.out.println("Search for the product you would like to purchase, or C, to continue to Checkout");
                        Product toOrder = searchForProduct(input);
                        if (toOrder == null) {
                            System.err.println("Sorry, we don't have the product you are looking for");
                            break;
                        }
                        System.out.println("Please enter the quantity desired: ");
                        int quantity = Integer.parseInt(input.next());
                        if (toOrder.getStockQuantity() >= quantity) {
                            System.out.println("1. Standard Shipping");
                            System.out.println("2. Rush Shipping");
                            System.out.println("3. Overnight Shipping");
                            System.out.print("Please select the type of shipping desired: ");
                            int shippingType = Integer.parseInt(input.next());
                            current.addOrder(order);
                        }
                    }
                     break;
                case "D":
                    System.out.println("1. View Unshipped Orders");
                    System.out.println("2. View Shipped Orders");

                case "X":
                    quit = true;
                    break;
                default:
                    System.out.println("Sorry, your choice was not a valid option. Try again!");
            }
        }
        System.out.println("Goodbye " + current.getFirstName() + "!" );
    }




    private static Product searchForProduct(Scanner input){
        System.out.println("Please select one:");
        System.out.println("1. Search by name");
        System.out.println("2. Search by category");
        int searchBy = Integer.parseInt(input.next());
        System.out.print("Enter your search: ");
        String searchFor = input.nextLine();
        System.out.println("\nsearching... ");
        Product product = null;
        if(searchBy == 1){
            product = productsByName.search(new Product(searchFor), new ProductComparator());

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
        int signInType = Integer.parseInt(input.next());

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
        //todo add signup logic, add account to customer hashmap, at the end of exec. load hashmap back to file
        return false;
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
            customerTable = FileHandler.loadCustomerFile();
            current = customerTable.get(new Customer(email, password));
            if(current == null) return true;
            return false;
        }
        else {
            employeeTable = FileHandler.loadWorkerFile();
            current = employeeTable.get(new Employee(email, password));
            if(current == null) return true;
            return false;
        }

    }
}




 class ProductComparator implements Comparator<Product> {
     @Override
     public int compare(Product o1, Product o2) {
         return 0;
     }
 }

 class CategoryComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return 0;
    }
 }
