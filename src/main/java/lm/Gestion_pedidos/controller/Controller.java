package lm.Gestion_pedidos.controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import lm.Gestion_pedidos.model.Category;
import lm.Gestion_pedidos.model.Ingredient;
import lm.Gestion_pedidos.model.Product;
import lm.Gestion_pedidos.model.ProductIngredient;
import lm.Gestion_pedidos.service.CategoryService;
import lm.Gestion_pedidos.service.IngredientService;
import lm.Gestion_pedidos.service.ProductIngredientService;
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
 * 
 * TODO: agrandar el area de trabajo del homePage para que se puedan ver los ingredientes de los productos y poder añadir un botón de añadir producto al pedido
 * cambiar el foco al ingresar una categoría con enter o un ingrediente
 * 
 */
@Component
public class Controller implements ActionListener{
    
    
    private HashSet<String> listIngredientsProduct = new HashSet<>();
    private HashSet<Long> listIdIngredientsInProduct = new HashSet<>();
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
    
    @Autowired
    private ProductIngredientService productIngredientService;
    
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
        
        // desde el comboBox activar al seleccionar una categoría
        this.manageProduct.getBoxCategory().addActionListener((ActionEvent e) -> {
        setCategory((String) manageProduct.getBoxCategory().getSelectedItem());
    });
        
        //TODO: seleccionar producto de la tabla y permitir eliminarlo y editarlo
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
        
        // Acciones del ManageProduct
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
    
    // llenar sólo la tabla de el manageProduct, la tabla del homePage se tiene que llenar al seleccionar la categoría y según sea esta 
    private void fillTableProduct() {
        DefaultTableModel model = new DefaultTableModel();
        String[] headers = {"Nº", "Nombre", "Ingredientes", "PVP"};
        model.setColumnIdentifiers(headers);
        homepage.getTableProducts().setModel(model);
        manageProduct.getTableProducts().setModel(model);
        
        List<Product> listProducts = productService.findAllProducts();
        
        listProducts.forEach((product) -> {
            HashSet<String> ingredientList = obtenerListaIngredientes(product.getProductId());
            Object[] productLine = {
                product.getProductId(), 
                product.getName(), 
                String.join(", ", ingredientList), 
                product.getPrice()
                   
            };
            model.addRow(productLine);
        });   
        
        columnWidthProductHomepage(homepage.getTableProducts());
        columnWidthProductManageProduct(manageProduct.getTableProducts());
    }
    
    private HashSet<String> obtenerListaIngredientes(Long productId) {
        List<Long> listIdsIngredients = productIngredientService.findIngredientIdsByProductId(productId);
        HashSet<String> listNameIngredients = new HashSet<>();
        
        listIdsIngredients.forEach((id) -> {
            Ingredient ingredient = ingredientService.findIngredientById(id);
            String nameIngredient = ingredient.getName();
            listNameIngredients.add(nameIngredient);
        });
        return listNameIngredients;
    }
    
