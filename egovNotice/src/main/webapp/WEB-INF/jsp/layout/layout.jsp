<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title><c:out value="${pageTitle}" default="전자정부 공지시스템" /></title>

<!-- 공통 CSS -->
<link rel="stylesheet" href="<c:url value='/resources/css/header.css' />">
<link rel="stylesheet" href="<c:url value='/resources/css/footer.css' />">
<link rel="stylesheet" href="<c:url value='/resources/css/layout.css' />">
<!-- 공통 JS -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<!-- 페이지별 head 추가 영역 -->
<c:if test="${not empty extraHead}">
	<c:out value="${extraHead}" escapeXml="false" />
</c:if>
</head>

<body>
	<div id="wrap">

		<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>

		<main id="container">
			<div class="container-inner">

				<c:if test="${not empty pageTitle}">
					<h2 class="page-title">
						<c:out value="${pageTitle}" />
					</h2>
				</c:if>

				<jsp:include page="${contentPage}" />
			</div>
		</main>

		<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>

	</div>
</body>
</html>
