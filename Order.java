import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Order {
    private int orderId;
    private Map<Book, Integer> books;
    private double totalAmount;
    private LocalDateTime orderDate;
    private String status;
    
    public Order(int orderId, Map<Book, Integer> books, double totalAmount) {
        this.orderId = orderId;
        this.books = new HashMap<>(books);
        this.totalAmount = totalAmount;
        this.orderDate = LocalDateTime.now();
        this.status = "Placed";
    }
    
    public int getOrderId() {
        return orderId;
    }
    
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    
    public Map<Book, Integer> getBooks() {
        return new HashMap<>(books);
    }
    
    public void setBooks(Map<Book, Integer> books) {
        this.books = new HashMap<>(books);
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public LocalDateTime getOrderDate() {
        return orderDate;
    }
    
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public int getTotalItemCount() {
        return books.values().stream().mapToInt(Integer::intValue).sum();
    }
    
    public int getUniqueItemCount() {
        return books.size();
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(orderId).append("\n");
        sb.append("Date: ").append(orderDate.format(formatter)).append("\n");
        sb.append("Status: ").append(status).append("\n");
        sb.append("Books:\n");
        for (Map.Entry<Book, Integer> entry : books.entrySet()) {
            Book book = entry.getKey();
            int quantity = entry.getValue();
            double itemTotal = book.getPrice() * quantity;
            
            sb.append("  - ").append(book.getTitle())
              .append(" by ").append(book.getAuthor())
              .append(" ($").append(String.format("%.2f", book.getPrice()))
              .append(") x ").append(quantity)
              .append(" = $").append(String.format("%.2f", itemTotal))
              .append("\n");
        }
        sb.append("Total Items: ").append(getTotalItemCount()).append("\n");
        sb.append("Total Amount: $").append(String.format("%.2f", totalAmount));
        return sb.toString();
    }
}
