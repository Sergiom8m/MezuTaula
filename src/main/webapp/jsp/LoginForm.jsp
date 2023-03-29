<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: Sergio
  Date: 07/03/2023
  Time: 19:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session = "false"%>
<%@ page import="java.util.*" %>
<html>
<head>
        <title>Login Form</title>
        <link href="/MezuTaula/css/styleSheet.css" rel="stylesheet" />
    </head>
<body>

    <header>
        <h1>A webapp to share short messages</h1>
        <h3>Log In form</h3>
    </header>

    <% String mezua = (String) request.getAttribute("message");
            String logout = (String) request.getAttribute("logout");

            if (mezua != null) {
            %>
                <h1> <%= mezua%> </h1>

            <% } else if (logout != null) {%>

                <h3>LOG OUT!</h3>

            <% } %>

    <section>
        <form method="POST" action="/MezuTaula/servlet/Login">
                <input name="email" type="text" placeholder="Type email..." required>
                <input name="password" type="password" placeholder="Type password..." required>
                <button> Send </button>
        </form>
    </section>

    <footer>
        Server Date: <%=new Date().toString()%>
        <script src="/MezuTaula/js/clientDate.js"></script>
    </footer>
</body>
</html>
