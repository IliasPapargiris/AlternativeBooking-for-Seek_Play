<%-- 
    Document   : booking_creation
    Created on : 15-Dec-2018, 23:46:58
    Author     : Ilias
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="spring" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book a court!</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://code.jquery.com/jquery-2.2.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js""></script>
        <link href="https://code.jquery.com/ui/1.12.1/themes/black-tie/jquery-ui.css" rel="stylesheet">
        <script>
       
        $(document).ready(function () {
            $("#court").change(function () {
                selected=document.getElementById("court").value;
                
                $.ajax({
                    type: 'get',
                    url: 'bookcreation/court.htm?court=' + selected,
                    contentType: 'aplication/json',
                    success: function (result) {
                   
                        $("#bookeddates").empty();
                        jsonobj = $.parseJSON(result);
                        $.each(jsonobj, function (i, item) {
                            $tr = $('<tr>').append($('<td>').text(item));
                            $("#bookeddates").append($tr);
                        });
                    }
               });
            });
       
            var minDate=new Date();
            $( function() {
                $( "#date" ).datepicker({
                    showAnim:'drop',
                    numberofMonth:2,
                    minDate:minDate,
                    maxDate: "+1m",
                    dateFormat:'yy-mm-dd',
                    beforeShowDay: function(date){
                        var string = jQuery.datepicker.formatDate('yy-mm-dd', date);
                        return [$.inArray(string, jsonobj) == -1];
                    },
 
               });
            });
          
            
           
          
        $("#date").change(function (){    
            date1=document.getElementById("date").value;
             
//            date2=document.getElementById("booking_date").value;
//            alert(date2); 
             
         var court=document.getElementById("court").value;
            alert(court);
            $.ajax({
                type: 'get',
                url: 'bookcreation/hour.htm?court=' + selected+'&date='+date1,
                contentType: 'aplication/json',
                success: function (result) {
                    //edw prepei na typwseis to apotelesma stn courtlist
                    $("#hour").empty();
                    jsonHour = $.parseJSON(result);
                    $select = $("#hour").append(
                        $('<option>').text("Select hour")   
                    );
                    $.each(jsonHour, function (i, item) {
                        $select = $("#hour").append(
                            $('<option value=' + item.hoursId + '>').text(item.hour)
                        );               
                    });
              }  
            });
              
        });                         
                                  $('#hour').change(function() {
                                      
                                    const x = $(this).val();
                                    
                                    $('#hours').val(x);
                                     hider=document.getElementById("hours").value;
                                     console.log(hider);
                                });   
     
        
          $("#checked").click(function () {
                $.ajax({
                    type: 'get',
                    url: 'bookcreation/booking.htm?court=' + selected+'&date='+date1+'&hours='+hider,
                    contentType: 'aplication/json',
                        success: function (result) {
                        console.log(result.response_html); 
                        $("#result1").empty();
                          jsonRes = $.parseJSON(result);                       
                          // Alert(jsonRes);
                           $("#result1").append(result);                        
                    }
               });
             
           });            


    });
          </script>
     
    </head>
    <body>
       
            <spring:form method = "GET" action ="${pageContext.request.contextPath}/book1.htm"  modelAttribute="reservation">
         <table>
            <tr>
           <td>
            <spring:select id="court" path="courtId.id" name="court">
             <spring:option  value="None" path="" label="Select court"/>
             <c:forEach items="${courtlist}" var="court">
            <spring:option value="${court.id}" path="courtId.id" label="${court.name}"/>
            </c:forEach>
            </spring:select>
          </td>
            </tr>
           <tr>
               <td><input type="text" id="date" name="date"></td>                                                
            </tr>
            <tr> 
              <td>Hours<select id="hour" >
                  </select></td>
            <div id="availableHours"> </div>               
            <td><spring:hidden  value=''  path = "hours.hoursId" id="hours" name="hours"/></td>              
            </tr>          
            <tr>${resResult}</tr>
            <div id="result"></div>
            <tr>
              <td> <input type="button" id="checked" label="Book it"> Book it</td>
              <td><div id="result1"></div></td>
            </tr>
         </table> 
   
        </spring:form>
    </body>
</html>
