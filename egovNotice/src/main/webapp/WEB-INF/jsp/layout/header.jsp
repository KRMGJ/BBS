<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet" href="<c:url value='/resources/css/header.css' />">
<link rel="stylesheet" href="<c:url value='/resources/css/cmm.css' />">
<header id="egov-header">
	<div class="header-inner">
		<!-- 로고 -->
		<h1 class="logo">
			<a href="<c:url value='/' />">전자정부 공지시스템</a>
		</h1>

		<!-- GNB -->
		<nav class="gnb">
			<ul>
				<li><a href="<c:url value='/notice/list.do' />">공지사항</a></li>
				<li><a href="#">자료실</a></li>
				<li><a href="#">민원안내</a></li>
			</ul>
		</nav>

		<!-- 사용자 영역 -->
		<div class="user-area">
			<c:choose>
				<c:when test="${not empty loginVO}">
					<span class="user-name">${loginVO.name}님</span>
					<a href="" class="btn" id="logout">로그아웃</a>
				</c:when>
				<c:otherwise>
					<a href="<c:url value='/user/loginView.do' />" class="btn">로그인</a>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</header>

<script>
	$(function() {
		var path = location.pathname;

		$(".gnb a").each(function() {
			if (path.indexOf($(this).attr("href")) === 0) {
				$(this).addClass("active");
			}
		});
		$("#logout").on("click", function() {
            if (confirm("로그아웃 하시겠습니까?")) {
                $.ajax({
                    url: "<c:url value='/user/logout.do'/>",
                    method: "POST",
                    success: function() {
                        alert("로그아웃 되었습니다.");
                        location.href = "<c:url value='/notice/list.do'/>";
                    },
                    error: function() {
                        alert("로그아웃 중 오류가 발생했습니다.");
                    }
                });
            }
        });
	});
</script>
