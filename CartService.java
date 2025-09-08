public class CartService {
    private Cart cart;
    private BookService bookService;
    
    public CartService(BookService bookService) {
        this.cart = new Cart();
        this.bookService = bookService;
    }
    
    public boolean addBookToCart(int bookId) {
        return addBookToCart(bookId, 1);
    }
    
    public boolean addBookToCart(int bookId, int quantity) {
        Book book = bookService.findBookById(bookId);
        if (book != null) {
            try {
                cart.addBook(book, quantity);
                return true;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                return false;
            }
        }
        return false;
    }
    
    public boolean removeBookFromCart(int bookId) {
        return cart.removeBook(bookId);
    }
    
    public Cart getCart() {
        return cart;
    }
    
    public void clearCart() {
        cart.clear();
    }
    
    public double getCartTotal() {
        return cart.getTotalPrice();
    }
    
    public boolean isCartEmpty() {
        return cart.isEmpty();
    }
    
    public void displayCart() {
        System.out.println(cart.toString());
    }
    
    public boolean validateCartStock() {
        return cart.validateStock();
    }
    
    public void processCartOrder() {
        if (validateCartStock()) {
            cart.processOrder();
        } else {
            throw new IllegalStateException("Cannot process order: insufficient stock for some items");
        }
    }
}
