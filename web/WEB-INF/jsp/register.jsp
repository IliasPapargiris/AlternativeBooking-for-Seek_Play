<%-- 
    Document   : emptyForm
    Created on : Nov 2, 2018, 7:30:30 PM
    Author     : Michail Sitmalidis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="spring" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Register JSP</h1>

        <spring:form modelAttribute="user" action="${pageContext.request.contextPath}/registerFormHandling.htm" method="POST">  
            <table>
                <tr>
                    <td> <spring:label path="username">Username</spring:label></td>   
                    <td> <spring:input path="username"/> </td>
                    <td> <spring:errors path="username" cssClass="error"/></td>
                </tr>

                <tr>
                    <td> <spring:label path="firstname">Firstname</spring:label></td>   
                    <td> <spring:input path="firstname"/> </td>
                    <td><spring:errors path="firstname" cssClass="error"/></td>
                </tr>
                <tr>
                    <td> <spring:label path="lastname">Lastname</spring:label></td>   
                    <td> <spring:input path="lastname"/> </td>
                    <td><spring:errors path="lastname" cssClass="error"/></td>
                </tr>

                <tr>
                    <td> <spring:label path="password">Password</spring:label></td>   
                    <td> <spring:password path="password"/> </td>
                    <td><spring:errors path="password" cssClass="error"/></td>
                </tr>

                <tr>
                    <td> <spring:label path="password_confirmation">Password Confirmation</spring:label></td>   
                    <td> <spring:password path="password_confirmation"/> </td>
                    <td><spring:errors path="password_confirmation" cssClass="error"/></td>
                </tr>

            </table>
            <input type="submit">
        </spring:form> 
    </body>
</html>
