<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to Spring Web MVC project</title>
    
        
   <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
   <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>    
        <script>
       
               $( function() {
              $( "#datepicker" ).datepicker();
              });   
        </script>
    </head>

    <body>
        <p>Welcome to Seek & Play!</p>


        <form autocomplete="on" modelAttribute="user" action="${pageContext.request.contextPath}/loginFormHandling.htm" method="POST">
            <h1>Log in</h1> 
            <p> 
                <label  > Your username </label>
                <input id="username" name="username" required="required" type="text" placeholder="myusername"/>
            </p>
            <p> 
                <label > Your password </label>
                <input id="password" name="password" required="required" type="password" placeholder="eg. X8df!90EO" /> 
            </p>
            <div>${message}</div>
            <p class="login button"> 
                <input type="submit" id="submit" value="Login" /> 
            </p>
            <p class="change_link">
                Not a member yet ?
                <a href="http://localhost:8080/seek_play/goToRegisterForm.htm">Go to register page</a>
            </p>
        </form>
        <a href="http://localhost:8080/seek_play/adduser.htm">Add one user</a><br/>


        <a href="http://localhost:8080/seek_play/trialLink.htm">Trial link</a>
           <p>Book a date: <input type="text" id="datepicker"></p>
       
    </body>
</html>
