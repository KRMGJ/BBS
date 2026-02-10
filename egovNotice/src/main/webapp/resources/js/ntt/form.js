$(function() {
	$("#btnList").on("click", function() {
		location.href = "/notice/list.do";
	});

	$("#btnSubmit").on("click", function() {
		var $f = $("#noticeForm");

		var title = $.trim($f.find("[name='subject']").val());
		var body = $.trim($f.find("[name='content']").val());

		if (!title) {
			alert("제목을 입력하세요.");
			$f.find("[name='subject']").focus();
			return;
		}
		if (!body) {
			alert("내용을 입력하세요.");
			$f.find("[name='content']").focus();
			return;
		}

		var b = $.trim($f.find("[name='startDate']").val());
		var e = $.trim($f.find("[name='endDate']").val());

		if ((b && b.length !== 8) || (e && e.length !== 8)) {
			alert("게시기간은 YYYYMMDD 형식으로 입력하세요.");
			return;
		}
		if (b && e && b > e) {
			alert("시작일은 종료일보다 클 수 없습니다.");
			return;
		}

		$f.submit();
	});
});