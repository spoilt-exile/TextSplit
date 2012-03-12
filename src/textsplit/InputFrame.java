/*
 * This code is distributed under terms of GNU GPLv2.
 * *See LICENSE file.
 * ©UKRINFORM 2011-2012
 */

package textsplit;

/**
 * Input window class
 * @author Stanislav Nepochatov
 */
public class InputFrame extends javax.swing.JFrame {
    
    /** Creates new form InpitFrame */
    public InputFrame() {
        initComponents();
    }
    
    //Clipboard provider object
    private clipboardProvider outClipboard = new clipboardProvider();

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        textField = new javax.swing.JTextPane();
        splitButton = new javax.swing.JButton();
        eraseButton = new javax.swing.JButton();
        splitMode = new javax.swing.JComboBox();
        pasteBut = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("textsplit/LocBundle"); // NOI18N
        setTitle(bundle.getString("input_gui_window_title")); // NOI18N

        jScrollPane1.setViewportView(textField);

        splitButton.setText(bundle.getString("input_gui_split")); // NOI18N
        splitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                splitButtonMouseClicked(evt);
            }
        });

        eraseButton.setText(bundle.getString("input_gui_fluch")); // NOI18N
        eraseButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eraseButtonMouseClicked(evt);
            }
        });

        splitMode.setModel(new javax.swing.DefaultComboBoxModel(TextSplit.localizator.getString("input_box").split("#")));
        pasteBut.setText(bundle.getString("input_gui_paste")); // NOI18N
        pasteBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasteButActionPerformed(evt);
            }
        });
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(splitButton)
                        .addGap(18, 18, 18)
                        .addComponent(pasteBut)
                        .addGap(18, 18, 18)
                        .addComponent(eraseButton)
                        .addGap(18, 18, 18)
                        .addComponent(splitMode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(splitButton)
                    .addComponent(splitMode, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(eraseButton)
                    .addComponent(pasteBut))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void splitButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_splitButtonMouseClicked
    TextSplit.FlushStack();
    switch (this.splitMode.getSelectedIndex()) {
        case 0:
            TextSplit.ekopSplit(true);
            break;
        case 1:
            TextSplit.ekopSplit(false);
            break;
        case 2:
            TextSplit.lineSplit();
    }
}//GEN-LAST:event_splitButtonMouseClicked

    private void eraseButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eraseButtonMouseClicked
        this.textField.setText("");
    }//GEN-LAST:event_eraseButtonMouseClicked

    private void pasteButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasteButActionPerformed
        this.textField.setText(this.outClipboard.getClipboardContents());
    }//GEN-LAST:event_pasteButActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InputFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InputFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InputFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InputFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new InputFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton eraseButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton pasteBut;
    private javax.swing.JButton splitButton;
    private javax.swing.JComboBox splitMode;
    public javax.swing.JTextPane textField;
    // End of variables declaration//GEN-END:variables
}
