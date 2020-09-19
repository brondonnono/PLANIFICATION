/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.gt.views;
import com.java.gt.beans.History;
import com.java.gt.beans.Notification;
import com.java.gt.beans.Task;
import com.java.gt.calendar.CalendrierCadre;
import com.java.gt.components.NotificationCellRenderer;
import com.java.gt.components.NotificationModel;
import com.java.gt.controllers.MainController;
import com.java.gt.controllers.store_controllers.StorageController;
import com.java.gt.store.CustomFileReader;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Brondon Nono
 */
public class Accueil extends javax.swing.JFrame {
    
    private final static int nb = 4, n = 3;
    private CalendrierCadre calend;
    private ActionListener listener, actionListener;
    private JButton[] boutons = new JButton[nb], buttons = new JButton[n];
    private DefaultTableModel model, notifModel;
    private MainController control = new MainController();
    public ArrayList<Task> taskList = new ArrayList<Task>(), taskListArranged = new ArrayList<Task>();
    public ArrayList<Notification> notificationList = new ArrayList<Notification>();
    private final String[] EQUIPMENT_LIST = control.EQUIPMENT_LIST;
    public final String[] TASK_TYPE = new Task().TASK_TYPE_LABEL;
    public StorageController storageController;
    public String[] checked = {"",""}, elem = {"","","","",""}, dt = {"", "", ""};
    private final static String title = "PLANIFICATION"; 
    public NotificationModel notificationModel = new NotificationModel();
    public JTable table;
        
             // control.init();
    
