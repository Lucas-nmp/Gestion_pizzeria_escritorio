package lm.Gestion_pedidos.view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Lucas
 */
@Component
public class ManageProduct extends javax.swing.JDialog {

    /**
     * Creates new form ManageProduct
     */
    @Autowired
    public ManageProduct() { 
        initComponents();
    }
    
    public JButton getBtnAddIngredient() {
        return manageProductAddIngredient;
    }
    
    public JButton getBtnSaveProduct() {
        return manageProductSaveProduct;
    }
    
    public JButton getBtnDeleteIngredient() {
        return manageProductDeleteIngredient;
    }
    
    public JTable getTableProducts() {
        return manageProductTable;
    }
    
    public JTextField getEdtNameProduct() {
        return manageProductEdtName;
    }
    
    public JTextField getEdtPriceProduct() {
        return manageProductEdtPrice;
    }
    
    public JComboBox getBoxCategory() {
        return manageProductCategorysBox;
    }
    
    public JComboBox getBoxIngredients() {
        return manageProductIngredientsBox;
    }
    
    public JLabel getCategoryTxt() {
        return manageProductCategory;
    }
    
    public JLabel getNameTxt() {
        return manageProductName;
    }
    
    public JLabel getIngredientsTxt() {
        return manageProductIngredients;
    }
    
    public JLabel getPriceTxt() {
        return manageProductPrice;
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
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        manageProductCategorysBox = new javax.swing.JComboBox<>();
        manageProductEdtName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        manageProductEdtPrice = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        manageProductIngredientsBox = new javax.swing.JComboBox<>();
        manageProductAddIngredient = new javax.swing.JButton();
        manageProductDeleteIngredient = new javax.swing.JButton();
        manageProductCategory = new javax.swing.JLabel();
        manageProductName = new javax.swing.JLabel();
        manageProductPrice = new javax.swing.JLabel();
        manageProductIngredients = new javax.swing.JLabel();
        manageProductSaveProduct = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        manageProductTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(26, 44, 68));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Categoría");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nombre");

        manageProductCategorysBox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        manageProductCategorysBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        manageProductEdtName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Precio");

        manageProductEdtPrice.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Ingredietes");

        manageProductIngredientsBox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        manageProductIngredientsBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        manageProductAddIngredient.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        manageProductAddIngredient.setText("Añadir ingrediente");

        manageProductDeleteIngredient.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        manageProductDeleteIngredient.setText("Quitar ingrediente");

        manageProductCategory.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        manageProductCategory.setForeground(new java.awt.Color(255, 255, 255));
        manageProductCategory.setText("jLabel5");

        manageProductName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        manageProductName.setForeground(new java.awt.Color(255, 255, 255));
        manageProductName.setText("jLabel6");

        manageProductPrice.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        manageProductPrice.setForeground(new java.awt.Color(255, 255, 255));
        manageProductPrice.setText("jLabel7");

        manageProductIngredients.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        manageProductIngredients.setForeground(new java.awt.Color(255, 255, 255));
        manageProductIngredients.setText("jLabel8");

        manageProductSaveProduct.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        manageProductSaveProduct.setText("GuardarProducto");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(manageProductSaveProduct)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(manageProductCategory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1)
                            .addComponent(manageProductCategorysBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(54, 54, 54)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(manageProductEdtName, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                            .addComponent(manageProductName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3)
                            .addComponent(manageProductEdtPrice)
                            .addComponent(manageProductPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(65, 65, 65)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(manageProductIngredientsBox, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(manageProductAddIngredient)
                                .addGap(18, 18, 18)
                                .addComponent(manageProductDeleteIngredient))
                            .addComponent(manageProductIngredients, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(manageProductCategorysBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(manageProductEdtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(manageProductEdtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(manageProductIngredientsBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(manageProductAddIngredient)
                    .addComponent(manageProductDeleteIngredient))
                .addGap(35, 35, 35)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(manageProductCategory)
                    .addComponent(manageProductName)
                    .addComponent(manageProductPrice)
                    .addComponent(manageProductIngredients))
                .addGap(18, 18, 18)
                .addComponent(manageProductSaveProduct)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 970, 220));

        manageProductTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(manageProductTable);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 964, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 970, 430));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 970, 630));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton manageProductAddIngredient;
    private javax.swing.JLabel manageProductCategory;
    private javax.swing.JComboBox<String> manageProductCategorysBox;
    private javax.swing.JButton manageProductDeleteIngredient;
    private javax.swing.JTextField manageProductEdtName;
    private javax.swing.JTextField manageProductEdtPrice;
    private javax.swing.JLabel manageProductIngredients;
    private javax.swing.JComboBox<String> manageProductIngredientsBox;
    private javax.swing.JLabel manageProductName;
    private javax.swing.JLabel manageProductPrice;
    private javax.swing.JButton manageProductSaveProduct;
    private javax.swing.JTable manageProductTable;
    // End of variables declaration//GEN-END:variables
}
