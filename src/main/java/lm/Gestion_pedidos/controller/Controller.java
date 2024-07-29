package lm.Gestion_pedidos.controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import lm.Gestion_pedidos.model.Category;
import lm.Gestion_pedidos.model.Ingredient;
import lm.Gestion_pedidos.model.Product;
import lm.Gestion_pedidos.service.CategoryService;
import lm.Gestion_pedidos.service.IngredientService;
import lm.Gestion_pedidos.service.ProductService;
import lm.Gestion_pedidos.view.AddCategory;
import lm.Gestion_pedidos.view.Homepage;
import lm.Gestion_pedidos.view.ManageIngredient;
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
    private Ingredient ingredientSelectedToModify;
    private Category categorySelectedToModify;
    
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
    private ManageIngredient manageIngredient;
    

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
        fillTableProduct();
        manageProduct.setVisible(true);  
    }
    
    private void openViewIngredients() {
        manageIngredient.setLocationRelativeTo(null);
        manageIngredient.setResizable(false);
        manageIngredient.setModal(true);
        fillTableIngredients();
        manageIngredient.setVisible(true);
    }
    
    @Autowired
    public void setHomepage(Homepage homepage) {
        this.homepage = homepage;
        
        this.homepage.getBtnCategory().addActionListener(this);
        this.homepage.getBtnProduct().addActionListener(this);
        this.homepage.getBtnIngredient().addActionListener(this);
        
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
        this.addCategory.getDeleteCategory().addActionListener(this);
        this.addCategory.getCategorySave().addActionListener(this);
        this.addCategory.getCategoryName().addActionListener((ActionEvent e) -> {
            saveCategory();
            fillTableCategory();
            fillCategorys(homepage.getCategorys()); 
            cleanCategory();
        });
        
        this.addCategory.getCategoryTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 1) {
                    categorySelectedToModify = new Category();
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    Long idCategory = (Long) target.getValueAt(row, 0);
                    String nameCategory = (String) target.getValueAt(row, 1);
                    addCategory.getCategoryName().setText(nameCategory);
                    categorySelectedToModify.setCategoryId(idCategory);
                    categorySelectedToModify.setName(nameCategory);
                }
            }
            
        });
        
        // TODO seleccionar categoría de la tabla y permitir eliminarla o modificarla
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
        
        //TODO seleccionar producto de la tabla y permitir eliminarlo y editarlo
    }
    
    @Autowired
    public void setManageIngredient(ManageIngredient manageIngredient) {
        this.manageIngredient = manageIngredient;
        
        this.manageIngredient.getBtnSaveIngredient().addActionListener(this);
        this.manageIngredient.getBtnDeleteIngredient().addActionListener(this);
        
        //permite guardar un ingrediente con la tecla enter desde el textField de precio
        this.manageIngredient.getEdtPriceIngredient().addActionListener((ActionEvent e) -> {
            saveIngredient();
        });
        
        this.manageIngredient.getTableIngredient().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    ingredientSelectedToModify = new Ingredient();
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();

                    Long idIngredient = (Long) target.getValueAt(row, 0);
                    String nameIngredient = (String) target.getValueAt(row, 1);
                    BigDecimal priceIngredient = (BigDecimal) target.getValueAt(row, 2);
                    manageIngredient.getEdtNameIngredient().setText(nameIngredient);
                    manageIngredient.getEdtPriceIngredient().setText(priceIngredient.toString());
                    ingredientSelectedToModify.setIngredientId(idIngredient);
                    ingredientSelectedToModify.setName(nameIngredient);
                    ingredientSelectedToModify.setPrice(priceIngredient);
                }  
            }            
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
        
        if (e.getSource() == homepage.getBtnIngredient()) {
            openViewIngredients();
        }
        
        // Acciones del AddCategory
        if (e.getSource() == addCategory.getCategorySave()) {
            saveCategory();
            fillTableCategory();
            fillCategorys(homepage.getCategorys()); 
            cleanCategory();
        }
        
        if (e.getSource() == addCategory.getDeleteCategory()) {
            deleteCategory();
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
        
        // Acciones del ManageIngredient
        if (e.getSource() == manageIngredient.getBtnDeleteIngredient()) {
            deleteIngredient();
        }
        
        if (e.getSource() == manageIngredient.getBtnSaveIngredient()) {
            saveIngredient();
        }
    }

    private void saveCategory() {
        String nameCategory = addCategory.getCategoryName().getText();
        if (!nameCategory.isEmpty()) {
            if (categorySelectedToModify == null) {
                Category category = new Category(null, nameCategory);
                categoryService.addCategory(category); 
                fillCategorys(homepage.getCategorys());
                fillTableCategory();
                cleanCategory();
            } else {
                categorySelectedToModify.setName(nameCategory);
                categoryService.addCategory(categorySelectedToModify);
                fillCategorys(homepage.getCategorys());
                fillTableCategory();
                cleanCategory();
                categorySelectedToModify = null;
            }
            
        } else {
            JOptionPane.showMessageDialog(addCategory, "El nombre de la categoría no puede estar vacío");
        }  
    }
    
    
    private void deleteCategory() {
        if (categorySelectedToModify != null) {
            categoryService.deleteCategory(categorySelectedToModify);
            fillTableCategory();
            fillCategorys(homepage.getCategorys());
            cleanCategory();
            categorySelectedToModify = null;
        } else {
            JOptionPane.showMessageDialog(addCategory, "Seleccione una Categoría de la tabla");
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
        String[] headers = {"Nº", "Nombre", "Ingredientes", "Precio"};
        model.setColumnIdentifiers(headers);
        homepage.getTableProducts().setModel(model);
        manageProduct.getTableProducts().setModel(model);
        
        List<Product> listProducts = productService.findAllProducts();
        
        listProducts.forEach((product) -> {
            Object[] categoryLine = {
                product.getProductId(), 
                product.getName(), 
                null, 
                product.getPrice()
                // crear un string con la lista de ingredientes obtenida por id desde ingredienteProducto    
            };
            model.addRow(categoryLine);
        });   
        
    }
    
    private void fillTableIngredients() {
        DefaultTableModel model = new DefaultTableModel();
        String[] headers = {"Nº", "Ingrediente", "Precio"};
        model.setColumnIdentifiers(headers);
        manageIngredient.getTableIngredient().setModel(model);
        
        List<Ingredient> listIngredients = ingredientService.getAllIngredients();
        if (listIngredients.isEmpty()) {
            //JOptionPane.showMessageDialog(homepage, "Añada ingredientes a la categoría");
        } else {
            listIngredients.forEach((ingredient) -> {
                Object[] ingredientLine = {
                    ingredient.getIngredientId(), 
                    ingredient.getName(), 
                    ingredient.getPrice()
                };
                model.addRow(ingredientLine);
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
            boxCategorys.addItem("Seleccionar");
            listCategorys.forEach((category) -> {
                String item = category.getCategoryId() + ", " + category.getName();
                boxCategorys.addItem(item);
            });
        } 
    }
    
    private void fillIngredients(JComboBox boxIngredients) {
        List<Ingredient> listIngredients = ingredientService.getAllIngredients();
        boxIngredients.setPreferredSize(new Dimension(120, 25));
        boxIngredients.setMaximumSize(new Dimension(120, 25));
        if (listIngredients.isEmpty()) {
            boxIngredients.removeAllItems();
            boxIngredients.addItem("Añadir");
        } else {
            boxIngredients.removeAllItems();
            boxIngredients.addItem("Seleccionar");
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
        // al guardar un producto hay que guardar la lista de ingredientes en productIngredient con el id del producto
    }

    private void setNameProduct(String text) {
        manageProduct.getNameTxt().setText(text);
    }

    private void setPriceProduct(String text) {
        manageProduct.getPriceTxt().setText(text); 
    }

    private void addIngredientToProduct() {
        String ingredient = manageProduct.getBoxIngredients().getSelectedItem().toString();
        if (ingredient.equals("Seleccionar")) {
            JOptionPane.showMessageDialog(manageProduct, "Seleccione un ingrediente");
        } else {
            listIngredientProducts.add(ingredient);
            updateIngredientsInProduct(listIngredientProducts);
        }
    }

    private void deleteIngredientFromProduct() {
        String ingredient = manageProduct.getBoxIngredients().getSelectedItem().toString();
        if (ingredient.equals("Seleccionar")) {
            JOptionPane.showMessageDialog(manageProduct, "Seleccione un ingrediente");
        } else {
            listIngredientProducts.remove(ingredient);
            updateIngredientsInProduct(listIngredientProducts);
        }

        
    }
    
    private void updateIngredientsInProduct(HashSet<String> listIngredientProducts) {
        manageProduct.getIngredientsTxt().setText(listIngredientProducts.toString());
        
        // añadir solo el nombre no el id, el id usarlo para otra cosa y volver a seleccionar el primer elemento del box selector, también en el de categoría
        // tal vez podría hacer una lista con los id de los ingredientes separando el id y el nombre, el id esta en el box de ingredientes con una , 
        // separarlo con un split y añadirlo a una lista de int que despues puedo usar para buscar los ingredientes y añadirlos a la entidad productIngredient
        
        /*
        List<String> lista = new ArrayList<>();
        listIngredientProducts.forEach(ingredient -> {
            lista.add(ingredient.getName());
        });
        manageProduct.getIngredientsTxt().setText(String.join(", ", lista));
        */
    }

    private void deleteIngredient() {
        if (ingredientSelectedToModify != null) {
            ingredientService.deleteIngredient(ingredientSelectedToModify);
            fillTableIngredients();
            cleanDataIngredient();
            fillIngredients(homepage.getIngredientModify());
            ingredientSelectedToModify = null;
        } else {
            JOptionPane.showMessageDialog(manageIngredient, "Seleccione un ingrediente");
        }
        
    }

    
    private void saveIngredient() {
        String name = manageIngredient.getEdtNameIngredient().getText();
        BigDecimal price = null;
        try {
            price = new BigDecimal(manageIngredient.getEdtPriceIngredient().getText().replace(',', '.'));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(manageIngredient, "El precio tiene que ser numérico");
            return;
        }

        if (price != null && !name.isEmpty()) {
            if (ingredientSelectedToModify == null) {
                Ingredient ingredient = new Ingredient(null, name, price);
                ingredientService.addModifyIngredient(ingredient);
                fillTableIngredients();
                cleanDataIngredient();
                fillIngredients(homepage.getIngredientModify());
            } else {
                ingredientSelectedToModify.setName(name);
                ingredientSelectedToModify.setPrice(price);
                ingredientService.addModifyIngredient(ingredientSelectedToModify);
                fillTableIngredients();
                cleanDataIngredient();
                fillIngredients(homepage.getIngredientModify());
                ingredientSelectedToModify = null;
            }
        } else {
            JOptionPane.showMessageDialog(manageIngredient, "Tiene que escribir el nombre y el precio del ingrediente");
        }
    }

    private void cleanDataIngredient() {
        manageIngredient.getEdtNameIngredient().setText("");
        manageIngredient.getEdtPriceIngredient().setText("");
        
    }

    

        
        
        
        
        
    
    
    
    
    
    
    

    
    
}
