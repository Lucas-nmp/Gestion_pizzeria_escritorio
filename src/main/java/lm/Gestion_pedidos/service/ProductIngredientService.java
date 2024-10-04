package lm.Gestion_pedidos.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lm.Gestion_pedidos.model.Ingredient;
import lm.Gestion_pedidos.model.Product;
import lm.Gestion_pedidos.model.ProductIngredient;
import lm.Gestion_pedidos.repository.ProductIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Clase de servicio `ProductIngredientService` que gestiona las operaciones relacionadas con la entidad `ProductIngredient`.
 * <p>
 * Esta clase está anotada con {@link org.springframework.stereotype.Service @Service}, lo que la define como un 
 * componente de servicio dentro del contexto de Spring. La anotación {@code @Service} indica que esta clase 
 * es responsable de interactuar con el repositorio de Productos/Ingredientes (`ProductIngredientRepository`).
 * </p>
 * <p>
 * La clase utiliza la anotación {@link org.springframework.beans.factory.annotation.Autowired @Autowired} 
 * para inyectar automáticamente la interfaz del repositorio de Productos/Ingredientes (`ProductIngredientRepository`).
 * Esto permite que Spring gestione la creación y la inyección de dependencias, haciendo que podamos utilizar 
 * los métodos estándar como `findAll()`, `save()`, `delete()` proporcionados por `JpaRepository`, así como 
 * los métodos adicionales que hayamos definido para realizar búsquedas específicas en la base de datos.
 * </p>
 * 
 * @author Lucas Morandeira Parejo
 */
@Service
public class ProductIngredientService {
    
    @Autowired
    ProductIngredientRepository productIngredientRepository;
    
    public void saveProductIngredient(ProductIngredient productIngredient) {
        productIngredientRepository.save(productIngredient);
    }
    
    public List<Long> findIngredientIdsByProductId(Long productId) {
        return productIngredientRepository.findIngredientIdsByProductId(productId);
    }
    
    public List<ProductIngredient> findByProduct(Product product) {
        return productIngredientRepository.findByProductId(product.getProductId());
    }

    public void deleteProductIngredient(ProductIngredient productIngredient) {
        productIngredientRepository.delete(productIngredient);
    }
    
    @Transactional
    public void deleteAll(List<ProductIngredient> productIngredients) {
        productIngredientRepository.deleteAll(productIngredients);
    }
    
    
}
