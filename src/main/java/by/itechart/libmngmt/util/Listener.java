package by.itechart.libmngmt.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Listener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DatabaseInitializer.createDatabase();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
