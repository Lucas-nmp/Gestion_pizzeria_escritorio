package lm.Gestion_pedidos.repository;

import lm.Gestion_pedidos.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Lucas
 */
public interface CategoryRepository extends JpaRepository<Category, Long>{
    
}
