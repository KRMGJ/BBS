<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="dta-board-detail">

	<h3 class="dta-title">
		<c:out value="${result.subject}" />
	</h3>

	<div class="dta-info">
		<span>작성자 : <c:out value="${result.frstRegisterId}" /></span> <span>작성일
			: <c:out value="${fn:substring(result.frstRegistPnttm, 0, 10)}" />
		</span> <span>조회수 : <c:out value="${result.viewCnt}" /></span>
	</div>

	<div class="dta-content">
		<c:out value="${result.content}" escapeXml="false" />
	</div>

	<c:if test="${not empty result.atchFileId}">
		<div class="dta-attach">
			<h4>첨부파일</h4>
			<ul>
				<c:forEach var="file" items="${fileList}">
					<li><a class="dta-file-link"
						href="<c:url value='/cmm/fms/FileDown.do'>
                                    <c:param name='atchFileId' value='${file.atchFileId}'/>
                                    <c:param name='fileSn' value='${file.fileSn}'/>
                                 </c:url>">
							<c:out value="${file.orignlFileNm}" />
					</a> (<c:out value="${file.fileSize}" /> byte)</li>
				</c:forEach>
			</ul>
		</div>
	</c:if>

	<div class="dta-btn-area">
		<a class="dta-btn" href="<c:url value='/bbs/dta/list.do'/>">목록</a> <a
			class="dta-btn"
			href="<c:url value='/bbs/dta/modify.do'>
                    <c:param name='nttId' value='${result.nttId}'/>
                 </c:url>">수정</a>
		<a class="dta-btn dta-btn-delete"
			href="<c:url value='/bbs/dta/delete.do'>
                    <c:param name='nttId' value='${result.nttId}'/>
                 </c:url>">삭제</a>
	</div>

</div>
