package by.itechart.libmngmt.util;

import by.itechart.libmngmt.util.emailScheduler.SchedulerTrigger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Listener implements ServletContextListener {
    private DatabaseInitializer databaseInitializer = DatabaseInitializer.getInstance();
    private FolderPropertiesAdder folderPropertiesAdder = FolderPropertiesAdder.getInstance();
    private SchedulerTrigger schedulerTrigger = SchedulerTrigger.getInstance();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        databaseInitializer.createDatabase();
        folderPropertiesAdder.addImageFolder();
        schedulerTrigger.startScheduler();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
