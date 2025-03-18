public class User {
    public enum Role{
        CUSTOMER,
        EMPLOYEE,
        MANAGER,
        GUEST
    }

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;



    public User() {
        this.role = Role.GUEST;
    }

    public User(int id, String firstName,
                String lastName, String email, String password, Role role){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Role getRole() {
        return role;
    }


    public boolean passwordMatch(String otherPassword){
        return this.password.equals(otherPassword);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        else if(!(obj instanceof User)) return false;
        User other = (User) obj;
        if(this.role == other.role && email.equals(other.email) && passwordMatch(other.password))
            return true;
        return false;
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
