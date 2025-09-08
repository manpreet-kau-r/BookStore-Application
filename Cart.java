import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Book> items;
    
    public Cart() {
        this.items = new ArrayList<>();
    }
    
    public void addBook(Book book) {
        items.add(book);
    }
    
    public boolean removeBook(int bookId) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == bookId) {
                items.remove(i);
                return true;
            }
        }
        return false;
    }
    
    public List<Book> getItems() {
        return new ArrayList<>(items);
    }
    
    public double getTotalPrice() {
        double total = 0.0;
        for (Book book : items) {
            total += book.getPrice();
        }
        return total;
    }
    
    public void clear() {
        items.clear();
    }
    
    public boolean isEmpty() {
        return items.isEmpty();
    }
    
    public int getItemCount() {
        return items.size();
    }
    
    @Override
    public String toString() {
        if (items.isEmpty()) {
            return "Cart is empty";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Cart Items:\n");
        for (Book book : items) {
            sb.append("- ").append(book.getTitle())
              .append(" by ").append(book.getAuthor())
              .append(" ($").append(book.getPrice()).append(")\n");
        }
        sb.append("Total: $").append(String.format("%.2f", getTotalPrice()));
        return sb.toString();
    }
}
