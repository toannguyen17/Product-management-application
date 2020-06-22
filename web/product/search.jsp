<%--
  Created by IntelliJ IDEA.
  User: toanv
  Date: 22/06/2020
  Time: 14:34
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Search Product</title>
    <style>
        .message {
            color: #fd0000;
        }
    </style>
</head>
<body>
<h1>Search product</h1>
<c:if test='${requestScope["message"] != null}'>
    <p>
        <span class="message">${requestScope["message"]}</span>
    </p>
</c:if>
<p>
    <a href="/products">Back to product list</a>
</p>
<form method="POST">
    <fieldset>
        <legend>Search product</legend>
        <table>
            <tr>
                <td>Name: </td>
                <td><input type="text" name="search" value="" placeholder="Enter product name"></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" value="Search"></td>
            </tr>
        </table>
    </fieldset>
</form>
<c:if test='${requestScope["results"] != null}'>
    <table border="1">
        <tr>
            <td>Name</td>
            <td>Price</td>
            <td>Producer</td>
            <td>Description</td>
            <td>Edit</td>
            <td>Delete</td>
        </tr>
        <c:forEach items='${requestScope["results"]}' var="product">
            <tr>
                <td><a href="/products?action=view&id=${product.getId()}">${product.getName()}</a></td>
                <td>${product.getPrice()}</td>
                <td>${product.getDescription()}</td>
                <td>${product.getProducer()}</td>
                <td><a href="/products?action=edit&id=${product.getId()}">edit</a></td>
                <td><a href="/products?action=delete&id=${product.getId()}">delete</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>
</body>
</html>
