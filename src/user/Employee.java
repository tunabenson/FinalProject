package user;

import user.User;

public class Employee extends User {

    public static final String EMPLOYEE_BYPASS = generateEmployeeBypass();
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



    private static String generateEmployeeBypass(){
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder random = new StringBuilder();
        while (random.length() < 20) {
            int index = (int) (Math.random() * SALTCHARS.length());
            random.append(SALTCHARS.charAt(index));
        }
        return random.toString();

    }
}
