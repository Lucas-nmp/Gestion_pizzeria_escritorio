package lm.Gestion_pedidos.controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import lm.Gestion_pedidos.model.Category;
import lm.Gestion_pedidos.model.Customer;
import lm.Gestion_pedidos.model.Ingredient;
import lm.Gestion_pedidos.model.Product;
import lm.Gestion_pedidos.model.ProductIngredient;
import lm.Gestion_pedidos.service.CategoryService;
import lm.Gestion_pedidos.service.CustomerService;
import lm.Gestion_pedidos.service.IngredientService;
import lm.Gestion_pedidos.service.ProductIngredientService;
import lm.Gestion_pedidos.service.ProductService;
import lm.Gestion_pedidos.view.ManageCategory;
import lm.Gestion_pedidos.view.Homepage;
import lm.Gestion_pedidos.view.ManageCustomer;
import lm.Gestion_pedidos.view.ManageIngredient;
import lm.Gestion_pedidos.view.ManageProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
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
public class Controller {
    
    
    private HashSet<String> listIngredientsProduct = new HashSet<>();
    private HashSet<Long> listIdIngredientsInProduct = new HashSet<>();
    private Ingredient ingredientSelectedToModify;
    private Category categorySelectedToModify;
    private Product productSelectedToModify;
    private Customer customerSelectedToModify;
    private Customer customerSelectedForTheOrder;
    private Product productSelectedForTheOrder;
    private HashSet<String> modifications;
    private BigDecimal modificationsPrice;
    private BigDecimal total;
    
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
    
    @Autowired
    private CustomerService customerService;
    
    private Homepage homepage;
    private ManageCategory addCategory;
    private ManageProduct manageProduct;
    private ManageIngredient manageIngredient;
    private ManageCustomer manageCustomer;
    

    public void viewHomePage() {
        homepage.setLocationRelativeTo(null);
        homepage.setResizable(false);
        fillIngredients(homepage.getIngredientModify());
        fillCategorys(homepage.getCategorys()); 
        fillHeadersOrder();
        modifications = new HashSet<>();
        modificationsPrice = BigDecimal.ZERO;
        total = BigDecimal.ZERO;
        homepage.getCategorys().setSelectedIndex(1);
        String selectedItem = (String) homepage.getCategorys().getSelectedItem();
        Category category = categoryService.findCategoryByName(selectedItem);
        
        fillTableProduct(category);
        homepage.setVisible(true);  
    }
    
    private void openManageCategory() {
        
        addCategory.setLocationRelativeTo(null);
        addCategory.setResizable(false);
        fillTableCategory();
        cleanCategory();
        addCategory.setModal(true);
        addCategory.setVisible(true);  
    }
    
