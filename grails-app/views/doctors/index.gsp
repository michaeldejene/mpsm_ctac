
<html>
<body>
<% docs.each { num -> %>
<p><%="Hello ${num.firstName}" %></p>
<%}%>
</body>
</html>