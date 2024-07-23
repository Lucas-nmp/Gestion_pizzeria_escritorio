package lm.Gestion_pedidos.controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import lm.Gestion_pedidos.model.Category;
import lm.Gestion_pedidos.model.Ingredient;
import lm.Gestion_pedidos.service.CategoryService;
import lm.Gestion_pedidos.view.AddCategory;
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
    
    @Autowired
    private CategoryService categoryService;
    
    private Homepage homepage;
    private AddCategory addCategory;
    

    public void viewHomePage() {
        homepage.setLocationRelativeTo(null);
        homepage.setResizable(false);
        homepage.setVisible(true); 
        fillCategorys();
    }
    
    private void openAddCategory() {
        addCategory.setLocationRelativeTo(null);
        addCategory.setResizable(false);
        fillTableCategory();
        addCategory.setVisible(true);
        
    }
    
    @Autowired
    public void setHomepage(Homepage homepage) {
        this.homepage = homepage;
        
        this.homepage.getBtnCategory().addActionListener(this);
        
        /*
        para acciones con intro
        this.managementPage.getInvoiceId().addActionListener((ActionEvent e) -> {
            addInvoiceCustomer();
        });
        */
    }
    
    @Autowired
    public void setAddCategory(AddCategory addCategory) {
        this.addCategory = addCategory;
        
        this.addCategory.getCategorySave().addActionListener(this);
        this.addCategory.getCategoryName().addActionListener((ActionEvent e) -> {
            saveCategory();
            fillTableCategory();
            fillCategorys();
            cleanCategory();
        });
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        
        // Acciones del homepage
        if (e.getSource() == homepage.getBtnCategory()) {
            openAddCategory();
        }
        
        // Acciones del AddCategory
        if (e.getSource() == addCategory.getCategorySave()) {
            saveCategory();
            fillTableCategory();
            fillCategorys();
            cleanCategory();
        }
        
    }

    

    private void saveCategory() {
        String nameCategory = addCategory.getCategoryName().getText();
        Category category = new Category(null, nameCategory);
        categoryService.addCategory(category); 
    }
    
    private void fillTableCategory() {
        DefaultTableModel model = new DefaultTableModel();
        String[] cab = {"ID", "Nombre"};
        model.setColumnIdentifiers(cab);
        addCategory.getCategoryTable().setModel(model);
        
        List<Category> listCategorys = categoryService.getAllCategorys();
        listCategorys.forEach((category) -> {
            Object[] categoryLine = {
                category.getCategoryId(), 
                category.getName()
            };
            model.addRow(categoryLine);
        });
        
    }
    
    private void cleanCategory(){
        addCategory.getCategoryName().setText("");
    }

    private void fillCategorys() {
        List<Category> listCategorys = categoryService.getAllCategorys();
        JComboBox listCategary = homepage.getCategorys();
        listCategary.setPreferredSize(new Dimension(100, 25));
        listCategary.setMaximumSize(new Dimension(100, 25));
        if (listCategorys.isEmpty()) {
            listCategary.removeAllItems();
            listCategary.addItem("Añadir");
        } else {
            listCategary.removeAllItems();
            listCategorys.forEach((category) -> {
                String item = category.getCategoryId() + ", " + category.getName();
                listCategary.addItem(item);
            });
        }
        
    }
    
}
