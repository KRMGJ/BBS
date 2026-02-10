$(function() {
	$('#btnList').on('click', function() {
		location.href = '/bbs/dta/list.do';
	});

	$(document).on("click", ".js-file-download", function(e) {
		e.preventDefault();

		var atchFileId = $(this).data("atch");
		var fileSn = $(this).data("sn");

		if (atchFileId === undefined || atchFileId === null || atchFileId === "") {
			alert("첨부파일 정보가 올바르지 않습니다.");
			return;
		}
		if (fileSn === undefined || fileSn === null || fileSn === "") {
			alert("첨부파일 정보가 올바르지 않습니다.");
			return;
		}

		var $form = $("#downloadForm");
		if ($form.length === 0) {
			alert("다운로드 폼이 없습니다.");
			return;
		}

		$form.find("input[name='atchFileId']").val(atchFileId);
		$form.find("input[name='fileSn']").val(fileSn);
		$form.submit();
	});
});