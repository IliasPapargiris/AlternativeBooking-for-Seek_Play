<%-- 
    Document   : booking_modify
    Created on : 11-Nov-2018, 23:47:54
    Author     : Herc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
            <script>

         $(document).ready(function () {
                $("#game").change(function () {
                    selected=document.getElementById("game").value;
                    alert(selected);
                    $.ajax({
                        type: 'get',
                        url: 'bookcreation/request1.htm?game=' + selected,
                        contentType: 'aplication/json',
                        success: function (result) {
                            alert(result);
                            //edw prepei na typwseis to apotelesma stn courtlist
                              $("#players").empty();
                        jsonPlayers = $.parseJSON(result);
                         alert(jsonPlayers);
                        $select = $("#players").append(
                            $('<option>').text("Select player")   
                        );
                        $.each(jsonPlayers, function (i, item) {
                            $select = $("#players").append(
                                $('<option value=' + item.player + '>').text(item.username+" teamwork:"+item.teamwork +" athletism:"+item.athletism+" technique:"+item.technique)
                            );               
                        });
                        }
                   });
                }); 
                     $("#invite").click(function () {
                     player=document.getElementById("players").value;
                     alert(player);
                $.ajax({
                    type: 'get',
                    url: 'bookcreation/SendReq.htm?game='+selected+'&players='+player,
                    contentType: 'aplication/json',
                    success: function (result) {
                        var a=(result.toString());
                        console.log(result.response_html); 
                        alert(result);
                        console.log(result)
                        $("#result").empty();
                        jsonRes = $.parseJSON(result);                       
                            $("#result").text(a);
                        
                    }
               });
             
           });   
             
       });
            </script>
        <title>Find a player!</title>
    </head>
    <body>
        <h1>Select a booking to modify</h1>
        <select id="game" >
            <option  value="None" path="" label="Your games">your games</option>
           <c:forEach items="${matchlist}" var="match">
           <option value="${match.courtReservationID}">Match date:${match.date} court:${match.name} match duration:${match.hour}</option>
           </c:forEach> 
            </select>
        Available players for this match<select id="players" ></select>
        <input type="button" id="invite" label="">Send request to player
         <div id="result"></div>
    </body>
</html>
