package lm.Gestion_pedidos.repository;

import lm.Gestion_pedidos.model.ProductIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Lucas
 */
public interface ProductIngredientRepository extends JpaRepository<ProductIngredient, Long>{
    
}
