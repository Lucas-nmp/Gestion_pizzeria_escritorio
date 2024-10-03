package lm.Gestion_pedidos.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Lucas
 */

public class ManageCategory extends javax.swing.JDialog {

    /**
     * Creates new form AddCategory
     */
    
    public ManageCategory() {
        
        initComponents();
    }
    
    public JButton getCategorySave() {
        return CategorySave;
    }
    
    public JButton getDeleteCategory() {
        return categoryDelete;       
    }
    
    public JTable getCategoryTable() {
        return categoryTable;
    }
    
    public JTextField getCategoryName() {
        return categoryName;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        categoryName = new javax.swing.JTextField();
        CategorySave = new javax.swing.JButton();
        categoryDelete = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        categoryTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(26, 44, 68));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        categoryName.setBackground(new java.awt.Color(26, 44, 68));
        categoryName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        categoryName.setForeground(new java.awt.Color(255, 255, 255));
        categoryName.setBorder(null);
        jPanel1.add(categoryName, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 210, -1));

        CategorySave.setBackground(new java.awt.Color(26, 44, 68));
        CategorySave.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        CategorySave.setForeground(new java.awt.Color(255, 255, 255));
        CategorySave.setText("Guardar");
        CategorySave.setBorder(null);
        jPanel1.add(CategorySave, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 40, -1, -1));

        categoryDelete.setBackground(new java.awt.Color(26, 44, 68));
        categoryDelete.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        categoryDelete.setForeground(new java.awt.Color(255, 255, 255));
        categoryDelete.setText("Eliminar Categoría");
        categoryDelete.setBorder(null);
        jPanel1.add(categoryDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 40, -1, -1));

        jSeparator1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 210, 10));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Nombre de la categoría");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        jPanel3.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 110));

        categoryTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(categoryTable);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 670, 200));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CategorySave;
    private javax.swing.JButton categoryDelete;
    private javax.swing.JTextField categoryName;
    private javax.swing.JTable categoryTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
