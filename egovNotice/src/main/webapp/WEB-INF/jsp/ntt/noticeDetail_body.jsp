<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="nt-wrap">
	<h1>공지사항 상세</h1>

	<div class="nt-box">
		<div style="font-size: 18px; font-weight: 700; margin-bottom: 10px;">
			<c:out value="${notice.nttSj}" />
		</div>

		<div class="nt-meta">
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
				<div class="nt-muted">[공지]</div>
			</c:if>
		</div>

		<div class="nt-content">
			<c:out value="${notice.nttCn}" />
		</div>

		<div class="nt-files">
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
								<span class="nt-muted">(<c:out value="${f.fileSize}" />bytes)</span>
							</li>
						</c:forEach>
					</ul>
				</c:when>
				<c:otherwise>
					<div class="nt-muted">첨부파일이 없습니다.</div>
				</c:otherwise>
			</c:choose>
		</div>

		<div class="nt-actions">
			<button type="button" id="btnList">목록</button>
			<button type="button" id="btnEdit">수정</button>
			<button type="button" id="btnDelete">삭제</button>
		</div>
	</div>
</div>
