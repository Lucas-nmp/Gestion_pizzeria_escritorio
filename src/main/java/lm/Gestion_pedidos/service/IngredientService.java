package lm.Gestion_pedidos.service;

import java.util.List;
import lm.Gestion_pedidos.model.Ingredient;
import lm.Gestion_pedidos.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lucas
 */
@Service
public class IngredientService {
    
    @Autowired
    IngredientRepository ingredientRepository;
    
    public List<Ingredient> findAllIngredients() {
        return ingredientRepository.findAll();
    }
    
    public void deleteIngredient(Ingredient ingredient) {
        ingredientRepository.delete(ingredient);
    }
    
    public Ingredient findIngredientById(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id).orElse(null);
        return ingredient;
    }
    
    public void addModifyIngredient(Ingredient ingredient) {
        ingredientRepository.save(ingredient);
    }
    
    
}
