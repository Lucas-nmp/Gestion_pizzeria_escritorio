package lm.Gestion_pedidos.controller;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.annotation.Target;
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

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import java.time.LocalDate;
import lm.Gestion_pedidos.model.Order;
import lm.Gestion_pedidos.model.OrderProduct;
import lm.Gestion_pedidos.service.OrderProductService;
import lm.Gestion_pedidos.service.OrderService;

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
    private Product productSelectedToModify; // eliminar  
    private HashSet<String> modifications;
    private BigDecimal modificationsPrice;
    private BigDecimal total;
    
    private final BigDecimal PRICE_DOUBLE_CHEESE = BigDecimal.valueOf(0.60);
    
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
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private OrderProductService orderProductService;
    
    private Homepage homepage;
    private ManageCategory manageCategory;
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
        setCategory();
        
        String selectedItem = (String) homepage.getCategorys().getSelectedItem();
        Category category = categoryService.findCategoryByName(selectedItem);
        
        fillTableProduct(category);
        homepage.setVisible(true);  
    }
    
    private void openManageCategory() {
        
        manageCategory.setLocationRelativeTo(null);
        manageCategory.setResizable(false);
        fillTableCategory();
        cleanCategory();
        manageCategory.setModal(true);
        manageCategory.setVisible(true);  
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
        this.homepage.getBtnCancelOrder().addActionListener(e -> clearOrderData());
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
                
            }
            
        });
    }
    
    @Autowired
    public void setManageCategory(ManageCategory manageCategory) {
        this.manageCategory = manageCategory;
        this.manageCategory.getDeleteCategory().addActionListener(e -> deleteCategory());
        this.manageCategory.getCategorySave().addActionListener(e -> {
            saveOrModifyCategory();
            fillTableCategory();
            fillCategorys(homepage.getCategorys()); 
            cleanCategory();    
        });
        
        this.manageCategory.getCategoryName().addActionListener((ActionEvent e) -> {
            saveOrModifyCategory();
            fillTableCategory();
            fillCategorys(homepage.getCategorys()); 
            cleanCategory();
        });
        
        this.manageCategory.getCategoryTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTable target = (JTable) e.getSource();
                int selectedRow = target.getSelectedRow();

                if (selectedRow != -1) {
                    String name = (String) target.getValueAt(selectedRow, 1);
                    manageCategory.getCategoryName().setText(name);
                
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
        
        this.manageIngredient.getBtnSaveIngredient().addActionListener(e -> saveOrModifyIngredient());
        this.manageIngredient.getBtnDeleteIngredient().addActionListener(e -> deleteIngredient());
        
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
                JTable target = (JTable) e.getSource();
                int selectedRow = target.getSelectedRow();
                if (e.getClickCount() != -1) {
                    String nameIngredient = (String) target.getValueAt(selectedRow, 1);
                    BigDecimal priceIngredient = (BigDecimal) target.getValueAt(selectedRow, 2);
                    manageIngredient.getEdtNameIngredient().setText(nameIngredient);
                    manageIngredient.getEdtPriceIngredient().setText(priceIngredient.toString());

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
                JTable target = (JTable) e.getSource();
                int selectedRow = target.getSelectedRow();
                if (e.getClickCount() != -1) {                  
                    String name = (String) target.getValueAt(selectedRow, 1);
                    String addres = (String) target.getValueAt(selectedRow, 2);
                    String phone = (String) target.getValueAt(selectedRow, 3);
                    String status = (String) target.getValueAt(selectedRow, 4);
                    
                    manageCustomer.getEdtNameCustomer().setText(name);
                    manageCustomer.getEdtAddresCustomer().setText(addres);
                    manageCustomer.getEdtPhoneCustomer().setText(phone);
                    manageCustomer.getEdtStatusCustomer().setText(status);
                    
                    
                }
            }
            
        });
                
    }
    
    private void setCategory() {
        int itemCount = homepage.getCategorys().getItemCount();
        if (itemCount > 1) {
            homepage.getCategorys().setSelectedIndex(1);
        } else {
            homepage.getCategorys().setSelectedIndex(0);
        } 
            
    }
    
    private void saveOrModifyCategory() {
        JTable target = manageCategory.getCategoryTable();
        int selectedRow = target.getSelectedRow();
        String name = manageCategory.getCategoryName().getText();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(manageCategory, "El nombre no puede estar vacío");
            return;
        }

        Category category = new Category();
        if (selectedRow != -1) { // Modificar categoría existente
            Long id = (Long) target.getValueAt(selectedRow, 0);
            category.setCategoryId(id);
        }

        category.setName(name);
        categoryService.addCategory(category);
        fillCategorys(homepage.getCategorys());
        fillTableCategory();
        cleanCategory();
    }
    
    
    private void deleteCategory() {
        JTable target = manageCategory.getCategoryTable();
        int selectedRow = target.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(manageCategory, "Seleccione una Categoría de la tabla");
            return;
        }

        String name = (String) target.getValueAt(selectedRow, 1);
        Category category = categoryService.findCategoryByName(name);

        try {
            categoryService.deleteCategory(category);
            JOptionPane.showMessageDialog(manageCategory, "Categoría eliminada");
        } catch (DataIntegrityViolationException e) {
            JOptionPane.showMessageDialog(manageCategory, "La categoría contiene productos. No se puede eliminar");
        } finally {
            fillTableCategory();
            fillCategorys(homepage.getCategorys());
            homepage.getCategorys().setSelectedIndex(1);
            cleanCategory();
        }
    }
    
    
    private void fillTableCategory() {
        DefaultTableModel model = new DefaultTableModel();
        String[] headers = {"ID", "Nombre"};
        model.setColumnIdentifiers(headers);
        manageCategory.getCategoryTable().setModel(model);
        
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
        manageCategory.getCategoryName().setText("");
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
        JTable target = manageIngredient.getTableIngredient();
        int selectedRow = target.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(manageIngredient, "Seleccione un ingrediente de la tabla");
            return;
        }
        
        Long id = (Long) target.getValueAt(selectedRow, 0);
        Ingredient ingredient = ingredientService.findIngredientById(id);
        try {
            ingredientService.deleteIngredient(ingredient);
            JOptionPane.showMessageDialog(manageIngredient, "Ingrediente eliminado"); 
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(manageIngredient, "no eliminado"); 
        } finally {
            fillTableIngredients();
            cleanDataIngredient();
            fillIngredients(homepage.getIngredientModify());
        }  
    }

    
    private void saveOrModifyIngredient() {
        JTable target = manageIngredient.getTableIngredient();
        int selectedRow = target.getSelectedRow();
        String name = manageIngredient.getEdtNameIngredient().getText();
        BigDecimal price;
        try {
            price = new BigDecimal(manageIngredient.getEdtPriceIngredient().getText().replace(',', '.'));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(manageIngredient, "El precio tiene que ser numérico");
            return;
        }
        
        if (name.isEmpty()){
            JOptionPane.showMessageDialog(manageIngredient, "El nombre no puede estar vacío");
            return;
        }
        
        Ingredient ingredient = new Ingredient();
        if (selectedRow != -1) {
            Long id = (Long) target.getValueAt(selectedRow, 0);
            ingredient.setIngredientId(id);
        }
        
        ingredient.setName(name);
        ingredient.setPrice(price);
        ingredientService.addModifyIngredient(ingredient);
        fillTableIngredients();
        cleanDataIngredient();
        fillIngredients(homepage.getIngredientModify());
        
    }

    private void cleanDataIngredient() {
        manageIngredient.getEdtNameIngredient().setText("");
        manageIngredient.getEdtPriceIngredient().setText("");  
    }

    private void setCategory(String string) {
        manageProduct.getCategoryTxt().setText(string);
    }
    /*
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
    */
    
    private void deleteProduct() {
        JTable target = manageProduct.getTableProducts();
        int selectedRow = target.getSelectedRow();
        
        if (selectedRow != -1) {
            Long id = (Long) target.getValueAt(selectedRow, 0);
            Product product = productService.findProductById(id);
            List<ProductIngredient> listProductIngredient = productIngredientService.findByProduct(product);
            try {
                productIngredientService.deleteAll(listProductIngredient);
                productService.deleteProduct(product);
                JOptionPane.showMessageDialog(manageProduct, "Producto eliminado correctamente");
                fillTableProduct();
                clearViewProduct();
            } catch (DataIntegrityViolationException e) {
                JOptionPane.showMessageDialog(manageProduct, "El producto está en algún pedido, no se puede eliminar. Pruebe a modificarlo");
            }
            
        } else {
            JOptionPane.showMessageDialog(manageProduct, "Seleccione un producto para eliminar");
        }
    }

    private void deleteCustomer() {
        JTable target = manageCustomer.getTableCustomer();
        int selectedRow = target.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(manageCustomer, "Seleccione un cliente de la tabla");
            return;
        }
        
        Long id = (Long) target.getValueAt(selectedRow, 0);
        Customer customer = customerService.findCustomerById(id);
        
        try {
            customerService.deleteCustomer(customer);
            JOptionPane.showMessageDialog(manageCustomer, "Cliente eliminado correctamente");
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(manageCustomer, "Cliente no eliminado");
        } finally {
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
                String status = customer.getStatus();
                if (!status.isEmpty()) {
                    JOptionPane.showMessageDialog(homepage, status);
                } 
                homepage.getTxtAddresCustomer().setText(customer.getAddress());
                homepage.getTxtNameCustomer().setText(customer.getName());
                
                homepage.getTxtOrderNameCustomer().setText(customer.getName());
                homepage.getTxtOrderAddresCustomer().setText(customer.getAddress());
                homepage.getTxtOrderPhoneCustomer().setText(customer.getPhone());
                
            }
        } 
        
    }
    
    private void lookForCustomer() {
        
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
            }
        }   
    }

    private void saveOrModifyCustomer() {
        JTable target = manageCustomer.getTableCustomer();
        int selectedRow = target.getSelectedRow();
        
        String name = manageCustomer.getEdtNameCustomer().getText();
        String phone = manageCustomer.getEdtPhoneCustomer().getText();
        String addres = manageCustomer.getEdtAddresCustomer().getText();
        String status = manageCustomer.getEdtStatusCustomer().getText();
        
        if (!validateInputsCustomer(name, phone, addres)){
            return;
        }
        
        Customer customer;
        
        if (selectedRow != -1) {
            Long id = (Long) target.getValueAt(selectedRow, 0);
            customer = customerService.findCustomerById(id);
            
            if (!customer.getPhone().equals(phone) && customerService.findCustomerByPhone(phone) != null) {
            JOptionPane.showMessageDialog(manageCustomer, "Ya existe un cliente con ese Nº de teléfono");
            return;
            }
        } else {
            if (customerService.findCustomerByPhone(phone) != null) {
                JOptionPane.showMessageDialog(manageCustomer, "Ya existe un cliente con ese Nº de teléfono");
                return;
            }
            customer = new Customer(); 
        }
        
        customer.setName(name);
        customer.setAddress(addres);
        customer.setPhone(phone);
        customer.setStatus(status);

        customerService.addCustomer(customer);

        clearViewCustomer("");
        JOptionPane.showMessageDialog(manageCustomer, "Cliente añadido/modificado Correctamente");
        fillTableCustomer();   
    }
        
        
        
    

    private void clearViewCustomer(String phone) {
        manageCustomer.getEdtNameCustomer().setText("");
        manageCustomer.getEdtAddresCustomer().setText("");
        manageCustomer.getEdtPhoneCustomer().setText(phone);
        manageCustomer.getEdtStatusCustomer().setText("");
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
        String[] headers = {"ID", "Producto", "Observaciones", "PVP"};
        model.setColumnIdentifiers(headers);
        homepage.getTableOrder().setModel(model);
        columnWidthOrderTable(homepage.getTableOrder());
        
    }
    
    private void columnWidthOrderTable(JTable table) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(1).setPreferredWidth(80); 
        table.getColumnModel().getColumn(2).setPreferredWidth(327); 
        table.getColumnModel().getColumn(3).setPreferredWidth(40);  
    }

    private void addForTheOrder() {
        
        checkOptions();
        
        checkObservations();
        
        JTable target = homepage.getTableProducts();
        int selectedRow = target.getSelectedRow();
        if (selectedRow != -1) {
            Long id = (Long) target.getValueAt(selectedRow, 0);
            Product product = productService.findProductById(id);
            
            DefaultTableModel model = (DefaultTableModel) homepage.getTableOrder().getModel();
            Object[] rowData = {
                product.getProductId(),
                product.getName(),  
                String.join(", ", modifications),                                    
                modificationsPrice = modificationsPrice.add(product.getPrice())   
            };
            total = total.add(modificationsPrice);
            homepage.getTxtTotalPrice().setText(total.toString());
            
            model.addRow(rowData);

            
            modifications.clear();
            clearChecks();
            modificationsPrice = BigDecimal.ZERO;
            target.clearSelection();
        } else {
            JOptionPane.showMessageDialog(homepage, "Seleccione un producto"); 
        }
        
    }
    
    
    
    private void checkOptions() {
        if (homepage.getCheckDoubleCheese().isSelected()) {
            modifications.add("Doble de queso");
            modificationsPrice = modificationsPrice.add(PRICE_DOUBLE_CHEESE);
        }
        
        if (homepage.getCheckUndercooked().isSelected()) {
            modifications.add("Poco hecha");
        }
        
        if (homepage.getCheckVeryCooked().isSelected()) {
            modifications.add("Muy hecha");
        }
        
        if (homepage.getCheckWithoutCheese().isSelected()) {
            modifications.add("Sin queso");
        }
    }
    
    private void checkObservations() {
        String observations = homepage.getEdtObservations().getText();

        try {
            String[] dto = observations.split(" ");
            if (dto.length > 0) {
                // Intenta convertir el primer elemento a un número
                BigDecimal descuento = new BigDecimal(dto[0]);
                // Resta el descuento a modificationsPrice y guarda el resultado
                modificationsPrice = modificationsPrice.subtract(descuento);
            }
        } catch (NumberFormatException e) {
            
        }

        modifications.add(observations);
    }
    
    private void clearChecks() {
        homepage.getCheckDoubleCheese().setSelected(false);
        homepage.getCheckUndercooked().setSelected(false);
        homepage.getCheckVeryCooked().setSelected(false);
        homepage.getCheckWithoutCheese().setSelected(false);
        homepage.getEdtObservations().setText("");
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

    
    private void clearOrderData() {
        DefaultTableModel model = (DefaultTableModel) homepage.getTableOrder().getModel();
        model.setRowCount(0);
        
        total = BigDecimal.ZERO;
        homepage.getTxtTotalPrice().setText(total.toString());
        homepage.getEdtPhoneCustomer().setText("Teléfono");
        homepage.getTxtAddresCustomer().setText("Dirección");
        homepage.getTxtNameCustomer().setText("Nombre");
        homepage.getEdtAlternativeAddres().setText("Dirección alternativa");
        homepage.getTxtOrderNameCustomer().setText("Nombre");
        homepage.getTxtOrderPhoneCustomer().setText("Teléfono");
        homepage.getTxtOrderAddresCustomer().setText("Dirección");
        clearChecks();
    }

    private void confirmOrder() {
        if (homepage.getTxtOrderPhoneCustomer().getText().equals("Teléfono")) {
            JOptionPane.showMessageDialog(homepage, "Seleccione un cliente");
            return;
        }
        String address = homepage.getEdtAlternativeAddres().getText();
        if (address.isEmpty() || address.equals("Dirección alternativa")) {
            address = homepage.getTxtAddresCustomer().getText();
        }
        homepage.getTxtOrderAddresCustomer().setText(address);
        String phone = homepage.getTxtOrderPhoneCustomer().getText();
        Customer customer = customerService.findCustomerByPhone(phone);
        
        LocalDate orderDate = LocalDate.now();
        Order order = new Order();
        order.setCustomer(customer);
        order.setDate(orderDate);
        order.setTotalPrice(total);
        
        DefaultTableModel model = (DefaultTableModel) homepage.getTableOrder().getModel();
        int rowCount = model.getRowCount();
        
        if (rowCount == 0) {
            JOptionPane.showMessageDialog(homepage, "Seleccione al menos un producto");
            return;
        }
        
        orderService.addOrder(order);

        // Crear una lista para almacenar los productos del pedido
        List<OrderProduct> OrderProducts = new ArrayList<>();
        for (int i = 0; i<rowCount; i++) {
            Product product = productService.findProductById((Long) model.getValueAt(i, 0));
            String observation = (String) model.getValueAt(i, 2);
            OrderProduct or = new OrderProduct(null, order, product, observation);
            orderProductService.saveOrderProduct(or);
        }
        
        clearOrderData();
  
    }

    
    private void removeFromOrder() {
        // con este mismo enfoque puedo mirar si podría eliminar los elementos de la base de datos
        JTable target = homepage.getTableOrder();
        int selectedRow = target.getSelectedRow();
        BigDecimal price = (BigDecimal) target.getValueAt(selectedRow, 3);
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
