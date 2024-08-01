package lm.Gestion_pedidos.repository;

import java.util.List;
import lm.Gestion_pedidos.model.ProductIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Lucas
 */
public interface ProductIngredientRepository extends JpaRepository<ProductIngredient, Long>{
    
     @Query("SELECT pi.ingredient.ingredientId FROM ProductIngredient pi WHERE pi.product.productId = :productId")
    List<Long> findIngredientIdsByProductId(@Param("productId") Long productId);
    
}
