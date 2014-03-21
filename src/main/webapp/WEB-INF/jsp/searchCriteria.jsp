<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book Search Criteria</title>
</head>
<body>
<form:form action="searchForBooks" commandName="searchCriteria">
	<table>
		<tr>
			<td>Authors:</td><td><form:input path="authors"/></td>
		</tr>
		<tr>
			<td>Title:</td><td><form:input path="title"/></td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit" value="Search"/></td>
		</tr>
	</table>
</form:form>
</body>
</html>