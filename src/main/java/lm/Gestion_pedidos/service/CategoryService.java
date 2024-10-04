package lm.Gestion_pedidos.service;

import java.util.List;
import lm.Gestion_pedidos.model.Category;
import lm.Gestion_pedidos.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Clase de servicio `CategoryService` que gestiona las operaciones relacionadas con la entidad `Category`.
 * <p>
 * Esta clase está anotada con {@link org.springframework.stereotype.Service @Service}, lo que la define como un 
 * componente de servicio dentro del contexto de Spring. La anotación {@code @Service} indica que esta clase 
 * es responsable de interactuar con el repositorio de categorías (`CategoryRepository`).
 * </p>
 * <p>
 * La clase utiliza la anotación {@link org.springframework.beans.factory.annotation.Autowired @Autowired} 
 * para inyectar automáticamente la interfaz del repositorio de categorías (`CategoryRepository`).
 * Esto permite que Spring gestione la creación y la inyección de dependencias, haciendo que podamos utilizar 
 * los métodos estándar como `findAll()`, `save()`, `delete()` proporcionados por `JpaRepository`, así como 
 * los métodos adicionales que hayamos definido para realizar búsquedas específicas en la base de datos.
 * </p>
 * 
 * @author Lucas Morandeira Parejo
 */

@Service
public class CategoryService {
    
    @Autowired
    CategoryRepository categoryRepository;
    
    public List<Category> getAllCategorys() {
        return categoryRepository.findAll();
    }
    
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }
    
    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }
    
    public Category findCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }
    
    
}
