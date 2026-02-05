<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="pageTitle" scope="request" class="java.lang.String" />

<%
request.setAttribute("pageTitle", "회원가입");
request.setAttribute("extraHead",
        "<link rel=\"stylesheet\" href=\"" + request.getContextPath()
        + "/resources/css/join.css\" /> <script src=\"" + request.getContextPath()
        + "/resources/js/user/join.js\"></script>");
request.setAttribute("contentPage", "/WEB-INF/jsp/user/join_body.jsp");
%>

<jsp:include page="/WEB-INF/jsp/layout/layout.jsp" />