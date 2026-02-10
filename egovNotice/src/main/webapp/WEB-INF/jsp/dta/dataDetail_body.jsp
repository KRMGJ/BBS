<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="dta-wrap">

	<div class="dta-box">

		<h2 class="dta-title">
			<c:out value="${result.subject}" />
		</h2>

		<div class="dta-meta">
			<span>작성자: <c:out value="${result.frstRegisterId}" /></span> 
			<span>작성일: <c:out value="${result.frstRegistPnttm}" /></span> 
			<span>조회수: <c:out value="${result.viewCnt}" /></span>
		</div>

		<div class="dta-content">
			<c:out value="${result.content}" />
		</div>

		<div class="dta-files">
			<strong>첨부파일</strong>

			<c:choose>
				<c:when test="${empty fileList}">
					<div class="dta-muted">첨부파일이 없습니다.</div>
				</c:when>
				<c:otherwise>
					<ul>
						<c:forEach var="f" items="${fileList}">
							<li><a href="#" class="js-file-download"
								data-atch="${f.atchFileId}" data-sn="${f.fileSn}"> <c:out
										value="${f.orignlFileNm}" />
							</a> <span class="dta-muted"> (<c:out value="${f.fileSize}" />
									byte)
							</span></li>
						</c:forEach>
					</ul>

					<!-- 파일 다운로드 (소속검증용) -->
					<form id="downloadForm" method="post" action="<c:url value='/bbs/dta/downloadDataFile.do'/>">
						<input type="hidden" name="nttId" value="<c:out value='${result.nttId}'/>" /> 
						<input type="hidden" name="atchFileId" /> 
						<input type="hidden" name="fileSn" />
					</form>
				</c:otherwise>
			</c:choose>
		</div>

	</div>

	<div class="dta-actions">
		<button type="button" class="dta-btn" id="btnList">목록</button>
	</div>

</div>
