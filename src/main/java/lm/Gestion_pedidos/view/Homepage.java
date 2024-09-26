package lm.Gestion_pedidos.view;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



public class Homepage extends javax.swing.JFrame {

    /**
     * Creates new form Homepage
     */
    
    public Homepage() {
        initComponents();
    
    }
    
    public JButton getBtnAdd() {
        return homepageBtnAdd;
    }
    
    public JButton getBtnAdd2() {
        return homepageBtnAdd2;
    }
    
    
    public JButton getBtnCustomer() {
        return btnCustomer;
    }
    
    public JButton getBtnDemo() {
        return btnDemo;
    }
    
    public JButton getBtnIngredient() {
        return btnIngredient;
    }
    
    public JButton getBtnExit() {
        return btnExit;
    }
    
    public JButton getBtnCategory() {
        return btnCategory;
    }
    
    public JButton getBtnSetting() {
        return btnSettings;
    }
    
    public JButton getBtnStatistics() {
        return btnStatistics;
    }
    
    public JButton getBtnProduct() {
        return btnProduct;
    }
    
    public JTextField getEdtPhoneCustomer() {
        return customerPhone;
    }
    
    public JTextField getEdtAlternativeAddres() {
        return customerAlternativeAddres;
    }
    
    public JLabel getTxtAddresCustomer() {
        return customerAddres;
    }
    
    public JLabel getTxtNameCustomer() {
        return customerName;
    }
    
    public JLabel getTxtOrderNameCustomer() {
        return orderName;
    }
    
    public JLabel getTxtOrderPhoneCustomer() {
        return orderPhone;
    }
    
    public JLabel getTxtOrderAddresCustomer() {
        return orderAddres;
    }
    
    public JLabel getTxtTotalPrice() {
        return totalPrice;
    }
    
    public JLabel getTxtCompanyName() {
        return companyName;
    }
    
    public JButton getBtnRemoveIngredient() {
        return ingredientRemove;
    }
    
    public JButton getBtnAddIngredient() {
        return ingredientAdd;         
    }
    
    public JButton getBtnConfirmOrder() {
        return btnConfirmOrder;
    }
    
    public JButton getBtnCancelOrder() {
        return btnCancelOrder;
    }
    
    public JButton getBtnRemoveFromOrder() {
        return BtnRemoveFromOrder;
    }
    
    public JComboBox getCategorys() {
        return Category;
    }
    
    public JComboBox getAmount() {
        return boxAmount;
    }
    
    public JComboBox getIngredientModify() {
        return boxIngredientModify;
    }
    
    public JTable getTableProducts() {
        return ProductTable;
    }
    
    public JTable getTableOrder() {
        return orderTable; 
    }
    
    public JCheckBox getCheckDoubleCheese() {
        return checkDoubleCheese;
    }
    
    public JCheckBox getCheckWithoutCheese() {
        return checkWithoutCheese;
    }
    
    public JCheckBox getCheckUndercooked() {
        return checkUndercooked;
    }
    
    public JCheckBox getCheckVeryCooked() {
        return checkVeryCooked;
    }
    
    public JTextArea getEdtObservations() {
        return edtObservations;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        companyName = new javax.swing.JLabel();
        btnCategory = new javax.swing.JButton();
        btnProduct = new javax.swing.JButton();
        btnIngredient = new javax.swing.JButton();
        btnCustomer = new javax.swing.JButton();
        btnStatistics = new javax.swing.JButton();
        btnSettings = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        btnDemo = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        customerPhone = new javax.swing.JTextField();
        customerAddres = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        customerName = new javax.swing.JLabel();
        customerAlternativeAddres = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel5 = new javax.swing.JPanel();
        Category = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        ProductTable = new javax.swing.JTable();
        boxIngredientModify = new javax.swing.JComboBox<>();
        checkDoubleCheese = new javax.swing.JCheckBox();
        checkUndercooked = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        edtObservations = new javax.swing.JTextArea();
        checkVeryCooked = new javax.swing.JCheckBox();
        checkWithoutCheese = new javax.swing.JCheckBox();
        ingredientRemove = new javax.swing.JButton();
        ingredientAdd = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        orderTable = new javax.swing.JTable();
        orderName = new javax.swing.JLabel();
        orderPhone = new javax.swing.JLabel();
        orderAddres = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        totalPrice = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        homepageBtnAdd2 = new javax.swing.JButton();
        homepageBtnAdd = new javax.swing.JButton();
        BtnRemoveFromOrder = new javax.swing.JButton();
        btnCancelOrder = new javax.swing.JButton();
        btnConfirmOrder = new javax.swing.JButton();
        boxAmount = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(26, 44, 68));

        companyName.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        companyName.setForeground(new java.awt.Color(255, 255, 255));
        companyName.setText("Nombre de la empresa");

        btnCategory.setBackground(new java.awt.Color(26, 44, 68));
        btnCategory.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnCategory.setForeground(new java.awt.Color(255, 255, 255));
        btnCategory.setText("Categorías");
        btnCategory.setBorder(null);

