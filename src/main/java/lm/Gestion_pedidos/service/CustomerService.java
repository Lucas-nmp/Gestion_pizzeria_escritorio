package lm.Gestion_pedidos.service;

import java.util.List;
import lm.Gestion_pedidos.model.Customer;
import lm.Gestion_pedidos.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Clase de servicio `CustomerService` que gestiona las operaciones relacionadas con la entidad `Customer`.
 * <p>
 * Esta clase está anotada con {@link org.springframework.stereotype.Service @Service}, lo que la define como un 
 * componente de servicio dentro del contexto de Spring. La anotación {@code @Service} indica que esta clase 
 * es responsable de interactuar con el repositorio de clientes (`CustomerRepository`).
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
public class CustomerService {
    
    @Autowired
    CustomerRepository customerRepository;
    
    public Customer findCustomerByPhone(String phone){
        Customer customer = customerRepository.findCustomerByPhone(phone);
        return customer;
    }
    
    public Customer findCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        return customer;
    }
    
    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customer);
    }
    
    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }
    
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }
    
    
}
