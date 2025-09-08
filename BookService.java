import java.util.ArrayList;
import java.util.List;

public class BookService {
    private List<Book> booksList;
    private int nextId;
    
    public BookService() {
        this.booksList = new ArrayList<>();
        this.nextId = 1;
        initializeBooks();
    }
    
    private void initializeBooks() {
        booksList.add(new Book(nextId++, "The Great Gatsby", "F. Scott Fitzgerald", 12.99, 10));
        booksList.add(new Book(nextId++, "To Kill a Mockingbird", "Harper Lee", 14.99, 15));
        booksList.add(new Book(nextId++, "1984", "George Orwell", 13.99, 8));
        booksList.add(new Book(nextId++, "Pride and Prejudice", "Jane Austen", 11.99, 12));
        booksList.add(new Book(nextId++, "The Catcher in the Rye", "J.D. Salinger", 15.99, 5));
        
        // Set some books as recommended
        booksList.get(0).setRecommended(true);
        booksList.get(2).setRecommended(true);
    }
    
    public List<Book> listBooks() {
        return new ArrayList<>(booksList);
    }
    
    public List<Book> listRecommendedBooks() {
        List<Book> recommendedBooks = new ArrayList<>();
        for (Book book : booksList) {
            if (book.isRecommended()) {
                recommendedBooks.add(book);
            }
        }
        return recommendedBooks;
    }
    
    public List<Book> searchBook(String searchTerm) {
        List<Book> results = new ArrayList<>();
        String lowerSearchTerm = searchTerm.toLowerCase();
        
        for (Book book : booksList) {
            if (book.getTitle().toLowerCase().contains(lowerSearchTerm) ||
                book.getAuthor().toLowerCase().contains(lowerSearchTerm)) {
                results.add(book);
            }
        }
        return results;
    }
    
    public Book findBookById(int id) {
        for (Book book : booksList) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }
    
    public boolean addBook(String title, String author, double price) {
        Book newBook = new Book(nextId++, title, author, price, 0);
        return booksList.add(newBook);
    }
    
    public boolean addBook(String title, String author, double price, int stockQuantity) {
        Book newBook = new Book(nextId++, title, author, price, stockQuantity);
        return booksList.add(newBook);
    }
    
    public boolean updateBook(int id, String title, String author, double price) {
        Book book = findBookById(id);
        if (book != null) {
            book.setTitle(title);
            book.setAuthor(author);
            book.setPrice(price);
            return true;
        }
        return false;
    }
    
    public boolean deleteBook(int id) {
        Book book = findBookById(id);
        if (book != null) {
            return booksList.remove(book);
        }
        return false;
    }
    
    public boolean setBookAsRecommended(int id, boolean recommended) {
        Book book = findBookById(id);
        if (book != null) {
            book.setRecommended(recommended);
            return true;
        }
        return false;
    }
}
