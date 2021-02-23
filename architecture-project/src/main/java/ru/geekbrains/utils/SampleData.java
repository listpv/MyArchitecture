package ru.geekbrains.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.geekbrains.entities.Category;
import ru.geekbrains.entities.Product;
import ru.geekbrains.entities.Role;
import ru.geekbrains.entities.User;
import ru.geekbrains.repositories.*;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Date;

//@Component
@RequiredArgsConstructor
public class SampleData {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @PostConstruct
    public void init(){
        Category category1 = new Category("redFruit", "Red fruit");
        Category category2 = new Category("yellowFruit", "Yellow Fruit");
        Category category3 = new Category("other", "Other");

            categoryRepository.insert(category1);
            categoryRepository.insert(category2);
            categoryRepository.insert(category3);

            Product product1 = new Product("Orange","Brand Orange", new BigDecimal("50.0"), "orange.jpg", categoryRepository.findCategoryByName("Red fruit"));
            Product product2 = new Product("Lemon","Brand Lemon", new BigDecimal("70.0"), "lemon.jpg", categoryRepository.findCategoryByName("Yellow Fruit"));
            Product product3 = new Product("Lime", "Brand Lime",new BigDecimal("20.0"), "lime.jpg", categoryRepository.findCategoryByName("Other"));
            Product product4 = new Product("Mango","Brand Mango", new BigDecimal("100.0"), "mango.jpg", categoryRepository.findCategoryByName("Red fruit"));
            Product product5 = new Product("Apple","Brand Apple", new BigDecimal("95.0"), "apple.jpg", categoryRepository.findCategoryByName("Red fruit"));
            Product product6 = new Product("Pineapple","Brand Pineapple", new BigDecimal("110.0"), "pineapple.jpg", categoryRepository.findCategoryByName("Yellow Fruit"));
            Product product7 = new Product("Avocado","Brand Avocado", new BigDecimal("80.0"), "avocado.jpg", categoryRepository.findCategoryByName("Other"));
            Product product8 = new Product("Strawberry","Brand Strawberry", new BigDecimal("85.0"), "strawberry.jpg", categoryRepository.findCategoryByName("Other"));

//            Product product1 = new Product("Orange","Brand Orange", new BigDecimal("50.0"), "orange.jpg");
//            Product product2 = new Product("Lemon","Brand Lemon", new BigDecimal("70.0"), "lemon.jpg");
//            Product product3 = new Product("Lime", "Brand Lime",new BigDecimal("20.0"), "lime.jpg");
//            Product product4 = new Product("Mango","Brand Mango", new BigDecimal("100.0"), "mango.jpg");
//            Product product5 = new Product("Apple","Brand Apple", new BigDecimal("95.0"), "apple.jpg");
//            Product product6 = new Product("Pineapple","Brand Pineapple", new BigDecimal("110.0"), "pineapple.jpg");
//            Product product7 = new Product("Avocado","Brand Avocado", new BigDecimal("80.0"), "avocado.jpg");
//            Product product8 = new Product("Strawberry","Brand Strawberry", new BigDecimal("85.0"), "strawberry.jpg");

//            category1.addProduct(product1);
//            category1.addProduct(product4);
//            category1.addProduct(product5);
//            category2.addProduct(product2);
//            category2.addProduct(product6);
//            category3.addProduct(product3);
//            category3.addProduct(product7);
//            category3.addProduct(product8);

            productRepository.insert(product1);
            productRepository.insert(product2);
            productRepository.insert(product3);
            productRepository.insert(product4);
            productRepository.insert(product5);
            productRepository.insert(product6);
            productRepository.insert(product7);
            productRepository.insert(product8);

//            List<Product> productList = new ArrayList<>();
//            productList.add(product1);
//            productList.add(product4);
//            productList.add(product5);

//            categoryRepository.update("redFruit", productList);

            System.out.println("Category  " + "  --  " + categoryRepository.findCategoryByName("Red fruit").getProductList() + "  " + categoryRepository.findCategoryByName("Red fruit").getId());

            Role role1 = new Role("ROLE_USER");
            Role role2 = new Role("ROLE_ADMIN");

            User user1 = new User("Alex", "test@test1.com", "79000000001", new Date(), "Russia, SPb, Leninskiy street 10-10");
            user1.setUsername("alex");
            user1.setPassword("$2y$12$UUZsNpdv7iDXrhFab/LsmeNoMYmhlpzOd5lOdBXyKrhcbJfMIVGyO"); // 11
            user1.getRoles().add(role1);
            user1.getRoles().add(role2);

            User user2 = new User("Alena", "test@test2.com", "79000000002", new Date(), "Russia, Msk, Leninskiy street 01-01");
            user2.setUsername("alena");
            user2.setPassword("$2y$12$UUZsNpdv7iDXrhFab/LsmeNoMYmhlpzOd5lOdBXyKrhcbJfMIVGyO"); // 11
            user2.getRoles().add(role1);

            roleRepository.insert(role1);
            roleRepository.insert(role2);

            userRepository.insert(user1);
            userRepository.insert(user2);

            userRoleRepository.insert(user1, role1);
            userRoleRepository.insert(user1, role2);
            userRoleRepository.insert(user2, role1);


    }
}
