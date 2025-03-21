import structs.Heap;
import structs.LinkedList;

public abstract class User {

    private String firstName;
    private String lastName;
    private String email;
    private String password;





    public User() {
        //GUEST USER WHAT TO DO HERE?
    }

    public User( String firstName,
                String lastName, String email, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public User(String email , String password){
        this.email = email;
        this.password = password;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public boolean passwordMatch(String otherPassword) {
        return this.password.equals(otherPassword);
    }

    @Override
    public int hashCode() {
        String key = email + password;
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash += key.charAt(i);
        }
        return hash;
    }


}