        btnProduct.setBackground(new java.awt.Color(26, 44, 68));
        btnProduct.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnProduct.setForeground(new java.awt.Color(255, 255, 255));
        btnProduct.setText("Productos");
        btnProduct.setBorder(null);

        btnIngredient.setBackground(new java.awt.Color(26, 44, 68));
        btnIngredient.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnIngredient.setForeground(new java.awt.Color(255, 255, 255));
        btnIngredient.setText("Ingredientes");
        btnIngredient.setBorder(null);

        btnCustomer.setBackground(new java.awt.Color(26, 44, 68));
        btnCustomer.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnCustomer.setForeground(new java.awt.Color(255, 255, 255));
        btnCustomer.setText("Clientes");
        btnCustomer.setBorder(null);

        btnStatistics.setBackground(new java.awt.Color(26, 44, 68));
        btnStatistics.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnStatistics.setForeground(new java.awt.Color(255, 255, 255));
        btnStatistics.setText("Estadísticas");
        btnStatistics.setBorder(null);

        btnSettings.setBackground(new java.awt.Color(26, 44, 68));
        btnSettings.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnSettings.setForeground(new java.awt.Color(255, 255, 255));
        btnSettings.setText("Configuración");
        btnSettings.setBorder(null);

        btnExit.setBackground(new java.awt.Color(26, 44, 68));
        btnExit.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnExit.setForeground(new java.awt.Color(204, 0, 0));
        btnExit.setText("Salir");
        btnExit.setBorder(null);

