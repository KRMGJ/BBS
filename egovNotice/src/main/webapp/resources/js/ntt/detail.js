$(function() {

	// 첨부파일 다운로드 (소속검증 전제: nttId + atchFileId + fileSn POST)
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

	// 삭제 (논리삭제) - 서버에서 권한 재검증 필수
	$(document).on("click", ".js-delete", function(e) {
		e.preventDefault();

		var $form = $("#deleteForm");
		if ($form.length === 0) {
			alert("삭제 폼이 없습니다.");
			return;
		}

		if (!confirm("삭제하시겠습니까?\n삭제된 글은 목록에서 보이지 않습니다.")) {
			return;
		}

		$form.submit();
	});

});

$(document).ready(function() {

	const nttId = $("#nttId").val();
	const loginUserId = $("#loginUserId").val();

	$("#btnNoticeLike").on("click", function() {

		if (!loginUserId) {
			alert("로그인 후 이용하세요.");
			return;
		}

		const $btn = $(this);
		const $count = $("#noticeLikeCnt");

		$.ajax({
			type: "POST",
			url: "/bbs/notice/like.do",
			data: { nttId: nttId },
			success: function(res) {

				let current = parseInt($count.text()) || 0;

				if (res === "LIKED") {
					$count.text(current + 1);
					$btn.addClass("active");
				}
				else if (res === "UNLIKED") {
					$count.text(current - 1);
					$btn.removeClass("active");
				}
				else {
					alert("처리 중 오류가 발생했습니다.");
				}
			},
			error: function() {
				alert("서버 오류가 발생했습니다.");
			}
		});
	});

});
