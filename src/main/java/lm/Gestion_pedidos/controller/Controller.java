package lm.Gestion_pedidos.controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import lm.Gestion_pedidos.model.Category;
import lm.Gestion_pedidos.model.Ingredient;
import lm.Gestion_pedidos.model.Product;
import lm.Gestion_pedidos.service.CategoryService;
import lm.Gestion_pedidos.service.IngredientService;
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
    
    
    private HashSet<String> listIngredientProducts = new HashSet<>();
    
    @Autowired
    private ApplicationContext context;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private IngredientService ingredientService;
    
    private Homepage homepage;
    private AddCategory addCategory;
    private ManageProduct manageProduct;
    

    public void viewHomePage() {
        homepage.setLocationRelativeTo(null);
        homepage.setResizable(false);
        fillIngredients(homepage.getIngredientModify());
        fillCategorys(homepage.getCategorys()); 
        fillTableProduct();
        homepage.setVisible(true);
        
        
    }
    
    private void openAddCategory() {
        
        addCategory.setLocationRelativeTo(null);
        addCategory.setResizable(false);
        fillTableCategory();
        addCategory.setModal(true);
        addCategory.setVisible(true);  
    }
    
    private void openViewProducts() {
        
        manageProduct.setLocationRelativeTo(null);
        manageProduct.setResizable(false);
        manageProduct.setModal(true);
        fillIngredients(manageProduct.getBoxIngredients());
        fillCategorys(manageProduct.getBoxCategory());
        manageProduct.setVisible(true);
        
        
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
        
        this.manageProduct.getEdtNameProduct().addActionListener((ActionEvent e) -> {
            setNameProduct(manageProduct.getEdtNameProduct().getText()); 
        });
        
        this.manageProduct.getEdtPriceProduct().addActionListener((ActionEvent e) -> {
            setPriceProduct(manageProduct.getEdtPriceProduct().getText()); 
        });
        
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        
        // Acciones del homepage
        if (e.getSource() == homepage.getBtnCategory()) {
            openAddCategory();
        }
        
        if (e.getSource() == homepage.getBtnAddProduct()) {
            openViewProducts();
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
        if (e.getSource() == manageProduct.getBtnSaveProduct()) {
            saveProduct();
        }
        
        if (e.getSource() == manageProduct.getBtnAddIngredient()) {
            addIngredientToProduct();
        }
        
        if (e.getSource() == manageProduct.getBtnDeleteIngredient()) {
            deleteIngredientFromProduct();
        }        
    }

    private void saveCategory() {
        String nameCategory = addCategory.getCategoryName().getText();
        if (!nameCategory.isEmpty()) {
            Category category = new Category(null, nameCategory);
            categoryService.addCategory(category); 
        } else {
            JOptionPane.showMessageDialog(addCategory, "El nombre de la categoría no puede estar vacío");
        }
        
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

    private void fillCategorys(JComboBox boxCategorys) {
        List<Category> listCategorys = categoryService.getAllCategorys();
        //JComboBox listCategory = homepage.getCategorys();
        boxCategorys.setPreferredSize(new Dimension(100, 25));
        boxCategorys.setMaximumSize(new Dimension(100, 25));
        if (listCategorys.isEmpty()) {
            boxCategorys.removeAllItems();
            boxCategorys.addItem("Añadir");
        } else {
            boxCategorys.removeAllItems();
            listCategorys.forEach((category) -> {
                String item = category.getCategoryId() + ", " + category.getName();
                boxCategorys.addItem(item);
            });
        } 
    }
    
    private void fillIngredients(JComboBox boxIngredients) {
        List<Ingredient> listIngredients = ingredientService.getAllIngredients();
        boxIngredients.setPreferredSize(new Dimension(100, 25));
        boxIngredients.setMaximumSize(new Dimension(100, 25));
        if (listIngredients.isEmpty()) {
            boxIngredients.removeAllItems();
            boxIngredients.addItem("Añadir");
        } else {
            boxIngredients.removeAllItems();
            listIngredients.forEach((ingredient) -> {
                String item = ingredient.getIngredientId() + ", " + ingredient.getName();
                boxIngredients.addItem(item);
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

    private void saveProduct() {
        
    }

    private void setNameProduct(String text) {
        manageProduct.getNameTxt().setText(text);
    }

    private void setPriceProduct(String text) {
        manageProduct.getPriceTxt().setText(text); 
    }

    private void addIngredientToProduct() {
        String ingredient = manageProduct.getBoxIngredients().getSelectedItem().toString();
        
        //Ingredient ingredient = (Ingredient) manageProduct.getBoxIngredients().getSelectedItem(); // creo que es mejor trabajar con String en lugar con ingredientes y luego buscarlos por el id
        listIngredientProducts.add(ingredient);
        updateIngredientsInProduct(listIngredientProducts);
    }

    private void deleteIngredientFromProduct() {
        String ingredient = manageProduct.getBoxIngredients().getSelectedItem().toString();
        //Ingredient ingredient = (Ingredient) manageProduct.getBoxIngredients().getSelectedItem();
        listIngredientProducts.remove(ingredient);
        updateIngredientsInProduct(listIngredientProducts);
    }
    
    private void updateIngredientsInProduct(HashSet<String> listIngredientProducts) {
        manageProduct.getIngredientsTxt().setText(listIngredientProducts.toString());
        
        /*
        List<String> lista = new ArrayList<>();
        listIngredientProducts.forEach(ingredient -> {
            lista.add(ingredient.getName());
        });
        manageProduct.getIngredientsTxt().setText(String.join(", ", lista));
        */
    }
    
    
    
    
    
    

    
    
}
