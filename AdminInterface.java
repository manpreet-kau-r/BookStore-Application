import java.util.List;
import java.util.Scanner;

public class AdminInterface {
    private BookService bookService;
    private Scanner scanner;
    
    public AdminInterface(BookService bookService) {
        this.bookService = bookService;
        this.scanner = new Scanner(System.in);
    }
    
    public void runAdmin() {
        int choice;
        while (true) {
            System.out.println("\n=== ADMIN INTERFACE ===");
            System.out.println("1. List books");
            System.out.println("2. Search book");
            System.out.println("3. Update book");
            System.out.println("4. Delete book");
            System.out.println("5. Create/Add book");
            System.out.println("6. Set book as recommended");
            System.out.println("7. Go back");
            System.out.print("Enter your choice: ");
            
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    listBooks();
                    break;
                case 2:
                    searchBook();
                    break;
                case 3:
                    updateBook();
                    break;
                case 4:
                    deleteBook();
                    break;
                case 5:
                    addBook();
                    break;
                case 6:
                    setBookAsRecommended();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void listBooks() {
        System.out.println("\n=== ALL BOOKS ===");
        List<Book> books = bookService.listBooks();
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            for (Book book : books) {
                System.out.println("ID: " + book.getId() + 
                                 " | Title: " + book.getTitle() + 
                                 " | Author: " + book.getAuthor() + 
                                 " | Price: $" + book.getPrice() +
                                 " | Stock: " + book.getStockQuantity() +
                                 " | Recommended: " + (book.isRecommended() ? "Yes" : "No"));
            }
        }
    }
    
    private void searchBook() {
        System.out.print("Enter title or author to search: ");
        String searchTerm = scanner.nextLine();
        
        List<Book> results = bookService.searchBook(searchTerm);
        if (results.isEmpty()) {
            System.out.println("No books found matching: " + searchTerm);
        } else {
            System.out.println("\n=== SEARCH RESULTS ===");
            for (Book book : results) {
                System.out.println("ID: " + book.getId() + 
                                 " | Title: " + book.getTitle() + 
                                 " | Author: " + book.getAuthor() + 
                                 " | Price: $" + book.getPrice() +
                                 " | Recommended: " + (book.isRecommended() ? "Yes" : "No"));
            }
        }
    }
    
    private void updateBook() {
        System.out.print("Enter book ID to update: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        Book book = bookService.findBookById(bookId);
        if (book == null) {
            System.out.println("Book not found with ID: " + bookId);
            return;
        }
        
        System.out.println("Current book details:");
        System.out.println("Title: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor());
        System.out.println("Price: $" + book.getPrice());
        
        System.out.print("Enter new title (or press Enter to keep current): ");
        String newTitle = scanner.nextLine();
        if (newTitle.trim().isEmpty()) {
            newTitle = book.getTitle();
        }
        
        System.out.print("Enter new author (or press Enter to keep current): ");
        String newAuthor = scanner.nextLine();
        if (newAuthor.trim().isEmpty()) {
            newAuthor = book.getAuthor();
        }
        
        System.out.print("Enter new price (or -1 to keep current): ");
        double newPrice = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        if (newPrice == -1) {
            newPrice = book.getPrice();
        }
        
        if (bookService.updateBook(bookId, newTitle, newAuthor, newPrice)) {
            System.out.println("Book updated successfully!");
        } else {
            System.out.println("Failed to update book.");
        }
    }
    
    private void deleteBook() {
        System.out.print("Enter book ID to delete: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        Book book = bookService.findBookById(bookId);
        if (book == null) {
            System.out.println("Book not found with ID: " + bookId);
            return;
        }
        
        System.out.println("Are you sure you want to delete: " + book.getTitle() + "? (y/n)");
        String confirm = scanner.nextLine();
        
        if (confirm.toLowerCase().equals("y")) {
            if (bookService.deleteBook(bookId)) {
                System.out.println("Book deleted successfully!");
            } else {
                System.out.println("Failed to delete book.");
            }
        } else {
            System.out.println("Delete operation cancelled.");
        }
    }
    
    private void addBook() {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();
        
        System.out.print("Enter book price: ");
        double price = scanner.nextDouble();
        
        System.out.print("Enter stock quantity: ");
        int stockQuantity = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        if (stockQuantity < 0) {
            System.out.println("Stock quantity cannot be negative.");
            return;
        }
        
        if (bookService.addBook(title, author, price, stockQuantity)) {
            System.out.println("Book added successfully with " + stockQuantity + " units in stock!");
        } else {
            System.out.println("Failed to add book.");
        }
    }
    
    private void setBookAsRecommended() {
        System.out.print("Enter book ID: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        Book book = bookService.findBookById(bookId);
        if (book == null) {
            System.out.println("Book not found with ID: " + bookId);
            return;
        }
        
        System.out.println("Current recommendation status: " + (book.isRecommended() ? "Recommended" : "Not Recommended"));
        System.out.print("Set as recommended? (y/n): ");
        String choice = scanner.nextLine();
        
        boolean recommended = choice.toLowerCase().equals("y");
        
        if (bookService.setBookAsRecommended(bookId, recommended)) {
            System.out.println("Book recommendation status updated successfully!");
        } else {
            System.out.println("Failed to update recommendation status.");
        }
    }
}
