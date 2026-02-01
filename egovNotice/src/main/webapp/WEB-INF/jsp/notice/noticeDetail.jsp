<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<title>공지사항 상세</title>

<link rel="stylesheet" href="<c:url value='/resources/css/notice.css'/>" />
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
	<div class="wrap">
		<h1>공지사항 상세</h1>

		<div class="box">
			<div style="font-size: 18px; font-weight: 700; margin-bottom: 10px;">
				<c:out value="${notice.nttSj}" />
			</div>

			<div class="meta">
				<div>
					등록일:
					<c:out value="${notice.frstRegistPnttm}" />
				</div>
				<div>
					작성자:
					<c:out value="${notice.frstRegisterId}" />
				</div>
				<div>
					조회수:
					<c:out value="${notice.inqireCo}" />
				</div>
				<c:if test="${notice.noticeAt == 'Y'}">
					<div class="muted">[공지]</div>
				</c:if>
			</div>

			<div class="content">
				<c:out value="${notice.nttCn}" />
			</div>

			<div class="files">
				<div style="font-weight: 700;">첨부파일</div>
				<c:choose>
					<c:when test="${not empty fileList}">
						<ul>
							<c:forEach var="f" items="${fileList}">
								<li>
									<a href="<c:url value='/notice/file/download.do'>
                          				<c:param name='atchFileId' value='${f.atchFileId}'/>
                          				<c:param name='fileSn' value='${f.fileSn}'/>
                        				</c:url>">
										<c:out value="${f.orignlFileNm}" />
									</a> 
									<span class="muted">(<c:out value="${f.fileSize}" />bytes)</span>
								</li>
							</c:forEach>
						</ul>
					</c:when>
					<c:otherwise>
						<div class="muted">첨부파일이 없습니다.</div>
					</c:otherwise>
				</c:choose>
			</div>

			<div class="actions">
				<button type="button" id="btnList">목록</button>
				<button type="button" id="btnEdit">수정</button>
				<button type="button" id="btnDelete">삭제</button>
			</div>
		</div>

	</div>
</body>
<script>
	$(function() {
		$("#btnList").on("click", function() {
			location.href = "<c:url value='/notice/list.do'/>";
		});

		$("#btnEdit").on("click", function() {
					var id = "${notice.nttId}";
					location.href = "<c:url value='/notice/form.do'/>?nttId=" + encodeURIComponent(id);
				});

		$("#btnDelete").on("click", function() {
					if (!confirm("삭제하시겠습니까?"))
						return;
					var id = "${notice.nttId}";
					location.href = "<c:url value='/notice/delete.do'/>?nttId=" + encodeURIComponent(id);
				});
	});
</script>
</html>
