package vn.edu.hcmuaf.fit.webbansach.model.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public class BookDto {
    private Integer id;
    @NotBlank(message = "Tieu de khong duoc de trong")
    private String title;
    @NotBlank(message = "Tac gia khong duoc de trong")
    private String author;
    private String description;
    @DecimalMin(value = "0.0", inclusive = false, message = "Gia phai lon hon 0")
    private Double price;
    @Min(value = 0, message = "So luong ton kho khong duoc am")
    private Integer stockQty;
    @PastOrPresent(message = "Ngay xuat ban khong duoc trong tuong lai")
    private LocalDate publishedDate;
    private String imageUrl;
    @NotNull(message = "Danh muc khong duoc de trong")
    @Size(min = 1, message = "Phai co it nhat mot danh muc")
    private List<Integer> categoryIds;

    // Getters và setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public Integer getStockQty() { return stockQty; }
    public void setStockQty(Integer stockQty) { this.stockQty = stockQty; }
    public LocalDate getPublishedDate() { return publishedDate; }
    public void setPublishedDate(LocalDate publishedDate) { this.publishedDate = publishedDate; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public List<Integer> getCategoryIds() { return categoryIds; }
    public void setCategoryIds(List<Integer> categoryIds) { this.categoryIds = categoryIds; }
}