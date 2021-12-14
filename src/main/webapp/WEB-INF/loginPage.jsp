<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- c:out ; c:forEach etc. --> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Formatting (dates) --> 
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- form:form -->
<%-- <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> --%>
<%--  <!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true" %> --%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
<!--     <link rel="stylesheet" href="/css/main.css"> change to match your file/naming structure
 -->    
 	<script src="/webjars/jquery/jquery.min.js"></script>
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
	<a href="/registration">Register a User</a>
	<h1>Login</h1>
	<p><c:out value="${ message }" /></p>
	<form method="POST" action="/login">
		<p>
			<!-- this label had type="email" in it but was causing an error -->
			<label for="email">Email</label>
			<input type="text" id="email" name="email"/>
		</p>
		<p>
			<label for="password">Password</label>
			<input type="password" id="password" name="password"/>
		</p>
		<input type="submit" value="Login!"/>
	</form>
		
</body>
</html>
