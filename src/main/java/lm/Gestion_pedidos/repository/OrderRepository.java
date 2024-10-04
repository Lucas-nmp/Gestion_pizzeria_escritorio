package lm.Gestion_pedidos.repository;

import lm.Gestion_pedidos.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfaz de repositorio `OrderRepository` que gestiona las operaciones de persistencia de la entidad `Order`.
 * <p>
 * Esta interfaz extiende {@link org.springframework.data.jpa.repository.JpaRepository JpaRepository}, lo que proporciona
 * un conjunto completo de métodos para realizar operaciones estándar de CRUD (crear, leer, actualizar y eliminar) en 
 * la base de datos. Además, podemos definir métodos personalizados para consultas más complejas 
 * y específicas.
 * </p>
 * <p>
 * Al extender la interfaz `JpaRepository`, el repositorio hereda funcionalidades como `findAll()`, `save()`, `delete()`, 
 * entre otras, facilitando la interacción con la base de datos y minimizando la necesidad de implementar código manual 
 * para operaciones comunes.
 * </p>
 *
 * @author Lucas Morandeira Parejo
 */
public interface OrderRepository extends JpaRepository<Order, Long>{
    
}
