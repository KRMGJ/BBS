<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="join-wrap">
	<input type="text" id="userId" placeholder="아이디" />
	<button type="button" id="btnCheckId">중복확인</button>

	<input type="password" id="password" placeholder="비밀번호" /> 
	<input type="text" id="userNm" placeholder="이름" /> 
	<input type="email" id="email" placeholder="이메일" /> 
	<input type="text" id="mobile" placeholder="휴대폰" />

	<button type="button" id="btnJoin">가입하기</button>

	<p id="msg"></p>
</div>