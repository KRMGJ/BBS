<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<footer id="egov-footer">
	<div class="footer-inner">
		<div class="footer-info">
			<p class="site-name">전자정부 공지시스템</p>
			<p>
				(12345) 서울특별시 ○○구 ○○로 123<br> TEL : 02-1234-5678 | FAX :
				02-1234-5679
			</p>
		</div>

		<div class="footer-links">
			<a href="#">이용약관</a> <span class="divider">|</span> <a href="#">개인정보처리방침</a>
			<span class="divider">|</span> <a href="#">저작권정책</a>
		</div>

		<div class="footer-copy">
			<p>Copyright © <span class="year"></span> 전자정부표준프레임워크.</p> All Rights Reserved.
		</div>
	</div>
</footer>
<script>
	$(function() {
		var year = new Date().getFullYear();
		$(".footer-copy").find(".year").text(year);
	});
</script>
