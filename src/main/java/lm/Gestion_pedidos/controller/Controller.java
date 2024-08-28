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
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Random;
import java.util.Set;
import javax.swing.JTextField;
import lm.Gestion_pedidos.model.Company;
import lm.Gestion_pedidos.model.Order;
import lm.Gestion_pedidos.model.OrderProduct;
import lm.Gestion_pedidos.service.CompanyService;
import lm.Gestion_pedidos.service.OrderProductService;
import lm.Gestion_pedidos.service.OrderService;
import lm.Gestion_pedidos.view.Setting;
import lm.Gestion_pedidos.view.Statistics;

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
    
    @Autowired
    private CompanyService companyService;
    
    private Homepage homepage;
    private ManageCategory manageCategory;
    private ManageProduct manageProduct;
    private ManageIngredient manageIngredient;
    private ManageCustomer manageCustomer;
    private Setting setting;
    private Statistics statistics;
    
    
    public void viewHomePage() {
        this.homepage = new Homepage();
        
        this.homepage.getBtnCategory().addActionListener(e -> openManageCategory());
        this.homepage.getBtnProduct().addActionListener(e -> openManageProducts());
        this.homepage.getBtnIngredient().addActionListener(e -> openManageIngredients());
        this.homepage.getBtnCustomer().addActionListener(e -> openManageCustomer(""));
        this.homepage.getBtnSetting().addActionListener(e -> openSettings());
        this.homepage.getBtnStatistics().addActionListener(e -> openStatistics());
        this.homepage.getBtnAdd().addActionListener(e -> addForTheOrder());
        this.homepage.getBtnAdd2().addActionListener(e -> addForTheOrder());
        this.homepage.getBtnRemoveIngredient().addActionListener(e -> removeIngredient());
        this.homepage.getBtnAddIngredient().addActionListener(e -> addIngredient());
        this.homepage.getBtnCancelOrder().addActionListener(e -> clearOrderData());
        this.homepage.getBtnConfirmOrder().addActionListener(e -> confirmOrder());
        this.homepage.getBtnRemoveFromOrder().addActionListener(e -> removeFromOrder());
        
        this.homepage.getBtnDemo().addActionListener(e -> loadDemoData());
        
        
        this.homepage.getEdtPhoneCustomer().addActionListener((ActionEvent e) -> {
            String phone = homepage.getEdtPhoneCustomer().getText();
            lookForCustomer(phone);
        });
          
        this.homepage.getCategorys().addActionListener( e -> handleCategorySelection());
        
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
                        } */
                    }
                }
            }
        
        });
        
        
        
        this.homepage.getTableProducts().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }
            
        });
        
        this.homepage.getEdtPhoneCustomer().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                JTextField field = homepage.getEdtPhoneCustomer();
                String texto = "Teléfono";
                checkFieldFocusGained(field, texto);
            }

            @Override
            public void focusLost(FocusEvent e) {
                JTextField field = homepage.getEdtPhoneCustomer();
                String texto = "Teléfono";
                checkFieldFocusLost(field, texto);
            }
            
            
        });
        
        this.homepage.getEdtAlternativeAddres().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                JTextField field = homepage.getEdtAlternativeAddres();
                String texto = "Dirección alternativa";
                checkFieldFocusGained(field, texto);
            }

            @Override
            public void focusLost(FocusEvent e) {
                JTextField field = homepage.getEdtAlternativeAddres();
                String texto = "Dirección alternativa";
                checkFieldFocusLost(field, texto);
            }
            
            
            
        });
        
        limitPhoneCharacters(this.homepage.getEdtPhoneCustomer());
        
        homepage.setLocationRelativeTo(null);
        homepage.setResizable(false);
        fillIngredients(homepage.getIngredientModify());
        fillCategorys(homepage.getCategorys()); 
        
        DefaultTableModel model = new DefaultTableModel();
        String[] headers = {"ID", "Producto", "Observaciones","Unds.", "PVP"}; // Añadir cantidad de producto
        JTable table = homepage.getTableOrder();
        fillTableHeaders(model, headers, table);
        columnWidthOrderTable(homepage.getTableOrder());
        
        DefaultTableModel modelProduct = new DefaultTableModel();
        String[] headersProduct = {"Nº", "Nombre", "Ingredientes", "PVP"};
        JTable tableProduct = homepage.getTableProducts();
        fillTableHeaders(modelProduct, headersProduct, tableProduct);
        
        
        modifications = new HashSet<>();
        modificationsPrice = BigDecimal.ZERO;
        total = BigDecimal.ZERO;
        setCategory();
        Company company = companyService.fingCompanyById(1L);
        String companyName = "Nombre de la empresa";
        if (company != null) {
            companyName = company.getName();
        }
        homepage.getTxtCompanyName().setText(companyName);
        
        String selectedItem = (String) homepage.getCategorys().getSelectedItem();
        Category category = categoryService.findCategoryByName(selectedItem);
        
        fillTableProduct(category);
        homepage.setVisible(true);  
    }
    
    
    private void openManageCategory() {
        this.manageCategory = new ManageCategory();
        
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
        
        manageCategory.setLocationRelativeTo(null);
        manageCategory.setResizable(false);
        
        DefaultTableModel model = new DefaultTableModel();
        String[] headers = {"ID", "Nombre"};
        JTable table = manageCategory.getCategoryTable();
        fillTableHeaders(model, headers, table);
        fillTableCategory();
        cleanCategory();
        manageCategory.setModal(true);
        manageCategory.setVisible(true);  
    }
    
    private void openManageProducts() {
        this.manageProduct = new ManageProduct();
        
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
                    
                    
                    listIdIngredientsInProduct = updateIdIngredientesInProduct(listIngredientsProduct);
                    
                    
  
                }
            }  
        });    
        
        manageProduct.setLocationRelativeTo(null);
        manageProduct.setResizable(false);
        manageProduct.setModal(true);
        fillIngredients(manageProduct.getBoxIngredients());
        fillCategorys(manageProduct.getBoxCategory());
        String[] headers = {"Nº", "Nombre", "Ingredientes", "PVP"};
        DefaultTableModel model = new DefaultTableModel();
        JTable table = manageProduct.getTableProducts();
        fillTableHeaders(model, headers, table);
        fillTableProduct();
        columnWidthProductManageProduct(manageProduct.getTableProducts());
        clearViewProduct();
        manageProduct.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                int itemCount = homepage.getCategorys().getItemCount();
                if (itemCount > 1) {
                    homepage.getCategorys().setSelectedIndex(1);
                } else {
                    homepage.getCategorys().setSelectedIndex(0);
                }    
            }
        });
        manageProduct.setVisible(true);  
    }
    
    private void openManageIngredients() {
        this.manageIngredient = new ManageIngredient();
        
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
        
        manageIngredient.setLocationRelativeTo(null);
        manageIngredient.setResizable(false);
        manageIngredient.setModal(true);
        
        DefaultTableModel model = new DefaultTableModel();
        String[] headers = {"Nº", "Ingrediente", "Precio"};
        JTable table = manageIngredient.getTableIngredient();
        fillTableHeaders(model, headers, table);
        
        fillTableIngredients();
        cleanDataIngredient();
        manageIngredient.setVisible(true);
    }
    
    private void openManageCustomer(String phone) {
        this.manageCustomer = new ManageCustomer();
        
        this.manageCustomer.getBtnDeleteCustomer().addActionListener(e -> deleteCustomer());
        this.manageCustomer.getBtnLookForCustomer().addActionListener(e -> lookForCustomer());
        this.manageCustomer.getBtnSaveCustomer().addActionListener(e -> saveOrModifyCustomer());
        
        this.manageCustomer.getEdtPhoneCustomer().addActionListener((ActionEvent e) ->{
            lookForCustomer();
        });
        
        this.manageCustomer.getEdtAddresCustomer().addActionListener((ActionEvent e) -> {
            saveOrModifyCustomer();
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
                    manageCustomer.getEdtNameCustomer().setForeground(java.awt.Color.BLACK);
                    manageCustomer.getEdtAddresCustomer().setText(addres);
                    manageCustomer.getEdtAddresCustomer().setForeground(java.awt.Color.BLACK);
                    manageCustomer.getEdtPhoneCustomer().setText(phone);
                    manageCustomer.getEdtPhoneCustomer().setForeground(java.awt.Color.BLACK);
                    manageCustomer.getEdtStatusCustomer().setText(status);   
                    manageCustomer.getEdtStatusCustomer().setForeground(java.awt.Color.BLACK);
                }
            } 
        });
        
        this.manageCustomer.getEdtNameCustomer().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                JTextField fieldName =  manageCustomer.getEdtNameCustomer();
                checkFieldFocusGained(fieldName, "Nombre");
                
            }

            @Override
            public void focusLost(FocusEvent e) {
                JTextField fieldName =  manageCustomer.getEdtNameCustomer();
                checkFieldFocusLost(fieldName, "Nombre");
                
            }

 
        });
        
        
        
        this.manageCustomer.getEdtAddresCustomer().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                JTextField fieldName = manageCustomer.getEdtAddresCustomer();
                checkFieldFocusGained(fieldName, "Dirección");
            }

            @Override
            public void focusLost(FocusEvent e) {
                JTextField fieldName = manageCustomer.getEdtAddresCustomer();
                checkFieldFocusLost(fieldName, "Dirección");
            }
        });
        
        this.manageCustomer.getEdtPhoneCustomer().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                JTextField fieldName = manageCustomer.getEdtPhoneCustomer();
                checkFieldFocusGained(fieldName, "Teléfono");
            }

            @Override
            public void focusLost(FocusEvent e) {
                JTextField fieldName = manageCustomer.getEdtPhoneCustomer();
                checkFieldFocusLost(fieldName, "Teléfono");
            }
        });
        
        this.manageCustomer.getEdtStatusCustomer().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                JTextField fieldName = manageCustomer.getEdtStatusCustomer();
                checkFieldFocusGained(fieldName, "Observaciones");
            }

            @Override
            public void focusLost(FocusEvent e) {
                JTextField fieldName = manageCustomer.getEdtStatusCustomer();
                checkFieldFocusLost(fieldName, "Observaciones");
            }
            
            
        });
        
        limitPhoneCharacters(manageCustomer.getEdtPhoneCustomer());
        
        manageCustomer.setLocationRelativeTo(null);
        manageCustomer.setResizable(false);
        manageCustomer.setModal(true);
        
        DefaultTableModel model = new DefaultTableModel();
        String[] headers = {"ID" ,"Nombre", "Dirección", "Teléfono", "Observaciones"};
        JTable table = manageCustomer.getTableCustomer();
        fillTableHeaders(model, headers, table);
        
        fillTableCustomer();
        clearViewCustomer(phone);
        manageCustomer.setVisible(true); 
    }
    
    
    private void openStatistics() {
        this.statistics = new Statistics();
        
        this.statistics.getBoxMonth().addActionListener(e -> handleMonthSelection());
        
        statistics.setLocationRelativeTo(null);
        statistics.setResizable(false);
        statistics.setModal(true);
        fillMonth();
        statistics.setVisible(true);
        
    }
    
    private void openSettings() {
        this.setting = new Setting();
        
        this.setting.getSaveSettings().addActionListener(e -> saveSettings());
        
        setting.setLocationRelativeTo(null);
        setting.setResizable(false);
        setting.setModal(true);
        setting.setVisible(true);
    }
    

    
    private void checkFieldFocusGained(JTextField fieldName, String texto) {
        if (fieldName.getText().equals(texto)) {
            fieldName.setText("");
            fieldName.setForeground(java.awt.Color.BLACK);
        }
    }  
    
    private void checkFieldFocusLost(JTextField fieldName, String texto) {
        if (fieldName.getText().equals(texto) || fieldName.getText().isEmpty()) {
            fieldName.setText(texto);
            fieldName.setForeground(java.awt.Color.GRAY);
        }
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
        if (selectedRow != -1) { 
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
        DefaultTableModel model = (DefaultTableModel) manageCategory.getCategoryTable().getModel();
        model.setRowCount(0);
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
    
    private void handleMonthSelection() {
        String selectedMonth = (String) statistics.getBoxMonth().getSelectedItem();
        
        if (selectedMonth != null && !selectedMonth.equals("Seleccione un mes")) {
            seeStatistics(selectedMonth);
        }
    }
    
    private void fillTableHeaders(DefaultTableModel model, String[] headers, JTable table) {
        model.setColumnIdentifiers(headers);
        table.setModel(model);
    }
    
    
    private void fillTableProduct(Category category) {
        DefaultTableModel model = (DefaultTableModel) homepage.getTableProducts().getModel();
        model.setRowCount(0);
        
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

    }
    
    private void fillTableProduct() {
        DefaultTableModel model = (DefaultTableModel) manageProduct.getTableProducts().getModel();
        model.setRowCount(0);
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
        DefaultTableModel model = (DefaultTableModel) manageIngredient.getTableIngredient().getModel();
        model.setRowCount(0);
        
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
        
        boxCategorys.setPreferredSize(new Dimension(100, 25));
        boxCategorys.setMaximumSize(new Dimension(100, 25));
        if (listCategorys.isEmpty()) {
            boxCategorys.removeAllItems();
            boxCategorys.addItem("Añadir");
        } else {
            boxCategorys.removeAllItems();
            boxCategorys.addItem("Seleccionar"); 
            listCategorys.forEach((category) -> {
                
                String item = category.getName();
                boxCategorys.addItem(item);
            });
        } 
    }
    
    private void fillMonth() {
        JComboBox boxMonth = statistics.getBoxMonth();
        boxMonth.removeAllItems();
        List<String> months = List.of(
            "Seleccione un mes",    
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", 
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        );
        
        months.forEach((month) -> {
            boxMonth.addItem(month);
        });
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
        String message = "Producto añadido correctamente";
        
        if (!validateInputs(categoryName, name, price)) {
            return;
        }
        
        String ingredients = manageProduct.getIngredientsTxt().getText();
        List<Long> listId = getIdIngredients(ingredients);
        
        BigDecimal priceB = convertToBigDecimal(price);
        if (priceB == null) {
            JOptionPane.showMessageDialog(manageProduct, "El precio no tiene el formato correcto");
            return;
        }

        Category category = categoryService.findCategoryByName(categoryName);
        Product product = new Product();
        JTable target = manageProduct.getTableProducts();
        int selectedRow = target.getSelectedRow();
        if (selectedRow != -1) {
            Long id = (Long) target.getValueAt(selectedRow, 0);
            product = productService.findProductById(id);
            
            List<ProductIngredient> listProductIngredient = productIngredientService.findByProduct(product);
            productIngredientService.deleteAll(listProductIngredient);
            message = "Producto modificado correctamente";
        }
        
        product.setName(name);
        product.setPrice(priceB);
        product.setCategory(category);
        
        productService.saveModifyProduct(product);
        
        for (Long id : listIdIngredientsInProduct) {
            Ingredient ingredient = ingredientService.findIngredientById(id);
            ProductIngredient productIngredient = new ProductIngredient(null, product, ingredient);
            productIngredientService.saveProductIngredient(productIngredient);
        }
        JOptionPane.showMessageDialog(manageProduct, message);
        
        fillTableProduct();
        
        clearViewProduct();
            
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
    
    
    private List<Long> getIdIngredients(String ingredients) {
        List<Long> listId = new ArrayList<>();
        String[] nameIngredients = ingredients.split(", ");
        for (String name : nameIngredients) {
            Ingredient i = ingredientService.findIngredientByName(name);
            listId.add(i.getIngredientId());
        }
        return listId;
    }

    private void clearViewProduct(){
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
        String message = "Cliente añadido correctamente";
        String name = manageCustomer.getEdtNameCustomer().getText();
        String phone = manageCustomer.getEdtPhoneCustomer().getText();
        String addres = manageCustomer.getEdtAddresCustomer().getText();
        String status = manageCustomer.getEdtStatusCustomer().getText();
        
        if (!validateInputsCustomer(name, phone, addres)){
            return;
        }
        
        Customer customer;
        
        if (selectedRow != -1) {
            message = "Cliente modificado correctamente";
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
        if(status.equals("Observaciones")) {
            status = "";
        }
        customer.setStatus(status);

        customerService.addCustomer(customer);

        clearViewCustomer("");
        JOptionPane.showMessageDialog(manageCustomer, message);
        fillTableCustomer(); 
        //manageCustomer.dispose();
    }
        
    private void clearViewCustomer(String phone) {
        manageCustomer.getEdtNameCustomer().setForeground(java.awt.Color.GRAY);
        manageCustomer.getEdtAddresCustomer().setForeground(java.awt.Color.GRAY);
        manageCustomer.getEdtStatusCustomer().setForeground(java.awt.Color.GRAY);
        manageCustomer.getEdtPhoneCustomer().setForeground(java.awt.Color.GRAY);
        manageCustomer.getEdtNameCustomer().setText("Nombre");
        manageCustomer.getEdtAddresCustomer().setText("Dirección");
        manageCustomer.getEdtStatusCustomer().setText("Observaciones");
        if (phone.isEmpty()) {
            manageCustomer.getEdtPhoneCustomer().setText("Teléfono");
        } else {
            manageCustomer.getEdtPhoneCustomer().setText(phone);
        }
        
        
    }

    private void fillTableCustomer() {
        DefaultTableModel model = (DefaultTableModel) manageCustomer.getTableCustomer().getModel();
        model.setRowCount(0);
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
        } else if(phone.equals("Teléfono") || phone.isEmpty() || phone.length()<9) {
            JOptionPane.showMessageDialog(manageCustomer, "El teléfono no tine el formato correcto");
            return false;
        } else if (addres.equals("Dirección") || addres.isEmpty()) {
            JOptionPane.showMessageDialog(manageCustomer, "La dirección no puede estar vacía");
            return false;
        } 
        
        return true;
        
    }
    
    
    private void columnWidthOrderTable(JTable table) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(1).setPreferredWidth(80); 
        table.getColumnModel().getColumn(2).setPreferredWidth(290);
        table.getColumnModel().getColumn(3).setPreferredWidth(37);
        table.getColumnModel().getColumn(4).setPreferredWidth(40);  
    }

    private void addForTheOrder() {
        
        checkOptions();
        
        checkObservations();
        
        JTable target = homepage.getTableProducts();
        int selectedRow = target.getSelectedRow();
        if (selectedRow != -1) {
            Long id = (Long) target.getValueAt(selectedRow, 0);
            Product product = productService.findProductById(id);
            
            int quantity = Integer.parseInt(homepage.getAmount().getSelectedItem().toString());

            // Calcular el precio total basado en la cantidad
            BigDecimal totalPriceForItem = product.getPrice().add(modificationsPrice).multiply(new BigDecimal(quantity));
            
            DefaultTableModel model = (DefaultTableModel) homepage.getTableOrder().getModel();
            Object[] rowData = {
                product.getProductId(),
                product.getName(),  
                String.join(", ", modifications), 
                quantity,
                totalPriceForItem
            };
            total = total.add(totalPriceForItem);
            homepage.getTxtTotalPrice().setText(total.toString());
            
            model.addRow(rowData);

            
            modifications.clear();
            clearChecks();
            modificationsPrice = BigDecimal.ZERO;
            target.clearSelection();
            homepage.getAmount().setSelectedIndex(0);
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
        String observations = homepage.getEdtObservations().getText().trim();  // Elimina espacios en blanco al principio y al final

        if (!observations.isEmpty()) {
            try {
                String[] dto = observations.split(" ");
                if (dto.length > 0) {
                    // Intenta convertir el primer elemento a un número
                    BigDecimal descuento = new BigDecimal(dto[0]);
                    // Resta el descuento a modificationsPrice y guarda el resultado
                    modificationsPrice = modificationsPrice.subtract(descuento);
                }
            } catch (NumberFormatException e) {
                // Manejar excepciones si el primer elemento no es un número
            }

            modifications.add(observations);  // Solo se agrega si no está vacío
        }
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

        
        for (int i = 0; i<rowCount; i++) {
            Product product = productService.findProductById((Long) model.getValueAt(i, 0));
            String observation = (String) model.getValueAt(i, 2);
            BigDecimal price = (BigDecimal) model.getValueAt(i, 3);
            OrderProduct orderProduct = new OrderProduct(null, order, product, observation, price);
            orderProductService.saveOrderProduct(orderProduct);
        }
        
        printOrder(address, total, order);
        
        clearOrderData();
  
    }

    
    private void removeFromOrder() {
        
        JTable target = homepage.getTableOrder();
        int selectedRow = target.getSelectedRow();
        BigDecimal price = (BigDecimal) target.getValueAt(selectedRow, 4);
        total = total.subtract(price);
        homepage.getTxtTotalPrice().setText(total.toString());

        if (selectedRow != -1) { 
            DefaultTableModel model = (DefaultTableModel) target.getModel();
            model.removeRow(selectedRow); 
        } else {
            JOptionPane.showMessageDialog(homepage, "Seleccione una fila para eliminar");
        }
        
    }   

    private void printOrder(String address, BigDecimal total, Order order) {
        try {
            String fileName = order.getOrderId() + "-" + order.getDate().getYear() + "-" + order.getCustomer().getCustomerId();
            PdfWriter writer = new PdfWriter(new FileOutputStream(fileName+ ".pdf"));
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);
            
            Customer customer = order.getCustomer();
            
            // Título y datos de la empresa
            Color blue = new DeviceRgb(173, 216, 230);
            PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

            Paragraph title = new Paragraph("Pedido Nº: " + fileName)
                    .setFont(boldFont)
                    .setFontSize(25)
                    .setFontColor(blue)
                    .setTextAlignment(TextAlignment.RIGHT);

            Company company = companyService.fingCompanyById(1l);
            if (company == null) {
                JOptionPane.showMessageDialog(homepage, "Introduzca los datos de la empresa");
                return;
            }
            
            Paragraph companyDetails = new Paragraph()
                    .add(company.getName() + "\n")
                    .add(company.getAddress() + "\n")
                    .add("CIF: " + company.getCif() + "\n")
                    .add("Teléfono: " + company.getPhone() + "\n")
                    .setTextAlignment(TextAlignment.RIGHT);

            document.add(title);
            document.add(companyDetails);
            
            
            document.add(new Paragraph("\n"));
            

            // Datos del cliente
            Paragraph clientDetails = new Paragraph()
                    .add("Cliente: " + customer.getName() + "\n")
                    .add("Dirección: " + address + "\n")
                    .add("Teléfono: " + customer.getPhone() + "\n")
                    .setTextAlignment(TextAlignment.LEFT);

            document.add(clientDetails);

            
            document.add(new Paragraph("\n"));
            

            // Tabla de productos
            float[] columnWidths = {1, 5, 1}; // Ancho de las columnas
            Table table = new Table(UnitValue.createPercentArray(columnWidths));
            table.setWidth(UnitValue.createPercentValue(100));
            table.addHeaderCell(new Cell().add(new Paragraph("Producto").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Observaciones").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Precio").setBold()));

            List<OrderProduct> listOrderProduct = orderProductService.getOrdersProductByOrder(order);
            
            
            for (OrderProduct product : listOrderProduct) {
                table.addCell(new Paragraph(product.getProduct().getName()));
                table.addCell(new Paragraph(product.getObservations()));
                table.addCell(new Paragraph(product.getPriceWithModifications().toString()));
               
                
            }

            document.add(table);

            

            Paragraph totalParagraph = new Paragraph("Total: " + total)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBold();

            document.add(new Paragraph("\n"));
            document.add(totalParagraph);

            document.close();
            
            // Abrir el archivo PDF automáticamente
            File pdfFile = new File(fileName+".pdf");
            if (pdfFile.exists()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(pdfFile);
                } else {
                    JOptionPane.showMessageDialog(null, "No se puede abrir el archivo automáticamente.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "El archivo no se pudo abrir.");
            }
            
            
        } catch (IOException | NumberFormatException e) {
        }
    }

    private void saveSettings() {
        Long id = 1l;
        String name = setting.getSettingsName().getText();
        String address = setting.getSettingsAddress().getText();
        String cif = setting.getSettingsCif().getText();
        String phone = setting.getSettingsPhone().getText();
        if (name.isEmpty() || address.isEmpty() || phone.isEmpty() || cif.isEmpty()) {
            JOptionPane.showMessageDialog(setting, "Introduzca todos los datos de la empresa");
            return;
        }
        Company company = new Company(id, name, phone, address, cif);
        companyService.saveCompany(company);
        homepage.getTxtCompanyName().setText(company.getName());
        JOptionPane.showMessageDialog(setting, "Datos guardados correctamente");
        setting.dispose();  
    }

    private void seeStatistics(String month) {
        List<Order> orders = orderService.findAllOrders();
        
        LocalDate date = LocalDate.now();
        int year = date.getYear();
        
        int totalOrdersForYear = 0;
        int totalOrdersForMonth = 0;
        
        BigDecimal totalIncomeForYear = BigDecimal.ZERO;
        BigDecimal totalIncomeForMonth = BigDecimal.ZERO;
        Set<Customer> uniqueCustomers = new HashSet<>(); 
        
        int monthNumber = getMonthNumber(month);
        
        for (Order order : orders) {
            if(year == order.getDate().getYear()) {
                totalOrdersForYear++;
                totalIncomeForYear = totalIncomeForYear.add(order.getTotalPrice());
            }
            
            if (year == order.getDate().getYear() && monthNumber == order.getDate().getMonthValue()) {
                totalOrdersForMonth++;
                totalIncomeForMonth = totalIncomeForMonth.add(order.getTotalPrice());
            } 
            
            
            uniqueCustomers.add(order.getCustomer());
        }
        
        int totalCustomersForMonth = uniqueCustomers.size();
        
        statistics.getTotalOrdersForMonth().setText(String.valueOf(totalOrdersForMonth));
        statistics.getTotalCustomersForMonth().setText(String.valueOf(totalCustomersForMonth));
        statistics.getTotalIncomeForMonth().setText(totalIncomeForMonth.toString());
        statistics.getTotalOrdersForYear().setText(String.valueOf(totalOrdersForYear));
        statistics.getTotalIncomeForYear().setText(totalIncomeForYear.toString());
        
        
    }
    
    private int getMonthNumber(String month) {
        List<String> months = List.of(
            "ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", 
            "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"
        );

        return months.indexOf(month.toUpperCase()) + 1;
    }

    private void limitPhoneCharacters(JTextField edtPhone) {
        edtPhone.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
                
                if (edtPhone.getText().length() >= 9) {
                    e.consume();
                }    
            }
        });
    }
    
    public void loadDemoData(){

        List<Category> categorys = categoryService.getAllCategorys();
        if (categorys.isEmpty()) {
            loadCategorys();
            loadIngredients();
            loadPizzas();
            loadDrinks();
            loadCustomers();
            loadSnaks();
            loadSandwichs();
            loadPreparedDishes();
            loadTortillas();
            generateRandomOrders(10000);
            fillCategorys(homepage.getCategorys());
            fillIngredients(homepage.getIngredientModify());
        } else {
            JOptionPane.showMessageDialog(homepage, "Ya hay datos en la base de datos");
        }
        
        
        
    }
    
    private void loadCategorys() {
        String[] categorys = {"Pizzas", "Platos preparados", "Tortillas", "Bocadillos", "Aperitivos", "Bebidas"};
        for (String name : categorys) {
            Category category = new Category(null, name);
            categoryService.addCategory(category);
        }
    }
    
    private void loadIngredients() {
        String ingredients[] = {"mozzarella",  "york", "olivas", "champiñón", "alcaparras", "bacon", "atún",
                "espárragos", "alcachofas", "roquefort", "pimiento", "guindilla", "pepinillos", "pepperoni",  "calamares", "gambas", "ajo", 
                "anchoas","cebolla", "salsa barbacoa", "carne", "chorizo", "maíz", "tomate natural", "nata", "kebab de pollo", "salmón", "carne con tomate", "salchichas", "huevo",
                "pollo", "jamón serrano", "emmental", "edam", "piña", "palitos de mar", "Sin lactosa", "Bebida", "lechuga", "Plato preparado", "Aperitivo",
                "Bocadillo", "queso", "Tortilla"};
        
        BigDecimal prices[] = {BigDecimal.valueOf(0.80), BigDecimal.valueOf(0.40), BigDecimal.valueOf(0.50), BigDecimal.valueOf(0.60), BigDecimal.valueOf(0.70)};
        
        for (String name : ingredients) {
            Ingredient ingredient = new Ingredient();
            ingredient.setName(name);
            Random random = new Random();
            int randomIndex = random.nextInt(prices.length);
            ingredient.setPrice(prices[randomIndex]);
            ingredientService.addModifyIngredient(ingredient); 
        }
    }
    
    private void loadPizzas() {
        Random random = new Random();
        List<Ingredient> ingredients = ingredientService.getAllIngredients();
        
        String pizzas[] = {"Pericote", "Roquefort", "Olimpia", "Ciccio", "Don Pepino", "Bacon", "Fruto del mar", "Vegetal", "Diávolo", "Napolitana", "Atún", "Barbacoa",
                "4 Estaciones", "Primavera", "Prosciutto", "York", "Filipensas", "Carbonara", "Finiculi", "Siciliana", "Pepperoni", "Láctea", "Kebab", "Bella Napoli", 
                "Margherita", "Gamsal", "Bolognesa" , "Santiago", "pollinata", "Serrana", "Fungi", "4 Quesos", "Pollo", "Piña", "Hawaiana"};
        
        BigDecimal prices[] = {BigDecimal.valueOf(7.50), BigDecimal.valueOf(7.70),BigDecimal.valueOf(8.00), BigDecimal.valueOf(8.20), BigDecimal.valueOf(8.50), BigDecimal.valueOf(9.00), BigDecimal.valueOf(9.50)};
        
        for (String name : pizzas) {
            Category category = categoryService.findCategoryByName("Pizzas");
            Product product = new Product();
            product.setName(name);
            product.setCategory(category);

            // Asignar precio aleatorio
            int randomIndex = random.nextInt(prices.length);
            product.setPrice(prices[randomIndex]);

            // Guardar el producto
            productService.saveModifyProduct(product);
            
            // Asignar entre 2 y 4 ingredientes aleatorios
            int numIngredients = random.nextInt(5) + 2; // Genera un número aleatorio entre 2 y 4
            Set<Ingredient> selectedIngredients = new HashSet<>(); // Para evitar ingredientes duplicados

            while (selectedIngredients.size() < numIngredients) {
                randomIndex = random.nextInt(ingredients.size());
                selectedIngredients.add(ingredients.get(randomIndex));  
            }  
            for (Ingredient in : selectedIngredients) {
                ProductIngredient prIn = new ProductIngredient(null, product, in);
                productIngredientService.saveProductIngredient(prIn);
            }
        }

    }
    
    private void loadDrinks() {
        //Ingredient i = ingredientService.findIngredientById(Long.MIN_VALUE) plato preparado 41, aperitivo 42, salchicha 43, queso 44, bebida 39
        Ingredient ingredient = ingredientService.findIngredientById(38l);
        Category category = categoryService.findCategoryByName("Bebidas");
        BigDecimal price = BigDecimal.valueOf(1.20);
        String names[] = {"Coca-cola", "Fanta Naranja", "Fanta Limón", "Acuarius", "Cerveza", "Agua"};
        
        saveProduct(names, category, price, ingredient);
        
    }
    
    private void loadSnaks() {
        Ingredient ingredient = ingredientService.findIngredientById(41l);
        Category category = categoryService.findCategoryByName("Aperitivos");
        BigDecimal price = BigDecimal.valueOf(1.50);
        String[] names = {"olivas rellenas de anchoa", "Pepinillos", "Olivas violadas", "Banderillas picantes"};   
        
        saveProduct(names, category, price, ingredient);
    }
    
    private void loadTortillas() {
        Ingredient ingredient = ingredientService.findIngredientById(44l);
        Category category = categoryService.findCategoryByName("Tortillas");
        BigDecimal price = BigDecimal.valueOf(4.00);
        String[] names = {"Francesa", "Jamón", "Gambas"};   
        
        saveProduct(names, category, price, ingredient);
    }
    
    private void loadPreparedDishes() {
        //Ingredient i = ingredientService.findIngredientById(Long.MIN_VALUE) plato preparado 41, aperitivo 42, salchicha 43, queso 44, bebida 39
        Ingredient ingredient = ingredientService.findIngredientById(40l);
        Category category = categoryService.findCategoryByName("Platos preparados");
        BigDecimal price = BigDecimal.valueOf(5.00);
        String names[] = {"Lasaña", "Macarrones", "Callos"};
        
        saveProduct(names, category, price, ingredient);
    }
    
    private void loadSandwichs() {
        Ingredient ingredient = ingredientService.findIngredientById(42l);
        Category category = categoryService.findCategoryByName("Bocadillos");
        BigDecimal price = BigDecimal.valueOf(4.00);
        String names[] = {"Lomo", "Hamburguesa", "Hamburguesa completa", "Perrito", "Bacon", "Sandwich"};
        
        saveProduct(names, category, price, ingredient);
        
    }
    
 
    private void saveProduct(String[] names, Category category, BigDecimal price, Ingredient ingredient) {
        for (String name : names) {
            Product product = new Product();
            product.setCategory(category);
            product.setName(name);
            product.setPrice(price);
            productService.saveModifyProduct(product);
            ProductIngredient prIn = new ProductIngredient(null, product, ingredient);
            productIngredientService.saveProductIngredient(prIn);
        }
    }
    

    private void loadCustomers() {
        List<String> names = Arrays.asList(
            "Antonio", "María", "Manuel", "Carmen", "José", "Ana", "Francisco", "Laura", "David", "Isabel", 
            "Juan", "Marta", "Javier", "Lucía", "Carlos", "Sara", "Miguel", "Paula", "Daniel", "Claudia", 
            "Pedro", "Patricia", "Alejandro", "Sofía", "Rafael", "Elena", "Raúl", "Irene", "Adrián", "Silvia", 
            "Álvaro", "Rosa", "Sergio", "Cristina", "Luis", "Beatriz", "Fernando", "Victoria", "Jorge", "Eva", 
            "Pablo", "Nuria", "Roberto", "Alicia", "Alberto", "Natalia", "Mario", "Julia", "Andrés", "Teresa"
        );
        
        List<String> surnames = Arrays.asList(
            "García", "Martínez", "López", "Sánchez", "Rodríguez", "Fernández", "Pérez", "González", "Gómez", "Ruiz", 
            "Díaz", "Hernández", "Muñoz", "Álvarez", "Moreno", "Jiménez", "Romero", "Torres", "Navarro", "Gutiérrez", 
            "Ramos", "Gil", "Vázquez", "Serrano", "Molina", "Blanco", "Castro", "Ortiz", "Rubio", "Marín", 
            "Suárez", "Sanz", "Medina", "Vega", "Domínguez", "Fuentes", "Cabrera", "Iglesias", "Reyes", "Rivas"
        );
        
        List<String> streets = Arrays.asList(
            "Calle Mayor", "Avenida de la Constitución", "Calle Real", "Paseo de la Castellana", "Calle del Carmen", 
            "Calle de los Olmos", "Calle Gran Vía", "Calle de la Paz", "Calle San Juan", "Calle del Sol", 
            "Calle del Mar", "Calle de la Estrella", "Avenida de España", "Calle del Río", "Calle del Prado", 
            "Calle del Valle", "Calle de la Iglesia", "Calle de la Libertad", "Calle Nueva", "Calle del Molino", 
            "Calle de las Flores", "Calle de la Fuente", "Calle del Pilar", "Calle de San Pedro", "Calle de la Luna", 
            "Calle del Agua", "Calle de los Pinos", "Calle de San Antonio", "Calle del Norte", "Calle del Cid", 
            "Calle de Santa Ana", "Calle del Pozo", "Calle del Aire", "Calle del Olivo", "Calle de la Amistad", 
            "Calle de los Abetos", "Calle del Almendro", "Calle del Álamo", "Calle de la Cruz", "Calle del Roble", 
            "Calle de los Naranjos", "Calle de la Estación", "Calle del Castillo", "Calle del Fuego", "Calle del Bosque", 
            "Calle del Coloso", "Calle de los Laureles", "Calle de la Encina", "Calle del Arenal", "Calle de las Rosas"
        );
        
        List<String> numbers = Arrays.asList("1º", "1ºB", "1ºA", "2º", "2ºB", "2ºA", "3º", "3ºB", "3ºA" );
        
        Random random = new Random();
        for (int i=0; i<1000; i++) {
            int index = random.nextInt(names.size());
            String name = names.get(index);
            
            index = random.nextInt(surnames.size());
            String surname = surnames.get(index);
            
            index = random.nextInt(streets.size());
            String street = streets.get(index);
            
            index = random.nextInt(numbers.size());
            String number = numbers.get(index);
            String address = street + " " + number;

            String phone;
            Customer cExist;

            do {
                phone = generatePhone();
                cExist = customerService.findCustomerByPhone(phone);
            } while (cExist != null);  // Repetir hasta que no se encuentre un cliente con el mismo teléfono

            Customer customer = new Customer();
            customer.setName(name + " " + surname);
            customer.setAddress(address);
            customer.setPhone(phone);

            customerService.addCustomer(customer);

        }
        
        
    }
    
    private String generatePhone() {
        int n = 600000000;
        int m = 699999999;
        int phone = (int)Math.floor(Math.random()*(n-m+1)+m);
        return String.valueOf(phone);
    }
    
    
    public void generateRandomOrders(int numberOfOrders) {
        Random random = new Random();

        // Obtener todos los clientes y productos disponibles
        List<Customer> customers = customerService.findAllCustomers();
        List<Product> products = productService.findAllProducts();

        // Generar las órdenes
        for (int i = 0; i < numberOfOrders; i++) {
            // Seleccionar un cliente aleatorio
            Customer randomCustomer = customers.get(random.nextInt(customers.size()));

            // Crear una nueva orden
            Order order = new Order();
            order.setCustomer(randomCustomer);

            // Generar una fecha aleatoria del último año
            LocalDate randomDate = LocalDate.now().minusDays(random.nextInt(365));
            order.setDate(randomDate);

            // Crear una lista de productos para esta orden (entre 1 y 5 productos aleatorios)
            int numberOfProducts = random.nextInt(5) + 1; // Entre 1 y 5 productos
            BigDecimal totalPrice = BigDecimal.ZERO;
            List<OrderProduct> orderProducts = new ArrayList<>();

            for (int j = 0; j < numberOfProducts; j++) {
                // Seleccionar un producto aleatorio
                Product randomProduct = products.get(random.nextInt(products.size()));

                // Crear una nueva instancia de OrderProduct
                OrderProduct orderProduct = new OrderProduct();
                orderProduct.setOrder(order);
                orderProduct.setProduct(randomProduct);
                orderProduct.setPriceWithModifications(randomProduct.getPrice()); // Usamos el precio del producto

                // Añadir el producto a la lista de productos de la orden
                orderProducts.add(orderProduct);

                // Sumar el precio al total de la orden
                totalPrice = totalPrice.add(randomProduct.getPrice());
            }

            // Asignar el total y la lista de productos a la orden
            order.setTotalPrice(totalPrice);
            order.setOrderProducts(orderProducts);

            // Guardar la orden en la base de datos
            orderService.addOrder(order);
        }
    }

    

    
}
