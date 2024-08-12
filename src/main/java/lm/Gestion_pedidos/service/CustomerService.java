package lm.Gestion_pedidos.service;

import java.util.List;
import lm.Gestion_pedidos.model.Customer;
import lm.Gestion_pedidos.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lucas
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
