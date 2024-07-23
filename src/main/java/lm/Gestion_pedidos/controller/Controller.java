package lm.Gestion_pedidos.controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import lm.Gestion_pedidos.model.Category;
import lm.Gestion_pedidos.model.Ingredient;
import lm.Gestion_pedidos.model.Product;
import lm.Gestion_pedidos.service.CategoryService;
import lm.Gestion_pedidos.service.ProductService;
import lm.Gestion_pedidos.view.AddCategory;
import lm.Gestion_pedidos.view.Homepage;
import lm.Gestion_pedidos.view.ManageProduct;
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
    
    @Autowired
    private ProductService productService;
    
    private Homepage homepage;
    private AddCategory addCategory;
    private ManageProduct manageProduct;
    

    public void viewHomePage() {
        homepage.setLocationRelativeTo(null);
        homepage.setResizable(false);
        
        homepage.setVisible(true); 
        fillCategorys(homepage.getCategorys()); 
        fillTableProduct();
        
        
    }
    
    private void openAddCategory() {
        addCategory = new AddCategory(homepage);
        addCategory.setLocationRelativeTo(null);
        addCategory.setResizable(false);
        fillTableCategory();
        addCategory.setVisible(true);  
    }
    
    private void openViewProducts() {
        manageProduct = new ManageProduct(homepage);
        manageProduct.setLocationRelativeTo(null);
        manageProduct.setResizable(false);
        manageProduct.setVisible(true);
        
        fillCategorys(manageProduct.getBoxCategory());
        
    }
    
    @Autowired
    public void setHomepage(Homepage homepage) {
        this.homepage = homepage;
        
        this.homepage.getBtnCategory().addActionListener(this);
        this.homepage.getBtnProduct().addActionListener(this);
        
        this.homepage.getCategorys().addActionListener((ActionEvent e) -> {
            handleCategorySelection();
            
        });
        
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
            fillCategorys(homepage.getCategorys()); 
            cleanCategory();
        });
    }
    
    @Autowired
    public void setManageProduct(ManageProduct manageProduct) {
        this.manageProduct = manageProduct;
        
        this.manageProduct.getBtnAddIngredient().addActionListener(this);
        this.manageProduct.getBtnDeleteIngredient().addActionListener(this);
        this.manageProduct.getBtnSaveProduct().addActionListener(this);
        
        
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        
        // Acciones del homepage
        if (e.getSource() == homepage.getBtnCategory()) {
            openAddCategory();
        }
        
        if (e.getSource() == homepage.getBtnProduct()) {
            openViewProducts();
        }
        
        // Acciones del AddCategory
        if (e.getSource() == addCategory.getCategorySave()) {
            saveCategory();
            fillTableCategory();
            fillCategorys(homepage.getCategorys()); 
            cleanCategory();
        }
        
        // Acciones del AddProduct
        if (e.getSource() == homepage.getBtnAddProduct()) {
            openViewProducts();
        }
        
    }

    

    private void saveCategory() {
        String nameCategory = addCategory.getCategoryName().getText();
        Category category = new Category(null, nameCategory);
        categoryService.addCategory(category); 
    }
    
    private void fillTableCategory() {
        DefaultTableModel model = new DefaultTableModel();
        String[] headers = {"ID", "Nombre"};
        model.setColumnIdentifiers(headers);
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
    
    private void fillTableProduct() {
        DefaultTableModel model = new DefaultTableModel();
        String[] headers = {"Nº", "Nombre", "Ingredientes"};
        model.setColumnIdentifiers(headers);
        homepage.getTableProducts().setModel(model);
        
        List<Product> listProducts = productService.findAllProducts();
        if (listProducts.isEmpty()) {
            JOptionPane.showMessageDialog(homepage, "Añada productos a la categoría");
        } else {
            listProducts.forEach((product) -> {
                Object[] categoryLine = {
                    product.getProductId(), 
                    product.getName(), 
                    null
                    // crear un string con la lista de ingredientes obtenida por id desde ingredienteProducto    
                };
                model.addRow(categoryLine);
            });   
        }
        
    }
    
    private void cleanCategory(){
        addCategory.getCategoryName().setText("");
    }

    private void fillCategorys(JComboBox listCategory) {
        List<Category> listCategorys = categoryService.getAllCategorys();
        //JComboBox listCategory = homepage.getCategorys();
        listCategory.setPreferredSize(new Dimension(100, 25));
        listCategory.setMaximumSize(new Dimension(100, 25));
        if (listCategorys.isEmpty()) {
            listCategory.removeAllItems();
            listCategory.addItem("Añadir");
        } else {
            listCategory.removeAllItems();
            listCategorys.forEach((category) -> {
                String item = category.getCategoryId() + ", " + category.getName();
                listCategory.addItem(item);
            });
        } 
    }
    
    

    private void handleCategorySelection() {
        String selectedItem = (String) homepage.getCategorys().getSelectedItem();
        if (selectedItem != null && !selectedItem.equals("Añadir")) {
            // añadir un elemento a la tabla que diga qua añada categorias y productos
            //JOptionPane.showMessageDialog(homepage, selectedItem);
        } else {
            fillTableProduct(); // para que llene la lista de productos cuando se selecciona una nueva categoría
            // mostrar la lista de productos de la categoría seleccionada
            //JOptionPane.showMessageDialog(homepage, "Añada Categorias y productos ");
        }
        
    }

    
    
}
