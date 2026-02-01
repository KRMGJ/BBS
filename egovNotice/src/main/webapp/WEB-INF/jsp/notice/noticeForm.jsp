<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<title>공지사항 등록/수정</title>

<link rel="stylesheet" href="<c:url value='/resources/css/notice.css'/>" />
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
	<div class="wrap">
		<h1>공지사항 등록/수정</h1>

		<div class="box">
			<c:set var="isEdit" value="${not empty notice and not empty notice.nttId}" />

			<form id="noticeForm" method="post" enctype="multipart/form-data"
				action="<c:url value='${isEdit ? "/notice/update.do" : "/notice/insert.do"}'/>">

				<input type="hidden" name="nttId" value="${notice.nttId}" /> 
				<input type="hidden" name="bbsId" value="${empty notice.bbsId ? param.bbsId : notice.bbsId}" />

				<div class="row">
					<label for="nttSj">제목</label> 
					<input type="text" id="nttSj" name="nttSj" value="${fn:escapeXml(notice.nttSj)}" maxlength="500" />
				</div>

				<div class="row">
					<label for="nttCn">내용</label>
					<textarea id="nttCn" name="nttCn"><c:out value="${notice.nttCn}" /></textarea>
				</div>

				<div class="row grid2">
					<div>
						<label for="noticeAt">공지 설정</label> 
						<select id="noticeAt" name="noticeAt">
							<option value="N"
								<c:if test="${notice.noticeAt != 'Y'}">selected</c:if>>일반</option>
							<option value="Y"
								<c:if test="${notice.noticeAt == 'Y'}">selected</c:if>>공지(상단)</option>
						</select>
						<div class="hint">공지(상단) 선택 시 목록 상단에 노출</div>
					</div>

					<div>
						<label>게시기간(선택)</label>
						<div class="grid2" style="grid-template-columns: 1fr 1fr;">
							<input type="text" name="noticeBgnde" placeholder="시작(YYYYMMDD)"
								value="${fn:escapeXml(notice.noticeBgnde)}" /> 
							<input type="text" name="noticeEndde" placeholder="종료(YYYYMMDD)"
								value="${fn:escapeXml(notice.noticeEndde)}" />
						</div>
						<div class="hint">비우면 기간 제한 없이 노출(백엔드 정책에 따라)</div>
					</div>
				</div>

				<div class="row">
					<label for="files">첨부파일</label> 
					<input type="file" id="files" name="files" multiple />
					<div class="hint">여러 파일 선택 가능</div>

					<c:if test="${isEdit}">
						<div style="margin-top: 10px;">
							<div style="font-weight: 700;">기존 첨부</div>
							<c:choose>
								<c:when test="${not empty fileList}">
									<ul style="margin: 6px 0 0; padding-left: 18px;">
										<c:forEach var="f" items="${fileList}">
											<li><c:out value="${f.orignlFileNm}" /> 
												<span class="muted">(<c:out value="${f.fileSize}" /> bytes)</span> 
												<label style="display: inline; font-weight: 400; margin-left: 8px;">
													<input type="checkbox" name="delFileSn" value="${f.fileSn}" />
													삭제
												</label>
											</li>
										</c:forEach>
									</ul>
								</c:when>
								<c:otherwise>
									<div class="muted">기존 첨부파일이 없습니다.</div>
								</c:otherwise>
							</c:choose>
						</div>
					</c:if>
				</div>

				<div class="actions">
					<button type="button" id="btnList">목록</button>
					<button type="button" id="btnSubmit">
						<c:out value="${isEdit ? '수정' : '등록'}" />
					</button>
				</div>
			</form>
		</div>

	</div>
</body>
<script>
	$(function() {
		$("#btnList").on("click", function() {
			location.href = "<c:url value='/notice/list.do'/>";
		});

		$("#btnSubmit").on("click", function() {
			var $f = $("#noticeForm");

			var title = $.trim($f.find("[name='nttSj']").val());
			var body = $.trim($f.find("[name='nttCn']").val());

			if (!title) {
				alert("제목을 입력하세요.");
				$f.find("[name='nttSj']").focus();
				return;
			}
			if (!body) {
				alert("내용을 입력하세요.");
				$f.find("[name='nttCn']").focus();
				return;
			}

			var b = $.trim($f.find("[name='noticeBgnde']").val());
			var e = $.trim($f.find("[name='noticeEndde']").val());

			if ((b && b.length !== 8) || (e && e.length !== 8)) {
				alert("게시기간은 YYYYMMDD 형식으로 입력하세요.");
				return;
			}
			if (b && e && b > e) {
				alert("시작일은 종료일보다 클 수 없습니다.");
				return;
			}

			$f.submit();
		});
	});
</script>
</html>
