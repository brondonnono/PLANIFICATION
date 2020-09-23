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
import com.java.gt.controllers.MainController;
import com.java.gt.controllers.beans_controllers.EquipmentController;
import com.java.gt.controllers.store_controllers.StorageController;
import com.java.gt.store.CustomFileReader;
import com.java.gt.store.CustomFileReaderNotification;
import com.java.gt.threads.MainThread;
import com.java.gt.threads.NotificationThread;
import com.java.gt.threads.TbleThread;
import java.awt.BorderLayout;
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
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
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
    
    private final static int nb = 4, n = 3, na = 5;
    private int ligneSelected;
    private CalendrierCadre calend;
    private ActionListener listener, actionListener;
    private JButton[] boutons = new JButton[nb], buttons = new JButton[n], taskButtons = new JButton[na];
    private DefaultTableModel model, notifModel;
    private MainController control = new MainController();
    public ArrayList<Task> taskList = new ArrayList<Task>(), taskListArranged = new ArrayList<Task>();
    public ArrayList<Notification> notificationList = new ArrayList<Notification>();
    private final String[] EQUIPMENT_LIST = control.EQUIPMENT_LIST;
    public final String[] TASK_TYPE = new Task().TASK_TYPE_LABEL;
    public StorageController storageController = new StorageController();
    public String[] checked = {"",""}, elem = {"","","","",""}, dt = {"", "", ""};
    private final static String title = "PLANIFICATION";
    public JTable table;
    public static boolean canRead = false, SHOULD_STOP_APPLICATION = false;
        
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
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        //this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void initTable(String type) {
   
        model= new DefaultTableModel();
        
        model.addColumn("#");
        model.addColumn("Dispositif");
        model.addColumn("Intervalle");
        model.addColumn("Article");
        model.addColumn("Dernière fois");
        model.addColumn("Temps [H]");
        
        if(canRead){
            this.taskList = storageController.getFileReader().getTaskList();
            if(taskList != null){
                arrangeTaskList(taskList,type);
                this.taskListArranged.forEach((task) -> {
                    model.addRow(new Object[]{task.getId(), checked[0]+"  "+task.getType(), task.displayInterval(), task.getSecteur()+" "+task.getName(), task.getLastMaintaintDate(), task.displayOperatingTime()});
                });        
                tble.setModel(model);
                tble.setAutoCreateRowSorter(true);
            }
        }
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
                elem = new String[5];
                for(int i=0; i<5; i++)
                    elem[i] = "";
                checked[1] = "";
                clean(model);
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
                       // storageController = new StorageController(command);
                       MainController.equipmentList.forEach((equipment) ->{
                           if(equipment.getStorageController().getFolderName().equals(command)){
                               storageController = equipment.getStorageController();
                               canRead = true;
                               //System.out.println("StorageController");
                            }   
                       });
                        taskList = control.getAllEquipementTasks(EQUIPMENT_LIST[i]);
                        checked[0] = command;
                        checked[1] = "";
                        
                        taskButtons[0] = btn_help;
                        taskButtons[1] = btn_history;
                        taskButtons[2] = btn_addTask;
                        taskButtons[3] = btn_done;
                        taskButtons[4] = btn_dropTask;
                        btnState(taskButtons,false, false);
                        
                        taskButtons[0] = ac1;
                        taskButtons[1] = ac2;
                        taskButtons[2] = ac3;
                        taskButtons[3] = btn_history;
                        taskButtons[4] = btn_addTask;
                        btnState(taskButtons, true, true);
                        
                    }
                }
            }  
        };
        
        actionListener = new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                elem = new String[5];
                for(int i=0; i<5; i++)
                    elem[i] = "";
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
                        taskButtons[0] = btn_help;
                        taskButtons[1] = btn_history;
                        taskButtons[2] = btn_addTask;
                        taskButtons[3] = btn_done;
                        taskButtons[4] = btn_dropTask;
                        btnState(taskButtons,false, true);
                        
                        taskButtons[0] = ac1;
                        taskButtons[1] = ac2;
                        taskButtons[2] = ac3;
                        taskButtons[3] = btn_history;
                        taskButtons[4] = btn_addTask;
                        btnState(taskButtons, true, false);
                        btnSetColor(btn_history,true);
                        btnSetColor(btn_addTask,true);

                    }
                }
                //System.out.println("action");
            }
        };
    }
    
    public void showNotification(ArrayList<Notification> notificationList) {
        notifModel = new DefaultTableModel();
        notifModel.addColumn("Nom equipment -> Action");
        notifModel.addColumn("Type");
        notifModel.addColumn("Temps");
        notifModel.addColumn("Messages");
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
        String equipmentName = MainController.equipmentList.get(notification.getEquipmentId()-1).getEquipment().getName();
        String taskType = MainController.equipmentList.get(notification.getEquipmentId()-1).getEquipment().getTaskList().get(notification.getTaskId()-1).getType();

        notifModel.addRow(new Object[]{equipmentName+" -> "+taskType, notification.getType(), notification.getCreatedAt(), notification.getMessage()});
        tble_alert.setModel(notifModel);
        for(int i=0; i<4; i++)
            tble_alert.getColumnModel().getColumn(i).setCellRenderer(new NotificationCellRenderer(tble_alert.getColumnName(i)));
        main_pan.add(pan_alert);
    }
    
    private void alertNotification(Notification notification){
        String equipmentName = MainController.equipmentList.get(notification.getEquipmentId()-1).getEquipment().getName();
        String taskType = MainController.equipmentList.get(notification.getEquipmentId()-1).getEquipment().getTaskList().get(notification.getTaskId()-1).getType();
        notifModel.addRow(new Object[]{equipmentName+" -> "+taskType, notification.getType(), notification.getCreatedAt(), notification.getMessage()});        tble_alert.setModel(notifModel);
        for(int i=1; i<4; i++)
            tble_alert.getColumnModel().getColumn(i).setCellRenderer(new NotificationCellRenderer(tble_alert.getColumnName(i)));
        main_pan.add(pan_alert);
    }
    
    private void getNotificationList(){
        for(EquipmentController equipment:MainController.equipmentList){
            for(Task task :equipment.getStorageController().getFileReader().getTaskList()){
                if(task.notifAlert())
                    notificationList.add(new Notification(task.getId(),equipment.getEquipment().getId(),new Date(),task.getSecteur(),"Alert"));
            }    
        }
    }
    
    private void jbInit() throws Exception {
    
        //recuperation de la liste des tâches
        getNotificationList();
        
        //desactivation des boutons liés aux tâches
        taskButtons[0] = btn_help;
        taskButtons[1] = btn_history;
        taskButtons[2] = btn_addTask;
        taskButtons[3] = btn_done;
        taskButtons[4] = btn_dropTask;
        btnState(taskButtons,false, true);
        
        calend = new CalendrierCadre(this);
        initListeners();
        buttonEquipmentInit();
        buttonActionInit();
        showNotification(notificationList);
        
        //desactivation des boutons d'action
        buttons[0] = ac1;
        buttons[1] = ac2;
        buttons[2] = ac3;
        btnState(buttons, false, true);
        
        new TbleThread(this).start();
        new NotificationThread(this).start();
        
    } 
    
    public void btnState(JButton[] btn, boolean state, boolean giveColor) {
        for (JButton btn1 : btn){
            btn1.setEnabled(state);
            if(giveColor)
                btnSetColor(btn1,state);
        }
    }
    
    
    private void btnSetColor(JButton btn1, boolean state){
        if(state == false){
            btn1.setBackground(Color.lightGray);
            btn1.setForeground(Color.white);
        }else{
            btn1.setBackground(Color.black);
            btn1.setForeground(Color.white);
        }
    }
    
    private void deplace(int i){
        try {
            elem = new String[5];
            elem[0] = model.getValueAt(i,0).toString();
            elem[1] = model.getValueAt(i,1).toString();
            elem[2] = model.getValueAt(i,2).toString();
            elem[3] = model.getValueAt(i,3).toString();
            elem[4] = model.getValueAt(i,4).toString();
        }
        catch (Exception e) {
            System.err.println(e);
            JOptionPane.showMessageDialog (null, "Erreur de Selection"+e.getLocalizedMessage());
        }
    }
    
    public void arrangeTaskList(ArrayList<Task> taskList, String type){
        taskList.forEach((task) -> {
           if(task.getType().equals(type)){
               this.taskListArranged.add(task);
           }
        });
    }
    
    public ArrayList<Task> arrangedTaskList(ArrayList<Task> taskList, String type){
        ArrayList<Task> taskListArranged = new ArrayList<Task>();
        taskList.forEach((task) -> {
           if(task.getType().equals(type))
               taskListArranged.add(task);
        });
        return taskListArranged;
    }

    public void clean(DefaultTableModel model){
        if(model != null)
            while(model.getRowCount()>0) 
                model.removeRow(0);
        elem = null;
    }
    
    private boolean removeTask(int indexTask){
        Task t = this.storageController.getFileReader().getTaskList().remove(indexTask);
        if(t==null)
            return false;
        return true;
    }
    
    private void dropTask(){
        int reply = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer cette tâche ?", "Confirmation Suppression", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            if(this.removeTask(Integer.parseInt(elem[0])-1)){
                model.removeRow(ligneSelected);
                model.fireTableDataChanged();
                JOptionPane.showMessageDialog(null, "Tâche supprimée");
            } else
                JOptionPane.showMessageDialog(null, "Echec de suppression");
        } else {
            JOptionPane.showMessageDialog(null, "Suppression annulée");
        }  
    }
    
    private boolean canStopApplication(){
        return MainThread.SAFE_SAVES[0] && MainThread.SAFE_SAVES[1] && MainThread.SAFE_SAVES[2] && MainThread.SAFE_SAVES[3];
    }
    
    private void showCloseMessage() {
        final JDialog dialog = new JDialog();
        dialog.setTitle("Fermeture de l'application en cours");
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setSize(100, 100);
        dialog.setLocationRelativeTo(null);
        dialog.setIconImage((new ImageIcon(getClass().getResource("/com/java/gt/img/logo.png"))).getImage());
        dialog.pack();
        dialog.setVisible(true);
    }
    
    private void stopThread(){
        
        int reply = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment fermer l'application ?", "Fermeture de l'application", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
           Accueil.SHOULD_STOP_APPLICATION = true;
           //new AlertClosingFe().setVisible(true);
           this.showCloseMessage();
           do {
               for(boolean bool: MainThread.SAFE_SAVES)
                   System.out.println("accueil bool: "+ bool);
                   System.out.println("-----------------------------------------------");
           } while(!canStopApplication());
           for(boolean bool: MainThread.SAFE_SAVES)
                   System.out.println("accueil bool: "+ bool);
                   System.out.println("-----------------------------------------------");
           System.exit(0);
           
        } else {
           Accueil.SHOULD_STOP_APPLICATION = false;
        } 
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
        btn_done = new javax.swing.JButton();
        btn_addTask = new javax.swing.JButton();
        btn_dropTask = new javax.swing.JButton();
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
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

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
        jLabel1.setText("PLANIFICATION");
        jLabel1.setToolTipText("");

        javax.swing.GroupLayout title_panLayout = new javax.swing.GroupLayout(title_pan);
        title_pan.setLayout(title_panLayout);
        title_panLayout.setHorizontalGroup(
            title_panLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, title_panLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        title_panLayout.setVerticalGroup(
            title_panLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(title_panLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(title_panLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(title_panLayout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)))
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

        btn_done.setBackground(new java.awt.Color(0, 0, 0));
        btn_done.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btn_done.setForeground(new java.awt.Color(255, 255, 255));
        btn_done.setText("Fait");
        btn_done.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_doneActionPerformed(evt);
            }
        });

        btn_addTask.setBackground(new java.awt.Color(0, 0, 0));
        btn_addTask.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btn_addTask.setForeground(new java.awt.Color(255, 255, 255));
        btn_addTask.setText("Ajouter une tâche");
        btn_addTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addTaskActionPerformed(evt);
            }
        });

        btn_dropTask.setBackground(new java.awt.Color(0, 0, 0));
        btn_dropTask.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btn_dropTask.setForeground(new java.awt.Color(255, 255, 255));
        btn_dropTask.setText("Supprimer une tâche");
        btn_dropTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dropTaskActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pan_btn3Layout = new javax.swing.GroupLayout(pan_btn3);
        pan_btn3.setLayout(pan_btn3Layout);
        pan_btn3Layout.setHorizontalGroup(
            pan_btn3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pan_btn3Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(btn_dropTask)
                .addGap(18, 18, 18)
                .addComponent(btn_help, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_history, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_addTask)
                .addGap(18, 18, 18)
                .addComponent(btn_done, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pan_btn3Layout.setVerticalGroup(
            pan_btn3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_btn3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btn_done, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                .addComponent(btn_addTask, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE))
            .addComponent(btn_history, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_dropTask, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_help, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

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

        pan_alert.setBorder(javax.swing.BorderFactory.createTitledBorder("Notifications"));

        tble_alert.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "     Temps", "    Messages    "
            }
        ));
        tble_alert.setEnabled(false);
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
                    .addComponent(jScrollPane3)
                    .addGroup(main_panLayout.createSequentialGroup()
                        .addGroup(main_panLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pan_btn1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pan_btn2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pan_Cal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(main_panLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(pan_alert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 8, Short.MAX_VALUE))
                    .addGroup(main_panLayout.createSequentialGroup()
                        .addComponent(cls)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pan_btn3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        main_panLayout.setVerticalGroup(
            main_panLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(main_panLayout.createSequentialGroup()
                .addComponent(title_pan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                .addContainerGap(70, Short.MAX_VALUE))
        );

        getContentPane().add(main_pan);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
        // TODO add your handling code here:
/*        try {
            //notificationList = new CustomFileReaderNotification().readFileDataNotification();
            showNotification(notificationList);     
        }
        catch(Exception e) {
            e.printStackTrace();
       }
*/ 
    }//GEN-LAST:event_formMouseEntered

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        stopThread();
    }//GEN-LAST:event_formWindowClosing

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        stopThread();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void clsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clsActionPerformed
        // TODO add your handling code here:
        clean(model);
        taskButtons[0] = btn_help;
        taskButtons[1] = btn_history;
        taskButtons[2] = btn_addTask;
        taskButtons[3] = btn_done;
        taskButtons[4] = btn_dropTask;
        btnState(taskButtons,false, true);

        for(JButton btn:buttons){
            btn.setBackground(Color.black);
            btn.setForeground(Color.white);
        }
        btnState(buttons,false, true);
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

    private void btn_helpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_helpActionPerformed
        // TODO add your handling code here:
        new Help(this).setVisible(true);
    }//GEN-LAST:event_btn_helpActionPerformed

    private void btn_historyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_historyActionPerformed
        // TODO add your handling code here:
        new HistoryView(this).setVisible(true);
    }//GEN-LAST:event_btn_historyActionPerformed

    private void btn_doneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_doneActionPerformed
        // TODO add your handling code here:
        new validate(this).setVisible(true);
    }//GEN-LAST:event_btn_doneActionPerformed

    private void btn_addTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addTaskActionPerformed
        // TODO add your handling code here:
        new TaskDefinition(this).setVisible(true);
    }//GEN-LAST:event_btn_addTaskActionPerformed

    private void btn_dropTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dropTaskActionPerformed
        // TODO add your handling code here:
        dropTask();
    }//GEN-LAST:event_btn_dropTaskActionPerformed

    private void tbleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbleMouseClicked
        // TODO add your handling code here:
        try{
            ligneSelected=tble.getSelectedRow();
            deplace(ligneSelected);
            /*     while(model.getRowCount()>0)
            model.removeRow(0);
            initTable();
            */
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur de deplacement"+e.getLocalizedMessage());
            taskButtons[0] = btn_help;
            taskButtons[1] = btn_history;
            taskButtons[2] = btn_addTask;
            taskButtons[3] = btn_done;
            taskButtons[4] = btn_dropTask;
            btnState(taskButtons,false, true);
        }
        taskButtons[0] = btn_help;
        taskButtons[1] = btn_history;
        taskButtons[2] = btn_addTask;
        taskButtons[3] = btn_done;
        taskButtons[4] = btn_dropTask;
        btnState(taskButtons,true, true);
    }//GEN-LAST:event_tbleMouseClicked

    private void ac2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ac2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ac2ActionPerformed

    private void ac1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ac1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ac1ActionPerformed

    private void ac3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ac3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ac3ActionPerformed

    private void b1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_b1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_b1FocusLost

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

    /*Génération de button*/
    public void generate_btn(String name) {
        
    }

        /*Masquage du tableau et reaffichage*/
    public void show_hide_table(java.awt.event.ActionEvent evt) {
       String a = evt.getActionCommand();
       //System.out.println(a);
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
    private javax.swing.JButton btn_addTask;
    private javax.swing.JButton btn_done;
    private javax.swing.JButton btn_dropTask;
    private javax.swing.JButton btn_help;
    private javax.swing.JButton btn_history;
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
    
    public DefaultTableModel getModel(){
        return model;
    }
    public DefaultTableModel getNotifModel(){
        return notifModel;
    }
    public JTable getTableAlert(){
        return tble_alert;
    }
}
