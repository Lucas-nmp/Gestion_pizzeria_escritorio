package lm.Gestion_pedidos.service;

import java.util.List;
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
    
}
