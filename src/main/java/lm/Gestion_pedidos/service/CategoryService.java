package lm.Gestion_pedidos.service;

import java.util.List;
import lm.Gestion_pedidos.model.Category;
import lm.Gestion_pedidos.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lucas
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
