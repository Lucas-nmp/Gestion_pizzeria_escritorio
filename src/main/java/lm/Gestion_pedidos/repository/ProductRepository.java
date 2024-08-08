package lm.Gestion_pedidos.repository;

import java.util.List;
import lm.Gestion_pedidos.model.Category;
import lm.Gestion_pedidos.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Lucas
 */
public interface ProductRepository extends JpaRepository<Product, Long>{
    
    /*
      @Query("SELECT pi FROM ProductIngredient pi WHERE pi.product.id = :productId")
    List<ProductIngredient> findByProductId(@Param("productId") Long productId);
    */
    
    List<Product> findByCategory(Category category);
    
}
