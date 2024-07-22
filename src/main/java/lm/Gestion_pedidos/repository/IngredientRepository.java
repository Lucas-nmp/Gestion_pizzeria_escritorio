package lm.Gestion_pedidos.repository;

import lm.Gestion_pedidos.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Lucas
 */
public interface IngredientRepository extends JpaRepository<Ingredient, Long>{
    
}
