public class Book {
    private int id;
    private String title;
    private String author;
    private double price;
    private boolean isRecommended;
    private int stockQuantity;
    
    public Book(int id, String title, String author, double price, int stockQuantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.isRecommended = false;
        this.stockQuantity = stockQuantity;
    }
    
    // Constructor for backward compatibility
    public Book(int id, String title, String author, double price) {
        this(id, title, author, price, 0);
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public boolean isRecommended() {
        return isRecommended;
    }
    
    public void setRecommended(boolean recommended) {
        isRecommended = recommended;
    }
    
    public int getStockQuantity() {
        return stockQuantity;
    }
    
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
    
    public boolean isInStock() {
        return stockQuantity > 0;
    }
    
    public boolean hasStock(int requestedQuantity) {
        return stockQuantity >= requestedQuantity;
    }
    
    public void reduceStock(int quantity) {
        if (quantity > 0 && stockQuantity >= quantity) {
            stockQuantity -= quantity;
        }
    }
    
    public void addStock(int quantity) {
        if (quantity > 0) {
            stockQuantity += quantity;
        }
    }
    
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", isRecommended=" + isRecommended +
                ", stockQuantity=" + stockQuantity +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return id == book.id;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
