import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private Map<Book, Integer> items;
    
    public Cart() {
        this.items = new HashMap<>();
    }
    
    public void addBook(Book book) {
        addBook(book, 1);
    }
    
    public void addBook(Book book, int quantity) {
        if (book == null || quantity <= 0) {
            throw new IllegalArgumentException("Book cannot be null and quantity must be positive");
        }
        
        // Check if there's enough stock
        int currentQuantityInCart = items.getOrDefault(book, 0);
        int totalRequestedQuantity = currentQuantityInCart + quantity;
        
        if (!book.hasStock(totalRequestedQuantity)) {
            throw new IllegalArgumentException("Not enough stock available. Available: " + 
                book.getStockQuantity() + ", Already in cart: " + currentQuantityInCart + 
                ", Requested: " + quantity);
        }
        
        items.put(book, totalRequestedQuantity);
    }
    
    public boolean removeBook(Book book) {
        if (book == null) {
            return false;
        }
        return items.remove(book) != null;
    }
    
    public boolean removeBook(int bookId) {
        Book bookToRemove = null;
        for (Book book : items.keySet()) {
            if (book.getId() == bookId) {
                bookToRemove = book;
                break;
            }
        }
        if (bookToRemove != null) {
            items.remove(bookToRemove);
            return true;
        }
        return false;
    }
    
    public void updateQuantity(Book book, int quantity) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        
        if (quantity <= 0) {
            items.remove(book);
        } else {
            // Check if there's enough stock for the new quantity
            if (!book.hasStock(quantity)) {
                throw new IllegalArgumentException("Not enough stock available. Available: " + 
                    book.getStockQuantity() + ", Requested: " + quantity);
            }
            items.put(book, quantity);
        }
    }
    
    public int getQuantity(Book book) {
        return items.getOrDefault(book, 0);
    }
    
    public Map<Book, Integer> getItems() {
        return new HashMap<>(items);
    }
    
    public List<Book> getItemsAsList() {
        List<Book> bookList = new ArrayList<>();
        for (Map.Entry<Book, Integer> entry : items.entrySet()) {
            Book book = entry.getKey();
            int quantity = entry.getValue();
            for (int i = 0; i < quantity; i++) {
                bookList.add(book);
            }
        }
        return bookList;
    }
    
    public double getTotalPrice() {
        double total = 0.0;
        for (Map.Entry<Book, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }
    
    public void clear() {
        items.clear();
    }
    
    public boolean isEmpty() {
        return items.isEmpty();
    }
    
    public int getTotalItemCount() {
        return items.values().stream().mapToInt(Integer::intValue).sum();
    }
    
    public int getUniqueItemCount() {
        return items.size();
    }
    
    public boolean containsBook(Book book) {
        return items.containsKey(book);
    }
    
    public boolean containsBook(int bookId) {
        return items.keySet().stream().anyMatch(book -> book.getId() == bookId);
    }
    
    public boolean validateStock() {
        for (Map.Entry<Book, Integer> entry : items.entrySet()) {
            Book book = entry.getKey();
            int requestedQuantity = entry.getValue();
            if (!book.hasStock(requestedQuantity)) {
                return false;
            }
        }
        return true;
    }
    
    public void processOrder() {
        // Reduce stock for all items in cart
        for (Map.Entry<Book, Integer> entry : items.entrySet()) {
            Book book = entry.getKey();
            int quantity = entry.getValue();
            book.reduceStock(quantity);
        }
    }
    
    @Override
    public String toString() {
        if (items.isEmpty()) {
            return "Cart is empty";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Cart Items:\n");
        for (Map.Entry<Book, Integer> entry : items.entrySet()) {
            Book book = entry.getKey();
            int quantity = entry.getValue();
            double itemTotal = book.getPrice() * quantity;
            
            sb.append("- ").append(book.getTitle())
              .append(" by ").append(book.getAuthor())
              .append(" ($").append(String.format("%.2f", book.getPrice()))
              .append(") x ").append(quantity)
              .append(" = $").append(String.format("%.2f", itemTotal))
              .append(" [Stock: ").append(book.getStockQuantity()).append("]")
              .append("\n");
        }
        sb.append("Total Items: ").append(getTotalItemCount()).append("\n");
        sb.append("Total Price: $").append(String.format("%.2f", getTotalPrice()));
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cart cart = (Cart) obj;
        return items.equals(cart.items);
    }
    
    @Override
    public int hashCode() {
        return items.hashCode();
    }
}
