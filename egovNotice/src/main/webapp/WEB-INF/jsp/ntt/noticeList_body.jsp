<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<div class="nt-wrap">
	<form id="searchForm" method="get">
		<input type="hidden" name="pageIndex" value="${empty searchVO.pageIndex ? 1 : searchVO.pageIndex}" /> 
		<input type="hidden" name="bbsId" value="${searchVO.bbsId}" />

		<div class="nt-toolbar">
			<div class="nt-search">
				<select name="searchCondition">
					<option value="0"
						<c:if test="${searchVO.searchCondition == '0'}">selected</c:if>>제목</option>
					<option value="1"
						<c:if test="${searchVO.searchCondition == '1'}">selected</c:if>>내용</option>
					<option value="2"
						<c:if test="${searchVO.searchCondition == '2'}">selected</c:if>>작성자</option>
				</select> 
				<input type="text" name="searchKeyword" value="${fn:escapeXml(searchVO.searchKeyword)}" placeholder="검색어 입력" />
				<button type="button" id="btnSearch">검색</button>
			</div>

			<div>
				<button type="button" id="btnReg">등록</button>
			</div>
		</div>
	</form>

	<div class="nt-muted">
		총 <b>
		<c:out value="${empty paginationInfo ? (empty totalCount ? 0 : totalCount) : paginationInfo.totalRecordCount}" />
		</b>
		건
	</div>

	<table>
		<thead>
			<tr>
				<th class="nt-col-no">번호</th>
				<th>제목</th>
				<th class="nt-col-date">등록일</th>
				<th class="nt-col-view">조회</th>
			</tr>
		</thead>

		<tbody>
			<!-- 공지 상단 -->
			<c:if test="${not empty pinnedList}">
				<c:forEach var="n" items="${pinnedList}">
					<tr class="nt-notice">
						<td class="nt-col-no">공지</td>
						<td>
							<a href="<c:url value='/notice/selectNoticeDetail.do'><c:param name='nttId' value='${n.nttId}'/></c:url>">
								<c:out value="${n.nttSj}" />
							</a> 
							<c:if test="${not empty n.atchFileId}">
								<span class="nt-muted">[첨부]</span>
							</c:if>
						</td>
						<td class="nt-col-date"><c:out value="${n.frstRegistPnttm}" /></td>
						<td class="nt-col-view"><c:out value="${n.inqireCo}" /></td>
					</tr>
				</c:forEach>
			</c:if>

			<!-- 일반 목록 -->
			<c:choose>
				<c:when test="${not empty noticeList}">
					<c:forEach var="n" items="${noticeList}">
						<tr>
							<td class="nt-col-no"><c:out value="${n.nttId}" /></td>
							<td>
								<a href="<c:url value='/notice/selectNoticeDetail.do'><c:param name='nttId' value='${n.nttId}'/></c:url>">
									<c:out value="${n.nttSj}" />
								</a> 
								<c:if test="${not empty n.atchFileId}">
									<span class="muted">[첨부]</span>
								</c:if>
							</td>
							<td class="nt-col-date"><c:out value="${n.frstRegistPnttm}" /></td>
							<td class="nt-col-view"><c:out value="${n.inqireCo}" /></td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="4" style="text-align: center; color: #666;">조회된 데이터가 없습니다.</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>

	<div class="pagination">
		<c:if test="${not empty paginationInfo}">
			<ui:pagination paginationInfo="${paginationInfo}" type="text" jsFunction="fn_egov_link_page" />
		</c:if>
	</div>
</div>
