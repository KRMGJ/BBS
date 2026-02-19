function link_page(pageNo) {
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

$(document).ready(function() {

	$("#btnDelete").on("click", function() {

		const checked = $("input[name='nttId']:checked");

		if (checked.length === 0) {
			alert("삭제할 게시글을 선택하세요.");
			return;
		}

		if (!confirm("선택한 게시글을 삭제하시겠습니까?")) {
			return;
		}

		let nttIdArr = [];

		checked.each(function() {
			nttIdArr.push($(this).val());
		});

		$.ajax({
			url: "/bbs/notice/deleteList.do",
			type: "POST",
			traditional: true,
			data: { nttIdList: nttIdArr },
			success: function() {
				alert("삭제되었습니다.");
				location.reload();
			},
			error: function() {
				alert("삭제 중 오류가 발생했습니다.");
			}
		});
	});

	$(document).on("click", ".btnDeleteReply", function() {

		const nttId = $(this).data("id");

		if (!confirm("댓글을 삭제하시겠습니까?")) {
			return;
		}

		$.ajax({
			url: "/bbs/notice/deleteList.do",
			type: "POST",
			data: { nttId: nttId },
			success: function() {
				alert("댓글이 삭제되었습니다.");
				location.reload();
			},
			error: function() {
				alert("댓글 삭제 중 오류가 발생했습니다.");
			}
		});
	});

});

$(window).on("load", function() {

	const opened = JSON.parse(localStorage.getItem("openedRoots") || "[]");

	if (!opened.length) return;

	opened.forEach(function(rootId) {

		const children = $(".nt-reply-row[data-root='" + rootId + "']");
		const parent = $(".parent-title[data-root='" + rootId + "']");
		const icon = parent.find(".toggle-icon");

		if (children.length > 0) {
			children.show();
			icon.text("▼");
		}

	});

});


$(document).on("click", ".parent-title", function(e) {

	if ($(e.target).is("a")) return;

	const rootId = $(this).data("root");
	const children = $(".nt-reply-row[data-root='" + rootId + "']");
	const icon = $(this).find(".toggle-icon");

	let opened = JSON.parse(localStorage.getItem("openedRoots") || "[]");

	if (children.first().is(":visible")) {
		children.hide();
		icon.text("▶");

		// 열린 상태에서 닫히면 opened 배열에서 제거
		opened = opened.filter(id => id != rootId);

	} else {
		children.show();
		icon.text("▼");

		if (!opened.includes(String(rootId))) {
			opened.push(String(rootId));
		}
	}

	localStorage.setItem("openedRoots", JSON.stringify(opened));
});

$(function() {

	$(document).on("click", ".btnReply", function(e) {

		e.stopPropagation();

		const parentId = $(this).data("parent");
		const row = $(this).closest("tr");

		let targetRow = row;
		if (row.next().hasClass("nt-content")) {
			targetRow = row.next();
		}

		$("#parntNttId").val(parentId);

		$("#replyRow")
			.insertAfter(targetRow)
			.show();

	});

	$("#btnCancelReply").on("click", function() {
		$("#replyRow").hide();
	});

});


