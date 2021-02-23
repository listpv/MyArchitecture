package ru.geekbrains.services;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.entities.Product;
import ru.geekbrains.repositories.ProductRepository;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getProductsByCategory(String code){
        return productRepository.getProductsByCategory(code);
    }

    public Product getOne(Long id) {
        return productRepository.getOne(id);
    }

    @Transactional
    public void insert(Product product){
            productRepository.insert(product);
    }

    @Transactional
    public void update(Product product){
        productRepository.update(product);
    }

//
//    public void getProductByMaxPrice(){
//        List<Product> productByMaxPrice = productRepository.getProductByMaxPrice();
//        System.out.println(productByMaxPrice);
//    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

//    @Transactional
//    public void remove(Long id) {
//        productRepository.deleteById(id);
//    }
}
