package lm.Gestion_pedidos.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad {@code Ingredient} que representa un Ingrediente dentro de la aplicación.
 * <p>
 * Esta clase está anotada con Entity, lo que la define como una entidad gestionada por JPA.
 * Utiliza las anotaciones Data, NoArgsConstructor y 
 * AllArgsConstructor para generar automáticamente los métodos getter y setter, así como
 * los constructores con y sin argumentos.
 * </p>
 * 
 * Los atributos de esta entidad son:
 * <ul>
 *   <li>{@code ingredientId}: identificador único, generado automáticamente.</li>
 *   <li>{@code name}: nombre del ingrediente.</li>
 *   <li>{@code price}: precio individual del ingrediente representado como un {@link java.math.BigDecimal}.</li>
 * </ul>
 * 
 * <p>
 * Relacionada con otras entidades mediante:
 * <ul>
 *   <li>{@code @OneToMany} con la entidad {@link ProductIngredient} para representar los productos que contienen este ingrediente.</li>
 * </ul>
 * </p>
 * 
 * @author Lucas Morandeira Parejo
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ingredientId;
    private String name;
    private BigDecimal price;
    
    
    @OneToMany(mappedBy = "ingredient")
    private List<ProductIngredient> products;

}