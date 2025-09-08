import java.util.ArrayList;
import java.util.List;

public class UserService {
    private List<User> users;
    
    public UserService() {
        this.users = new ArrayList<>();
        // Initialize with some default users
        users.add(new User("admin", "admin"));
        users.add(new User("customer1", "customer"));
    }
    
    public User findUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    
    public User createUser(String username, String role) {
        User newUser = new User(username, role);
        users.add(newUser);
        return newUser;
    }
    
    public boolean userExists(String username) {
        return findUser(username) != null;
    }
    
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
}
