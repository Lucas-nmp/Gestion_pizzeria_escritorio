package lm.Gestion_pedidos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad {@code ProductIngredient} que representa la relación entre un producto y los ingredientes que contien dentro de la aplicación.
 * <p>
 * Esta clase está anotada con Entity, lo que la define como una entidad gestionada por JPA.
 * Utiliza las anotaciones Data, NoArgsConstructor y 
 * AllArgsConstructor para generar automáticamente los métodos getter y setter, 
 * así como los constructores con y sin argumentos.
 * </p>
 * <p>
 * Los principales atributos de esta entidad son:
 * <ul>
 *   <li>{@code productIngredientId}: identificador único de la relación entre producto e ingrediente, generado automáticamente.</li>
 * </ul>
 * </p>
 * <p>
 * Relacionada con otras entidades mediante:
 * <ul>
 *   <li>{@code @ManyToOne} con la entidad {@link Product} para indicar el producto al que pertenece esta relación. 
 *   Utiliza {@code @JoinColumn(name = "product_id", nullable = false)} para establecer la columna `product_id` en la base de datos como 
 *   clave foránea y evitar valores nulos.</li>
 *   <li>{@code @ManyToOne} con la entidad {@link Ingredient} para indicar el ingrediente específico que forma parte del producto. 
 *   Utiliza {@code @JoinColumn(name = "ingredient_id", nullable = false)} para establecer la columna `ingredient_id` en la base de datos como 
 *   clave foránea y evitar valores nulos.</li>
 * </ul>
 * </p>
 * 
 * @author Lucas Morandeira Parejo
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor        
public class ProductIngredient {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productIngredientId;
    
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @ManyToOne
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;
    
}

