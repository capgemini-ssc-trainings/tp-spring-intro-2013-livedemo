<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book Edit</title>
</head>
<body>
	<form:form action="saveBook" modelAttribute="book">
		<table>
			<tr>
				<td>Authors:</td>
				<td><form:input path="authors" /></td>
				<td><form:errors path="authors" /></td>
			</tr>
			<tr>
				<td>Title:</td>
				<td><form:input path="title" /></td>
				<td><form:errors path="title" /></td>
			</tr>
			<tr>
				<td colspan="3"><input type="submit" value="Save" /></td>
			</tr>
		</table>
	</form:form>
	<br/>
	<a href="showMatchedBooks">&lt;- Back to result list</a>
</body>
</html>