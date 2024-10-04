package lm.Gestion_pedidos.service;

import lm.Gestion_pedidos.model.Company;
import lm.Gestion_pedidos.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Clase de servicio `CompanyService` que gestiona las operaciones relacionadas con la entidad `Company`.
 * <p>
 * Esta clase está anotada con {@link org.springframework.stereotype.Service @Service}, lo que la define como un 
 * componente de servicio dentro del contexto de Spring. La anotación {@code @Service} indica que esta clase 
 * es responsable de interactuar con el repositorio de categorías (`CategoryRepository`).
 * </p>
 * <p>
 * La clase utiliza la anotación {@link org.springframework.beans.factory.annotation.Autowired @Autowired} 
 * para inyectar automáticamente la interfaz del repositorio de compañia (`CompanyRepository`).
 * Esto permite que Spring gestione la creación y la inyección de dependencias, haciendo que podamos utilizar 
 * los métodos estándar como `findById()`, `save()`, `delete()` proporcionados por `JpaRepository`, así como 
 * los métodos adicionales que hayamos definido para realizar búsquedas específicas en la base de datos.
 * </p>
 * 
 * @author Lucas Morandeira Parejo
 */
@Service
public class CompanyService {
    
    @Autowired
    CompanyRepository companyRepository;
    
    public void saveCompany(Company company){
        companyRepository.save(company);
    }
    
    public Company fingCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }
    
}
