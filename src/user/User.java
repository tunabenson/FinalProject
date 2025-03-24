package user;

public abstract class User {

    protected String firstName;
    protected String lastName;
    protected String email;
    protected String password;





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

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        else if(!(obj instanceof User)) return false;
        User user = (User) obj;
        if(user.email.equals(this.email) && passwordMatch(user.password)) return true;
        return false;
    }
}
