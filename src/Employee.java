public class Employee extends User{
    private boolean isManager;

    public Employee(String firstName, String lastName, String email, String password, boolean isManager){
        super(firstName,lastName, email, password);
        this.isManager = isManager;
    }

    public Employee(String email, String password) {
        super(email, password);
    }

    public boolean isManager() {
        return isManager;
    }
}
