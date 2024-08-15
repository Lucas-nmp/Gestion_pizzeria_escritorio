
package lm.Gestion_pedidos.repository;

import lm.Gestion_pedidos.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Lucas
 */
public interface CompanyRepository extends JpaRepository<Company, Long>{
    
}
