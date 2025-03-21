import structs.BST;
import structs.HashTable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Scanner;

public class FileHandler {

    public static HashTable<Customer> loadCustomerFile(){
        /* format guide on worker file structure:
        # of total workers
        customer id
        firstName lastName
        email
        password
         */
        HashTable<Customer> customers = null;
        try {
            Scanner read = new Scanner(new File("src/customers.txt"));
            int numCustomers = Integer.parseInt(read.nextLine());
            customers = new HashTable<>(numCustomers);

            while(read.hasNextLine()) {
                String firstName = read.next();
                String lastName = read.next();
                String email = read.nextLine();
                String password = read.nextLine();
                String [] location = read.nextLine().split(","); //comma seperated (address, city, state, zip)
                Customer usr = new Customer(firstName, lastName, email, password, location[0],
                        location[1], location[2], location[3]);
                customers.add(usr);
            }
            return customers;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("loadCustomerFile(): file does not exist");
        }

    }

    public static HashTable<Employee> loadWorkerFile(){
        /* format guide on worker file structure:
        # of total workers
        employee id
        role
        firstName lastName
        email
        password
         */


        HashTable<Employee> workers = null;
        try {
            Scanner read = new Scanner(new File("src/workers.txt"));
            int numCustomers = Integer.parseInt(read.nextLine());
            workers = new HashTable<>(numCustomers);

            while(read.hasNextLine()) {
                int id = Integer.parseInt(read.nextLine());
                int role = Integer.parseInt(read.nextLine());//1 for employee 2 for manager
                String firstName = read.next();
                String lastName = read.next();
                String email = read.nextLine();
                String password = read.nextLine();
                //will be true if role == 2 (manager)
                Employee emp = new Employee(firstName, lastName, email, password, role == 2 );
                workers.add(emp);
            }
            return workers;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("loadWorkerFile(): file does not exist");
        }    }


    public static BST<Product> loadProductFile(Comparator cmp){
        BST<Product> inventory = new BST<>();

        return inventory;

    }

    public static void registerNewAccount(User usr, String filepath){

    }


}
