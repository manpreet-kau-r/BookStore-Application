import java.util.HashMap;
import java.util.Map;

public class UserDataManager {
    private static UserDataManager instance;
    private Map<String, CartService> userCarts;
    private Map<String, OrderService> userOrders;
    private BookService bookService;
    
    private UserDataManager(BookService bookService) {
        this.bookService = bookService;
        this.userCarts = new HashMap<>();
        this.userOrders = new HashMap<>();
    }
    
    public static UserDataManager getInstance(BookService bookService) {
        if (instance == null) {
            instance = new UserDataManager(bookService);
        }
        return instance;
    }
    
    public CartService getCartService(String username) {
        if (!userCarts.containsKey(username)) {
            userCarts.put(username, new CartService(bookService));
        }
        return userCarts.get(username);
    }
    
    public OrderService getOrderService(String username) {
        if (!userOrders.containsKey(username)) {
            userOrders.put(username, new OrderService());
        }
        return userOrders.get(username);
    }
    
    public void clearUserData(String username) {
        userCarts.remove(username);
        userOrders.remove(username);
    }
    
    public boolean hasUserData(String username) {
        return userCarts.containsKey(username) || userOrders.containsKey(username);
    }
}
