package by.itechart.libmngmt.util;

import by.itechart.libmngmt.util.emailScheduler.Trigger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Listener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DatabaseInitializer.createDatabase();
        FolderPropertiesAdder.addImageFolder();
        Trigger.startScheduler();


    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
