<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="bm-wrap">
	<form id="bmRegForm" method="post"
		data-insert-url="<c:url value='/bbs/admin/bbsMaster/insertBbsMaster.do'/>"
		data-list-url="<c:url value='/bbs/admin/bbsMaster/selectBbsMasterList.do'/>">

		<table class="bm-form">
			<tbody>
				<tr>
					<th>게시판명 *</th>
					<td><input type="text" name="bbsNm" id="bbsNm" /></td>
				</tr>
				<tr>
					<th>게시판 소개</th>
					<td><textarea name="bbsIntrcn" rows="4"></textarea></td>
				</tr>
				<tr>
					<th>게시판 유형 코드</th>
					<td><input type="text" name="bbsTyCode" /></td>
				</tr>
				<tr>
					<th>파일첨부 가능</th>
					<td>
						<select name="fileAtchPosblAt">
							<option value="Y" selected>Y</option>
							<option value="N">N</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>첨부 가능 파일 개수</th>
					<td>
						<input type="number" name="atchPosblFileNumber" value="3" min="0" />
					</td>
				</tr>
				<tr>
					<th>첨부 가능 파일 크기(MB)</th>
					<td>
						<input type="number" name="atchPosblFileSize" value="50" min="0" />
					</td>
				</tr>
				<tr>
					<th>사용 여부</th>
					<td>
						<select name="useAt">
							<option value="Y" selected>Y</option>
							<option value="N">N</option>
						</select>
					</td>
				</tr>
			</tbody>
		</table>

		<div class="bm-actions">
			<button type="button" class="bm-btn primary" id="btnSave">저장</button>
			<button type="button" class="bm-btn" id="btnList">목록</button>
		</div>
	</form>
</div>
