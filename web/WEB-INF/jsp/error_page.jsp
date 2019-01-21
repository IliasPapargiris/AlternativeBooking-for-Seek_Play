<%-- 
    Document   : error_page
    Created on : Nov 15, 2018, 8:32:17 AM
    Author     : Michail Sitmalidis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
         <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script src="https://code.jquery.com/jquery-2.2.4.min.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
        <link href="https://code.jquery.com/ui/1.11.3/themes/smoothness/jquery-ui.css" rel="stylesheet">
        <script>
          var minDate=newDate();
               $( function() {
              $( "#date" ).datepicker({
                  showAnim:'drop',
                  numberofMonth:2,
                  minDate:minDate,
                 dateFormat:'yy-mm-dd'
              });
              });   
        </script>
            
    </head>
    <body>
        <h1><input type="text" id="date"></h1>
    </body>
</html>
