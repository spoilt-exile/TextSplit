/*
 * This code is distributed under terms of GNU GPLv2.
 * *See LICENSE file.
 * ©UKRINFORM 2011-2012
 */

package textsplit;

/**
 * Output window class
 * @author Stanislav Nepochatov
 */
public class OutputFrame extends javax.swing.JDialog {

    /** Creates new form OutputFrame */
    public OutputFrame(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
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
        out = new javax.swing.JTextPane();
        forwardButton = new javax.swing.JButton();
        backwardButton = new javax.swing.JButton();
        stateLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("textsplit/LocBundle"); // NOI18N
        setTitle(bundle.getString("out_gui_window_title")); // NOI18N

        jScrollPane1.setAutoscrolls(true);

        out.setEditable(false);
        jScrollPane1.setViewportView(out);

        forwardButton.setText(bundle.getString("out_gui_forward_button")); // NOI18N
        forwardButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                forwardButtonMouseClicked(evt);
            }
        });

        backwardButton.setText(bundle.getString("out_gui_backward_button")); // NOI18N
        backwardButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backwardButtonMouseClicked(evt);
            }
        });

        stateLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        stateLabel.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(backwardButton)
                        .addGap(40, 40, 40)
                        .addComponent(stateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                        .addComponent(forwardButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(forwardButton)
                    .addComponent(backwardButton)
                    .addComponent(stateLabel))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void forwardButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forwardButtonMouseClicked
        if (this.forwardButton.isEnabled()) {
            this.moveStack(1); 
        }
}//GEN-LAST:event_forwardButtonMouseClicked

    private void backwardButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backwardButtonMouseClicked
        if (this.backwardButton.isEnabled()) {
            this.moveStack(0);
        }
    }//GEN-LAST:event_backwardButtonMouseClicked

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
            java.util.logging.Logger.getLogger(OutputFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OutputFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OutputFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OutputFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                OutputFrame dialog = new OutputFrame(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    
    /**
     * Show stack text method
     */
    public void showStack () {
        this.setVisible(true);
        this.backwardButton.setEnabled(false);
        this.currentStack = 0;
        this.currentString = (String) TextSplit.stringStack.get(0);
        this.stateLabel.setText(TextSplit.localizator.getString("block") + " " + 1 + " " + TextSplit.localizator.getString("from") + " " + TextSplit.stringStack.size() + " (" + this.currentString.length() + "." + this.currentString.getBytes().length + ")");
        this.out.setText(this.currentString);
        this.outClipboard.setClipboardContents(currentString);
        this.out.setCaretPosition(0);
    }
    
    /**
     * Current stack variable
     */
    private int currentStack = 0;
    
    /**
     * Current text piece
     */
    private String currentString;
    
    /**
     * Move stack forward or backward
     * @param mode (0 - move backward, 1 - move forward)
     */
    private void moveStack (int mode) {
        //Moving stack backward
        if (mode == 0) {
            this.currentStack--;
            System.out.println(TextSplit.localizator.getString("moving_stack") + " " + (currentStack + 2) + ">" + (currentStack + 1));
            this.currentString = (String) TextSplit.stringStack.get(currentStack);
            this.stateLabel.setText(TextSplit.localizator.getString("block") + " " + (currentStack + 1) + " " + TextSplit.localizator.getString("from") + " " + TextSplit.stringStack.size() + " (" + this.currentString.length() + "." + this.currentString.getBytes().length + ")");
            this.out.setText(this.currentString);
            this.outClipboard.setClipboardContents(currentString);
            //Disabling backward button if stack reaching end of range
            if (currentStack == 0) {
                this.backwardButton.setEnabled(false);
            }
            //Enabling forward button if disabled
            if (this.forwardButton.isEnabled() == false) {
                this.forwardButton.setEnabled(true);
            }
        }
        //Moving stack forward
        else {
            this.currentStack++;
            System.out.println(TextSplit.localizator.getString("moving_stack") + " " + currentStack + ">" + (currentStack + 1));
            this.currentString = (String) TextSplit.stringStack.get(currentStack);
            this.stateLabel.setText(TextSplit.localizator.getString("block") + " " + (currentStack + 1) + " " + TextSplit.localizator.getString("from") + " " + TextSplit.stringStack.size() + " (" + this.currentString.length() + "." + this.currentString.getBytes().length + ")");
            this.out.setText(this.currentString);
            this.outClipboard.setClipboardContents(currentString);
            //Disabling forward button if stack reaching end of range
            if (currentStack == TextSplit.stringStack.size() - 1) {
                this.forwardButton.setEnabled(false);
            }
            //Enabling backward button if disabled
            if (this.backwardButton.isEnabled() == false) {
                this.backwardButton.setEnabled(true);
            }
        }
        this.out.setCaretPosition(0);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton backwardButton;
    public javax.swing.JButton forwardButton;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTextPane out;
    public javax.swing.JLabel stateLabel;
    // End of variables declaration//GEN-END:variables
}
