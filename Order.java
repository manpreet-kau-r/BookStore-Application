import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private List<Book> books;
    private double totalAmount;
    private LocalDateTime orderDate;
    private String status;
    
    public Order(int orderId, List<Book> books, double totalAmount) {
        this.orderId = orderId;
        this.books = new ArrayList<>(books);
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
    
    public List<Book> getBooks() {
        return new ArrayList<>(books);
    }
    
    public void setBooks(List<Book> books) {
        this.books = new ArrayList<>(books);
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
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(orderId).append("\n");
        sb.append("Date: ").append(orderDate.format(formatter)).append("\n");
        sb.append("Status: ").append(status).append("\n");
        sb.append("Books:\n");
        for (Book book : books) {
            sb.append("  - ").append(book.getTitle())
              .append(" by ").append(book.getAuthor())
              .append(" ($").append(book.getPrice()).append(")\n");
        }
        sb.append("Total Amount: $").append(String.format("%.2f", totalAmount));
        return sb.toString();
    }
}
