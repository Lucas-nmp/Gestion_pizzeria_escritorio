package lm.Gestion_pedidos.repository;

import lm.Gestion_pedidos.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Lucas
 */
public interface IngredientRepository extends JpaRepository<Ingredient, Long>{
    
    
    @Query("SELECT i FROM Ingredient i WHERE i.name = :name")
    Ingredient findByName(@Param("name") String name);
    
}
