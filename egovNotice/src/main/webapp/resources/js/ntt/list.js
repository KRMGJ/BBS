function fn_egov_link_page(pageNo) {
	$("#searchForm input[name='pageIndex']").val(pageNo);
	$("#searchForm").attr("action", "/bbs/notice/list.do").submit();
}

$(function() {
	$("#btnSearch").on("click", function() {
		$("#searchForm input[name='pageIndex']").val(1);
		$("#searchForm").attr("action", "/bbs/notice/list.do").submit();
	});
	$("#btnReg").on("click", function() {
		location.href = "/bbs/notice/form.do";
	});
});