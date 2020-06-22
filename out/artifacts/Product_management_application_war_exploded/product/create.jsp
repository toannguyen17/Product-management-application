<%--
  Created by IntelliJ IDEA.
  User: toanv
  Date: 22/06/2020
  Time: 10:30
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Create Product</title>
    <style>
        .message {
            color: #fd0000;
        }
    </style>
</head>
<body>
<h1>Create new product</h1>
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
        <legend>Product information</legend>
        <table>
            <tr>
                <td>Name: </td>
                <td><input type="text" name="name" id="name"></td>
            </tr>
            <tr>
                <td>Price: </td>
                <td><input type="text" name="price" id="price"></td>
            </tr>
            <tr>
                <td>Description: </td>
                <td><input type="text" name="description" id="description"></td>
            </tr>
            <tr>
                <td>Producer: </td>
                <td><input type="text" name="producer" id="producer"></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" value="Create product"></td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html>
