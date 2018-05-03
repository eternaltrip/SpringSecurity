
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>  
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Admin page</title>
    <link href="<c:url value='/statics/css/bootstrap.css' />" rel="stylesheet"></link>
    <link href="<c:url value='/statics/css/app.css' />" rel="stylesheet"></link>
</head>
<body>
    <div class="success">
        Dear <strong>${user}</strong>, Welcome to Admin Page.
        <br/>
     	<sec:authorize access="isFullyAuthenticated()">  
	        <label><a href="#">Create New User</a> | <a href="#">View existing Users</a></label>  
	    </sec:authorize>  
	    <sec:authorize access="isRememberMe()">  
	        <label><a href="#">View existing Users</a></label>  
	    </sec:authorize> 
        <br/>
        <a href="<c:url value="/logout" />">Logout</a>
    </div>
</body>
</html>