package lm.Gestion_pedidos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad {@code Category} que representa una categoría dentro de la aplicación.
 * <p>
 * Esta clase está anotada con Entity, lo que la define como una entidad gestionada por JPA.
 * Utiliza las anotaciones Data, NoArgsConstructor y 
 * AllArgsConstructor para generar automáticamente los métodos getter y setter, así como
 * los constructores con y sin argumentos.
 * </p>
 * Los atributos de esta entidad son:
 * <ul>
 *   <li>{@code categoryId}: identificador único, generado automáticamente.</li>
 *   <li>{@code name}: nombre de la categoría.</li>
 * </ul>
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
