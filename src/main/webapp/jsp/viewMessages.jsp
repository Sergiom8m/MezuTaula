
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>

<%@ page import="java.util.*,helper.info.*" %>

<%
    ArrayList<MessageInfo> messageList = (ArrayList) request.getAttribute("mezuak");
    ServletContext context = request.getServletContext();
    HashMap<String,String> user_list = (HashMap) context.getAttribute("loggedin_users");
%>

<html>

    <head>
        <title>View Messages</title>
        <link href="/MezuTaula/css/styleSheet.css" rel="stylesheet" />
    </head>

    <body>

        <section>
            <table id="MezuTaula">
                <tr>
                    <th>Username</th>
                    <th>Message</th>
                </tr>
                <% for(int i = 0; i < messageList.size(); i++) {
                    MessageInfo messageInfo = messageList.get(i); %>
                    <tr>
                        <td><%= messageInfo.getUsername() %></td>
                        <td><%= messageInfo.getMessage() %></td>
                    </tr>
                <% } %>
            </table>
            <br>
            <div style="text-align: center;">
                <button style="text-align: center">
                    <a href="/MezuTaula/servlet/Main?logout=true">Log out</a>
                </button>
            </div>
        </section>

     <section>
            <table id="ErabTaula">
                <tr>
                    <th>Username</th>
                </tr>
                <% if (user_list != null){
                    for(int i = 0; i < user_list.size(); i++) { %>
                    <tr>
                        <td><%= user_list.get(session.getId())  %></td>
                    </tr>
                <% }
                } %>
            </table>
        </section>
    </body>
</html>