    private void columnWidthProductHomepage(JTable table) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Desactiva el ajuste automático de tamaño
        table.getColumnModel().getColumn(0).setPreferredWidth(30); 
        table.getColumnModel().getColumn(1).setPreferredWidth(90); 
        table.getColumnModel().getColumn(2).setPreferredWidth(306); 
        table.getColumnModel().getColumn(3).setPreferredWidth(50); 
    }
    
    private void columnWidthProductManageProduct(JTable table) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Desactiva el ajuste automático de tamaño
        table.getColumnModel().getColumn(0).setPreferredWidth(30); 
        table.getColumnModel().getColumn(1).setPreferredWidth(100); 
        table.getColumnModel().getColumn(2).setPreferredWidth(782); 
        table.getColumnModel().getColumn(3).setPreferredWidth(55); 
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
                //String item = category.getCategoryId() + ", " + category.getName();
                String item = category.getName();
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
        String categoryName = manageProduct.getCategoryTxt().getText();
        String name = manageProduct.getNameTxt().getText();
        String price = manageProduct.getPriceTxt().getText();
        BigDecimal priceB = null;
        if (categoryName.equals("Seleccionar") || categoryName.equals("Añadir")) {
            JOptionPane.showMessageDialog(manageProduct, "Seleccione una categoría de producto");
        } else if(name.equals("Nombre") || name.isEmpty()) {
            JOptionPane.showMessageDialog(manageProduct, "Escriba el nombre del producto");
        } else if(price.equals("Precio") || price.isEmpty()) {
            JOptionPane.showMessageDialog(manageProduct, "Escriba el precio del producto");
        } else if(listIdIngredientsInProduct.isEmpty() || listIdIngredientsInProduct == null) {
            JOptionPane.showMessageDialog(manageProduct, "Seleccione al menos un ingrediente");
        } else {
            JOptionPane.showMessageDialog(manageProduct, categoryName + name + price + listIdIngredientsInProduct.toString());
            try {
                priceB = new BigDecimal(price);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(manageProduct, "El precio no tiene el formato correcto");
            }
            Category category = categoryService.findCategoryByName(categoryName);
            Product product = new Product(null, name, priceB, category, null );
            productService.saveModifyProduct(product);
            
            for (Long id : listIdIngredientsInProduct) {
                Ingredient ingredient = ingredientService.findIngredientById(id);
                ProductIngredient productIngredient = new ProductIngredient(null, product, ingredient);
                productIngredientService.saveProductIngredient(productIngredient);
            }
            fillTableProduct();
            clearProduct();
        }  
    }
    
    private void clearProduct(){
        manageProduct.getBoxCategory().setSelectedIndex(0);
        manageProduct.getEdtNameProduct().setText("");
        manageProduct.getEdtPriceProduct().setText("");
        manageProduct.getNameTxt().setText("Nombre");
        manageProduct.getPriceTxt().setText("Precio");
        manageProduct.getIngredientsTxt().setText("Ingredientes");
        listIdIngredientsInProduct.clear();
        listIngredientsProduct.clear();
        
    }

    private void setNameProduct(String text) {
        manageProduct.getNameTxt().setText(text);
    }

    private void setPriceProduct(String text) {
        manageProduct.getPriceTxt().setText(text); 
    }

    private void addIngredientToProduct() {
        String ingredient = manageProduct.getBoxIngredients().getSelectedItem().toString();
        Long idInt = null;
        if (ingredient.equals("Seleccionar")) {
            JOptionPane.showMessageDialog(manageProduct, "Seleccione un ingrediente");
        } else {
            String[] idNameIngredient = ingredient.split(",");
            String id = idNameIngredient[0];
            try {
                idInt = Long.valueOf(id);
            } catch (NumberFormatException e) {
            }
            String nameIngredient = idNameIngredient[1];
            listIngredientsProduct.add(nameIngredient);
            listIdIngredientsInProduct.add(idInt);
            updateIngredientsInProduct(listIngredientsProduct);
        }
    }

    private void deleteIngredientFromProduct() {
        String ingredient = manageProduct.getBoxIngredients().getSelectedItem().toString();
        int idInt = 0;
        if (ingredient.equals("Seleccionar")) {
            JOptionPane.showMessageDialog(manageProduct, "Seleccione un ingrediente");
        } else {
            String[] idNameIngredient = ingredient.split(",");
            String id = idNameIngredient[0];
            try {
                idInt = Integer.parseInt(id);
            } catch (NumberFormatException e) {
            }
            String nameIngredient = idNameIngredient[1];
            listIngredientsProduct.remove(nameIngredient);
                listIdIngredientsInProduct.remove(idInt);
            updateIngredientsInProduct(listIngredientsProduct);
        }

        
    }
    
    private void updateIngredientsInProduct(HashSet<String> listIngredientProducts) {
        manageProduct.getIngredientsTxt().setText(String.join(", ", listIngredientProducts));
        manageProduct.getBoxIngredients().setSelectedIndex(0);
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

    private void setCategory(String string) {
        manageProduct.getCategoryTxt().setText(string);
    }

    

    

        
        
        
        
        
    
    
    
    
    
    
    

    
    
}
