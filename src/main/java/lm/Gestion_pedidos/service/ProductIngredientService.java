package lm.Gestion_pedidos.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lm.Gestion_pedidos.model.Ingredient;
import lm.Gestion_pedidos.model.Product;
import lm.Gestion_pedidos.model.ProductIngredient;
import lm.Gestion_pedidos.repository.ProductIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lucas
 */
@Service
public class ProductIngredientService {
    
    @Autowired
    ProductIngredientRepository productIngredientRepository;
    
    public void saveProductIngredient(ProductIngredient productIngredient) {
        productIngredientRepository.save(productIngredient);
    }
    
    public List<Long> findIngredientIdsByProductId(Long productId) {
        return productIngredientRepository.findIngredientIdsByProductId(productId);
    }
    
    public List<ProductIngredient> findByProduct(Product product) {
        return productIngredientRepository.findByProductId(product.getProductId());
    }

    public void deleteProductIngredient(ProductIngredient productIngredient) {
        productIngredientRepository.delete(productIngredient);
    }

    public void deleteByProductAndIngredient(Product product, Ingredient ingredient) {
        productIngredientRepository.deleteByProductAndIngredient(product, ingredient);
    }
    
    @Transactional
    public void deleteAll(List<ProductIngredient> productIngredients) {
        productIngredientRepository.deleteAll(productIngredients);
    }
    
    
}
