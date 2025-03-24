import shop.Order;
import shop.Product;
import structs.BST;
import structs.HashTable;
import structs.PriorityQueue;
import user.Customer;
import user.Employee;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class FileHandler {

    public static HashTable<Customer> loadCustomerFile() {
        /* format guide on customer file structure:
        # of total workers
        customer id
        firstName lastName
        full address (comma seperated)
        email
        password
         */
        HashTable<Customer> customers = null;
        try {
            Scanner read = new Scanner(new File("files/customers.txt"));
            int numCustomers = Integer.parseInt(read.nextLine());
            customers = new HashTable<>(numCustomers);

            while (read.hasNextLine()) {
                String [] name = read.nextLine().split(" ");
                String loc = read.nextLine();
                String[] location = loc.split(","); //comma seperated (address, city, state, zip)
                String email = read.nextLine();
                String password = read.nextLine();
                Customer usr = new Customer(name[0], name[1], email, password, location[0],
                        location[1], location[2], location[3]);
                customers.add(usr);
            }
            return customers;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("loadCustomerFile(): file does not exist");
        }

    }

    public static HashTable<Employee> loadWorkerFile() {
        /* format guide on worker file structure:
        # of total workers
        employee id
        role (1 and 2)
        firstName lastName
        email
        password
         */


        HashTable<Employee> workers = null;
        try {
            Scanner read = new Scanner(new File("files/employee.txt"));
            int numCustomers = Integer.parseInt(read.nextLine());
            workers = new HashTable<>(numCustomers);

            while (read.hasNextLine()) {
                int id = Integer.parseInt(read.nextLine());
                int role = Integer.parseInt(read.nextLine());//1 for employee 2 for manager
                String firstName = read.next();
                String lastName = read.next();
                String email = read.nextLine();
                String password = read.nextLine();
                //will be true if role == 2 (manager)
                Employee emp = new Employee(firstName, lastName, email, password, role == 2);
                workers.add(emp);
            }
            return workers;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("loadWorkerFile(): file does not exist");
        }
    }


    public static BST<Product> loadProductFile(Comparator<Product> cmp) {
        BST<Product> inventory = new BST<>();
        try {
            Scanner read = new Scanner(new File("files/products.txt"));
            int numProducts = Integer.parseInt(read.nextLine());
            for (int i = 0; i < numProducts; i++) {
                String name = read.nextLine();
                String category = read.nextLine();
                double price = Double.parseDouble(read.nextLine());
                int stock = Integer.parseInt(read.nextLine());
                String description = read.nextLine();

                Product product = new Product(name, category, price, description, stock);
                inventory.insert(product, cmp);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("loadProductFile(): file does not exist");
        }
        return inventory;
    }


    public static PriorityQueue<Order> loadOrderFile(HashTable<Customer> customers, BST<Product> productByName) {
        ArrayList<Order> orderList = new ArrayList<>();
        try {
            Scanner read = new Scanner(new File("files/orders.txt"));
            Order.order_seed = Integer.parseInt(read.nextLine());
            int numOrders = Integer.parseInt(read.nextLine());
            for (int i = 0; i < numOrders; i++) {
                int orderId = Integer.parseInt(read.nextLine());
                String customerEmail = read.nextLine();
                int shippingType = Integer.parseInt(read.nextLine());
                int productCount = Integer.parseInt(read.nextLine());


                //this is only the email, we want to get this customer from the table
                Customer customer = new Customer(customerEmail, Employee.EMPLOYEE_BYPASS);


                Order order = new Order(customer, orderId, shippingType);

                for (int j = 0; j < productCount; j++) {
                    String productName = read.nextLine();
                    int quantity = Integer.parseInt(read.nextLine());
                    Product product = productByName.search(new Product(productName), new NameComparator()); // relies on product equality by name
                    order.addToOrder(product, quantity);
                }

                orderList.add(order);
            }

            return new PriorityQueue<>(new OrderComparator(), orderList);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("loadOrderFile(): file does not exist");
        }
    }

}

