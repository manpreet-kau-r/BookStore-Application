import java.util.LinkedList;

public class BrowsingHistory {
    private LinkedList<Book> history;
    private static final int MAX_HISTORY_SIZE = 20; // Limit history to prevent memory issues
    
    public BrowsingHistory() {
        this.history = new LinkedList<>();
    }
    
    public void addBook(Book book) {
        if (book == null) {
            return;
        }
        
        // Remove the book if it already exists to avoid duplicates
        history.remove(book);
        
        // Add the book to the front of the list (most recent)
        history.addFirst(book);
        
        // Maintain maximum size
        if (history.size() > MAX_HISTORY_SIZE) {
            history.removeLast();
        }
    }
    
    public LinkedList<Book> getHistory() {
        return new LinkedList<>(history);
    }
    
    public boolean isEmpty() {
        return history.isEmpty();
    }
    
    public void clearHistory() {
        history.clear();
    }
    
    public int getHistorySize() {
        return history.size();
    }
    
    public void displayHistory() {
        if (history.isEmpty()) {
            System.out.println("No browsing history available.");
            return;
        }
        
        System.out.println("\n=== RECENTLY BROWSED BOOKS ===");
        System.out.println("(Most recent first)");
        
        int count = 1;
        for (Book book : history) {
            System.out.println(count + ". ID: " + book.getId() + 
                             " | Title: " + book.getTitle() + 
                             " | Author: " + book.getAuthor() + 
                             " | Price: $" + book.getPrice() +
                             " | Stock: " + book.getStockQuantity() +
                             (book.isRecommended() ? " [RECOMMENDED]" : ""));
            count++;
        }
        
        System.out.println("\nTotal browsed books: " + history.size());
    }
    
    @Override
    public String toString() {
        if (history.isEmpty()) {
            return "Browsing history is empty";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Recently Browsed Books (").append(history.size()).append(" items):\n");
        
        int count = 1;
        for (Book book : history) {
            sb.append(count).append(". ").append(book.getTitle())
              .append(" by ").append(book.getAuthor()).append("\n");
            count++;
        }
        
        return sb.toString();
    }
}
