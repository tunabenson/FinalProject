import structs.BST;
import structs.HashTable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileHandler {

    public static HashTable<User> loadCustomerFile(){
        /* format guide on worker file structure:
        # of total workers
        customer id
        firstName lastName
        email
        password
         */
        HashTable<User> customers = null;
        try {
            Scanner read = new Scanner(new File("src/customers.txt"));
            int numCustomers = Integer.parseInt(read.nextLine());
            customers = new HashTable<>(numCustomers);

            while(read.hasNextLine()) {
                int id = Integer.parseInt(read.nextLine());
                String firstName = read.next();
                String lastName = read.next();
                String email = read.nextLine();
                String password = read.nextLine();
                User usr = new User(id, firstName, lastName, email, password, User.Role.CUSTOMER);
                customers.add(usr);
            }
            return customers;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("loadCustomerFile(): file does not exist");
        }

    }

    public static HashTable<User> loadWorkerFile(){
        /* format guide on worker file structure:
        # of total workers
        employee id
        role
        firstName lastName
        email
        password
         */


        HashTable<User> workers = null;
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
                User usr = new User(id, firstName, lastName, email, password, User.Role.values()[role]);
                workers.add(usr);
            }
            return workers;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("loadWorkerFile(): file does not exist");
        }    }


    public static BST<Product> loadProductFile(){
        BST<Product> inventory = new BST<>();
        return inventory;

    }

    public static void registerNewAccount(User usr, String filepath){

    }


}
