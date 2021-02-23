package ru.geekbrains.entities;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Category{

    private Long id;

    @NotEmpty
    @Size(min=3, max=20, message="Title must have 3-20 characters")
    private String code;

    @NotEmpty
    @Size(min=3, max=20, message="Title must have 3-20 characters")
    private String name;

    private List<Product> productList = new ArrayList<>();

    public Category() {

    }

    public Category(String code, String name) {
        this.code = code;
        this.name = name;
    }

}
