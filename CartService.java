public class CartService {
    private Cart cart;
    private BookService bookService;
    
    public CartService(BookService bookService) {
        this.cart = new Cart();
        this.bookService = bookService;
    }
    
    public boolean addBookToCart(int bookId) {
        Book book = bookService.findBookById(bookId);
        if (book != null) {
            cart.addBook(book);
            return true;
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
}
