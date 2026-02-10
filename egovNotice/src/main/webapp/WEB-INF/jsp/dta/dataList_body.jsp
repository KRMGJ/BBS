<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>

<div class="dta-wrap">

	<form id="searchForm" method="get"
		action="<c:url value='/bbs/dta/list.do'/>">

		<input type="hidden" name="pageIndex"
			value="${empty searchVO.pageIndex ? 1 : searchVO.pageIndex}" />

		<div class="dta-toolbar">
			<div class="dta-search">
				<select name="searchCondition">
					<option value="0"
						<c:if test="${searchVO.searchCondition eq '0'}">selected</c:if>>제목</option>
					<option value="1"
						<c:if test="${searchVO.searchCondition eq '1'}">selected</c:if>>내용</option>
					<option value="2"
						<c:if test="${searchVO.searchCondition eq '2'}">selected</c:if>>작성자</option>
				</select> <input type="text" name="searchKeyword"
					value="${fn:escapeXml(searchVO.searchKeyword)}"
					placeholder="검색어 입력" />

				<button type="submit">검색</button>
			</div>
		</div>
	</form>

	<div class="dta-muted">
		총 <b><c:out value="${paginationInfo.totalRecordCount}" /></b>건
	</div>

	<table class="dta-table">
		<thead>
			<tr>
				<th class="dta-col-no">번호</th>
				<th>제목</th>
				<th class="dta-col-attach">첨부</th>
				<th class="dta-col-date">작성일</th>
				<th class="dta-col-view">조회수</th>
			</tr>
		</thead>
		<tbody>

			<c:if test="${empty resultList}">
				<tr>
					<td colspan="5" style="text-align: center; color: #666;">등록된
						자료가 없습니다.</td>
				</tr>
			</c:if>

			<c:forEach var="row" items="${resultList}" varStatus="status">
				<tr>
					<td class="dta-col-no">${paginationInfo.totalRecordCount
						  - ((paginationInfo.currentPageNo - 1)
						  * paginationInfo.recordCountPerPage + status.index)}
					</td>

					<td class="dta-title-td"><a class="dta-title-link"
						href="<c:url value='/bbs/dta/detail.do'>
									<c:param name='nttId' value='${row.nttId}'/>
								</c:url>">
							<c:out value="${row.subject}" />
					</a></td>

					<td class="dta-col-attach"><c:if
							test="${not empty row.atchFileId}">
							<span class="dta-attach">📎</span>
						</c:if></td>

					<td class="dta-col-date"><c:out
							value="${fn:substring(row.frstRegistPnttm, 0, 10)}" /></td>

					<td class="dta-col-view"><c:out value="${row.viewCnt}" /></td>
				</tr>
			</c:forEach>

		</tbody>
	</table>

	<div class="dta-pagination">
		<ui:pagination paginationInfo="${paginationInfo}"
			jsFunction="dtaGoPage" />
	</div>

</div>
