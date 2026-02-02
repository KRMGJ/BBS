<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<title>회원가입</title>

<link rel="stylesheet" href="<c:url value='/resources/css/join.css'/>" />

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="<c:url value='/resources/js/user/join.js'/>"></script>
</head>
<body>
	<div class="join-wrap">
		<h1>회원가입</h1>

		<input type="text" id="userId" placeholder="아이디" />
		<button type="button" id="btnCheckId">중복확인</button>

		<input type="password" id="password" placeholder="비밀번호" /> 
		<input type="text" id="userNm" placeholder="이름" /> 
		<input type="email" id="email" placeholder="이메일" /> 
		<input type="text" id="mobile" placeholder="휴대폰" />

		<button type="button" id="btnJoin">가입하기</button>

		<p class="msg" id="msg"></p>
	</div>
</body>
</html>
