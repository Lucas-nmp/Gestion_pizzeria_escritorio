package lm.Gestion_pedidos.view;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;


/**
 *
 * @author Lucas
 */

public class ManageCustomer extends javax.swing.JDialog {

    /**
     * Creates new form ManageCustomer
     */
    
    public ManageCustomer() {
        initComponents();
    }
    
    public JButton getBtnSaveCustomer() {
        return manageCustomerSaveCustomer;
    }
    
    public JButton getBtnDeleteCustomer() {
        return manageCustomerDeleteCustomer;
    }
    
    public JButton getBtnLookForCustomer() {
        return manageCustomerLookForCustomer;
    }
    
    public JTextField getEdtNameCustomer() {
        return manageCustomerName;
    }
    
    public JTextField getEdtPhoneCustomer() {
        return manageCustomerPhone;
    }
    
    public JTextField getEdtAddresCustomer() {
        return manageCustomerAddres;
    }
    
    public JTextField getEdtStatusCustomer() {
        return manageCustomerStatus;
    }
    
    public JTable getTableCustomer() {
        return manageCustomerTable;
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
        jPanel2 = new javax.swing.JPanel();
        manageCustomerName = new javax.swing.JTextField();
        manageCustomerAddres = new javax.swing.JTextField();
        manageCustomerPhone = new javax.swing.JTextField();
        manageCustomerStatus = new javax.swing.JTextField();
        manageCustomerLookForCustomer = new javax.swing.JButton();
        manageCustomerSaveCustomer = new javax.swing.JButton();
        manageCustomerDeleteCustomer = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        manageCustomerTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(26, 44, 68));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        manageCustomerName.setBackground(new java.awt.Color(26, 44, 68));
        manageCustomerName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        manageCustomerName.setForeground(new java.awt.Color(255, 255, 255));
        manageCustomerName.setBorder(null);

        manageCustomerAddres.setBackground(new java.awt.Color(26, 44, 68));
        manageCustomerAddres.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        manageCustomerAddres.setForeground(new java.awt.Color(255, 255, 255));
        manageCustomerAddres.setBorder(null);

        manageCustomerPhone.setBackground(new java.awt.Color(26, 44, 68));
        manageCustomerPhone.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        manageCustomerPhone.setForeground(new java.awt.Color(255, 255, 255));
        manageCustomerPhone.setBorder(null);

        manageCustomerStatus.setBackground(new java.awt.Color(26, 44, 68));
        manageCustomerStatus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        manageCustomerStatus.setForeground(new java.awt.Color(255, 255, 255));
        manageCustomerStatus.setBorder(null);

        manageCustomerLookForCustomer.setBackground(new java.awt.Color(26, 44, 68));
        manageCustomerLookForCustomer.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        manageCustomerLookForCustomer.setForeground(new java.awt.Color(255, 255, 255));
        manageCustomerLookForCustomer.setText("Buscar");
        manageCustomerLookForCustomer.setBorder(null);

        manageCustomerSaveCustomer.setBackground(new java.awt.Color(26, 44, 68));
        manageCustomerSaveCustomer.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        manageCustomerSaveCustomer.setForeground(new java.awt.Color(255, 255, 255));
        manageCustomerSaveCustomer.setText("Guardar");
        manageCustomerSaveCustomer.setBorder(null);

        manageCustomerDeleteCustomer.setBackground(new java.awt.Color(26, 44, 68));
        manageCustomerDeleteCustomer.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        manageCustomerDeleteCustomer.setForeground(new java.awt.Color(255, 255, 255));
        manageCustomerDeleteCustomer.setText("Eliminar");
        manageCustomerDeleteCustomer.setBorder(null);

        jSeparator1.setBackground(new java.awt.Color(255, 255, 255));

        jSeparator2.setBackground(new java.awt.Color(255, 255, 255));

        jSeparator3.setBackground(new java.awt.Color(255, 255, 255));

        jSeparator4.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 569, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(manageCustomerLookForCustomer)
                            .addGap(50, 50, 50)
                            .addComponent(manageCustomerSaveCustomer)
                            .addGap(53, 53, 53)
                            .addComponent(manageCustomerDeleteCustomer))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(manageCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(manageCustomerPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(manageCustomerStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 572, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(manageCustomerAddres, javax.swing.GroupLayout.PREFERRED_SIZE, 569, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 572, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(manageCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(manageCustomerAddres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE)
                    .addComponent(jSeparator2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(manageCustomerPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(manageCustomerStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(manageCustomerLookForCustomer)
                    .addComponent(manageCustomerSaveCustomer)
                    .addComponent(manageCustomerDeleteCustomer))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 180));

        manageCustomerTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(manageCustomerTable);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 940, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 940, 290));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTextField manageCustomerAddres;
    private javax.swing.JButton manageCustomerDeleteCustomer;
    private javax.swing.JButton manageCustomerLookForCustomer;
    private javax.swing.JTextField manageCustomerName;
    private javax.swing.JTextField manageCustomerPhone;
    private javax.swing.JButton manageCustomerSaveCustomer;
    private javax.swing.JTextField manageCustomerStatus;
    private javax.swing.JTable manageCustomerTable;
    // End of variables declaration//GEN-END:variables
}
