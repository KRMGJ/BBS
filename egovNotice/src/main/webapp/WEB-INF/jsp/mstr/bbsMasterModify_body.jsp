<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="bm-wrap">
	<form id="bmModForm" method="post"
		data-update-url="<c:url value='/bbs/admin/bbsMaster/updateBbsMaster.do'/>"
		data-detail-url="<c:url value='/bbs/admin/bbsMaster/selectBbsMasterDetail.do'/>">
		<input type="hidden" name="bbsId" value="${bbsMasterVO.bbsId}" />

		<table class="bm-form">
			<tbody>
				<tr>
					<th>게시판ID</th>
					<td><c:out value="${bbsMasterVO.bbsId}" /></td>
				</tr>
				<tr>
					<th>게시판명 *</th>
					<td><input type="text" name="bbsNm" id="bbsNm" value="${fn:escapeXml(bbsMasterVO.bbsNm)}" /></td>
				</tr>
				<tr>
					<th>게시판 소개</th>
					<td><textarea name="bbsIntrcn" rows="4">${fn:escapeXml(bbsMasterVO.bbsIntrcn)}</textarea></td>
				</tr>
				<tr>
					<th>게시판 유형 코드</th>
					<td><input type="text" name="bbsTyCode" value="${fn:escapeXml(bbsMasterVO.bbsTyCode)}" /></td>
				</tr>
				<tr>
					<th>파일첨부 가능</th>
					<td>
						<select name="fileAtchPosblAt">
							<option value="Y"
								<c:if test="${bbsMasterVO.fileAtchPosblAt eq 'Y'}">selected</c:if>>Y</option>
							<option value="N"
								<c:if test="${bbsMasterVO.fileAtchPosblAt eq 'N'}">selected</c:if>>N</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>첨부 가능 파일 개수</th>
					<td><input type="number" name="atchPosblFileNumber" value="${bbsMasterVO.atchPosblFileNumber}" min="0" /></td>
				</tr>
				<tr>
					<th>첨부 가능 파일 크기(MB)</th>
					<td><input type="number" name="atchPosblFileSize" value="${bbsMasterVO.atchPosblFileSize}" min="0" /></td>
				</tr>
				<tr>
					<th>사용 여부</th>
					<td>
						<select name="useAt">
							<option value="Y"
								<c:if test="${bbsMasterVO.useAt eq 'Y'}">selected</c:if>>Y</option>
							<option value="N"
								<c:if test="${bbsMasterVO.useAt eq 'N'}">selected</c:if>>N</option>
						</select>
					</td>
				</tr>
			</tbody>
		</table>

		<div class="bm-actions">
			<button type="button" class="bm-btn primary" id="btnSave">저장</button>
			<button type="button" class="bm-btn" id="btnCancel">취소</button>
		</div>
	</form>
</div>
