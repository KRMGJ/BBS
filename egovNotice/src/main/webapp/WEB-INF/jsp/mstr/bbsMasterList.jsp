<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>

<link rel="stylesheet" href="<c:url value='/resources/css/bbsMaster.css'/>" />

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="<c:url value='/resources/js/bbsMaster.common.js'/>"></script>
<script src="<c:url value='/resources/js/bbsMaster.list.js'/>"></script>

<div class="bm-wrap">
	<h2 class="bm-title">게시판 마스터 관리</h2>

	<form id="bmListForm" method="post"
		data-list-url="<c:url value='/admin/bbsMaster/selectBbsMasterList.do'/>"
		data-regist-view-url="<c:url value='/admin/bbsMaster/insertBbsMasterView.do'/>"
		data-detail-url="<c:url value='/admin/bbsMaster/selectBbsMasterDetail.do'/>">

		<input type="hidden" name="pageIndex" value="${searchVO.pageIndex}" />
		<input type="hidden" name="bbsId" value="" />

		<div class="bm-search">
			<div class="row">
				<div>
					<label for="searchCondition">조건</label> 
					<select id="searchCondition" name="searchCondition">
						<option value="0"
							<c:if test="${searchVO.searchCondition eq '0'}">selected</c:if>>게시판명</option>
						<option value="1"
							<c:if test="${searchVO.searchCondition eq '1'}">selected</c:if>>게시판ID</option>
					</select>
				</div>

				<div>
					<label for="searchKeyword">키워드</label> 
					<input type="text"
						id="searchKeyword" name="searchKeyword"
						value="${fn:escapeXml(searchVO.searchKeyword)}"
						style="width: 280px;" />
				</div>

				<div>
					<label for="useAt">사용</label> 
					<select id="useAt" name="useAt">
						<option value=""
							<c:if test="${empty searchVO.useAt}">selected</c:if>>전체</option>
						<option value="Y"
							<c:if test="${searchVO.useAt eq 'Y'}">selected</c:if>>Y</option>
						<option value="N"
							<c:if test="${searchVO.useAt eq 'N'}">selected</c:if>>N</option>
					</select>
				</div>

				<div class="btns">
					<button type="button" class="bm-btn primary" id="btnSearch">조회</button>
					<button type="button" class="bm-btn" id="btnRegistView">등록</button>
				</div>
			</div>
		</div>

		<table class="bm-table">
			<thead>
				<tr>
					<th style="width: 60px;">No</th>
					<th style="width: 240px;">게시판ID</th>
					<th>게시판명</th>
					<th style="width: 110px;">첨부가능</th>
					<th style="width: 110px;">최대개수</th>
					<th style="width: 130px;">최대용량(MB)</th>
					<th style="width: 80px;">사용</th>
					<th style="width: 160px;">등록일</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${empty resultList}">
					<tr>
						<td colspan="8" class="center">조회 결과가 없습니다.</td>
					</tr>
				</c:if>

				<c:forEach var="row" items="${resultList}" varStatus="st">
					<tr>
						<td class="center">
							<c:out value="${(paginationInfo.totalRecordCount - ((searchVO.pageIndex-1) * searchVO.pageSize)) - st.index}" />
						</td>
						<td><a href="#" class="bm-link-detail" data-bbs-id="${row.bbsId}"> 
								<c:out value="${row.bbsId}" />
							</a>
						</td>
						<td><c:out value="${row.bbsNm}" /></td>
						<td class="center"><c:out value="${row.fileAtchPosblAt}" /></td>
						<td class="right"><c:out value="${row.atchPosblFileNumber}" /></td>
						<td class="right"><c:out value="${row.atchPosblFileSize}" /></td>
						<td class="center"><c:out value="${row.useAtCol}" /></td>
						<td class="center"><c:out value="${row.frstRegistPnttm}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<div class="bm-paging">
			<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fn_bm_page" />
		</div>
	</form>
</div>
