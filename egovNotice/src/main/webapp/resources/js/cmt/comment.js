$(document).ready(function() {

	const nttId = $("#nttId").val();
	const loginUserId = $("#loginUserId").val();

	if (!nttId) return;

	loadComments();

	$("#btnCommentSave").on("click", function() {
		const commentCn = $("#commentCn").val().trim();
		if (commentCn === "") return alert("댓글을 입력하세요.");

		$.post("/bbs/cmt/insert.do", {
			nttId: nttId,
			commentCn: commentCn
		}, function() {
			$("#commentCn").val("");
			loadComments();
		});
	});

	function loadComments() {
		$.getJSON("/bbs/cmt/list.do", { nttId: nttId }, function(list) {
			drawCommentList(list);
		});
	}

	function drawCommentList(list) {
		let html = "";
		$("#commentCnt").text(list.length);

		if (list.length === 0) {
			$("#commentList").html("<li class='comment-empty'>댓글이 없습니다.</li>");
			return;
		}

		$.each(list, function(i, c) {
			html += "<li class='comment-item' data-id='" + c.commentId + "'>";
			html += "  <div class='comment-info'>";
			html += "    <span class='writer'>" + (c.frstRegisterId || "익명") + "</span>";
			html += "    <span class='date'>" + (c.frstRegistPnttm || "") + "</span>";
			html += "  </div>";
			html += "  <div class='comment-cn'>" + c.commentCn + "</div>";
			html += "  <input type='hidden' id='frstRegisterId' value='" + c.frstRegisterId + "'/>";

			if (loginUserId && loginUserId === c.frstRegisterId) {
				html += "  <div class='comment-actions'>";
				html += "    <button class='btn-edit'>수정</button>";
				html += "    <button class='btn-delete'>삭제</button>";
				html += "  </div>";
			}

			html += "</li>";
		});

		$("#commentList").html(html);
	}

	$("#commentList").on("click", ".btn-delete", function() {
		if (!confirm("댓글을 삭제하시겠습니까?")) return;

		const commentId = $(this).closest(".comment-item").data("id");

		$.post("/bbs/cmt/delete.do", { commentId: commentId }, function() {
			loadComments();
		});
	});

	$("#commentList").on("click", ".btn-edit", function() {
		const $item = $(this).closest(".comment-item");
		const content = $item.find(".comment-cn").text();

		$item.find(".comment-cn").hide();
		$(this).parent().hide();

		const editHtml =
			"<div class='comment-edit-area'>" +
			"<textarea>" + content + "</textarea>" +
			"<button class='btn-save'>저장</button> " +
			"<button class='btn-cancel'>취소</button>" +
			"</div>";

		$item.append(editHtml);
	});

	$("#commentList").on("click", ".btn-save", function() {
		const $item = $(this).closest(".comment-item");
		const commentId = $item.data("id");
		const newContent = $item.find("textarea").val().trim();
		const frstRegisterId = $item.find("#frstRegisterId").val();

		if (newContent === "") return alert("내용을 입력하세요.");

		$.post("/bbs/cmt/update.do", {
			commentId: commentId,
			commentCn: newContent,
			frstRegisterId: frstRegisterId
		},
		function() {
			loadComments();
		});
	});

	$("#commentList").on("click", ".btn-cancel", function() {
		loadComments();
	});

});
