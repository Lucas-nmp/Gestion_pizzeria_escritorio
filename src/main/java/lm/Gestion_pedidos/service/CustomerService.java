package lm.Gestion_pedidos.service;

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
    
    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customer);
    }
    
    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }
    
    
}
