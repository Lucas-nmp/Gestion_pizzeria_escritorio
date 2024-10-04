package lm.Gestion_pedidos.repository;

import java.util.List;
import lm.Gestion_pedidos.model.Category;
import lm.Gestion_pedidos.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Interfaz de repositorio `ProductRepository` que gestiona las operaciones de persistencia de la entidad `Product`.
 * <p>
 * Esta interfaz extiende {@link org.springframework.data.jpa.repository.JpaRepository JpaRepository}, lo que proporciona
 * un conjunto completo de métodos para realizar operaciones estándar de CRUD (crear, leer, actualizar y eliminar) en 
 * la base de datos. Además, podemos definir métodos personalizados para consultas más complejas 
 * y específicas.
 * </p>
 * <p>
 * Se utilizan las anotaciones {@link org.springframework.data.jpa.repository.Query @Query} y 
 * {@link org.springframework.data.repository.query.Param @Param} para definir consultas personalizadas,
 * como la búsqueda de entidades por la categoría a la que pertenecen mediante el método {@code findByCategory}.
 * </p>
 * <p>
 * Al extender la interfaz `JpaRepository`, el repositorio hereda funcionalidades como `findAll()`, `save()`, `delete()`, 
 * entre otras, facilitando la interacción con la base de datos y minimizando la necesidad de implementar código manual 
 * para operaciones comunes.
 * </p>
 *
 * @author Lucas Morandeira Parejo
 */
public interface ProductRepository extends JpaRepository<Product, Long>{
    
    
    List<Product> findByCategory(Category category);
    
}
