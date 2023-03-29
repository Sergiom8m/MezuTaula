package test;

import com.google.gson.Gson;
import helper.db.MySQLdb;
import helper.info.MessageInfo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

//@WebServlet(name = "TestServlet", value = "/TestServlet")
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("---> TestServlet ---> doGet() metodoan sartzen");

        response.setHeader("Cache-Control", "no-cache");
        String type = request.getParameter("type");
        int kontsulta=-1;
        try {
            MySQLdb db = new MySQLdb();
            if (type.equals("registerUser")){
                kontsulta=0;
                System.out.println("---> TestServlet ---> register user egiten");

                String emaila = request.getParameter("email");
                String pasahitza = request.getParameter("password");
                String erabiltzailea = request.getParameter("username");

                db.setUserInfo(emaila, pasahitza,erabiltzailea);

                response.setContentType("text/plain");
                PrintWriter http_out = response.getWriter();
                http_out.println("Datu gordeketa ondo burutu da");

            }else if (type.equals("getUsername")){
                kontsulta=1;
                System.out.println("---> TestServlet ---> get username egiten");

                String emaila = request.getParameter("email");
                String pasahitza = request.getParameter("password");

                String username = db.getUsername(emaila, pasahitza);

                response.setContentType("text/plain");
                PrintWriter http_out = response.getWriter();
                http_out.println(username);

            }else if (type.equals("registerMessage")){
                System.out.println("---> TestServlet ---> register message egiten");

                String erabiltzailea = request.getParameter("username");
                String mezua = request.getParameter("message");

                db.setMessageInfo(mezua, erabiltzailea);

                response.setContentType("text/plain");
                PrintWriter http_out = response.getWriter();
                http_out.println("Datu gordeketa ondo burutu da");

            }else if(type.equals("getAllMessages")){
                System.out.println("---> TestServlet ---> get all messages egiten");

                ArrayList<MessageInfo> mezuak = db.getAllMessages();
                if(request.getParameter("format").equals("json")){
                    System.out.println("---> TestServlet ---> get all messages egiten (JSON)");
                    String json = new Gson().toJson(mezuak);

                    response.setContentType("application/json");
                    PrintWriter http_out = response.getWriter();
                    http_out.println(json);

                }else if(request.getParameter("format").equals("html")){
                    System.out.println("---> TestServlet ---> get all messages egiten (HTML)");
                    request.setAttribute("mezuak", mezuak);

                    RequestDispatcher rd = request.getRequestDispatcher("/jsp/viewMessages.jsp");
                    rd.forward(request, response); //pasatu kontrola jsp-ra baina servlet-a bere exekuzioa amaitzen du
                }
                System.out.println("<--- TestServlet <--- doGet() metodotik ateratzen");
            }

            response.setContentType("text/plain");
            PrintWriter http_out = response.getWriter();
            if(kontsulta==0){
                http_out.println("Erabiltzailea jada sortuta");
            }else if(kontsulta==1){
                http_out.println("Erabiltzailea ez da existitzen");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        System.out.println("<--- TestServlet <--- doGet() metodotik ateratzen");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("---> TestServlet ---> doPost() metodoan sartzen");
        doGet(request, response);
        System.out.println("<--- TestServlet <--- doPost() metodotik irtetzen");

    }
}