        btnDemo.setBackground(new java.awt.Color(26, 44, 68));
        btnDemo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnDemo.setForeground(new java.awt.Color(255, 255, 255));
        btnDemo.setText("Cargar datos demo");
        btnDemo.setBorder(null);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(companyName)
                .addGap(90, 90, 90)
                .addComponent(btnDemo)
                .addGap(53, 53, 53)
                .addComponent(btnCustomer)
                .addGap(18, 18, 18)
                .addComponent(btnIngredient)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(btnProduct)
                .addGap(18, 18, 18)
                .addComponent(btnCategory)
                .addGap(18, 18, 18)
                .addComponent(btnStatistics)
                .addGap(18, 18, 18)
                .addComponent(btnSettings)
                .addGap(18, 18, 18)
                .addComponent(btnExit)
                .addGap(30, 30, 30))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(companyName)
                    .addComponent(btnCustomer)
                    .addComponent(btnStatistics)
                    .addComponent(btnCategory)
                    .addComponent(btnProduct)
                    .addComponent(btnIngredient)
                    .addComponent(btnSettings)
                    .addComponent(btnExit)
                    .addComponent(btnDemo))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1400, 100));

        jPanel4.setBackground(new java.awt.Color(71, 120, 190));

        customerPhone.setBackground(new java.awt.Color(71, 120, 190));
        customerPhone.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        customerPhone.setForeground(new java.awt.Color(204, 204, 204));
        customerPhone.setText("Teléfono");
        customerPhone.setBorder(null);

        customerAddres.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        customerAddres.setForeground(new java.awt.Color(255, 255, 255));
        customerAddres.setText("Dirección ");

        customerName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        customerName.setForeground(new java.awt.Color(255, 255, 255));
        customerName.setText("Nombre");

        customerAlternativeAddres.setBackground(new java.awt.Color(71, 120, 190));
        customerAlternativeAddres.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        customerAlternativeAddres.setForeground(new java.awt.Color(204, 204, 204));
        customerAlternativeAddres.setText("Dirección alternativa");
        customerAlternativeAddres.setBorder(null);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(customerPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(customerAddres, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73)
                        .addComponent(customerName, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(customerAlternativeAddres, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                    .addComponent(jSeparator2))
                .addGap(35, 35, 35))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customerPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customerAddres)
                    .addComponent(customerName)
                    .addComponent(customerAlternativeAddres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 1400, 110));

        jPanel5.setBackground(new java.awt.Color(120, 168, 252));

        Category.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        Category.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        ProductTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ProductTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(ProductTable);

        boxIngredientModify.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        boxIngredientModify.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        checkDoubleCheese.setBackground(new java.awt.Color(120, 168, 252));
        checkDoubleCheese.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        checkDoubleCheese.setForeground(new java.awt.Color(255, 255, 255));
        checkDoubleCheese.setText("Doble de queso");
        checkDoubleCheese.setBorder(null);

        checkUndercooked.setBackground(new java.awt.Color(120, 168, 252));
        checkUndercooked.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        checkUndercooked.setForeground(new java.awt.Color(255, 255, 255));
        checkUndercooked.setText("Poco hecha");
        checkUndercooked.setBorder(null);

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Observaciones");

        edtObservations.setColumns(20);
        edtObservations.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        edtObservations.setRows(5);
        jScrollPane2.setViewportView(edtObservations);

        checkVeryCooked.setBackground(new java.awt.Color(120, 168, 252));
        checkVeryCooked.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        checkVeryCooked.setForeground(new java.awt.Color(255, 255, 255));
        checkVeryCooked.setText("Muy hecha");
        checkVeryCooked.setBorder(null);

        checkWithoutCheese.setBackground(new java.awt.Color(120, 168, 252));
        checkWithoutCheese.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        checkWithoutCheese.setForeground(new java.awt.Color(255, 255, 255));
        checkWithoutCheese.setText("Sin queso");
        checkWithoutCheese.setBorder(null);

        ingredientRemove.setBackground(new java.awt.Color(120, 168, 252));
        ingredientRemove.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ingredientRemove.setForeground(new java.awt.Color(255, 0, 0));
        ingredientRemove.setText("Quitar ");
        ingredientRemove.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        ingredientAdd.setBackground(new java.awt.Color(120, 168, 252));
        ingredientAdd.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ingredientAdd.setForeground(new java.awt.Color(0, 0, 102));
        ingredientAdd.setText("Añadir");
        ingredientAdd.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        orderTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        orderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(orderTable);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 440, 210));

        orderName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        orderName.setText("Nombre");
        jPanel2.add(orderName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        orderPhone.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        orderPhone.setText("Teléfono");
        jPanel2.add(orderPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 30, -1, -1));

        orderAddres.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        orderAddres.setText("Dirección");
        jPanel2.add(orderAddres, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Total:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 340, -1, -1));

        totalPrice.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        totalPrice.setText("0,0");
        jPanel2.add(totalPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 340, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Categoría");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Añadir / Quitar ingredientes");

        homepageBtnAdd2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        homepageBtnAdd2.setText("AÑADIR AL PEDIDO");
        homepageBtnAdd2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        homepageBtnAdd.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        homepageBtnAdd.setText("AÑADIR AL PEDIDO");
        homepageBtnAdd.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        BtnRemoveFromOrder.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        BtnRemoveFromOrder.setText("Quitar del pedido");

        btnCancelOrder.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCancelOrder.setText("Cancelar pedido");

        btnConfirmOrder.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnConfirmOrder.setText("Confirmar pedido");

        boxAmount.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        boxAmount.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Unidades");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(Category, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(homepageBtnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(boxIngredientModify, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(boxAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(ingredientRemove, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                                            .addComponent(ingredientAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(24, 24, 24))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(checkDoubleCheese)
                                            .addComponent(checkUndercooked))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(checkWithoutCheese)
                                            .addComponent(checkVeryCooked))
                                        .addGap(28, 28, 28))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel7)
                                            .addComponent(homepageBtnAdd2, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(27, 27, 27)))
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(332, 332, 332)))
                        .addContainerGap(19, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(272, 272, 272)
                        .addComponent(BtnRemoveFromOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelOrder)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnConfirmOrder)
                        .addGap(19, 19, 19))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(homepageBtnAdd)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(Category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(BtnRemoveFromOrder)
                                        .addComponent(btnCancelOrder)
                                        .addComponent(btnConfirmOrder))
                                    .addComponent(homepageBtnAdd2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(boxIngredientModify, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ingredientAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ingredientRemove)
                            .addComponent(boxAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(checkDoubleCheese)
                            .addComponent(checkWithoutCheese))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(checkUndercooked)
                            .addComponent(checkVeryCooked))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(118, 118, 118))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 1400, 490));

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 731, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnRemoveFromOrder;
    private javax.swing.JComboBox<String> Category;
    private javax.swing.JTable ProductTable;
    private javax.swing.JComboBox<String> boxAmount;
    private javax.swing.JComboBox<String> boxIngredientModify;
    private javax.swing.JButton btnCancelOrder;
    private javax.swing.JButton btnCategory;
    private javax.swing.JButton btnConfirmOrder;
    private javax.swing.JButton btnCustomer;
    private javax.swing.JButton btnDemo;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnIngredient;
    private javax.swing.JButton btnProduct;
    private javax.swing.JButton btnSettings;
    private javax.swing.JButton btnStatistics;
    private javax.swing.JCheckBox checkDoubleCheese;
    private javax.swing.JCheckBox checkUndercooked;
    private javax.swing.JCheckBox checkVeryCooked;
    private javax.swing.JCheckBox checkWithoutCheese;
    private javax.swing.JLabel companyName;
    private javax.swing.JLabel customerAddres;
    private javax.swing.JTextField customerAlternativeAddres;
    private javax.swing.JLabel customerName;
    private javax.swing.JTextField customerPhone;
    private javax.swing.JTextArea edtObservations;
    private javax.swing.JButton homepageBtnAdd;
    private javax.swing.JButton homepageBtnAdd2;
    private javax.swing.JButton ingredientAdd;
    private javax.swing.JButton ingredientRemove;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel orderAddres;
    private javax.swing.JLabel orderName;
    private javax.swing.JLabel orderPhone;
    private javax.swing.JTable orderTable;
    private javax.swing.JLabel totalPrice;
    // End of variables declaration//GEN-END:variables
}
