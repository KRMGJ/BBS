<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
request.setAttribute("pageTitle", "자료실 상세");
request.setAttribute("extraHead",
	"<link rel=\"stylesheet\" href=\"" + request.getContextPath() + "/resources/css/data.css\" />" +
	"<script src=\"" + request.getContextPath() + "/resources/js/dta/detail.js\"></script>"
);
request.setAttribute("contentPage", "/WEB-INF/jsp/dta/dataDetail_body.jsp");
%>

<jsp:include page="/WEB-INF/jsp/layout/layout.jsp" />
