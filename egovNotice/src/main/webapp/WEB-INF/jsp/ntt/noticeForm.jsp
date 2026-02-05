<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="pageTitle" scope="request" class="java.lang.String" />

<%
request.setAttribute("pageTitle", "공지사항 등록/수정");
request.setAttribute("extraHead",
        "<link rel=\"stylesheet\" href=\"" + request.getContextPath()
        + "/resources/css/notice.css\" /> <script src=\"" + request.getContextPath()
        + "/resources/js/ntt/form.js\"></script>");
request.setAttribute("contentPage", "/WEB-INF/jsp/ntt/noticeForm_body.jsp");
%>

<jsp:include page="/WEB-INF/jsp/layout/layout.jsp" />