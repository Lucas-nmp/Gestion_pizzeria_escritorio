package lm.Gestion_pedidos.service;

import lm.Gestion_pedidos.model.Company;
import lm.Gestion_pedidos.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lucas
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
