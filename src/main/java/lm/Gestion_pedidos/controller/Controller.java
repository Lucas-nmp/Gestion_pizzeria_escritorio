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
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import lm.Gestion_pedidos.model.Company;
import lm.Gestion_pedidos.model.Order;
import lm.Gestion_pedidos.model.OrderProduct;
import lm.Gestion_pedidos.service.CompanyService;
import lm.Gestion_pedidos.service.OrderProductService;
import lm.Gestion_pedidos.service.OrderService;
import lm.Gestion_pedidos.view.Setting;
import lm.Gestion_pedidos.view.Statistics;
import org.springframework.boot.SpringApplication;
import static org.springframework.boot.SpringApplication.main;

/**
 * Esta clase gestiona la lógica principal de la aplicación, encargándose de
 * coordinar la interacción entre las diferentes vistas y modelos de la aplicación.
 * 
 * Proporciona métodos para la gestión de pedidos, productos, clientes y
 * configuraciones del sistema.
 * 
 * @author Lucas Morandeira Parejo
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
    
    /**
    * Configura y gestiona la vista principal de la aplicación, estableciendo interacciones, efectos visuales 
    * y comportamientos para cada componente de la vista "Homepage".
    * <p>
    * Este método realiza las siguientes acciones:
    * </p>
    * 
    * <b>Acciones principales:</b>
    * <ul>
    *   <li>Inicializa la vista principal (`Homepage`).</li>
    *   <li>Establece las acciones y eventos de cada botón en la interfaz, incluyendo abrir vistas de gestión como
    *       categorías, productos, ingredientes, clientes y configuraciones.</li>
    *   <li>Aplica efectos visuales a los botones, como el cambio de color cuando el ratón pasa por encima (hover).</li>
    *   <li>Configura eventos de enfoque para los campos de texto (`JTextField`) en la vista principal.</li>
    *   <li>Controla el evento de búsqueda de clientes por número de teléfono al presionar la tecla "Enter".</li>
    *   <li>Configura y personaliza las tablas (`JTable`) de la vista principal, asignando modelos de datos y
    *       añadiendo funcionalidades de eliminación y selección de elementos.</li>
    *   <li>Rellena las listas desplegables (`ComboBox`) con las categorías e ingredientes disponibles.</li>
    *   <li>Gestiona la información de la empresa en la interfaz.</li>
    * </ul>
    * 
    * <b>Componentes visuales y eventos manejados:</b>
    * <ul>
    *   <li>Botones de la interfaz (`JButton`): `BtnCategory`, `BtnProduct`, `BtnIngredient`, `BtnCustomer`, `BtnSetting`, `BtnStatistics`, `BtnExit`, `BtnDemo`, y botones adicionales para la gestión de pedidos.</li>
    *   <li>Campos de texto (`JTextField`): `EdtPhoneCustomer` para capturar el teléfono del cliente y `EdtAlternativeAddress` para direcciones alternativas.</li>
    *   <li>Tablas (`JTable`): `TableOrder` para gestionar los productos del pedido y `TableProducts` para visualizar productos disponibles.</li>
    *   <li>Listas desplegables (`ComboBox`): `Categorys` para seleccionar categorías de productos.</li>
    * </ul>
    * 
    * <b>Excepciones:</b>
    * <ul>
    *   <li>Se muestran mensajes de error o advertencia en cuadros de diálogo (`JOptionPane`) si no se encuentran datos o si el usuario realiza una acción inválida.</li>
    * </ul>
    * 
    * <b>Funcionalidades adicionales:</b>
    * <ul>
    *   <li>Establece el comportamiento para limitar la cantidad de caracteres permitidos en el campo de teléfono del cliente.</li>
    *   <li>Inicializa variables como `modifications`, `modificationsPrice` y `total` para gestionar las modificaciones de los pedidos.</li>
    *   <li>Configura la información de la empresa en la interfaz, como el nombre de la compañía, y rellena la tabla de productos basándose en la categoría seleccionada.</li>
    *   <li>Hace visible la interfaz (`Homepage`) y la centra en la pantalla.</li>
    * </ul>
    */
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
        this.homepage.getBtnExit().addActionListener(e -> closeApp());
        
        this.homepage.getBtnDemo().addActionListener(e -> loadDemoData());
        
        
        // Visuales
        JButton btnCategory = this.homepage.getBtnCategory();
        JButton btnProduct = this.homepage.getBtnProduct();
        JButton btnIngredient = this.homepage.getBtnIngredient();
        JButton btnCustomer = this.homepage.getBtnCustomer();
        JButton btnSettings = this.homepage.getBtnSetting();
        JButton btnStatistics = this.homepage.getBtnStatistics();
        JButton btnExit = this.homepage.getBtnExit();
        JButton btnDeme = this.homepage.getBtnDemo();
        JButton btnConfirm = this.homepage.getBtnConfirmOrder();
        JButton btnAdd = this.homepage.getBtnAdd();
        JButton btnAdd2 = this.homepage.getBtnAdd2();
        JButton btnCancel = this.homepage.getBtnCancelOrder();
        JButton btnRemoveFrom = this.homepage.getBtnRemoveFromOrder();
        JButton btnAddIngredient = this.homepage.getBtnAddIngredient();
        JButton btnRemoveIngredient = this.homepage.getBtnRemoveIngredient();
        
        activateHoverEffect(btnCategory, java.awt.Color.green);
        activateHoverEffect(btnProduct, java.awt.Color.green);
        activateHoverEffect(btnIngredient, java.awt.Color.green);
        activateHoverEffect(btnCustomer, java.awt.Color.green);
        activateHoverEffect(btnSettings, java.awt.Color.green);
        activateHoverEffect(btnStatistics, java.awt.Color.green);
        activateHoverEffect(btnExit, java.awt.Color.red);
        activateHoverEffect(btnDeme, java.awt.Color.orange);
        activateHoverEffect(btnConfirm, java.awt.Color.green);
        activateHoverEffect(btnAdd, java.awt.Color.green);
        activateHoverEffect(btnAdd2, java.awt.Color.green);
        activateHoverEffect(btnCancel, java.awt.Color.red);
        activateHoverEffect(btnRemoveFrom, java.awt.Color.red);
        activateHoverEffect(btnRemoveIngredient, java.awt.Color.red);
        activateHoverEffect(btnAddIngredient, java.awt.Color.green);
        
        
        activateFocusEfect(this.homepage.getEdtPhoneCustomer(), "Teléfono");
        activateFocusEfect(this.homepage.getEdtAlternativeAddres(), "Dirección alternativa");
        
        
        
        this.homepage.getEdtPhoneCustomer().addActionListener((ActionEvent e) -> {
            String phone = homepage.getEdtPhoneCustomer().getText();
            lookForCustomer(phone);
        });
        
        this.homepage.getEdtAlternativeAddres().addActionListener((ActionEvent e) -> {
            String alternativeAddress = homepage.getEdtAlternativeAddres().getText();
            homepage.getTxtOrderAddresCustomer().setText(alternativeAddress);
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
        
       
        
        limitPhoneCharacters(this.homepage.getEdtPhoneCustomer());
        
        homepage.setLocationRelativeTo(null);
        homepage.setResizable(true);
        fillIngredients(homepage.getIngredientModify());
        fillCategorys(homepage.getCategorys()); 
        
        DefaultTableModel model = new DefaultTableModel();
        String[] headers = {"ID", "Producto", "Observaciones","Unds.", "PVP"}; 
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
        
        selectValidItemComboBox(this.homepage.getCategorys());
        Company company = companyService.fingCompanyById(1L);
        String companyName = "Nombre de la empresa";
        if (company != null) {
            companyName = company.getName();
        }
        
        setTextJLabel(homepage.getTxtCompanyName(), companyName);
        
        
        String selectedItem = (String) homepage.getCategorys().getSelectedItem();
        Category category = categoryService.findCategoryByName(selectedItem);
        
        fillTableProduct(category);
        homepage.setVisible(true);  
    }
    
    
    /**
    * Abre la vista que gestiona las categorías, configurando los eventos y componentes visuales de la ventana "Category".
    * <p>
    * Este método realiza las siguientes acciones:
    * </p>
    * <b>Acciones principales:</b>
    * <ul>
    *  <li>Inicializa la vista `ManageCategory` y establece sus parámetros de tamaño y posición.</li>
    *  <li>Configura las acciones para los botones de guardar y eliminar categorías, así como para el campo de nombre de la categoría (`CategoryName`).</li>
    *  <li>Aplica efectos visuales de "hover" a los botones de la interfaz, cambiando el color de las letras según corresponda.</li>
    *  <li>Gestiona la selección de filas en la tabla de categorías (`CategoryTable`), mostrando la información de la categoría seleccionada en los campos de texto.</li>
    *  <li>Configura la tabla de categorías (`CategoryTable`) con los encabezados y el modelo de datos necesario.</li>
    * </ul>
    * 
    * <b>Componentes visuales y eventos manejados:</b>
    * <ul>
    *   <li>Botones de la interfaz (`JButton`): `CategorySave` para guardar o modificar una categoría, y `DeleteCategory` para eliminarla.</li>
    *   <li>Campo de texto (`JTextField`): `CategoryName` para capturar el nombre de la categoría y realizar operaciones de guardado al presionar "Enter".</li>
    *   <li>Tabla (`JTable`): `CategoryTable` para visualizar y seleccionar categorías almacenadas.</li>
    * </ul>
    * 
    * <b>Excepciones:</b>
    * <ul>
    *   <li>Se muestran mensajes de confirmación (`JOptionPane`) cuando se selecciona una fila para su eliminación, y mensajes de advertencia en caso de errores o datos vacíos.</li>
    * </ul>
    * 
    * <b>Funcionalidades adicionales:</b>
    * <ul>
    *   <li>Rellena la tabla de categorías (`fillTableCategory()`) con los datos actuales de la base de datos.</li>
    *   <li>Limpia los campos de texto (`cleanCategory()`) tras guardar o modificar una categoría.</li>
    *   <li>Actualiza la lista de categorías disponible en la vista principal (`Homepage`) tras realizar cambios.</li>
    *   <li>Hace visible la ventana de `ManageCategory` como una ventana modal, bloqueando la interacción con la vista principal hasta su cierre.</li>
    * </ul>
     */
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
                    setTextJText(manageCategory.getCategoryName(), name);
                
                }
            }
            
        });  
        
        JButton btnSave = this.manageCategory.getCategorySave();
        JButton btnDelete = this.manageCategory.getDeleteCategory();
        
        activateHoverEffect(btnSave, java.awt.Color.green);
        activateHoverEffect(btnDelete, java.awt.Color.red);
        
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
    
    
    
    /**
    * Abre la vista que gestiona los productos, configurando los eventos y componentes visuales de la ventana `ManageProduct`.
    * <p>
    * Este método realiza las siguientes acciones:
    * </p>
    * 
    * <b>Acciones principales:</b>
    * <ul>
    *   <li>Inicializa la vista `ManageProduct` y establece sus parámetros de tamaño y posición.</li>
    *   <li>Configura las acciones para los botones de añadir y eliminar ingredientes, guardar y eliminar productos, así como para los campos de nombre y precio de producto.</li>
    *   <li>Aplica efectos visuales de "hover" a los botones de la interfaz, cambiando el color del texto según corresponda.</li>
    *   <li>Gestiona la selección de filas en la tabla productos (`TableProducts`), mostrando la información del producto seleccionado en los campos de texto, incluyendo su nombre, categoría, ingredientes y precio.</li>
    *   <li>Configura la tabla de productos (`TableProducts`) con los encabezados y el modelo de datos necesario.</li>
    *   <li>Actualiza la lista de categorías e ingredientes disponibles en la vista (`BoxCategory` y `BoxIngredients`).</li>
    * </ul>
    * 
    * <b>Componentes visuales y eventos manejados:</b>
    * <ul>
    *   <li>Botones de la interfaz (`JButton`): `BtnAddIngredient` para añadir ingredientes al producto, `BtnDeleteIngredient` para eliminarlos, `BtnSaveProduct` para guardar o modificar un producto, y `BtnDeleteProduct` para eliminarlo.</li>
    *   <li>Campos de texto (`JTextField`): `EdtNameProduct` y `EdtPriceProduct` para capturar el nombre y precio del producto, con eventos de validación y enfoque.</li>
    *   <li>Listas desplegables (`ComboBox`): `BoxCategory` para seleccionar la categoría de un producto y `BoxIngredients` para gestionar sus ingredientes.</li>
    *   <li>Tabla (`JTable`): `TableProducts` para visualizar y seleccionar productos almacenados.</li>
    * </ul>
    * 
    * <b>Excepciones:</b>
    * <ul>
    *   <li>Se muestran mensajes de confirmación (`JOptionPane`) cuando se selecciona un producto para su eliminación, y mensajes de advertencia en caso de errores o datos vacíos.</li>
    * </ul>
    * 
    * <b>Funcionalidades adicionales:</b>
    * <ul>
    *   <li>Rellena la tabla de productos (`fillTableProduct()`) con los datos actuales de la base de datos.</li>
    *   <li>Actualiza la lista de ingredientes y categorías disponibles en los `ComboBox` de la vista.</li>
    *   <li>Actualiza los ingredientes y sus identificadores (`listIngredientsProduct` y `listIdIngredientsInProduct`) tras seleccionar un producto.</li>
    *   <li>Limpia los campos de texto (`clearViewProduct()`) tras guardar o modificar un producto.</li>
    *   <li>Hace visible la ventana de `ManageProduct` como una ventana modal, bloqueando la interacción con la vista principal hasta su cierre.</li>
    *   <li>Se asegura de que, tras cerrar la ventana de `ManageProduct`, se actualice la lista de categorías seleccionada en la vista principal (`Homepage`).</li>
    * </ul>
    */
    private void openManageProducts() {
        this.manageProduct = new ManageProduct();
        
        this.manageProduct.getBtnAddIngredient().addActionListener(e -> addIngredientToProduct());
        this.manageProduct.getBtnDeleteIngredient().addActionListener(e -> deleteIngredientFromProduct());
        this.manageProduct.getBtnSaveProduct().addActionListener(e -> saveOrModifyProduct());
        this.manageProduct.getBtnDeleteProduct().addActionListener(e -> deleteProduct());
        
        this.manageProduct.getEdtNameProduct().addActionListener((ActionEvent e) -> { 
            setTextJLabel(manageProduct.getNameTxt(), manageProduct.getEdtNameProduct().getText());
            
        });
        
        this.manageProduct.getEdtNameProduct().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                setTextJLabel(manageProduct.getNameTxt(), manageProduct.getEdtNameProduct().getText());
                
            }
        });
        
        JButton btnAddIngredient = this.manageProduct.getBtnAddIngredient();
        JButton btnDeleteIngredient = this.manageProduct.getBtnDeleteIngredient();
        JButton btnSaveProduct = this.manageProduct.getBtnSaveProduct();
        JButton btnDeleteProduct = this.manageProduct.getBtnDeleteProduct();
        
        activateHoverEffect(btnAddIngredient, java.awt.Color.green);
        activateHoverEffect(btnDeleteIngredient, java.awt.Color.red);
        activateHoverEffect(btnSaveProduct, java.awt.Color.green);
        activateHoverEffect(btnDeleteProduct, java.awt.Color.red);
        
        
                
        this.manageProduct.getEdtPriceProduct().addActionListener((ActionEvent e) -> {
            setTextJLabel(manageProduct.getPriceTxt(), manageProduct.getEdtPriceProduct().getText());
        });
        
        this.manageProduct.getEdtPriceProduct().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                
                setTextJLabel(manageProduct.getPriceTxt(), manageProduct.getEdtPriceProduct().getText());
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
                    setTextJLabel(manageProduct.getCategoryTxt(), product.getCategory().getName());
                    setTextJLabel(manageProduct.getNameTxt(), name);
                    setTextJLabel(manageProduct.getPriceTxt(), price.toString());
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
    
    
    /**
    * Abre la vista que gestiona los ingredientes, configurando los eventos y componentes visuales de la ventana `ManageIngredient`.
    * <p>
    * Este método realiza las siguientes acciones:
    * </p>
    * 
    * <b>Acciones principales:</b>
    * <ul>
    *   <li>Inicializa la vista `ManageIngredient` y establece sus parámetros de tamaño y posición en pantalla.</li>
    *   <li>Configura las acciones para los botones de guardar y eliminar ingredientes, así como para los campos de nombre y precio del ingrediente.</li>
    *   <li>Aplica efectos visuales de "hover" a los botones de la interfaz, cambiando el color del texto según corresponda (verde para guardar y rojo para eliminar).</li>
    *   <li>Gestiona la selección de filas en la tabla de ingredientes (`TableIngredient`), mostrando la información del ingrediente seleccionado en los campos de texto correspondientes, incluyendo su nombre y precio.</li>
    *   <li>Configura la tabla de ingredientes (`TableIngredient`) con los encabezados y el modelo de datos necesario para mostrar la información.</li>
    *   <li>Establece el orden de enfoque de los campos de texto, permitiendo moverse entre los campos de nombre y precio usando la tecla Enter.</li>
    * </ul>
    * 
    * <b>Componentes visuales y eventos manejados:</b>
    * <ul>
    *   <li>Botones de la interfaz (`JButton`): `BtnSaveIngredient` para guardar o modificar un ingrediente, y `BtnDeleteIngredient` para eliminar un ingrediente.</li>
    *   <li>Campos de texto (`JTextField`): `EdtNameIngredient` y `EdtPriceIngredient` para capturar el nombre y precio del ingrediente, con eventos de validación y enfoque.</li>
    *   <li>Tabla (`JTable`): `TableIngredient` para visualizar y seleccionar ingredientes almacenados.</li>
    * </ul>
    * 
    * <b>Excepciones:</b>
    * <ul>
    *   <li>Se muestran mensajes de confirmación (`JOptionPane`) cuando se selecciona un ingrediente para su eliminación, y mensajes de advertencia en caso de errores o datos vacíos.</li>
    * </ul>
    * 
    * <b>Funcionalidades adicionales:</b>
    * <ul>
    *   <li>Rellena la tabla de ingredientes (`fillTableIngredients()`) con los datos actuales de la base de datos.</li>
    *   <li>Limpia los campos de texto (`cleanDataIngredient()`) tras guardar o modificar un ingrediente.</li>
    *   <li>Hace visible la ventana de `ManageIngredient` como una ventana modal, bloqueando la interacción con la vista principal hasta su cierre.</li>
    *   <li>Aplica efectos de hover a los botones de la ventana, mejorando la experiencia visual del usuario.</li>
    * </ul>
    */
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
                    setTextJText(manageIngredient.getEdtNameIngredient(), nameIngredient);
                    setTextJText(manageIngredient.getEdtPriceIngredient(), priceIngredient.toString());
                }  
            }            
        });
        
        JButton btnDeleteIngredient = this.manageIngredient.getBtnDeleteIngredient();
        JButton btnSaveIngredient = this.manageIngredient.getBtnSaveIngredient();
        
        activateHoverEffect(btnDeleteIngredient, java.awt.Color.red);
        activateHoverEffect(btnSaveIngredient, java.awt.Color.green);
        
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
    
    
    /**
    * Abre la vista que gestiona los clientes, configurando los eventos y componentes visuales de la ventana `ManageCustomer`.
    * <p>
    * Este método realiza las siguientes acciones:
    * </p>
    * 
    * <b>Acciones principales:</b>
    * <ul>
    *   <li>Inicializa la vista `ManageCustomer` y establece sus parámetros de tamaño y posición en pantalla.</li>
    *   <li>Configura las acciones para los botones de guardar, eliminar y buscar clientes, así como para los campos de nombre, dirección, teléfono y observaciones del cliente.</li>
    *   <li>Aplica efectos visuales de "hover" a los botones de la interfaz, cambiando el color del texto según corresponda (verde para guardar, rojo para eliminar, y azul para buscar).</li>
    *   <li>Gestiona la selección de filas en la tabla de clientes (`TableCustomer`), mostrando la información del cliente seleccionado en los campos de texto correspondientes, incluyendo su nombre, dirección, teléfono y observaciones.</li>
    *   <li>Configura la tabla de clientes (`TableCustomer`) con los encabezados y el modelo de datos necesario para mostrar la información.</li>
    *   <li>Limita la cantidad de caracteres permitidos en el campo de texto del teléfono (`EdtPhoneCustomer`).</li>
    * </ul>
    * 
    * <b>Componentes visuales y eventos manejados:</b>
    * <ul>
    *   <li>Botones de la interfaz (`JButton`): `BtnSaveCustomer` para guardar o modificar un cliente, `BtnDeleteCustomer` para eliminar un cliente, y `BtnLookForCustomer` para buscar un cliente por teléfono.</li>
    *   <li>Campos de texto (`JTextField`): `EdtNameCustomer` para capturar el nombre del cliente, `EdtAddresCustomer` para la dirección, `EdtPhoneCustomer` para el número de teléfono, y `EdtStatusCustomer` para observaciones adicionales del cliente. Se configuran eventos de validación y enfoque para cada campo de texto.</li>
    *   <li>Tabla (`JTable`): `TableCustomer` para visualizar y seleccionar clientes almacenados en la base de datos.</li>
    * </ul>
    * 
    * <b>Excepciones:</b>
    * <ul>
    *   <li>Se muestran mensajes de confirmación (`JOptionPane`) cuando se selecciona un cliente para su eliminación, y mensajes de advertencia en caso de errores o datos vacíos.</li>
    * </ul>
    * 
    * <b>Funcionalidades adicionales:</b>
    * <ul>
    *   <li>Rellena la tabla de clientes (`fillTableCustomer()`) con los datos actuales de la base de datos.</li>
    *   <li>Limpia los campos de texto (`clearViewCustomer(phone)`) tras guardar o modificar un cliente, utilizando el número de teléfono como referencia inicial.</li>
    *   <li>Hace visible la ventana de `ManageCustomer` como una ventana modal, bloqueando la interacción con la vista principal hasta su cierre.</li>
    *   <li>Aplica efectos visuales a los campos de texto, destacando el enfoque activo con texto de ayuda (placeholders).</li>
    * </ul>
    * 
    * @param phone Número de teléfono del cliente a buscar inicialmente en la vista. Si se proporciona, se utiliza para filtrar y mostrar el cliente correspondiente en la tabla.
    */
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
        
        JButton btnSave = this.manageCustomer.getBtnSaveCustomer();
        JButton btnDelete = this.manageCustomer.getBtnDeleteCustomer();
        JButton btnLookFor = this.manageCustomer.getBtnLookForCustomer();
        
        activateHoverEffect(btnSave, java.awt.Color.green);
        activateHoverEffect(btnDelete, java.awt.Color.red);
        activateHoverEffect(btnLookFor, java.awt.Color.blue);
        
        activateFocusEfect(this.manageCustomer.getEdtNameCustomer(), "Nombre");
        activateFocusEfect(this.manageCustomer.getEdtAddresCustomer(), "Dirección");
        activateFocusEfect(this.manageCustomer.getEdtPhoneCustomer(), "Teléfono");
        activateFocusEfect(this.manageCustomer.getEdtStatusCustomer(), "Observaciones");
        
        
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
                    
                    
                    setTextJText(manageCustomer.getEdtNameCustomer(), name);
                    manageCustomer.getEdtNameCustomer().setForeground(java.awt.Color.WHITE);
                    setTextJText(manageCustomer.getEdtAddresCustomer(), addres);
                    manageCustomer.getEdtAddresCustomer().setForeground(java.awt.Color.WHITE);
                    setTextJText(manageCustomer.getEdtPhoneCustomer(), phone);
                    manageCustomer.getEdtPhoneCustomer().setForeground(java.awt.Color.WHITE);  
                    setTextJText(manageCustomer.getEdtStatusCustomer(), status);
                    manageCustomer.getEdtStatusCustomer().setForeground(java.awt.Color.WHITE);
                    
                }
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
    
    
    /**
    * Abre la vista de estadísticas, configurando los eventos y componentes visuales de la ventana `Statistics`.
    * <p>
    * Este método realiza las siguientes acciones:
    * </p>
    * 
    * <b>Acciones principales:</b>
    * <ul>
    *   <li>Inicializa la vista `Statistics` y establece su ubicación en el centro de la pantalla.</li>
    *   <li>Configura la acción del `ComboBox` de meses (`BoxMonth`), llamando al método `handleMonthSelection()` cuando se selecciona un nuevo mes.</li>
    *   <li>Rellena la lista de meses disponibles en el `ComboBox` mediante el método `fillMonth()`.</li>
    * </ul>
    * 
    * <b>Componentes visuales y eventos manejados:</b>
    * <ul>
    *   <li>ComboBox (`JComboBox`): `BoxMonth` para seleccionar el mes del que se quieren ver las estadísticas.</li>
    * </ul>
    * 
    * <b>Funcionalidades adicionales:</b>
    * <ul>
    *   <li>Hace visible la ventana de `Statistics` como una ventana modal, bloqueando la interacción con la vista principal hasta su cierre.</li>
    * </ul>
    */
    private void openStatistics() {
        this.statistics = new Statistics();
        
        this.statistics.getBoxMonth().addActionListener(e -> handleMonthSelection());
        
        statistics.setLocationRelativeTo(null);
        statistics.setResizable(false);
        statistics.setModal(true);
        fillMonth();
        statistics.setVisible(true);
        
    }
    
    /**
    * Abre la vista de configuración de la aplicación, cargando los datos de la empresa y configurando los eventos y componentes visuales de la ventana `Setting`.
    * <p>
    * Este método realiza las siguientes acciones:
    * </p>
    * 
    * <b>Acciones principales:</b>
    * <ul>
    *   <li>Inicializa la vista `Setting` y establece su ubicación en el centro de la pantalla.</li>
    *   <li>Recupera los datos de la empresa (`Company`) desde el servicio `companyService` usando el ID `1` y rellena los campos de nombre, dirección, CIF y teléfono en la vista.</li>
    *   <li>Configura la acción del botón de guardar (`BtnSaveSettings`), llamando al método `saveSettings()` al presionar el botón.</li>
    * </ul>
    * 
    * <b>Componentes visuales y eventos manejados:</b>
    * <ul>
    *   <li>Botón (`JButton`): `BtnSaveSettings` para guardar los cambios realizados en la configuración de la empresa.</li>
    *   <li>Campos de texto (`JTextField`): `SettingsName`, `SettingsAddress`, `SettingsCif`, `SettingsPhone` para capturar la información de la empresa.</li>
    * </ul>
    * 
    * <b>Funcionalidades adicionales:</b>
    * <ul>
    *   <li>Aplica un efecto visual de "hover" en el botón de guardar, cambiando su color a verde al pasar el ratón por encima.</li>
    *   <li>Hace visible la ventana de `Setting` como una ventana modal, bloqueando la interacción con la vista principal hasta su cierre.</li>
    * </ul>
    */
    public void openSettings() {
        this.setting = new Setting();
        
        Company company = companyService.fingCompanyById(1l);
        if (company != null) {
            setTextJText(setting.getSettingsName(), company.getName());
            setTextJText(setting.getSettingsAddress(), company.getAddress());
            setTextJText(setting.getSettingsCif(), company.getCif());
            setTextJText(setting.getSettingsPhone(), company.getPhone());
           
        }
        
        JButton btnSave = this.setting.getSaveSettings();
        
        activateHoverEffect(btnSave, java.awt.Color.green);
        
        btnSave.addActionListener(e -> saveSettings());
        
        setting.setLocationRelativeTo(null);
        setting.setResizable(false);
        setting.setModal(true);
        setting.setVisible(true);
    }
    

    /**
    * Comprueba si un campo de texto ha ganado el foco y cambia su contenido y color según corresponda.
    * <p>
    * Este método realiza las siguientes acciones:
    * </p>
    * 
    * <b>Acciones principales:</b>
    * <ul>
    *   <li>Verifica si el campo de texto (`JTextField`) contiene el texto por defecto especificado.</li>
    *   <li>Si el texto coincide con el valor por defecto, lo elimina y cambia el color del texto a blanco.</li>
    * </ul>
    * 
    * <b>Componentes visuales y eventos manejados:</b>
    * <ul>
    *   <li>Campo de texto (`JTextField`): `fieldName` que gana el foco y cambia su contenido según el valor del parámetro `texto`.</li>
    * </ul>
    * 
    * @param fieldName Campo de texto que se está enfocando.
    * @param texto Valor por defecto a comparar y eliminar si el campo contiene dicho texto.
    */
    private void checkFieldFocusGained(JTextField fieldName, String texto) {
        if (fieldName.getText().equals(texto)) {
            setTextJText(fieldName, "");
            fieldName.setForeground(java.awt.Color.WHITE);
        }
    }  
    
    /**
    * Comprueba si un campo de texto ha perdido el foco y cambia su contenido y color según corresponda.
    * <p>
    * Este método realiza las siguientes acciones:
    * </p>
    * 
    * <b>Acciones principales:</b>
    * <ul>
    *   <li>Verifica si el campo de texto (`JTextField`) está vacío o contiene el texto por defecto especificado.</li>
    *   <li>Si el campo está vacío o contiene el valor por defecto, restaura el texto por defecto y cambia el color del texto a gris.</li>
    * </ul>
    * 
    * <b>Componentes visuales y eventos manejados:</b>
    * <ul>
    *   <li>Campo de texto (`JTextField`): `fieldName` que pierde el foco y cambia su contenido según el valor del parámetro `texto`.</li>
    * </ul>
    * 
    * @param fieldName Campo de texto que pierde el foco.
    * @param texto Valor por defecto a establecer si el campo está vacío o coincide con el valor especificado.
    */
    private void checkFieldFocusLost(JTextField fieldName, String texto) {
        if (fieldName.getText().equals(texto) || fieldName.getText().isEmpty()) {
            setTextJText(fieldName, texto);
            fieldName.setForeground(java.awt.Color.GRAY);
        }
    }
    
    
    /**
     * Selecciona por defecto el segundo item del comboBox si hay más de uno.
     * 
     * <p>Agiliza el uso del programa evitando que aparezca marcado por defecto el item informativo 
     * cuando ya hay items válidos cargados en el comboBox.</p>
     * 
     * @param comboBox componente visual que será modificado
     */
    private void selectValidItemComboBox(JComboBox comboBox) {
        int itemCount = comboBox.getItemCount();
        if (itemCount > 1) {
            comboBox.setSelectedIndex(1);
        } else {
            comboBox.setSelectedIndex(0);   
        }     
    }
    
    /**
     * Guarda o modifica una categoría.
     * <p>Con este método podemos crear una nueva categoría y modificar una existente seleccionandola de la tabla</p>
     * <b>Avisos</b>
     * <ul>
    *   <li>Muestra un mensaje en caso de intentar guardar una categoría sin haber escrito el nombre</li>
    * </ul>
     */
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
    
    /**
     * Elimina una categoría seleccionada de la tabla. 
     * <p>Comprueba que se ha seleccionado una categoría y si no contiene productos la elimina y actualiza la lista de categorías 
     * tanto en su vista como en la página principal "homepage"</p>
     * <b>Excepciones:</b>
     * <ul>
     *   <li>Si la categoría contiene productos no se puede eliminar, hay que eliminar los productos previamente</li>
     * </ul>
     * 
     * 
     */
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
            cleanCategory();
        }
    }
    
    /**
     * Llena la tabla de la vista Categorías con las Categorías almacenadas en la base de datos.
     * 
     */
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
    
    /**
     * Comprueba la categoría seleccionada la usa para llenar la tabla con los productos que le pertenecen.
     * <p>Primero comprueba que se ha seleccionado una categoría, busca la categoría en la base de datos y llama al método 
     * {@link #fillTableProduct(lm.Gestion_pedidos.model.Category) } para mostrar los productos de esa categoría</p>
     * @see #fillTableProduct(lm.Gestion_pedidos.model.Category) Método para mostrar los productos de una categoría concreta
     */
    private void handleCategorySelection() {
        String selectedItem = (String) homepage.getCategorys().getSelectedItem();
        
        if (selectedItem != null && !selectedItem.equals("Añadir")) {
            Category category = categoryService.findCategoryByName(selectedItem);
            fillTableProduct(category);  
        }
    }
    /**
     * Comprueba que se ha seleccionado un mes del comboBox para mostrar las estadísticas correspondientes a ese mes.
     * <p>Pasa el mes seleccionado al método {@link #seeStatistics(java.lang.String) } y muestra las estadísticas de ese mes
     * @see #seeStatistics(java.lang.String) 
     */
    private void handleMonthSelection() {
        String selectedMonth = (String) statistics.getBoxMonth().getSelectedItem();
        
        if (selectedMonth != null && !selectedMonth.equals("Seleccione un mes")) {
            seeStatistics(selectedMonth);
        }
    }
    
    /**
     * Configura los encabezados de la tabla y establece el modelo de datos asociado.
     * <p>
     * Recibe un modelo de tabla (`DefaultTableModel`) y una lista de encabezados (`headers`)
     * para configurar los títulos de las columnas de la tabla (`JTable`) que se pasa como parámetro.
     * Los encabezados se asignan al modelo y se establece el modelo en la tabla.
     * </p>
     * @param model   el modelo de datos de la tabla (`DefaultTableModel`) que se actualizará con los encabezados
     * @param headers lista de títulos que se van a establecer como encabezados de las columnas
     * @param table   la tabla (`JTable`) que se verá afectada por la modificación y a la que se le aplicará el modelo
     */
    private void fillTableHeaders(DefaultTableModel model, String[] headers, JTable table) {
        model.setColumnIdentifiers(headers);
        table.setModel(model);
    }
    
    
    /**
     * Llena la tabla con los productos pertenecientes a la categoría recibida como parámetro.
     * <p>Busca en la base de datos los productos pertenecientes a la categoría recibida y los muestra en la tabla de la página principal "homepage"</p>
     * <p>También establece unas medidas fijas a la tabla de productos de la página principal</p>
     * 
     * @param category Objeto Categoría que se va a usar para buscar y mostrar los productos asociados a ella  
     */
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
    
    /**
     * Llena la tabla con todos los productos existentes en la base de datos.
     * <p>Busca en la base de datos todos los productos sin distinción y los muestra en la tabla
     * para que se puedan ver, modificar o eliminar desde la vista de productos</p>
     */
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
    

    /**
     * Busca y retorna la lista de ingredientes que contiene un producto.
     * <p>Busca en la base de datos en la tabla "productIngredient" todos los ingredientes que estén asociados al id de producto 
     * recibido por parámetro. Almacena los nombres de esos ingredientes en una lista y lo retorna
     * @param productId id del producto del que queremos saber sus ingredientes.
     * @return lista con los nombres de los ingredientes sin repetir que contiene un producto. 
     */
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
    
    /**
     * Establece unas medidas fijas para el elemento JTable recibido. 
     * 
     * @param table elemento que se ve afectado por los cambios
     */
    private void columnWidthProductHomepage(JTable table) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Desactiva el ajuste automático de tamaño
        table.getColumnModel().getColumn(0).setPreferredWidth(28); 
        table.getColumnModel().getColumn(1).setPreferredWidth(90); 
        table.getColumnModel().getColumn(2).setPreferredWidth(422); 
        table.getColumnModel().getColumn(3).setPreferredWidth(41); 
    }
    
    /**
     * Establece unas medidas fijas para el elemento JTable recibido. 
     * 
     * @param table elemento que se ve afectado por los cambios
     */
    private void columnWidthProductManageProduct(JTable table) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 
        table.getColumnModel().getColumn(0).setPreferredWidth(30); 
        table.getColumnModel().getColumn(1).setPreferredWidth(100); 
        table.getColumnModel().getColumn(2).setPreferredWidth(782); 
        table.getColumnModel().getColumn(3).setPreferredWidth(55); 
    }
    
    
    /**
     * Llena la tabla de ingredientes con todos los ingredientes almacenados en la base de datos.
     * <p>Hace una petición a la base de datos para obtener todos los ingredientes almacenados y los 
     * muestra en la tabla para que puedan ser modificados o eliminados</p>
     */
    private void fillTableIngredients() {
        DefaultTableModel model = (DefaultTableModel) manageIngredient.getTableIngredient().getModel();
        model.setRowCount(0);
        
        List<Ingredient> listIngredients = ingredientService.getAllIngredients();
        if (!listIngredients.isEmpty()) {
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
    
    /**
     * Limpia los datos introducidos en los campos de la vista.
     */
    private void cleanCategory(){
        setTextJText(manageCategory.getCategoryName(), "");
    }

    /**
     * 
     * @param boxCategorys 
     */
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
        selectValidItemComboBox(boxCategorys);
    }
    
    /**
     * Llena el comboBox de la vista estadísticas con los nombres de los meses del año.
     */
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
    
    /**
     * 
     * @param listIngredientsProduct
     * @return 
     */
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

    /**
     * Limpia los campos de la vista Product.
     * <p>Establece a vacío o valores por defecto los campos visuales de la vista Product, 
     * también vacía las listas auxiliares  "listIdIngredientsInProduct" y "listIngredientsProduct"</p>
     */
    private void clearViewProduct(){
        manageProduct.getBoxCategory().setSelectedIndex(0);
        setTextJText(manageProduct.getEdtNameProduct(), "");
        setTextJText(manageProduct.getEdtPriceProduct(), "");
        setTextJLabel(manageProduct.getNameTxt(), "Nombre");
        setTextJLabel(manageProduct.getPriceTxt(), "Precio");
        setTextJLabel(manageProduct.getIngredientsTxt(), "Ingredientes");
        listIdIngredientsInProduct.clear();
        listIngredientsProduct.clear(); 
    }


    /**
     * Edita el contenido de un JTextField.
     * @param field  campo a modificar
     * @param text texto a colocar en el campo recibido
     */
    private void setTextJText(JTextField field, String text) {
        field.setText(text);
    }
    
    /**
     * Edita el contenido de un JLabel.
     * @param label campo a modificar
     * @param text texto a colocar en el campo recibido 
     */
    private void setTextJLabel(JLabel label, String text) {
        label.setText(text);
    }
    
    
    
    /**
     * Modifica el texto en el campo recibído por parámetro.
     * 
     * @param text 
     
    private void setNameProduct(String text) {
        manageProduct.getNameTxt().setText(text);
    }

    private void setPriceProduct(String text) {
        manageProduct.getPriceTxt().setText(text); 
    }
    */

    /**
     * Añade el ingrediente seleccionado de la lista de ingredientes del producto.
     * <p>Comprueba si se ha seleccionado un ingrediente y si está en el producto</p>
     * <b>Avisos:</b>
     * <p>Muestra un mensaje de aviso si no se ha seleccionado un ingrediente o si el ingrediente seleccionado no está incluido en el producto.</p>
     */
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

    /**
     * Elimina el ingrediente seleccionado de la lista de ingredientes incluida en el producto.
     * <p>
     * Este método obtiene el ingrediente seleccionado del `ComboBox` `BoxIngredients` y lo elimina
     * de la lista `listIngredientsProduct` si está presente. Si el ingrediente se elimina correctamente,
     * también se elimina su identificador de `listIdIngredientsInProduct`.
     * </p>
     * 
     * <b>Acciones principales:</b>
     * <ul>
     *   <li>Verifica que se haya seleccionado un ingrediente válido del `ComboBox`.</li>
     *   <li>Obtiene el nombre y el identificador del ingrediente del `ComboBox` y los elimina de las listas correspondientes.</li>
     *   <li>Si se elimina con éxito, actualiza la lista de ingredientes visualmente en la vista de producto usando el método {@link #updateIngredientsInProduct(java.util.HashSet) }.</li>
     *   <li>Si el ingrediente no está en la lista de ingredientes del producto, muestra un mensaje de advertencia.</li>
     * </ul>
     * <b>Avisos:</b>
     * <p>Muestra un mensaje de aviso si no se ha seleccionado un ingrediente o si el ingrediente seleccionado no está incluido en el producto.</p>
     * 
     * @see #updateIngredientsInProduct(java.util.HashSet)   Método utilizado para actualizar la lista visual de ingredientes en el producto.
     */
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
    
    /**
     * Actualiza la lista de ingredientes en el producto.
     * <p>Recibe la lista de ingredientes del producto, la separa por comas para mostrarla en el campo de texto 
     * y selecciona el valor por defecto en el comboBox.</p>
     * @param listIngredientProducts lista que se va a mostrar en el campo de texto
     */
    private void updateIngredientsInProduct(HashSet<String> listIngredientProducts) {
        manageProduct.getIngredientsTxt().setText(String.join(", ", listIngredientProducts));
        manageProduct.getBoxIngredients().setSelectedIndex(0);
    }
    
    /**
    * Elimina un ingrediente seleccionado de la tabla de ingredientes.
    * <p>
    * Este método obtiene la fila seleccionada de la tabla en la vista `manageIngredient`. 
    * Si no hay ninguna fila seleccionada, muestra un mensaje de advertencia al usuario.
    * En caso de que haya un ingrediente seleccionado, se obtiene el identificador de dicho ingrediente,
    * se busca en la base de datos y se elimina utilizando el servicio `ingredientService`.
    * Después de la eliminación, se actualiza la tabla de ingredientes y se limpia cualquier dato relacionado.
    * </p>
    * 
    * <b>Proceso:</b>
    * <ul>
    *   <li>Obtiene la fila seleccionada de la tabla de ingredientes.</li>
    *   <li>Verifica si hay un ingrediente seleccionado.</li>
    *   <li>Busca el ingrediente en la base de datos usando su ID.</li>
    *   <li>Elimina el ingrediente utilizando `ingredientService`.</li>
    *   <li>Muestra un mensaje de confirmación o error según el resultado.</li>
    *   <li>Actualiza la tabla de ingredientes y limpia datos asociados.</li>
    * </ul>
    * <b>Avisos:</b>
     * <ul>
     *   <li>Muestra un mensaje si no se ha seleccionado un ingrediente de la tabla</li>
     * </ul>
     * <b>Excepciones:</b>
     * <ul>
     *   <li>Muestra un mensaje si el producto se ha eliminado correctamente</li>
     *   <li>Muestra un mensaje si hay algún error y no se puede eliminar el producto seleccionado</li>
     * </ul>
    * 
    * <b>Excepciones:</b>
    * <ul>
    *   <li>En caso de que no se pueda eliminar el ingrediente, se captura la excepción y se muestra un mensaje de error.</li>
    * </ul>
    */
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

    /**
     * Crea un nuevo ingrediente o modifica uno existente si se ha seleccionado en la tabla.
     * <p>Comprueba que los datos introducidos sean correctos y crea o modifica un ingrediente, según si 
     * se ha seleccionado o no en la tabla.</p>
     * <b>Acciones:</b>
     * <ul>
     *   <li>Convierte el precio a BigDecimal si es posible.</li>
     *   <li>Comprueba si se ha seleccionado un ingrediente de la tabla</li>
     *   <li>Crea o modifica el ingrediente</li>
     *   <li>Limpia los campos de la vista</li>
     *   <li>Actualiza las tablas de ingredientes en la vista de ingredientes y el comboBox en la página principal</li>
     * </ul>
     * <b>Avisos:</b>
     * <ul>
     *   <li>Muestra un mensaje si el nombre del ingrediente está vacío</li>
     * </ul>
     * <b>Excepciones:</b>
     * <ul>
     *   <li>Muestra un mensaje si el precio introducido no está en el formato correcto</li>
     *   <li>Muestra un mensaje si hay algún error y no se puede eliminar el producto seleccionado</li>
     * </ul>
     * <b>Excepciones:</b>
     * <ul>
     *   <li>Muestra un mensaje si el producto se ha eliminado correctamente</li>
     * </ul>
     */
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

    /**
     * Limpia los campos de la vista Ingredient.
     * Establece a vacío los campos de texto de la vista Ingredient
     */
    private void cleanDataIngredient() {
        manageIngredient.getEdtNameIngredient().setText("");
        manageIngredient.getEdtPriceIngredient().setText("");  
    }

    /**
     * Coloca el texto recibido en el campo Categoría.
     * @param string texto que va a colocar en el campo 
     */
    private void setCategory(String string) {
        setTextJLabel(manageProduct.getCategoryTxt(), string);
    }
    
    /**
     * Elimina un producto de la base de datos.
     * <p>Comprueba que se haya seleccionado un producto en la tabla, lo elimina, limpia los campos y actualiza la tabla de productos</p>
     * <b>Avisos:</b>
     * <ul>
     *   <li>Muestra un mensaje si no se ha seleccionado un producto</li>
     * </ul>
     * <b>Excepciones:</b>
     * <ul>
     *   <li>Muestra un mensaje si el producto se ha eliminado correctamente</li>
     *   <li>Muestra un mensaje si hay algún error y no se puede eliminar el producto seleccionado</li>
     * </ul>
     * 
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

    /**
     * Elimina un cliente de la base de datos.
     * <p>Comprueba que se haya seleccionado un cliente en la tabla, lo elimina, limpia los campos y actualiza la tabla de clientes</p>
     * <b>Avisos:</b>
     * <ul>
     *   <li>Muestra un mensaje si no se ha seleccionado un cliente</li>
     * </ul>
     * <b>Excepciones:</b>
     * <ul>
     *   <li>Muestra un mensaje si el cliente se ha eliminado correctamente</li>
     *   <li>Muestra un mensaje si hay algún error y no se puede eliminar el cliente seleccionado</li>
     * </ul>
     * 
     */
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

    /**
     * Busca un cliente por su número de teléfono.
     * <p>Recibe el teléfono de un cliente y busca que exista en la base de datos.</p>
     * <b>Acciones:</b>
     * <ul>
     *   <li> Si no existe el cliente abre la vista ManageCustomer usando el método {@link #openManageCustomer(java.lang.String) } </li>
     *   <li> Si existe el cliente y tiene algún tipo de estado lo muestra con un mensaje </li>
     *   <li> Si existe el cliente y no tiene estado o se ha aceptado el mensaje se establecen los datos del cliente en los campos de texto </li>
     * </ul>
     * @param phone representa el teléfono del cliete. Se usa para buscar un cliente o para enviarlo a la vista para crear un cliente nuevo.  
     * @see #openManageCustomer(java.lang.String) Método utilizado para abrir la vista para gestionar los clientes.
     */
    private void lookForCustomer(String phone) {
        if(!phone.isEmpty()){
            Customer customer = customerService.findCustomerByPhone(phone);
            if (customer == null) {
                openManageCustomer(phone);
            } else {
                String status = customer.getStatus();
                if (status != null && !status.isEmpty()) {
                    JOptionPane.showMessageDialog(homepage, status);
                } 
                
                setTextJLabel(homepage.getTxtAddresCustomer(), customer.getAddress());
                setTextJLabel(homepage.getTxtNameCustomer(), customer.getName());
                setTextJLabel(homepage.getTxtOrderNameCustomer(), customer.getName());
                setTextJLabel(homepage.getTxtOrderAddresCustomer(), customer.getAddress());
                setTextJLabel(homepage.getTxtOrderPhoneCustomer(), customer.getPhone());
                
            }
        } 
        
    }
    
    /**
     * Busca un cliente por su teléfono.
     * <p>Comprueba que se ha escrito un teléfono y busca coincidencias en la base de datos, si lo encuentra coloca los datos en los campos
     * para su modificación o eliminación.</p>
     * <b>Avisos:</b>
     * <ul>
     *   <li>Muestra un mensaje si no se ha escrito un número de teléfono en el campo indicado</li>
     *   <li>Muestra un mensaje si no se encuentra un cliente con ese teléfono</li>
     * </ul>
     */
    private void lookForCustomer() {
        
        String phone = manageCustomer.getEdtPhoneCustomer().getText();
        if (phone.isEmpty()) {
            JOptionPane.showMessageDialog(manageCustomer, "Escriba un teléfono para buscar un cliente");
        } else {
            Customer customer = customerService.findCustomerByPhone(phone);
            if (customer == null) {
                JOptionPane.showMessageDialog(manageCustomer, "Cliente no encontrado");
            } else {
                setTextJText(manageCustomer.getEdtNameCustomer(), customer.getName());
                setTextJText(manageCustomer.getEdtAddresCustomer(), customer.getAddress()); 
                setTextJText(manageCustomer.getEdtStatusCustomer(), customer.getStatus());
            }
        }   
    }

    /**
     * Guarda o modifica un cliente.
     * <p>Comprueba si se ha seleccionado un cliente de la tabla
     * para su modificación o crea uno nuevo si no se ha seleccionado ninguno en la tabla de clientes.</p>
     * <b>Avisos:</b>
     * <ul>
     *   <li>Muestra un mensaje si se ha creado un cliente nuevo</li>
     *   <li>Muestra un mensaje si se ha modificado un cliente existente</li>
     *   <li>Muestra un mensaje si existe un cliente con el mismo número de teléfono y no crea el clente en ese caso</li>
     * </ul>
     */
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
        
    /**
     * Limpia los campos de la vista de Clientes.
     * <p>Establece a color gris los campos para mostrar el mensaje informativo en ellos</p>
     * <p>Si recibe un String que no esté vacío, significa que la llamada se ha realizado para crear un nuevo cliente y 
     * asegurarnos que no haya campos rellenos con datos antiguos y coloca el teléfono del nuevo cliente en su campo"</p>
     * @param phone 
     */
    private void clearViewCustomer(String phone) {
        manageCustomer.getEdtNameCustomer().setForeground(java.awt.Color.GRAY);
        manageCustomer.getEdtAddresCustomer().setForeground(java.awt.Color.GRAY);
        manageCustomer.getEdtStatusCustomer().setForeground(java.awt.Color.GRAY);
        manageCustomer.getEdtPhoneCustomer().setForeground(java.awt.Color.GRAY);
        
        setTextJText(manageCustomer.getEdtNameCustomer(), "Nombre");
        setTextJText(manageCustomer.getEdtAddresCustomer(), "Dirección");
        setTextJText(manageCustomer.getEdtStatusCustomer(), "Observaciones");
        
        
        if (phone.isEmpty()) {
            setTextJText(manageCustomer.getEdtPhoneCustomer(), "Teléfono");
        } else {
            setTextJText(manageCustomer.getEdtPhoneCustomer(), phone);
        }
        
        
    }

    /**
     * Busca todos los clientes existentes en la base de datos y llena la tabla con sus datos.
     */
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
    
    

    /**
     * Comprueba que los datos introducidos sean correctos y no sean los mensajes informativos.
     * 
     * @param name texto correspondiente al nombre
     * @param phone texto correspondiente al teléfono
     * @param addres texto correspondiente a la dirección
     * @return verdadero si los datos no son el texto informativo ni los campos están vacíos.
     */
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
    
    /**
     * Establece unas medidas fijas a la tabla indicada.
     * @param table tabla a la que se van a establecer estas medidas
     */
    private void columnWidthOrderTable(JTable table) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(1).setPreferredWidth(80); 
        table.getColumnModel().getColumn(2).setPreferredWidth(250);
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
        Company company = companyService.fingCompanyById(1l);
        if (company != null) {
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
                Integer amount = (Integer) model.getValueAt(i, 3);
                BigDecimal price = (BigDecimal) model.getValueAt(i, 4);
                
                OrderProduct orderProduct = new OrderProduct(null, order, product, amount, observation, price);
                orderProductService.saveOrderProduct(orderProduct);
            }

            printOrder(address, total, order);

            clearOrderData();
            
        } else {
            JOptionPane.showMessageDialog(homepage, "Introduzca los datos de la empresa");
        }
            
  
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
            float[] columnWidths = {1, 4, 1, 1}; // Ancho de las columnas
            Table table = new Table(UnitValue.createPercentArray(columnWidths));
            table.setWidth(UnitValue.createPercentValue(100));
            table.addHeaderCell(new Cell().add(new Paragraph("Producto").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Observaciones").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Cantidad").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Precio").setBold()));

            List<OrderProduct> listOrderProduct = orderProductService.getOrdersProductByOrder(order);
            
            
            for (OrderProduct product : listOrderProduct) {
                table.addCell(new Paragraph(product.getProduct().getName()));
                table.addCell(new Paragraph(product.getObservations()));
                table.addCell(new Paragraph(product.getAmount().toString()));
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

    /**
     * Guarda los datos de la empresa proporcionados. 
     * <p>Comprueba que todos los datos se han introducido y los guarda en la base de datos, si ya existen los actualiza</p>
     * <p>Actualiza el nombre de la compañia en la pantalla principal "homepage"</p>
     * <b>Aviso:</b>
     * <ul>
     *   <li>Muestra un mensaje si no se ha introducido algún dato de la empresa.</li>
     *   <li>Muestra un mensaje de confirmación al guardar los datos.</li>
     * </ul>
     */
    private void saveSettings() {
        Long id = 1l;
        String name = setting.getSettingsName().getText();
        String address = setting.getSettingsAddress().getText();
        String cif = setting.getSettingsCif().getText();
        String phone = setting.getSettingsPhone().getText();
        
        
        if (name.isEmpty() || address.isEmpty() || phone.isEmpty() || cif.isEmpty()) {
            JOptionPane.showMessageDialog(setting, "Introduzca todos los datos de la empresa y la conexión");
            return;
        }
        Company company = new Company(id, name, phone, address, cif);
        companyService.saveCompany(company);
        homepage.getTxtCompanyName().setText(company.getName());
        
        JOptionPane.showMessageDialog(setting, "Datos guardados correctamente");
        setting.dispose();  
    }
    

    /**
     * Recibe el nombre de un mes y muestra los datos estadísticos de ese mes.
     * <p>Busca todos los pedidos y los va filtrando para ir calculando las estadisticas</p>
     * <b>Acciones principales:</b>
     * <ul>
     *   <li>Filtra los pedido por año y muestra el Nº total de pedidos y el importe acumulado de todos ellos.</li>
     *   <li>Filtra los pedidos por año y mes, también muestra el total de pedidos en el mes, el importe acumulado y los clientes que realizaron pedidos .</li>
     *   <li>Almacena los pedidos por cliente para ver el cliente que más pedidos ha realizado en un mes concreto.</li>
     *   <li>Muestra los datos obtenidos en los resultados</li>
     * </ul>
     * @param month el mes para el cual se van a calcular las estadísticas.
     */
    private void seeStatistics(String month) {
        List<Order> orders = orderService.findAllOrders();
        
        LocalDate date = LocalDate.now();
        int year = date.getYear();
        
        int totalOrdersForYear = 0;
        int totalOrdersForMonth = 0;
        
        BigDecimal totalIncomeForYear = BigDecimal.ZERO;
        BigDecimal totalIncomeForMonth = BigDecimal.ZERO;
        // Almacena clientes únicos para contar cuántos clientes diferentes hay en el mes
        Set<Customer> uniqueCustomers = new HashSet<>(); 
        
        // Map para almacenar el número de pedidos por cliente
        Map<Customer, Integer> ordersByCustomer = new HashMap<>();
        
        // Obtener el número del mes a partir del nombre del mes
        int monthNumber = getMonthNumber(month);
        
        for (Order order : orders) {
            if(year == order.getDate().getYear()) {
                totalOrdersForYear++;
                totalIncomeForYear = totalIncomeForYear.add(order.getTotalPrice());
            }
            
            if (year == order.getDate().getYear() && monthNumber == order.getDate().getMonthValue()) {
                totalOrdersForMonth++;
                totalIncomeForMonth = totalIncomeForMonth.add(order.getTotalPrice());
                
                // Agregar el cliente al set para contar clientes únicos
                uniqueCustomers.add(order.getCustomer());
                
                // Actualizar el número de pedidos por cliente
                ordersByCustomer.put(order.getCustomer(), ordersByCustomer.getOrDefault(order.getCustomer(), 0) + 1);
            }  
        }
        
        // Determinar el cliente con más pedidos
        Customer topCustomer = null;
        int maxOrders = 0;
        for (Map.Entry<Customer, Integer> entry : ordersByCustomer.entrySet()) {
            if (entry.getValue() > maxOrders) {
                maxOrders = entry.getValue();
                topCustomer = entry.getKey();
            }
        }
        
        int totalCustomersForMonth = uniqueCustomers.size();
        
        statistics.getTotalOrdersForMonth().setText(String.valueOf(totalOrdersForMonth));
        statistics.getTotalCustomersForMonth().setText(String.valueOf(totalCustomersForMonth));
        statistics.getTotalIncomeForMonth().setText(totalIncomeForMonth.toString());
        statistics.getTotalOrdersForYear().setText(String.valueOf(totalOrdersForYear));
        statistics.getTotalIncomeForYear().setText(totalIncomeForYear.toString());
        if (topCustomer != null) {
            String bestCustomer = String.format("%s, tlf. %s (%d pedidos).", topCustomer.getName(), topCustomer.getPhone(), maxOrders);
            
            statistics.getBestCustomer().setText(bestCustomer);
        } else {
            statistics.getBestCustomer().setText("No hay pedidos en el mes seleccionado");
        }
    }
    
    /**
     * Método para convertir el nombre del mes a su número correspondiente en el calendario.
     * @param month nombre del mes a convertir 
     * @return El número que ocupa el mes recibido en el calendario 
     */
    private int getMonthNumber(String month) {
        List<String> months = List.of(
            "ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", 
            "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"
        );

        return months.indexOf(month.toUpperCase()) + 1;
    }
    
    /**
     * Método para limitar la entrada a sólo 9 números en el campo de texto recibido como parámetro.
     * 
     * @param edtPhone campo sobre el que se realizará la limitación 
     */
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
    
    /**
     * Método para activar el efecto hover en el botón recibido como parámetro con el color indicado.
     * Cuando el ratón entra en el campo de acción del botón cambia las letras de color y cuando sale las vuelve a su color por defecto
     * @param button Botón sobre el que se va a activar el efecto 
     * @param color Color al que se va a cambiar las letras
     */
    private void activateHoverEffect(JButton button, java.awt.Color color) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(color);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(java.awt.Color.WHITE);
            }
            
        });
    }
    
    /**
     * Método para activar el efecto Foco en el editText recibido como parámetro.
     *<p> Recibe un campo de texto y un texto cuando el campo recibe el foco lo comprueba con {@link #checkFieldFocusGained(javax.swing.JTextField, java.lang.String) }</p>
     * <p> Cuando el campo pierde el foco lo comprueba con {@link #checkFieldFocusLost(javax.swing.JTextField, java.lang.String) }
     * 
     * @param field campo editText sobre el que realizar las acciones
     * @param text texto informativo para comprobar 
     * 
     * @see #checkFieldFocusGained(javax.swing.JTextField, java.lang.String) 
     * @see #checkFieldFocusLost(javax.swing.JTextField, java.lang.String) 
     */
    private void activateFocusEfect(JTextField field, String text) {
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                String texto = text;
                checkFieldFocusGained(field, texto);
            }

            @Override
            public void focusLost(FocusEvent e) {
                String texto = text;
                checkFieldFocusLost(field, texto);
            }
        });
        
    }
    
    
    /**
     * Método controlador para centralizar la carga de todos los datos de demostración.
     * <b>Aviso:</b>
     * <p>Si ya hay categorías cargadas muestra un mensaje y no carga los datos</p>
     */
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
    
    /**
     * Método para almacenar las categorías demo
     */
    private void loadCategorys() {
        String[] categorys = {"Pizzas", "Platos preparados", "Tortillas", "Bocadillos", "Aperitivos", "Bebidas"};
        for (String name : categorys) {
            Category category = new Category(null, name);
            categoryService.addCategory(category);
        }
    }
    
    /**
     * Método para almacenar ingredientes con precios aleatorios de una lista proporcionada.
     */
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
    
    /**
     * Método para generar, en base a una lista de nombres proporcionada, productos con ingredientes y precios aleatorios.
     */
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
    
    /**
     * Método para guardar algúnos productos de una categoría conreta de ejemplo.
     */
    private void loadDrinks() {
        //Ingredient i = ingredientService.findIngredientById(Long.MIN_VALUE) plato preparado 41, aperitivo 42, salchicha 43, queso 44, bebida 39
        Ingredient ingredient = ingredientService.findIngredientById(38l);
        Category category = categoryService.findCategoryByName("Bebidas");
        BigDecimal price = BigDecimal.valueOf(1.20);
        String names[] = {"Coca-cola", "Fanta Naranja", "Fanta Limón", "Acuarius", "Cerveza", "Agua"};
        
        saveProduct(names, category, price, ingredient);
        
    }
    
    /**
     * Método para guardar algúnos productos de una categoría conreta de ejemplo.
     */
    private void loadSnaks() {
        Ingredient ingredient = ingredientService.findIngredientById(41l);
        Category category = categoryService.findCategoryByName("Aperitivos");
        BigDecimal price = BigDecimal.valueOf(1.50);
        String[] names = {"olivas rellenas de anchoa", "Pepinillos", "Olivas violadas", "Banderillas picantes"};   
        
        saveProduct(names, category, price, ingredient);
    }
    
    /**
     * Método para guardar algúnos productos de una categoría conreta de ejemplo.
     */
    private void loadTortillas() {
        Ingredient ingredient = ingredientService.findIngredientById(44l);
        Category category = categoryService.findCategoryByName("Tortillas");
        BigDecimal price = BigDecimal.valueOf(4.00);
        String[] names = {"Francesa", "Jamón", "Gambas"};   
        
        saveProduct(names, category, price, ingredient);
    }
    
    /**
     * Método para guardar algúnos productos de una categoría conreta de ejemplo.
     */
    private void loadPreparedDishes() {
        //Ingredient i = ingredientService.findIngredientById(Long.MIN_VALUE) plato preparado 41, aperitivo 42, salchicha 43, queso 44, bebida 39
        Ingredient ingredient = ingredientService.findIngredientById(40l);
        Category category = categoryService.findCategoryByName("Platos preparados");
        BigDecimal price = BigDecimal.valueOf(5.00);
        String names[] = {"Lasaña", "Macarrones", "Callos"};
        
        saveProduct(names, category, price, ingredient);
    }
    
    /**
     * Método para guardar algúnos productos de una categoría conreta de ejemplo.
     */
    private void loadSandwichs() {
        Ingredient ingredient = ingredientService.findIngredientById(42l);
        Category category = categoryService.findCategoryByName("Bocadillos");
        BigDecimal price = BigDecimal.valueOf(4.00);
        String names[] = {"Lomo", "Hamburguesa", "Hamburguesa completa", "Perrito", "Bacon", "Sandwich"};
        
        saveProduct(names, category, price, ingredient);
        
    }
    
 
    /**
     * Método para guardar los productos generados con unas características fijas
     * @param names lista de nombres de los productos a guardar
     * @param category Categoría en la que se van a incluir los productos
     * @param price Precio fijo para todos los productos igual
     * @param ingredient  Ingrediente fijo para todos los productos igual
     */
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
    

    /**
     * Genera 1000 clientes de manera aleatoria mezclando los datos de las listas proporcionadas.
     * He introducido listas de nombres, apellidos, calles y pisos para generar de manera aleatorio y ficticia clientes y almacenarlos en 
     * la base de datos para realizar pruebas.
     * @see #generatePhone() para cada cliente se genera un número de teléfono único
     */
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
    /**
     * Genera números aleatorios simulando teléfonos.
     * 
     * @return String con el valor aleatorio obtenido 
     */
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

    private void closeApp() {
        System.exit(0);
        
    }



    

    

  
 
}
