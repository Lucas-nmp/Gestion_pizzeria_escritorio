package lm.Gestion_pedidos.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad {@code Product} que representa un producto dentro de la aplicación.
 * <p>
 * Esta clase está anotada con Entity, lo que la define como una entidad gestionada por JPA.
 * Utiliza las anotaciones Data, NoArgsConstructor y 
 * AllArgsConstructor para generar automáticamente los métodos getter y setter, así como
 * los constructores con y sin argumentos.
 * </p>
 * <p>
 * Los atributos de esta entidad son:
 * <ul>
 *   <li>{@code productId}: identificador único, generado automáticamente.</li>
 *   <li>{@code name}: nombre del producto.</li>
 *   <li>{@code price}: precio del producto representado como un {@link java.math.BigDecimal}.</li>
 * </ul>
 * </p>
 * <p>
 * Relacionada con otras entidades mediante:
 * <ul>
 *   <li>{@code @ManyToOne} con la entidad {@link Category} para representar la categoría a la que pertenece el producto.</li>
 *   <li>{@code @OneToMany} con la entidad {@link ProductIngredient} para indicar los ingredientes asociados al producto.</li>
 * </ul>
 * </p>
 * 
 * @author Lucas Morandeira Parejo
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String name;
    private BigDecimal price;
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    
    @OneToMany(mappedBy = "product")
    private List<ProductIngredient> productIngredients;
    
}