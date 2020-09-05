import java.util.ArrayList;
import java.util.Date;

import com.java.gt.beans.Task;
import com.java.gt.controllers.MainController;
import com.java.gt.store.CustomFileReader;
import com.java.gt.store.CustomFileWriter;

public class Main {

    public static void maine(String [] args) {
        // CustomFileReader f = new CustomFileReader("unite");
        // CustomFileWriter w = new CustomFileWriter("unite");
        // Task t1 = new Task("Tâche de maintenace 1", 23, "Unité Centrale", new Date(), 32445533, "Maintenance");
        // Task t2 = new Task("Tâche de maintenace 2", 23, "Unité Centrale", new Date(), 32445533, "Maintenance");
        // Task t3 = new Task("Tâche de maintenace 3", 23, "Unité Centrale", new Date(), 32445533, "Maintenance");
        // Task t4 = new Task("Tâche de maintenace 4", 23, "Unité Centrale", new Date(), 32445533, "Maintenance");
        // ArrayList<Task> taskList = new ArrayList<Task>();
        // taskList.add(t1);
        // taskList.add(t2);
        // taskList.add(t3);
        // taskList.add(t4);
        // w.renderAllTasks(taskList);
        // w.reinitilaliseFile();
        // f.readFileData();
        MainController.init();
    }

}