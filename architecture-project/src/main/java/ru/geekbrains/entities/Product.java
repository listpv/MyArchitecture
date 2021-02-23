package ru.geekbrains.entities;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
public class Product{

    private Long id;

    @NotEmpty
    @Size(min=3, max=20, message="Title must have 3-20 characters")
    private String title;

    @NotEmpty
    @Size(min=3, max=20, message="Title must have 3-20 characters")
    private String brandName;

    private String image;

    private BigDecimal price;

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