    /**
     * Creates new form Accueil
     */
    public Accueil() {
        initComponents();
        control.init();
        main_pan.remove(pan_alert);
        this.setTitle(title);
        this.setSize((int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight() -35);
        this.setIconImage((new ImageIcon(getClass().getResource("/com/java/gt/img/logo.png"))).getImage());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void initTable(String type) {
   
        model= new DefaultTableModel();
        
        model.addColumn("#");
        model.addColumn("Dispositif");
        model.addColumn("Intervalle");
        model.addColumn("Article");
        model.addColumn("Dernière fois");
        model.addColumn("Temps [H]");
        
        this.taskList = storageController.getFileReader().getTaskList();
        arrangeTaskList(taskList,type);
        this.taskListArranged.forEach((task) -> {
            System.out.println(task);
            model.addRow(new Object[]{task.getId(), checked[0]+"  "+task.getType(), task.displayInterval(), task.getSecteur()+" "+task.getName(), task.getLastMaintaintDate(), task.displayOperatingTime()});
        });        
        tble.setModel(model);
        tble.setAutoCreateRowSorter(true);
    }
    
    private void buttonEquipmentInit() {
    
        boutons[0] = b1;
        boutons[1] = b2;
        boutons[2] = b3;
        boutons[3] = b4;
        for( int i=0; i < EQUIPMENT_LIST.length; i++ )
         boutons[i].setText(EQUIPMENT_LIST[i]);
        
        b1.setText(boutons[0].getText());
        b1.setActionCommand(boutons[0].getText());
        b1.addActionListener(listener);
        
        b2.setText(boutons[1].getText());
        b2.setActionCommand(boutons[1].getText());
        b2.addActionListener(listener);
        
        b3.setText(boutons[2].getText());
        b3.setActionCommand(boutons[2].getText());
        b3.addActionListener(listener);
        
        b4.setText(boutons[3].getText());
        b4.setActionCommand(boutons[3].getText());
        b4.addActionListener(listener);
    }
    
    private void buttonActionInit() {   
        
        ac1.setText(TASK_TYPE[0]);
        ac1.setActionCommand(TASK_TYPE[0]);
        ac1.addActionListener(actionListener);
        
        ac2.setText(TASK_TYPE[1]);
        ac2.setActionCommand(TASK_TYPE[1]);
        ac2.addActionListener(actionListener);
        
        ac3.setText(TASK_TYPE[2]);
        ac3.setActionCommand(TASK_TYPE[2]);
        ac3.addActionListener(actionListener);
    }
    
    private void initListeners() {
        listener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                for( int i=0; i < EQUIPMENT_LIST.length; i++) {
                    if(command.equals(EQUIPMENT_LIST[i])) {
                        if(!checked[0].equals(command))
                            for(JButton btn:boutons)
                                if(btn.getText().equalsIgnoreCase(checked[0])){
                                    btn.setBackground(Color.black);
                                    btn.setForeground(Color.white);
                                }
                        else
                            for(JButton btn1:boutons)
                                if(btn1.getText().equals(command)){
                                    btn1.setBackground(Color.white);
                                    btn1.setForeground(Color.black);
                                }
                        storageController = new StorageController(command); 
                        taskList = control.getAllEquipementTasks(EQUIPMENT_LIST[i]);
                        checked[0] = command;
                        buttons[0] = ac1;
                        buttons[1] = ac2;
                        buttons[2] = ac3;
                        btnState(buttons, true);
                    }
                }
            }
            
        };
        
        actionListener = new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                for (String TASK_TYPE1 : TASK_TYPE) {
                    if (command.equals(TASK_TYPE1)) {
                        if(!checked[1].equals(command))
                            for(JButton btn:buttons)
                                if(btn.getText().equals(checked[1])) {
                                    btn.setBackground(Color.black);
                                    btn.setForeground(Color.white);
                                }
                                else
                                    for(JButton btn1:buttons)
                                        if(btn1.getText() == command) {
                                            btn1.setBackground(Color.white);
                                            btn1.setForeground(Color.black);
                                        }
                        checked[1] = command;
                        if(!taskListArranged.isEmpty())
                            taskListArranged.clear();
                        initTable(command);
                    }
                }
                System.out.println("action");
            }
        };
    }
    
    private void showNotification(ArrayList<Notification> notificationList) {
        notifModel = new DefaultTableModel();
        notifModel.addColumn("Type");
        notifModel.addColumn("Temps");
        notifModel.addColumn("Messages");
        table = new JTable(notificationModel);
        //tble_alert = new JTable(notificationModel);
        if(!notificationList.isEmpty()){
            notificationList.forEach((notification) -> {
                if(notification.getType().equals("Alert"))
                    alertNotification(notification);
                else
                    warningNotification(notification);
            });
            tble_alert.setAutoCreateRowSorter(true);
            
        }
    }
    
    private void warningNotification(Notification notification){
        // System.out.println("Warning: "+notification.toString());
        dt[0] = notification.getType();
        notifModel.addRow(new Object[]{ notification.getType(), notification.getCreatedAt(), notification.getMessage()});
        //tble_alert.setModel(notifModel);
        for(int i=0; i<3; i++)
            tble_alert.getColumnModel().getColumn(i).setCellRenderer(new NotificationCellRenderer(tble_alert.getColumnName(i),dt));
        main_pan.add(pan_alert);
    }
    
    private void alertNotification(Notification notification){
       // System.out.println("Alert: "+notification.toString());
        dt[0] = notification.getType();
        notifModel.addRow(new Object[]{notification.getType(), notification.getCreatedAt(), notification.getMessage()});
        tble_alert.setModel(notifModel);
        for(int i=0; i<3; i++)
            tble_alert.getColumnModel().getColumn(i).setCellRenderer(new NotificationCellRenderer(tble_alert.getColumnName(i),dt));
                        
//System.out.println(tble_alert.getColumnName(0));
        main_pan.add(pan_alert);
    }
    
    private void jbInit() throws Exception {
    
        //recuperation de la liste des tâches
        notificationList = new CustomFileReader().readFileDataNotification();
        
        //desactivation des boutons liés aux tâches
        boutons[0] = btn_help;
        boutons[1] = btn_history;
        boutons[2] = btn_unity;
        boutons[3] = btn_done;
        btnState(boutons,false);
        
        calend = new CalendrierCadre(this);
        initListeners();
        buttonEquipmentInit();
        buttonActionInit();
        showNotification(notificationList);
        
        //desactivation des boutons d'action
        buttons[0] = ac1;
        buttons[1] = ac2;
        buttons[2] = ac3;
        btnState(buttons, false);
    } 
    
    public void btnState(JButton[] btn, boolean state) {
        for (JButton btn1 : btn){
            btn1.setEnabled(state);
            if(state == false){
                btn1.setBackground(Color.lightGray);
                btn1.setForeground(Color.white);
            }else{
                btn1.setBackground(Color.black);
                btn1.setForeground(Color.white);
            }
        }
    }
    
    private void deplace(int i){
        try {
            elem[0] = model.getValueAt(i,0).toString();
            elem[1] = model.getValueAt(i,1).toString();
            elem[2] = model.getValueAt(i,2).toString();
            elem[3] = model.getValueAt(i,3).toString();
            elem[4] = model.getValueAt(i,4).toString();
            //System.out.println(elem[0]);
        }
        catch (Exception e) {
            System.err.println(e);
            JOptionPane.showMessageDialog (null, "Erreur de Selection"+e.getLocalizedMessage());
        }
    }
    
    private void arrangeTaskList(ArrayList<Task> taskList, String type){
        taskList.forEach((task) -> {
           if(task.getType().equals(type)){
               this.taskListArranged.add(task);
               System.out.println(type);
               System.out.println("taskListArranged"+"\n"+this.taskListArranged.toString());
               
           }
        });
    }

    private void clean(){
        if(model != null)
            while(model.getRowCount()>0) 
                model.removeRow(0);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        main_pan = new javax.swing.JPanel();
        title_pan = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cls = new javax.swing.JButton();
        pan_btn3 = new javax.swing.JPanel();
        btn_help = new javax.swing.JButton();
        btn_history = new javax.swing.JButton();
        btn_unity = new javax.swing.JButton();
        btn_done = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tble = new javax.swing.JTable();
        pan_btn2 = new javax.swing.JPanel();
        ac2 = new javax.swing.JButton();
        ac1 = new javax.swing.JButton();
        ac3 = new javax.swing.JButton();
        pan_Cal = new javax.swing.JPanel();
        pan_btn1 = new javax.swing.JPanel();
        b1 = new javax.swing.JButton();
        b2 = new javax.swing.JButton();
        b3 = new javax.swing.JButton();
        b4 = new javax.swing.JButton();
        pan_alert = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tble_alert = new javax.swing.JTable();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("accueil"); // NOI18N
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        main_pan.setBackground(new java.awt.Color(255, 255, 51));

        title_pan.setBackground(new java.awt.Color(0, 0, 0));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/java/gt/img/exit.png"))); // NOI18N
        jButton2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/java/gt/img/mini-logo.png"))); // NOI18N
        jLabel1.setText("    PLANIFICATION");
        jLabel1.setToolTipText("");

        javax.swing.GroupLayout title_panLayout = new javax.swing.GroupLayout(title_pan);
        title_pan.setLayout(title_panLayout);
        title_panLayout.setHorizontalGroup(
            title_panLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, title_panLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        title_panLayout.setVerticalGroup(
            title_panLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(title_panLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cls.setBackground(new java.awt.Color(0, 0, 0));
        cls.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        cls.setForeground(new java.awt.Color(255, 255, 255));
        cls.setText("Nettoyage de l'écran");
        cls.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clsActionPerformed(evt);
            }
        });

        pan_btn3.setBackground(new java.awt.Color(255, 255, 51));

        btn_help.setBackground(new java.awt.Color(0, 0, 0));
        btn_help.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btn_help.setForeground(new java.awt.Color(255, 255, 255));
        btn_help.setText("Aide");
        btn_help.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_helpActionPerformed(evt);
            }
        });

        btn_history.setBackground(new java.awt.Color(0, 0, 0));
        btn_history.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btn_history.setForeground(new java.awt.Color(255, 255, 255));
        btn_history.setText("Historique");
        btn_history.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_historyActionPerformed(evt);
            }
        });

        btn_unity.setBackground(new java.awt.Color(0, 0, 0));
        btn_unity.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btn_unity.setForeground(new java.awt.Color(255, 255, 255));
        btn_unity.setText("Chaque Unité");
        btn_unity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_unityActionPerformed(evt);
            }
        });

        btn_done.setBackground(new java.awt.Color(0, 0, 0));
        btn_done.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btn_done.setForeground(new java.awt.Color(255, 255, 255));
        btn_done.setText("Fait");
        btn_done.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_doneActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pan_btn3Layout = new javax.swing.GroupLayout(pan_btn3);
        pan_btn3.setLayout(pan_btn3Layout);
        pan_btn3Layout.setHorizontalGroup(
            pan_btn3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pan_btn3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_help, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_history, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_unity, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_done, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pan_btn3Layout.setVerticalGroup(
            pan_btn3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_unity, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
            .addComponent(btn_done, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_history, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_help, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tble.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        tble.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Dispositif", "Intervalle", "Article", "Dernière fois", "Temps [H]"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tble.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tble.setOpaque(false);
        tble.setRowHeight(30);
        tble.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbleMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tble);
        if (tble.getColumnModel().getColumnCount() > 0) {
            tble.getColumnModel().getColumn(0).setMaxWidth(20);
            tble.getColumnModel().getColumn(5).setMaxWidth(100);
        }

        pan_btn2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Action", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        ac2.setBackground(new java.awt.Color(0, 0, 0));
        ac2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ac2.setForeground(new java.awt.Color(255, 255, 255));
        ac2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ac2ActionPerformed(evt);
            }
        });

        ac1.setBackground(new java.awt.Color(0, 0, 0));
        ac1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ac1.setForeground(new java.awt.Color(255, 255, 255));
        ac1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ac1ActionPerformed(evt);
            }
        });

        ac3.setBackground(new java.awt.Color(0, 0, 0));
        ac3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ac3.setForeground(new java.awt.Color(255, 255, 255));
        ac3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ac3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pan_btn2Layout = new javax.swing.GroupLayout(pan_btn2);
        pan_btn2.setLayout(pan_btn2Layout);
        pan_btn2Layout.setHorizontalGroup(
            pan_btn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_btn2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(ac1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(ac2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ac3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        pan_btn2Layout.setVerticalGroup(
            pan_btn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_btn2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pan_btn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pan_btn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(ac1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ac2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(ac3, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pan_Cal.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Planning", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        javax.swing.GroupLayout pan_CalLayout = new javax.swing.GroupLayout(pan_Cal);
        pan_Cal.setLayout(pan_CalLayout);
        pan_CalLayout.setHorizontalGroup(
            pan_CalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 268, Short.MAX_VALUE)
        );
        pan_CalLayout.setVerticalGroup(
            pan_CalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 214, Short.MAX_VALUE)
        );

        pan_btn1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Emplacement", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        b1.setBackground(new java.awt.Color(0, 0, 0));
        b1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        b1.setForeground(new java.awt.Color(255, 255, 255));
        b1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                b1FocusLost(evt);
            }
        });
        b1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b1ActionPerformed(evt);
            }
        });

        b2.setBackground(new java.awt.Color(0, 0, 0));
        b2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        b2.setForeground(new java.awt.Color(255, 255, 255));
        b2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b2ActionPerformed(evt);
            }
        });

        b3.setBackground(new java.awt.Color(0, 0, 0));
        b3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        b3.setForeground(new java.awt.Color(255, 255, 255));
        b3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b3ActionPerformed(evt);
            }
        });

        b4.setBackground(new java.awt.Color(0, 0, 0));
        b4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        b4.setForeground(new java.awt.Color(255, 255, 255));
        b4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pan_btn1Layout = new javax.swing.GroupLayout(pan_btn1);
        pan_btn1.setLayout(pan_btn1Layout);
        pan_btn1Layout.setHorizontalGroup(
            pan_btn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_btn1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(b1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(b2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(b3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(b4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        pan_btn1Layout.setVerticalGroup(
            pan_btn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_btn1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pan_btn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(b1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b3, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b4, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pan_alert.setBackground(new java.awt.Color(255, 255, 51));
        pan_alert.setBorder(javax.swing.BorderFactory.createTitledBorder("Notifications"));

        tble_alert.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "     Type    ", "     Temps", "    Messages    "
            }
        ));
        jScrollPane4.setViewportView(tble_alert);

        javax.swing.GroupLayout pan_alertLayout = new javax.swing.GroupLayout(pan_alert);
        pan_alert.setLayout(pan_alertLayout);
        pan_alertLayout.setHorizontalGroup(
            pan_alertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1295, Short.MAX_VALUE)
        );
        pan_alertLayout.setVerticalGroup(
            pan_alertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout main_panLayout = new javax.swing.GroupLayout(main_pan);
        main_pan.setLayout(main_panLayout);
        main_panLayout.setHorizontalGroup(
            main_panLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(title_pan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(main_panLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(main_panLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(main_panLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(pan_alert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(main_panLayout.createSequentialGroup()
                        .addComponent(cls)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pan_btn3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(main_panLayout.createSequentialGroup()
                        .addGroup(main_panLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3)
                            .addGroup(main_panLayout.createSequentialGroup()
                                .addGroup(main_panLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(pan_btn1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(pan_btn2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(pan_Cal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        main_panLayout.setVerticalGroup(
            main_panLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(main_panLayout.createSequentialGroup()
                .addComponent(title_pan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(main_panLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pan_Cal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(main_panLayout.createSequentialGroup()
                        .addComponent(pan_btn1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pan_btn2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(main_panLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pan_btn3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cls, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pan_alert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 19, Short.MAX_VALUE))
        );

        getContentPane().add(main_pan);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbleMouseClicked
        // TODO add your handling code here:
        try{
            int i=tble.getSelectedRow();
            deplace(i);
       /*     while(model.getRowCount()>0) 
                model.removeRow(0);
            initTable();
        */
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur de deplacement"+e.getLocalizedMessage());
        }
        boutons[0] = btn_help;
        boutons[1] = btn_history;
        boutons[2] = btn_unity;
        boutons[3] = btn_done;
        btnState(boutons,true);
    
    }//GEN-LAST:event_tbleMouseClicked

    private void btn_doneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_doneActionPerformed
        // TODO add your handling code here:
        new validate(this).setVisible(true);
    }//GEN-LAST:event_btn_doneActionPerformed

    private void btn_unityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_unityActionPerformed
        // TODO add your handling code here:
        new Setting().setVisible(true);
    }//GEN-LAST:event_btn_unityActionPerformed

    private void btn_historyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_historyActionPerformed
        // TODO add your handling code here:
        
        new history(storageController.getFileWriter(),storageController.getFileReader(), elem[0], elem[3]).setVisible(true);
    }//GEN-LAST:event_btn_historyActionPerformed

    private void btn_helpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_helpActionPerformed
        // TODO add your handling code here:
        new Help(this).setVisible(true);
    }//GEN-LAST:event_btn_helpActionPerformed

    private void clsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clsActionPerformed
        // TODO add your handling code here:    
        clean();
        boutons[0] = btn_help;
        boutons[1] = btn_history;
        boutons[2] = btn_unity;
        boutons[3] = btn_done;
        btnState(boutons,false);
        
        for(JButton btn:buttons){
            btn.setBackground(Color.black);
            btn.setForeground(Color.white);
        }
        btnState(buttons,false);
        boutons[0] = b1;
        boutons[1] = b2;
        boutons[2] = b3;
        boutons[3] = b4;
        for(JButton btn:boutons){
            btn.setBackground(Color.black);
            btn.setForeground(Color.white);
        }
        checked[0] = "";
        checked[1] = "";
    }//GEN-LAST:event_clsActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void b1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b1ActionPerformed

    private void b2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b2ActionPerformed

    private void b3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b3ActionPerformed

    private void b4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b4ActionPerformed

    private void ac1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ac1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ac1ActionPerformed

    private void ac2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ac2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ac2ActionPerformed

    private void ac3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ac3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ac3ActionPerformed

    private void b1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_b1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_b1FocusLost

    /*Génération de button*/
    public void generate_btn(String name) {
        
    }

        /*Masquage du tableau et reaffichage*/
    public void show_hide_table(java.awt.event.ActionEvent evt) {
       String a = evt.getActionCommand();
       System.out.println(a);
        //JOptionPane.showMessageDialog(null, a);
    }
    
    /**
     * @param args the command line arguments
     **/
     

    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
              new Accueil().setVisible(true);
            }
        });
        
    }  
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ac1;
    private javax.swing.JButton ac2;
    private javax.swing.JButton ac3;
    private javax.swing.JButton b1;
    private javax.swing.JButton b2;
    private javax.swing.JButton b3;
    private javax.swing.JButton b4;
    private javax.swing.JButton btn_done;
    private javax.swing.JButton btn_help;
    private javax.swing.JButton btn_history;
    private javax.swing.JButton btn_unity;
    private javax.swing.JButton cls;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JPanel main_pan;
    private javax.swing.JPanel pan_Cal;
    private javax.swing.JPanel pan_alert;
    private javax.swing.JPanel pan_btn1;
    private javax.swing.JPanel pan_btn2;
    private javax.swing.JPanel pan_btn3;
    private javax.swing.JTable tble;
    private javax.swing.JTable tble_alert;
    private javax.swing.JPanel title_pan;
    // End of variables declaration//GEN-END:variables

    //getter and setter of pan_Cal
    public JPanel getPan_Cal() {
        return pan_Cal;
    }
    public void setPan_Cal(JPanel pan_Cal) {
        this.pan_Cal = pan_Cal;
    }
}
