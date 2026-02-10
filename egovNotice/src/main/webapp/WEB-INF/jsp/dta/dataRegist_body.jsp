<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="dta-wrap">

	<form id="dtaRegForm" method="post" enctype="multipart/form-data" action="<c:url value='/bbs/dta/insert.do'/>">

		<input type="hidden" name="bbsId" value="${bbsId}" />

		<div class="dta-box">

			<div class="dta-row">
				<label>제목 <span class="dta-required">*</span></label>
				<input type="text" name="subject" class="dta-input" placeholder="제목을 입력하세요" />
			</div>

			<div class="dta-row">
				<label>내용</label>
				<textarea name="content" class="dta-textarea" placeholder="내용을 입력하세요"></textarea>
			</div>

			<div class="dta-row">
				<label for="files">첨부파일</label> 
				<input type="file" id="files" name="files" multiple />
				<div class="nt-hint">여러 파일 선택 가능</div>
			</div>

		</div>

		<div class="dta-actions">
			<button type="button" class="dta-btn" id="btnList">목록</button>
			<button type="button" class="dta-btn dta-btn-primary" id="btnSave">등록</button>
		</div>

	</form>

</div>
