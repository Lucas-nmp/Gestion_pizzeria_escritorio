/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lm.Gestion_pedidos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad {@code Company} que representa la empresa dentro de la aplicación.
 * <p>
 * Esta clase está anotada con {@link javax.persistence.Entity @Entity}, lo que la define como una entidad gestionada por JPA.
 * Utiliza las anotaciones {@link lombok.Data @Data}, {@link lombok.NoArgsConstructor @NoArgsConstructor} y 
 * {@link lombok.AllArgsConstructor @AllArgsConstructor} para generar automáticamente los métodos getter y setter, así como
 * los constructores con y sin argumentos.
 * </p>
 * <p>
 * Los atributos de esta entidad son:
 * <ul>
 *   <li>{@code Id}: identificador único, generado automáticamente.</li>
 *   <li>{@code name}: nombre de la compañia.</li>
 *   <li>{@code phone}: teléfono de la compañia.</li>
 *   <li>{@code address}: dirección de la compañia.</li>
 *   <li>{@code cif}: cif de la compañia.</li>
 * </ul>
 * </p>
 * 
 * @author Lucas Morandeira Parejo
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    private String phone;
    private String address;
    private String cif;
    
}
