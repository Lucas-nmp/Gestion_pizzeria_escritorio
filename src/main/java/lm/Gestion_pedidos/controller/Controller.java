package lm.Gestion_pedidos.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import lm.Gestion_pedidos.view.Homepage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author Lucas
 */
@Component
public class Controller implements ActionListener{
    
    @Autowired
    private ApplicationContext context;
    
    private Homepage homepage;
    

    public void viewHomePage() {
        
        homepage.setLocationRelativeTo(null);
        homepage.setResizable(false);
        homepage.setVisible(true); 
    }
    
    @Autowired
    public void setHomepage(Homepage homepage) {
        this.homepage = homepage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
}
