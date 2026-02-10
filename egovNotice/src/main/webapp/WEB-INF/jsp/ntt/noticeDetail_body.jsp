<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="pageIndex"
	value="${empty searchVO.pageIndex ? 1 : searchVO.pageIndex}" />
<c:set var="searchCondition"
	value="${empty searchVO.searchCondition ? '' : searchVO.searchCondition}" />
<c:set var="searchKeyword"
	value="${empty searchVO.searchKeyword ? '' : searchVO.searchKeyword}" />

<div class="nt-wrap">
	<!-- 상세 박스 -->
	<div class="nt-box">

		<!-- 제목 -->
		<h2 class="nt-title">
			<c:if test="${notice.pinnedAt eq 'Y'}">
				<span class="nt-badge-notice">[공지]</span>
			</c:if>
			<c:out value="${notice.subject}" />
		</h2>

		<!-- 메타 정보 -->
		<div class="nt-meta">
			<span>작성자: <c:out value="${notice.frstRegisterId}" /></span> <span>작성일:
				<c:out value="${notice.frstRegistPnttm}" />
			</span> <span>조회수: <c:out value="${notice.viewCnt}" /></span>
		</div>

		<!-- 공지 기간 -->
		<c:if
			test="${notice.pinnedAt eq 'Y' && (not empty notice.startDate || not empty notice.endDate)}">
			<div class="nt-notice-period nt-muted">
				게시기간 :
				<c:out value="${notice.startDate}" />
				~
				<c:out value="${notice.endDate}" />
			</div>
		</c:if>

		<!-- 내용 -->
		<div class="nt-content">
			<c:out value="${notice.content}" />
		</div>

		<!-- 첨부파일 -->
		<div class="nt-files">
			<strong>첨부파일</strong>

			<c:choose>
				<c:when test="${empty fileList}">
					<div class="nt-muted">첨부파일이 없습니다.</div>
				</c:when>
				<c:otherwise>
					<ul>
						<c:forEach var="f" items="${fileList}">
							<li><a href="#" class="js-file-download"
								data-atch="${f.atchFileId}" data-sn="${f.fileSn}"> <c:out
										value="${f.orignlFileNm}" />
							</a> <span class="nt-muted"> (<c:out value="${f.fileSize}" />
									byte)
							</span></li>
						</c:forEach>
					</ul>

					<!-- 파일 다운로드 (소속검증용) -->
					<form id="downloadForm" method="post" action="<c:url value='/bbs/notice/downloadNoticeFile.do'/>">
						<input type="hidden" name="nttId" value="<c:out value='${notice.nttId}'/>" /> 
						<input type="hidden" name="atchFileId" /> 
						<input type="hidden" name="fileSn" />
					</form>
				</c:otherwise>
			</c:choose>
		</div>

		<!-- 버튼 -->
		<div class="nt-actions">
			<a class="nt-btn"
				href="<c:url value='/bbs/notice/list.do'/>?pageIndex=${pageIndex}&searchCondition=${fn:escapeXml(searchCondition)}&searchKeyword=${fn:escapeXml(searchKeyword)}">
				목록 </a>

			<c:if test="${canEdit eq true}">
				<a class="nt-btn"
					href="<c:url value='/bbs/notice/updateNoticeView.do'/>?nttId=${notice.nttId}&pageIndex=${pageIndex}">
					수정 </a>

				<button type="button" class="js-delete nt-btn">삭제</button>

				<!-- 논리 삭제 -->
				<form id="deleteForm" method="post" action="<c:url value='/bbs/notice/deleteNotice.do'/>">
					<input type="hidden" name="nttId" value="<c:out value='${notice.nttId}'/>" /> 
					<input type="hidden" name="bbsId" value="<c:out value='${notice.bbsId}'/>" />
				</form>
			</c:if>
		</div>

	</div>
</div>
