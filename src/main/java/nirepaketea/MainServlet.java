package nirepaketea;

import helper.db.MySQLdb;
import helper.info.MessageInfo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

//@WebServlet(name = "MainServlet", value = "/MainServlet")
public class MainServlet extends HttpServlet {
    private MySQLdb mySQLdb;

    public void init() throws ServletException {
        mySQLdb = new MySQLdb();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("--> MainServlet --> doGet() metodoan sartzen");
        response.setHeader("Cache-Control", "no-cache");

        String logout = request.getParameter("logout");



        if (logout == null) {

            if (request.getSession(false) == null) {

                System.out.println("--> MainServlet --> The user is not logged in: redirecting to login form");
                RequestDispatcher rd = request.getRequestDispatcher("/jsp/LoginForm.jsp");
                rd.forward(request, response);

            } else {

                System.out.println("--> MainServlet --> The user is logged: redirecting to viewMessages.jsp");
                ArrayList<MessageInfo> messageList = mySQLdb.getAllMessages();
                request.setAttribute("mezuak", messageList);
                RequestDispatcher rd = request.getRequestDispatcher("/jsp/viewMessages.jsp");
                rd.forward(request, response);



            }
        }
        else if (logout.equals("true")){

            System.out.println("--> MainServlet --> logout");
            HttpSession session = request.getSession(false);
            session.invalidate();

            request.setAttribute("logout", logout);
            RequestDispatcher rd = request.getRequestDispatcher("/jsp/LoginForm.jsp");
            rd.forward(request, response);

        }
        System.out.println("<-- MainServlet <-- doGet() metodotik irtetzen");


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request, response);

    }
}
