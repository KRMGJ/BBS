<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="login-wrap">
	<div class="row">
		<label for="userId">아이디</label> 
		<input type="text" id="userId" autocomplete="username" />
	</div>

	<div class="row">
		<label for="password">비밀번호</label> 
		<input type="password" id="password" autocomplete="current-password" />
	</div>

	<button type="button" id="btnLogin">로그인</button>

	<p id="msg"></p>
</div>
