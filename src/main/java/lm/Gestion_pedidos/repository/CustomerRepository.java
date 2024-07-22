package lm.Gestion_pedidos.repository;

import lm.Gestion_pedidos.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Lucas
 */
public interface CustomerRepository extends JpaRepository<Customer, Long>{
    
    @Query("SELECT c FROM Customer c WHERE c.phone = :phone")
    Customer findCustomerByPhone (@Param("phone") String phone);
    
}
