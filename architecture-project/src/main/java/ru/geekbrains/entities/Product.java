package ru.geekbrains.entities;

import com.fasterxml.jackson.annotation.JsonView;
import ru.geekbrains.utils.ProductPriceObserver;
import ru.geekbrains.utils.Subject;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Size(min=3, max=20, message="Title must have 3-20 characters")
    @Column(name = "title")
    private String title;

    @NotEmpty
    @Size(min=3, max=20, message="Title must have 3-20 characters")
    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "image")
    private String image;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Product() {
    }

    public Product(String title, String brandName, BigDecimal price, String image, Category category) {
        this.title = title;
        this.brandName = brandName;
        this.price = price;
        this.image = image;
        this.category = category;
//        this.category.getProductList().add(this);
    }

    public Product(String title,String brandName, BigDecimal price, String image) {
        this.title = title;
        this.brandName = brandName;
        this.image = image;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {

        this.category = category;
//        this.category.getProductList().add(this);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + getId() +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", brandName=" + brandName +
                ", image=" + image +
                '}';
    }
}
