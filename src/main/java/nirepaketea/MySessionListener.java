package nirepaketea;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import javax.servlet.annotation.*;
import java.util.HashMap;
import java.util.Map;

//@WebListener
public class MySessionListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    private SimpleDateFormat data_formatua;
    public MySessionListener() {
        data_formatua = new SimpleDateFormat("yyyy-MM HH:mm:ss");
        data_formatua.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("--> MySessionListener --> A session is being created at");
        Date data = new Date();
        System.out.println(data_formatua.format(data));
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
         System.out.println("--> MySessionListener --> A session is being destroyed at");

          HttpSession session = se.getSession();
          String username = (String) session.getAttribute("username");

          ServletContext context = session.getServletContext();
          HashMap<String, String> loggedinUsers = (HashMap) context.getAttribute("loggedin_users");
          System.out.println("\tLoggedin users: " + loggedinUsers.toString());

          for(Map.Entry<String, String> entry : loggedinUsers.entrySet()) {
              if(entry.getValue().equals(username)) {
                  loggedinUsers.remove(entry.getKey());
                  context.setAttribute("loggedin_users", loggedinUsers);
                  break;
            }
        }

         Date data = new Date();
         System.out.println(data_formatua.format(data));
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is added to a session. */
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is removed from a session. */
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is replaced in a session. */
    }
}
