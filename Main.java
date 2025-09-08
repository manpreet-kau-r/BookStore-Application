import java.util.Scanner;

public class Main {
    private UserService userService;
    private BookService bookService;
    private UserDataManager userDataManager;
    private AdminInterface adminInterface;
    private Scanner scanner;
    
    public Main() {
        this.userService = new UserService();
        this.bookService = new BookService();
        this.userDataManager = UserDataManager.getInstance(bookService);
        this.adminInterface = new AdminInterface(bookService);
        this.scanner = new Scanner(System.in);
    }
    
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }
    
    public void run() {
        int choice;
        while (true) {
            System.out.println("\n=== BOOKSTORE SYSTEM ===");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");
            
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    handleLogin();
                    break;
                case 2:
                    System.out.println("Thank you for using Bookstore System!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void handleLogin() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        
        User user = userService.findUser(username);
        
        if (user != null) {
            System.out.println("Welcome back, " + username + "!");
            if (user.getRole().equals("admin")) {
                adminInterface.runAdmin();
            } else {
                // Get user-specific services that persist across sessions
                CartService cartService = userDataManager.getCartService(username);
                OrderService orderService = userDataManager.getOrderService(username);
                CustomerInterface customerInterface = new CustomerInterface(bookService, cartService, orderService);
                customerInterface.runCustomer();
            }
        } else {
            System.out.println("Username doesn't exist.");
            System.out.print("Do you want to create a new account? (y/n): ");
            String createNew = scanner.nextLine();
            
            if (createNew.toLowerCase().equals("y")) {
                createNewUser(username);
            } else {
                System.out.println("Returning to main menu...");
            }
        }
    }
    
    private void createNewUser(String username) {
        System.out.println("Select role:");
        System.out.println("1. Customer");
        System.out.println("2. Admin");
        System.out.print("Enter choice: ");
        
        int roleChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        String role = (roleChoice == 2) ? "admin" : "customer";
        
        User newUser = userService.createUser(username, role);
        System.out.println("User created successfully! Role: " + role);
        
        if (role.equals("admin")) {
            adminInterface.runAdmin();
        } else {
            // Get user-specific services that persist across sessions
            CartService cartService = userDataManager.getCartService(username);
            OrderService orderService = userDataManager.getOrderService(username);
            CustomerInterface customerInterface = new CustomerInterface(bookService, cartService, orderService);
            customerInterface.runCustomer();
        }
    }
}
