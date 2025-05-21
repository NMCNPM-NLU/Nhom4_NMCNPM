package vn.edu.hcmuaf.fit.webbansach.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

// Books.java
@Entity
@Table(name = "Books")
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String author;

    @Column(columnDefinition = "TEXT")
    private String description;

    private BigDecimal price;
    private Integer stockQty;
    private LocalDate publishedDate;
    private String imageUrl;

    @ManyToMany
    @JoinTable(
            name = "Book_Categories",          // đúng DDL
            joinColumns = @JoinColumn(
                    name = "bookId",      // cột trong Book_Categories
                    referencedColumnName = "id"         // cột PK của Books
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "categoryId",  // cột trong Book_Categories
                    referencedColumnName = "id"         // cột PK của Categories
            )
    )
    private Set<Categories> categories = new HashSet<>();

    public Books() {
    }

    public Books(Integer id, String title, String author, String description, BigDecimal price, Integer stockQty, LocalDate publishedDate, String imageUrl, Set<Categories> categories) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
        this.stockQty = stockQty;
        this.publishedDate = publishedDate;
        this.imageUrl = imageUrl;
        this.categories = categories;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStockQty() {
        return stockQty;
    }

    public void setStockQty(Integer stockQty) {
        this.stockQty = stockQty;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<Categories> getCategories() {
        return categories;
    }

    public void setCategories(Set<Categories> categories) {
        this.categories = categories;
    }
}