    private void openManageProducts() {
        manageProduct.setLocationRelativeTo(null);
        manageProduct.setResizable(false);
        manageProduct.setModal(true);
        fillIngredients(manageProduct.getBoxIngredients());
        fillCategorys(manageProduct.getBoxCategory());
        fillTableProduct();
        clearViewProduct();
        manageProduct.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                homepage.getCategorys().setSelectedIndex(1);
                String selectedItem = (String) homepage.getCategorys().getSelectedItem();
                Category category = categoryService.findCategoryByName(selectedItem);
            }
        
        });
        manageProduct.setVisible(true);  
    }
    
    private void openManageIngredients() {
        manageIngredient.setLocationRelativeTo(null);
        manageIngredient.setResizable(false);
        manageIngredient.setModal(true);
        fillTableIngredients();
        cleanDataIngredient();
        manageIngredient.setVisible(true);
    }
    
    private void openManageCustomer(String phone) {
        manageCustomer.setLocationRelativeTo(null);
        manageCustomer.setResizable(false);
        manageCustomer.setModal(true);
        fillTableCustomer();
        clearViewCustomer(phone);
        manageCustomer.setVisible(true);
        
    }
    
    @Autowired
    public void setHomepage(Homepage homepage) {
        this.homepage = homepage;
        
        this.homepage.getBtnCategory().addActionListener(e -> openManageCategory());
        this.homepage.getBtnProduct().addActionListener(e -> openManageProducts());
        this.homepage.getBtnIngredient().addActionListener(e -> openManageIngredients());
        this.homepage.getBtnCustomer().addActionListener(e -> openManageCustomer(""));
        this.homepage.getBtnAdd().addActionListener(e -> addForTheOrder());
        this.homepage.getBtnAdd2().addActionListener(e -> addForTheOrder());
        this.homepage.getBtnRemoveIngredient().addActionListener(e -> removeIngredient());
        this.homepage.getBtnAddIngredient().addActionListener(e -> addIngredient());
        this.homepage.getBtnCancelOrder().addActionListener(e -> cancelOrder());
        this.homepage.getBtnConfirmOrder().addActionListener(e -> confirmOrder());
        this.homepage.getBtnRemoveFromOrder().addActionListener(e -> removeFromOrder());
        
        
        this.homepage.getEdtPhoneCustomer().addActionListener((ActionEvent e) -> {
            String phone = homepage.getEdtPhoneCustomer().getText();
            lookForCustomer(phone);
        });
          
        this.homepage.getCategorys().addActionListener((ActionEvent e) -> {
            handleCategorySelection();
            
        });
        
        this.homepage.getTableOrder().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    JTable target = (JTable) e.getSource();
                    int selectedRow = target.getSelectedRow(); // Obtener la fila seleccionada

                    if (selectedRow != -1) { // Verificar que se haya seleccionado una fila
                        // Con este codigo al seleccionar pregunta si se quiere eliminar el artículo seleccionado
                        /*int response = JOptionPane.showConfirmDialog(homepage, 
                            "¿Deseas eliminar el elemento seleccionado?", 
                            "Confirmar eliminación", 
                            JOptionPane.YES_NO_OPTION);

                        if (response == JOptionPane.YES_OPTION) {
                            DefaultTableModel model = (DefaultTableModel) target.getModel();
                            model.removeRow(selectedRow); // Eliminar la fila del modelo
                        }*/
                    }
                }
            }
        
        });
        
        
        
        this.homepage.getTableProducts().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 1) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    Long id = (Long) target.getValueAt(row, 0);
                    productSelectedForTheOrder = productService.findProductById(id);
                }
            }
            
        });
    }
    
    @Autowired
    public void setManageCategory(ManageCategory addCategory) {
        this.addCategory = addCategory;
        this.addCategory.getDeleteCategory().addActionListener(e -> deleteCategory());
        this.addCategory.getCategorySave().addActionListener(e -> {
            saveCategory();
            fillTableCategory();
            fillCategorys(homepage.getCategorys()); 
            cleanCategory();    
        });
        
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
        
        
    }
    
    @Autowired
    public void setManageProduct(ManageProduct manageProduct) {
        this.manageProduct = manageProduct;
        
        this.manageProduct.getBtnAddIngredient().addActionListener(e -> addIngredientToProduct());
        this.manageProduct.getBtnDeleteIngredient().addActionListener(e -> deleteIngredientFromProduct());
        this.manageProduct.getBtnSaveProduct().addActionListener(e -> saveOrModifyProduct());
        this.manageProduct.getBtnDeleteProduct().addActionListener(e -> deleteProduct());
        
        this.manageProduct.getEdtNameProduct().addActionListener((ActionEvent e) -> {
            setNameProduct(manageProduct.getEdtNameProduct().getText()); 
        });
        
        this.manageProduct.getEdtNameProduct().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                setNameProduct(manageProduct.getEdtNameProduct().getText()); 
            }
        });
                
        this.manageProduct.getEdtPriceProduct().addActionListener((ActionEvent e) -> {
            setPriceProduct(manageProduct.getEdtPriceProduct().getText()); 
        });
        
        this.manageProduct.getEdtPriceProduct().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                setPriceProduct(manageProduct.getEdtPriceProduct().getText());
            }
        });
        
        // desde el comboBox activar al seleccionar una categoría
        this.manageProduct.getBoxCategory().addActionListener((ActionEvent e) -> {
        setCategory((String) manageProduct.getBoxCategory().getSelectedItem());
    });
        
        this.manageProduct.getTableProducts().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    listIngredientsProduct.clear();
                    productSelectedToModify = new Product();
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    
                    Long id = (Long) target.getValueAt(row, 0);
                    String name = (String) target.getValueAt(row, 1);
                    String ingredients = (String) target.getValueAt(row, 2);
                    BigDecimal price = (BigDecimal) target.getValueAt(row, 3);
                    
                    // Buscar el producto para ver su categoría
                    Product product = productService.findProductById(id);
                    
                    //Añadir a la lista de ingredientes para poder modificarlos
                    Arrays.stream(ingredients.split(",")).map(String::trim).forEach(listIngredientsProduct::add);
                    
                    // Añadir a los campos de texto
                    manageProduct.getCategoryTxt().setText(product.getCategory().getName());
                    manageProduct.getNameTxt().setText(name);
                    manageProduct.getPriceTxt().setText(price.toString());
                    updateIngredientsInProduct(listIngredientsProduct);
                    
                    // Añadir al producto para Modificarlo
                    productSelectedToModify.setProductId(id);
                    
                    
                    listIdIngredientsInProduct = updateIdIngredientesInProduct(listIngredientsProduct);
                    
                    
  
                }
            }  
        });    
    }
    
 
    @Autowired
    public void setManageIngredient(ManageIngredient manageIngredient) {
        this.manageIngredient = manageIngredient;
        
        this.manageIngredient.getBtnSaveIngredient().addActionListener(e -> deleteIngredient());
        this.manageIngredient.getBtnDeleteIngredient().addActionListener(e -> saveOrModifyIngredient());
        
        //permite guardar un ingrediente con la tecla enter desde el textField de precio
        this.manageIngredient.getEdtPriceIngredient().addActionListener((ActionEvent e) -> {
            saveOrModifyIngredient();
            this.manageIngredient.getEdtNameIngredient().requestFocus();
        });
        
        this.manageIngredient.getEdtNameIngredient().addActionListener((ActionEvent e) -> {
            this.manageIngredient.getEdtPriceIngredient().requestFocus();
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
    
    @Autowired
    public void setManageCustomer(ManageCustomer manageCustomer) {
        this.manageCustomer = manageCustomer;
        
        this.manageCustomer.getBtnDeleteCustomer().addActionListener(e -> deleteCustomer());
        this.manageCustomer.getBtnLookForCustomer().addActionListener(e -> lookForCustomer());
        this.manageCustomer.getBtnSaveCustomer().addActionListener(e -> saveOrModifyCustomer());
        
        this.manageCustomer.getEdtPhoneCustomer().addActionListener((ActionEvent e) ->{
            lookForCustomer();
        });
        
        this.manageCustomer.getEdtAddresCustomer().addActionListener((ActionEvent e) -> {
            saveOrModifyCustomer();
            clearViewCustomer("");
            manageCustomer.dispose();
        });
        
        
        
        this.manageCustomer.getTableCustomer().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    customerSelectedToModify = new Customer();
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    
                    Long id = (Long) target.getValueAt(row, 0);
                    String name = (String) target.getValueAt(row, 1);
                    String addres = (String) target.getValueAt(row, 2);
                    String phone = (String) target.getValueAt(row, 3);
                    String status = (String) target.getValueAt(row, 4);
                    
                    manageCustomer.getEdtNameCustomer().setText(name);
                    manageCustomer.getEdtAddresCustomer().setText(addres);
                    manageCustomer.getEdtPhoneCustomer().setText(phone);
                    manageCustomer.getEdtStatusCustomer().setText(status);
                    
                    customerSelectedToModify.setCustomerId(id);
                }
            }
            
        });
                
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
        if (categorySelectedToModify == null) {
            JOptionPane.showMessageDialog(addCategory, "Seleccione una Categoría de la tabla");
            return;
        }

        try {
            categoryService.deleteCategory(categorySelectedToModify);
            JOptionPane.showMessageDialog(addCategory, "Categoría eliminada");
        } catch (DataIntegrityViolationException e) {
            JOptionPane.showMessageDialog(addCategory, "La categoría contiene productos. No se puede eliminar");
        } finally {
            fillTableCategory();
            fillCategorys(homepage.getCategorys());
            homepage.getCategorys().setSelectedIndex(1);
            cleanCategory();
            categorySelectedToModify = null;
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
    
    private void handleCategorySelection() {
        String selectedItem = (String) homepage.getCategorys().getSelectedItem();
        
        if (selectedItem != null && !selectedItem.equals("Añadir")) {
            Category category = categoryService.findCategoryByName(selectedItem);
            fillTableProduct(category);  
        }
    }
    
    private void fillHeadersTableProducts(DefaultTableModel model) {
        String[] headers = {"Nº", "Nombre", "Ingredientes", "PVP"};
        model.setColumnIdentifiers(headers);
        homepage.getTableProducts().setModel(model);
        manageProduct.getTableProducts().setModel(model);
    }
    
    private void fillTableProduct(Category category) {
        DefaultTableModel model = new DefaultTableModel();
        fillHeadersTableProducts(model);
        
        List<Product> listProducts = productService.getProductByCategory(category);
        
        listProducts.forEach((product) -> {
            HashSet<String> ingredientList = getIngredientsInProductById(product.getProductId());
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
    

    private void fillTableProduct() {
        DefaultTableModel model = new DefaultTableModel();
        fillHeadersTableProducts(model);
        
        List<Product> listProducts = productService.findAllProducts();
        
        listProducts.forEach((product) -> {
            HashSet<String> ingredientList = getIngredientsInProductById(product.getProductId());
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
    
    private HashSet<String> getIngredientsInProductById(Long productId) {
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
        table.getColumnModel().getColumn(2).setPreferredWidth(426); 
        table.getColumnModel().getColumn(3).setPreferredWidth(50); 
    }
    
    private void columnWidthProductManageProduct(JTable table) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 
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
        categorySelectedToModify = null;
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
            boxCategorys.addItem("Seleccionar"); // Mejor será que aparezca seleccionado la primera categoría(la más usada) y con los productos en la lista 
            listCategorys.forEach((category) -> {
                //String item = category.getCategoryId() + ", " + category.getName();
                String item = category.getName();
                boxCategorys.addItem(item);
            });
        } 
    }
    
    private HashSet<Long> updateIdIngredientesInProduct(HashSet<String> listIngredientsProduct) {
        for (String nameIngredient : listIngredientsProduct) {
            Ingredient i = ingredientService.findIngredientByName(nameIngredient);
            listIdIngredientsInProduct.add(i.getIngredientId());
        }
        return listIdIngredientsInProduct;
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
    
    
    
    private void saveOrModifyProduct() {
        String categoryName = manageProduct.getCategoryTxt().getText();
        String name = manageProduct.getNameTxt().getText();
        String price = manageProduct.getPriceTxt().getText();
        String ingredients = manageProduct.getIngredientsTxt().getText();
        BigDecimal priceB;

        if (categoryName.equals("Seleccionar") || categoryName.equals("Añadir")) {
            JOptionPane.showMessageDialog(manageProduct, "Seleccione una categoría de producto");
        } else if (name.equals("Nombre") || name.isEmpty()) {
            JOptionPane.showMessageDialog(manageProduct, "Escriba el nombre del producto");
        } else if (price.equals("Precio") || price.isEmpty()) {
            JOptionPane.showMessageDialog(manageProduct, "Escriba el precio del producto");
        } else if (listIdIngredientsInProduct.isEmpty()) {
            JOptionPane.showMessageDialog(manageProduct, "Seleccione al menos un ingrediente");
        } else {
            try {
                priceB = new BigDecimal(price);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(manageProduct, "El precio no tiene el formato correcto");
                return; // Terminate if price is invalid
            }

            Category category = categoryService.findCategoryByName(categoryName);

            if (productSelectedToModify != null) {
                // Actualizar un producto existente
                productSelectedToModify.setName(name);
                productSelectedToModify.setPrice(priceB);
                productSelectedToModify.setCategory(category);
                List<Long> listId = getIdIngredients(ingredients);
                List<ProductIngredient> listProductIngredient = productIngredientService.findByProduct(productSelectedToModify);
                productIngredientService.deleteAll(listProductIngredient);
                
                
                for (Long id : listId) {
                    Ingredient ingredient = ingredientService.findIngredientById(id);
                    ProductIngredient productIngredient = new ProductIngredient(null, productSelectedToModify, ingredient);
                    productIngredientService.saveProductIngredient(productIngredient);
                }
 
                productService.saveModifyProduct(productSelectedToModify);
                
                JOptionPane.showMessageDialog(manageProduct, "Producto modificado");
            } else {
                // Crear un nuevo producto
                Product product = new Product();
                product.setName(name);
                product.setPrice(priceB);
                product.setCategory(category);
                productService.saveModifyProduct(product);
                             
                for (Long id : listIdIngredientsInProduct) {
                    Ingredient ingredient = ingredientService.findIngredientById(id);
                    ProductIngredient productIngredient = new ProductIngredient(null, product, ingredient);
                    productIngredientService.saveProductIngredient(productIngredient);
                }
                JOptionPane.showMessageDialog(manageProduct, "Producto añadido");
            }

            fillTableProduct();
            clearViewProduct();
        }
    }
    
    private List<Long> getIdIngredients(String ingredients) {
        List<Long> listId = new ArrayList<>();
        String[] nameIngredients = ingredients.split(", ");
        for (String name : nameIngredients) {
            Ingredient i = ingredientService.findIngredientByName(name);
            listId.add(i.getIngredientId());
        }
        return listId;
    }

    
    /*
    private void saveOrModifyProduct() {
        String categoryName = manageProduct.getCategoryTxt().getText();
        String name = manageProduct.getNameTxt().getText();
        String price = manageProduct.getPriceTxt().getText();

        if (!validateInputs(categoryName, name, price)) {
            return;
        }

        BigDecimal priceB = convertToBigDecimal(price);
        if (priceB == null) {
            JOptionPane.showMessageDialog(manageProduct, "El precio no tiene el formato correcto");
            return;
        }

        Category category = categoryService.findCategoryByName(categoryName);

        if (productSelectedToModify != null) {
            updateExistingProduct(productSelectedToModify, name, priceB, category);
        } else {
            createNewProduct(name, priceB, category);
        }

        fillTableProduct();
        clearProduct();
    }

    private boolean validateInputs(String categoryName, String name, String price) {
        if (categoryName.equals("Seleccionar") || categoryName.equals("Añadir")) {
            JOptionPane.showMessageDialog(manageProduct, "Seleccione una categoría de producto");
            return false;
        } else if (name.equals("Nombre") || name.isEmpty()) {
            JOptionPane.showMessageDialog(manageProduct, "Escriba el nombre del producto");
            return false;
        } else if (price.equals("Precio") || price.isEmpty()) {
            JOptionPane.showMessageDialog(manageProduct, "Escriba el precio del producto");
            return false;
        } else if (listIdIngredientsInProduct.isEmpty()) {
            JOptionPane.showMessageDialog(manageProduct, "Seleccione al menos un ingrediente");
            return false;
        }
        return true;
    }

    private BigDecimal convertToBigDecimal(String price) {
        try {
            return new BigDecimal(price);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void updateExistingProduct(Product product, String name, BigDecimal price, Category category) {
        product.setName(name);
        product.setPrice(price);
        product.setCategory(category);
        productService.saveModifyProduct(product);
        updateProductIngredients(product);
    }

    private void createNewProduct(String name, BigDecimal price, Category category) {
        Product product = new Product(null, name, price, category, null);
        productService.saveModifyProduct(product);
        updateProductIngredients(product);
    }

    private void updateProductIngredients(Product product) {
        
        List<ProductIngredient> existingIngredients = productIngredientService.findByProduct(product);

        // Convertimos las listas a sets para una comparación más sencilla
        Set<Long> existingIngredientIds = existingIngredients.stream()
            .map(pi -> pi.getIngredient().getIngredientId())
            .collect(Collectors.toSet());

        Set<Long> newIngredientIds = new HashSet<>(listIdIngredientsInProduct);

        // Ingredientes a eliminar
        existingIngredients.stream()
            .filter(pi -> !newIngredientIds.contains(pi.getIngredient().getIngredientId()))
            .forEach(pi -> productIngredientService.deleteProductIngredient(pi));

        // Ingredientes a añadir
        newIngredientIds.stream()
            .filter(id -> !existingIngredientIds.contains(id))
            .forEach(id -> {
                Ingredient ingredient = ingredientService.findIngredientById(id);
                ProductIngredient productIngredient = new ProductIngredient(null, product, ingredient);
                productIngredientService.saveProductIngredient(productIngredient);
            });
            
    }
    */
    
    
    private void clearViewProduct(){
        manageProduct.getBoxCategory().setSelectedIndex(0);
        manageProduct.getEdtNameProduct().setText("");
        manageProduct.getEdtPriceProduct().setText("");
        manageProduct.getNameTxt().setText("Nombre");
        manageProduct.getPriceTxt().setText("Precio");
        manageProduct.getIngredientsTxt().setText("Ingredientes");
        productSelectedToModify = null;
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
        if (ingredient.equals("Seleccionar")) {
            JOptionPane.showMessageDialog(manageProduct, "Seleccione un ingrediente");
            return;
        }

        String[] idNameIngredient = ingredient.split(",");
        Long id = Long.valueOf(idNameIngredient[0].trim());
        String nameIngredient = idNameIngredient[1].trim();
        
        if (listIngredientsProduct.add(nameIngredient)) {
            listIdIngredientsInProduct.add(id);
            updateIngredientsInProduct(listIngredientsProduct);
        } else {
            JOptionPane.showMessageDialog(manageProduct, "El ingrediente ya está en la lista");
        }
    }

    private void deleteIngredientFromProduct() {
        String ingredient = manageProduct.getBoxIngredients().getSelectedItem().toString();
        if (ingredient.equals("Seleccionar")) {
            JOptionPane.showMessageDialog(manageProduct, "Seleccione un ingrediente");
            return;
        }

        String[] idNameIngredient = ingredient.split(",");
        Long id = Long.valueOf(idNameIngredient[0].trim());
        String nameIngredient = idNameIngredient[1].trim();
        
        if (listIngredientsProduct.remove(nameIngredient)) {
            listIdIngredientsInProduct.remove(id);
            updateIngredientsInProduct(listIngredientsProduct);
        } else {
            JOptionPane.showMessageDialog(manageProduct, "El ingrediente no está en la lista");
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
            
        } else {
            JOptionPane.showMessageDialog(manageIngredient, "Seleccione un ingrediente");
        }
        
    }

    
    private void saveOrModifyIngredient() {
        String name = manageIngredient.getEdtNameIngredient().getText();
        BigDecimal price;
        try {
            price = new BigDecimal(manageIngredient.getEdtPriceIngredient().getText().replace(',', '.'));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(manageIngredient, "El precio tiene que ser numérico");
            return;
        }

        if (!name.isEmpty()) {
            if (ingredientSelectedToModify == null) {
                Ingredient ingredient = new Ingredient();
                ingredient.setName(name);
                ingredient.setPrice(price);
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
                
            }
        } else {
            JOptionPane.showMessageDialog(manageIngredient, "Tiene que escribir el nombre y el precio del ingrediente");
        }
    }

    private void cleanDataIngredient() {
        manageIngredient.getEdtNameIngredient().setText("");
        manageIngredient.getEdtPriceIngredient().setText("");
        ingredientSelectedToModify = null;
        
    }

    private void setCategory(String string) {
        manageProduct.getCategoryTxt().setText(string);
    }

    private void deleteProduct() {
        if (productSelectedToModify != null) {
            List<ProductIngredient> listProductIngredient = productIngredientService.findByProduct(productSelectedToModify);
            productIngredientService.deleteAll(listProductIngredient);
            productService.deleteProduct(productSelectedToModify);
            JOptionPane.showMessageDialog(manageProduct, "Producto eliminado correctamente");
            fillTableProduct();
            clearViewProduct();
        }
    }

    private void deleteCustomer() {
        if (customerSelectedToModify != null) {
            customerService.deleteCustomer(customerSelectedToModify);
            customerSelectedToModify = null;
            JOptionPane.showMessageDialog(manageCustomer, "Cliente eliminado correctamente");
            clearViewCustomer("");
            fillTableCustomer();
        }
    }

    private void lookForCustomer(String phone) {
        if(!phone.isEmpty()){
            Customer customer = customerService.findCustomerByPhone(phone);
            if (customer == null) {
                openManageCustomer(phone);
            } else {
                homepage.getTxtAddresCustomer().setText(customer.getAddress());
                homepage.getTxtNameCustomer().setText(customer.getName());
            }
        } 
        
    }
    
    private void lookForCustomer() {
        customerSelectedToModify = new Customer();
        String phone = manageCustomer.getEdtPhoneCustomer().getText();
        if (phone.isEmpty()) {
            JOptionPane.showMessageDialog(manageCustomer, "Escriba un teléfono para buscar un cliente");
        } else {
            Customer customer = customerService.findCustomerByPhone(phone);
            if (customer == null) {
                JOptionPane.showMessageDialog(manageCustomer, "Cliente no encontrado");
            } else {
                manageCustomer.getEdtNameCustomer().setText(customer.getName());
                manageCustomer.getEdtAddresCustomer().setText(customer.getAddress());
                manageCustomer.getEdtStatusCustomer().setText(customer.getStatus());
                
                customerSelectedToModify.setCustomerId(customer.getCustomerId());
                
            }
        }   
    }

    private void saveOrModifyCustomer() {
        String name = manageCustomer.getEdtNameCustomer().getText();
        String phone = manageCustomer.getEdtPhoneCustomer().getText();
        String addres = manageCustomer.getEdtAddresCustomer().getText();
        String status = manageCustomer.getEdtStatusCustomer().getText();
        
        if (!validateInputsCustomer(name, phone, addres)){
            return;
        }
        
        // comprobar si existe un cliente con el mismo telefono
        
        if (customerSelectedToModify != null) {
            customerSelectedToModify.setName(name);
            customerSelectedToModify.setAddress(addres);
            customerSelectedToModify.setPhone(phone);
            customerSelectedToModify.setStatus(status);
            customerService.addCustomer(customerSelectedToModify);
            clearViewCustomer(phone);
            JOptionPane.showMessageDialog(manageCustomer, "Cliente modificado correctamente");
            fillTableCustomer();
        } else {
            Customer customer = new Customer(null, name, phone, addres, status, null);
            customerService.addCustomer(customer);
            clearViewCustomer(phone);
            JOptionPane.showMessageDialog(manageCustomer, "Cliente añadido Correctamente");
            fillTableCustomer();
        }
        
        
            
        
        
        
    }

    private void clearViewCustomer(String phone) {
        manageCustomer.getEdtNameCustomer().setText("");
        manageCustomer.getEdtAddresCustomer().setText("");
        manageCustomer.getEdtPhoneCustomer().setText(phone);
        manageCustomer.getEdtStatusCustomer().setText("");
        customerSelectedToModify = null;
        
    }

    private void fillTableCustomer() {
        DefaultTableModel model = new DefaultTableModel();
        String[] headers = {"ID" ,"Nombre", "Dirección", "Teléfono", "Observaciones"};
        model.setColumnIdentifiers(headers);
        manageCustomer.getTableCustomer().setModel(model);
        
        List<Customer> listCustomer = customerService.findAllCustomers();
        if (!listCustomer.isEmpty()) {
            listCustomer.forEach((customer) -> {
                Object[] customerLine = {
                    customer.getCustomerId(),
                    customer.getName(), 
                    customer.getAddress(), 
                    customer.getPhone(), 
                    customer.getStatus()
                };
                model.addRow(customerLine);
            });
        }
    }
    
    

    private boolean validateInputsCustomer(String name, String phone, String addres) {
        if (name.equals("Nombre") || name.isEmpty()) {
            JOptionPane.showMessageDialog(manageCustomer, "El nombre no puede estar vacío");
            return false;
        } else if(phone.equals("Teléfono") || phone.isEmpty()) {
            JOptionPane.showMessageDialog(manageCustomer, "El teléfono no puede estar vacío");
            return false;
        } else if (addres.equals("Dirección") || addres.isEmpty()) {
            JOptionPane.showMessageDialog(manageCustomer, "La dirección no puede estar vacía");
            return false;
        } 
        
        return true;
        
    }
    
    private void fillHeadersOrder() {
        DefaultTableModel model = new DefaultTableModel();
        String[] headers = {"Producto", "Observaciones", "PVP"};
        model.setColumnIdentifiers(headers);
        homepage.getTableOrder().setModel(model);
        columnWidthOrderTable(homepage.getTableOrder());
        
    }
    
    private void columnWidthOrderTable(JTable table) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 
        table.getColumnModel().getColumn(0).setPreferredWidth(80); 
        table.getColumnModel().getColumn(1).setPreferredWidth(357); 
        table.getColumnModel().getColumn(2).setPreferredWidth(40);  
    }

    private void addForTheOrder() {
        if (productSelectedForTheOrder == null) {
            JOptionPane.showMessageDialog(homepage, "Seleccione un producto");
        } else {
            DefaultTableModel model = (DefaultTableModel) homepage.getTableOrder().getModel();
        
            // Crear una fila con los datos del producto seleccionado
            Object[] rowData = {
                productSelectedForTheOrder.getName(),  // Nombre del producto
                String.join(", ", modifications),                                    // Observaciones (dejar en blanco inicialmente)
                modificationsPrice = modificationsPrice.add(productSelectedForTheOrder.getPrice())   // Precio del producto
               
            };
            total = total.add(modificationsPrice);
            homepage.getTxtTotalPrice().setText(total.toString());
            // Añadir la fila al modelo
            model.addRow(rowData);

            productSelectedForTheOrder = null;
            modifications.clear();
            modificationsPrice = BigDecimal.ZERO;
        }
  
    }

    private void removeIngredient() {
        String ingredient = homepage.getIngredientModify().getSelectedItem().toString();
        if (ingredient.equals("Seleccionar")) {
            JOptionPane.showMessageDialog(manageProduct, "Seleccione un ingrediente");
            return;
        }
        
        String[] idNameIngredient = ingredient.split(",");
        String nameIngredient = idNameIngredient[1].trim();
        
        modifications.add("Sin " + nameIngredient);
        homepage.getIngredientModify().setSelectedIndex(0);
    }

    private void addIngredient() {
        
        String ingredient = homepage.getIngredientModify().getSelectedItem().toString();
        if (ingredient.equals("Seleccionar")) {
            JOptionPane.showMessageDialog(manageProduct, "Seleccione un ingrediente");
            return;
        }

        String[] idNameIngredient = ingredient.split(",");
        Long id = Long.valueOf(idNameIngredient[0].trim());
        String nameIngredient = idNameIngredient[1].trim();
        
        Ingredient in = ingredientService.findIngredientById(id);
        modificationsPrice = modificationsPrice.add(in.getPrice());
        homepage.getIngredientModify().setSelectedIndex(0);
        
        modifications.add("Con " + nameIngredient);
    }

    private void cancelOrder() {
        
    }

    private void confirmOrder() {
        
    }

    private void removeFromOrder() {
        // con este mismo enfoque puedo mirar si podría eliminar los elementos de la base de datos
        JTable target = homepage.getTableOrder();
        int selectedRow = target.getSelectedRow();
        BigDecimal price = (BigDecimal) target.getValueAt(selectedRow, 2);
        total = total.subtract(price);
        homepage.getTxtTotalPrice().setText(total.toString());

        if (selectedRow != -1) { // Verificar que se haya seleccionado una fila
            DefaultTableModel model = (DefaultTableModel) target.getModel();
            model.removeRow(selectedRow); // Eliminar la fila del modelo
        } else {
            JOptionPane.showMessageDialog(homepage, "Seleccione una fila para eliminar");
        }
        
    }

    

    

    

        
        
        
        
        
    
    
    
    
    
    
    

    
    
}
