
package lm.Gestion_pedidos.service;

import java.util.List;
import lm.Gestion_pedidos.model.Product;
import lm.Gestion_pedidos.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lucas
 */
@Service
public class ProductService {
    
    @Autowired
    ProductRepository productRepository;
    
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
    
    public Product findProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
    
    public void saveModifyProduct(Product product) {
        productRepository.save(product);
    }
    
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }
}
