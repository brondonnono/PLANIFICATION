/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.gt.views;

import com.java.gt.beans.History;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Brondon Nono
 */
public class HistoryView extends javax.swing.JFrame {

    private String title = "Historique";
    private Accueil parent;
    private ArrayList<History> historyList,tmpList, historyListArranged = new ArrayList<History>();
    private DefaultTableModel model;
    /**
     * Creates new form History
     */
    public HistoryView() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    public HistoryView(Accueil parent) {  
        this.parent = parent;
        this.historyList = this.parent.storageController.getFileReaderHistory().readFileDataHistory();
        this.setTitle(title);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setIconImage((new ImageIcon(getClass().getResource("/com/java/gt/img/logo.png"))).getImage());
        this.setResizable(false);
        this.setVisible(true);
        initComponents();
        clear.setEnabled(false);
        JbInit();
    }

    public ArrayList<History> ArrangeHistoryList(ArrayList<History> historyList){
        ArrayList<History> ListHistory = new ArrayList<History>();
        if(!historyList.isEmpty())
            historyList.forEach((history) -> {
                if(history.getId() == Integer.parseInt(parent.elem[0]))
                    ListHistory.add(history);
            });
        clean(historyList);
        return ListHistory;
    }
    /**@param ArrayList<History>
     * Fonction d'initialisation de notre TableModel 
     * avec la liste des historiques arrangés
    */
    public void initTable(ArrayList<History> historyListArranged){
        
        model = new DefaultTableModel();
        model.addColumn("#");
        model.addColumn("Article");
        model.addColumn("Date");
        model.addColumn("Opérateur");
        
        historyListArranged.forEach((history) -> {
            model.addRow( new Object[]{ 
                history.getId(), history.getArticle(), 
                history.getDate(), history.getOperator()
            });
        });
        
        tble_history.setModel(model);
    }
    
    public void clean(ArrayList<History> historyList) {
        tmpList = new ArrayList<History>();
        historyList.forEach((history) -> {
            tmpList.add(history);
        });
        tmpList.removeAll(historyListArranged);
        //.out.println("tmpList: "+tmpList);
    }
    
    public void clearAllHistory(){
        historyList.clear();
        parent.storageController.getFileWriterHistory().renderAllHistory(historyList);
        this.historyListArranged.clear();
        initTable(historyListArranged);
    }
    
    public void clearSpecifiedHistory(){
        //System.out.println("liste Temporaire :"+ tmpList);
        parent.storageController.getFileWriterHistory().renderAllHistory(tmpList);
        this.historyListArranged.clear();
        initTable(historyListArranged);
    }
    
    public void showAllHistory(ArrayList<History> historyList){
        initTable(historyList);
        clear.setForeground(Color.white);
        clear.setEnabled(true);
       // clean(historyList);
    }
    
    public void JbInit() {
        if(!parent.elem[0].equals("")) {
            historyListArranged = ArrangeHistoryList(historyList);
            this.historyList.clear();
            if(!historyListArranged.isEmpty()) {
                initTable(historyListArranged);
                clear.setForeground(Color.white);
                clear.setEnabled(true);
            }
        }else 
            showAllHistory(historyList);
        //System.out.println(this.historyListArranged);
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
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tble_history = new javax.swing.JTable();
        exit = new javax.swing.JButton();
        clear = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("              HISTORIQUE");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(252, 252, 252)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addContainerGap())
        );

        tble_history.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Article", "Date", "Opérateur"
            }
        ));
        jScrollPane1.setViewportView(tble_history);

        exit.setText("FERMER");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });

        clear.setBackground(new java.awt.Color(204, 0, 0));
        clear.setText("EFFACER TOUT");
        clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(clear, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addComponent(exit, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(clear, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(exit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearActionPerformed
        // TODO add your handling code here:
        if(!parent.elem[0].equals(""))
            clearSpecifiedHistory();
        else
            clearAllHistory();
    }//GEN-LAST:event_clearActionPerformed

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_exitActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clear;
    private javax.swing.JButton exit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tble_history;
    // End of variables declaration//GEN-END:variables
}
