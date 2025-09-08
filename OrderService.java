import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private List<Order> orders;
    private int nextOrderId;
    
    public OrderService() {
        this.orders = new ArrayList<>();
        this.nextOrderId = 1001; // Start order IDs from 1001
    }
    
    public Order placeOrder(Cart cart) {
        if (cart.isEmpty()) {
            return null;
        }
        
        Order order = new Order(nextOrderId++, cart.getItems(), cart.getTotalPrice());
        orders.add(order);
        return order;
    }
    
    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }
    
    public Order findOrderById(int orderId) {
        for (Order order : orders) {
            if (order.getOrderId() == orderId) {
                return order;
            }
        }
        return null;
    }
    
    public boolean updateOrderStatus(int orderId, String status) {
        Order order = findOrderById(orderId);
        if (order != null) {
            order.setStatus(status);
            return true;
        }
        return false;
    }
    
    public void displayAllOrders() {
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }
        
        System.out.println("\n=== ALL ORDERS ===");
        for (Order order : orders) {
            System.out.println(order.toString());
            System.out.println("------------------------");
        }
    }
    
    public void displayOrder(int orderId) {
        Order order = findOrderById(orderId);
        if (order != null) {
            System.out.println(order.toString());
        } else {
            System.out.println("Order not found with ID: " + orderId);
        }
    }
}
