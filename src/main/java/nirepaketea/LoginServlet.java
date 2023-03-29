package nirepaketea;

import helper.db.MySQLdb;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

//@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {

    private String mezua;
    private MySQLdb mySQLdb;

    public void init() throws ServletException {
        mySQLdb = new MySQLdb();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

         System.out.println("--> LoginServlet --> doGet() metodoan sartzen");
         response.setHeader("Cache-Control", "no-cache");

         String email = request.getParameter("email");
         String pasahitza = request.getParameter("password");


        if (email != null && pasahitza != null) {

            String username = MySQLdb.getUsername(email, pasahitza);

            if (username != null) {
                HttpSession session = request.getSession(true); //Saioa lortu. Sortuta ez badago sortu, horregatik true
                String sessionID = session.getId();
                session.setAttribute("username", username);

                System.out.println("--> LoginServlet --> redirecting to MainServlet");

                ServletContext context = request.getServletContext();
                HashMap<String, String> loggedinUsers = (HashMap) context.getAttribute("loggedin_users");

                if(loggedinUsers == null) {
                    loggedinUsers = new HashMap();
                    loggedinUsers.put(sessionID, username);
                } else {
                    if(!loggedinUsers.containsKey(sessionID)) {
                        loggedinUsers.put(sessionID, username);
                    }

                }

                context.setAttribute("loggedin_users", loggedinUsers);

                RequestDispatcher rd = context.getNamedDispatcher("MainServlet");
                rd.forward(request,response);

                /* response.setContentType("text/plain");
                PrintWriter http_out = response.getWriter();
                http_out.println("Login OK"); */

            } else {
                mezua = "LOGIN ERROREA";
                request.setAttribute("message", mezua);
                System.out.println("--> LoginServlet --> /jsp/LoginForm.jsp");
                RequestDispatcher rd = request.getRequestDispatcher("/jsp/LoginForm.jsp");
                rd.forward(request, response);
            }
        }
        else if (request.getSession(false) != null) { //Saioa existitzen da

            System.out.println("--> LoginServlet --> User alredy logged: redirecting to MainServlet");
            ServletContext context = request.getServletContext();
            RequestDispatcher rd = context.getNamedDispatcher("MainServlet");
            rd.forward(request, response);
        }
        else { //Servlet-a zuzenean, parametrorik gabe, deitzen da eta ez da saiorik existitzen

            System.out.println("--> LoginServlet --> User not logged: redirecting to login form");
            RequestDispatcher rd = request.getRequestDispatcher("/jsp/LoginForm.jsp");
            rd.forward(request, response);
        }

         System.out.println("<-- LoginServlet <-- doGet() metodotik irtetzen");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request, response);

    }
}
