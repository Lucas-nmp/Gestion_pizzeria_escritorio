package lm.Gestion_pedidos.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad {@code Category} que representa una categoría dentro de la aplicación.
 * <p>
 * Esta clase está anotada con {@link javax.persistence.Entity @Entity}, lo que la define como una entidad gestionada por JPA.
 * Utiliza las anotaciones {@link lombok.Data @Data}, {@link lombok.NoArgsConstructor @NoArgsConstructor} y 
 * {@link lombok.AllArgsConstructor @AllArgsConstructor} para generar automáticamente los métodos getter y setter, así como
 * los constructores con y sin argumentos.
 * </p>
 * <p>
 * Los atributos de esta entidad son:
 * <ul>
 *   <li>{@code categoryId}: identificador único, generado automáticamente.</li>
 *   <li>{@code name}: nombre de la categoría.</li>
 * </ul>
 * </p>
 * 
 * @author Lucas Morandeira Parejo
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String name;
    

    
}
