import java.util.List;
import java.util.Scanner;

public class CustomerInterface {
    private BookService bookService;
    private CartService cartService;
    private OrderService orderService;
    private BrowsingHistory browsingHistory;
    private Scanner scanner;
    
    public CustomerInterface(BookService bookService, CartService cartService, OrderService orderService, BrowsingHistory browsingHistory) {
        this.bookService = bookService;
        this.cartService = cartService;
        this.orderService = orderService;
        this.browsingHistory = browsingHistory;
        this.scanner = new Scanner(System.in);
    }
    
    public void runCustomer() {
        int choice;
        while (true) {
            System.out.println("\n=== CUSTOMER INTERFACE ===");
            System.out.println("1. List books");
            System.out.println("2. List recommended books");
            System.out.println("3. Search book");
            System.out.println("4. View book details");
            System.out.println("5. View recently browsed books");
            System.out.println("6. List Cart");
            System.out.println("7. Add book to cart");
            System.out.println("8. Remove book from cart");
            System.out.println("9. Checkout / Place order");
            System.out.println("10. List orders");
            System.out.println("11. Go back");
            System.out.print("Enter your choice: ");
            
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    listBooks();
                    break;
                case 2:
                    listRecommendedBooks();
                    break;
                case 3:
                    searchBook();
                    break;
                case 4:
                    viewBookDetails();
                    break;
                case 5:
                    viewRecentlyBrowsedBooks();
                    break;
                case 6:
                    listCart();
                    break;
                case 7:
                    addBookToCart();
                    break;
                case 8:
                    removeBookFromCart();
                    break;
                case 9:
                    checkout();
                    break;
                case 10:
                    listOrders();
                    break;
                case 11:
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
                                 (book.isRecommended() ? " [RECOMMENDED]" : ""));
            }
        }
    }
    
    private void listRecommendedBooks() {
        System.out.println("\n=== RECOMMENDED BOOKS ===");
        List<Book> recommendedBooks = bookService.listRecommendedBooks();
        if (recommendedBooks.isEmpty()) {
            System.out.println("No recommended books available.");
        } else {
            for (Book book : recommendedBooks) {
                System.out.println("ID: " + book.getId() + 
                                 " | Title: " + book.getTitle() + 
                                 " | Author: " + book.getAuthor() + 
                                 " | Price: $" + book.getPrice());
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
                                 (book.isRecommended() ? " [RECOMMENDED]" : ""));
            }
        }
    }
    
    private void listCart() {
        System.out.println("\n=== YOUR CART ===");
        cartService.displayCart();
    }
    
    private void addBookToCart() {
        System.out.print("Enter book ID to add to cart: ");
        int bookId = scanner.nextInt();
        
        Book book = bookService.findBookById(bookId);
        if (book == null) {
            System.out.println("Book not found with ID: " + bookId);
            scanner.nextLine(); // consume newline
            return;
        }
        
        if (!book.isInStock()) {
            System.out.println("Sorry, '" + book.getTitle() + "' is out of stock.");
            scanner.nextLine(); // consume newline
            return;
        }
        
        System.out.print("Enter quantity (available: " + book.getStockQuantity() + "): ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        if (quantity <= 0) {
            System.out.println("Quantity must be positive.");
            return;
        }
        
        if (cartService.addBookToCart(bookId, quantity)) {
            System.out.println("Added " + quantity + " copies of '" + book.getTitle() + "' to cart successfully!");
        } else {
            System.out.println("Failed to add book to cart. Please check stock availability.");
        }
    }
    
    private void removeBookFromCart() {
        System.out.print("Enter book ID to remove from cart: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        if (cartService.removeBookFromCart(bookId)) {
            System.out.println("Book removed from cart successfully!");
        } else {
            System.out.println("Book not found in cart or failed to remove.");
        }
    }
    
    private void checkout() {
        if (cartService.isCartEmpty()) {
            System.out.println("Your cart is empty. Add some books before checkout.");
            return;
        }
        
        System.out.println("\n=== CHECKOUT ===");
        cartService.displayCart();
        
        System.out.print("Confirm order? (y/n): ");
        String confirm = scanner.nextLine();
        
        if (confirm.toLowerCase().equals("y")) {
            Order order = orderService.placeOrder(cartService.getCart());
            if (order != null) {
                System.out.println("\nOrder placed successfully!");
                System.out.println("Order ID: " + order.getOrderId());
                System.out.println("Total Amount: $" + String.format("%.2f", order.getTotalAmount()));
                cartService.clearCart();
                System.out.println("Cart cleared.");
            } else {
                System.out.println("Failed to place order.");
            }
        } else {
            System.out.println("Order cancelled.");
        }
    }
    
    private void viewBookDetails() {
        System.out.print("Enter book ID to view details: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        Book book = bookService.findBookById(bookId);
        if (book == null) {
            System.out.println("Book not found with ID: " + bookId);
            return;
        }
        
        // Add book to browsing history
        browsingHistory.addBook(book);
        
        // Display detailed book information
        System.out.println("\n=== BOOK DETAILS ===");
        System.out.println("ID: " + book.getId());
        System.out.println("Title: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor());
        System.out.println("Price: $" + String.format("%.2f", book.getPrice()));
        System.out.println("Stock Quantity: " + book.getStockQuantity());
        System.out.println("Availability: " + (book.isInStock() ? "In Stock" : "Out of Stock"));
        System.out.println("Recommended: " + (book.isRecommended() ? "Yes" : "No"));
        
        System.out.println("\nBook has been added to your browsing history.");
    }
    
    private void viewRecentlyBrowsedBooks() {
        System.out.println("\n=== BROWSING HISTORY ===");
        browsingHistory.displayHistory();
        
        if (!browsingHistory.isEmpty()) {
            System.out.println("\nLinkedList Contents (Internal Structure):");
            System.out.println("LinkedList<Book> history = " + browsingHistory.getHistory());
            
            System.out.print("\nWould you like to clear your browsing history? (y/n): ");
            String choice = scanner.nextLine();
            if (choice.toLowerCase().equals("y")) {
                browsingHistory.clearHistory();
                System.out.println("Browsing history cleared.");
            }
        }
    }
    
    private void listOrders() {
        System.out.println("\n=== YOUR ORDERS ===");
        orderService.displayAllOrders();
    }
}
