<%-- 
    Document   : book_event
    Created on : Nov 15, 2018, 2:47:10 PM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="spring" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registration</title>
    </head>
    <body>
        <h1>Please put your personal data below</h1>
         <spring:form action = "bookcourt.htm" modelAttribute="booked"> 
             
        <table>
          <tr>
                <td><spring:label  path = "username">Username</spring:label></td>
               <td><spring:input  path = "username" /></td>               
            </tr>,
            <tr>               
               <td><spring:label  path = "password">Password</spring:label></td>
               <td><spring:input path = "password" id="password" /></td>
            </tr>
            <tr>
                   <td><form:label path = "country">Country</form:label></td>
                <td>
                  <form:select path = "sports">
                     <form:option value = "NONE" label = "Select"/>
                     <form:options items = "${book.sportList}" />
                  </form:select>     	
               </td>
            </tr>
         </table>
              
       </spring:form>
    </body>
</html>
