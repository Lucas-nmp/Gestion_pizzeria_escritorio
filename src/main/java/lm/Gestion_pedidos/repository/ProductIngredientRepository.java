package lm.Gestion_pedidos.repository;

import java.util.List;
import lm.Gestion_pedidos.model.Ingredient;
import lm.Gestion_pedidos.model.Product;
import lm.Gestion_pedidos.model.ProductIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Interfaz de repositorio `ProductIngredientRepository` que gestiona las operaciones de persistencia de la entidad `ProductIngredient`.
 * <p>
 * Esta interfaz extiende {@link org.springframework.data.jpa.repository.JpaRepository JpaRepository}, lo que proporciona
 * un conjunto completo de métodos para realizar operaciones estándar de CRUD (crear, leer, actualizar y eliminar) en 
 * la base de datos. Además, podemos definir métodos personalizados para consultas más complejas 
 * y específicas.
 * </p>
 * <p>
 * Se utilizan las anotaciones {@link org.springframework.data.jpa.repository.Query @Query} y 
 * {@link org.springframework.data.repository.query.Param @Param} para definir consultas personalizadas,
 * como la búsqueda de una lista de ingredientes por el id del producto en el que están incluidos mediante el método {@code findByProductId}.
 * </p>
 * <p>
 * Al extender la interfaz `JpaRepository`, el repositorio hereda funcionalidades como `findAll()`, `save()`, `delete()`, 
 * entre otras, facilitando la interacción con la base de datos y minimizando la necesidad de implementar código manual 
 * para operaciones comunes.
 * </p>
 *
 * @author Lucas Morandeira Parejo
 */
public interface ProductIngredientRepository extends JpaRepository<ProductIngredient, Long>{
    
     @Query("SELECT pi.ingredient.ingredientId FROM ProductIngredient pi WHERE pi.product.productId = :productId")
    List<Long> findIngredientIdsByProductId(@Param("productId") Long productId);
    
     @Query("SELECT pi FROM ProductIngredient pi WHERE pi.product.id = :productId")
    List<ProductIngredient> findByProductId(@Param("productId") Long productId);

    
    
    
    
}
