$(document).ready(function() {

	const nttId = $("#nttId").val();
	const loginUserId = $("#loginUserId").val();

	if (!nttId) return;

	// 댓글 등록
	$("#btnCommentSave").on("click", function() {

		const commentCn = $("#commentCn").val().trim();
		const parentId = $("#parentId").val();

		if (commentCn === "") {
			alert("댓글을 입력하세요.");
			return;
		}

		$.post("/bbs/cmt/insert.do", {
			nttId: nttId,
			commentCn: commentCn,
			parentId: parentId
		}, function() {
			$("#commentCn").val("");
			$("#parentId").val("");
			loadComments();
		});
	});

	$("#commentList").on("click", ".btn-like", function() {

		if (!loginUserId) {
			alert("로그인 후 이용하세요.");
			return;
		}

		const commentId = $(this).data("id");
		const $btn = $(this);

		$.post("/bbs/cmt/like.do", {
			commentId: commentId
		}, function(res) {

			if (res === "LIKED") {
				let $count = $btn.find(".like-count");
				let current = parseInt($count.text()) || 0;
				$count.text(current + 1);
			} else if (res === "UNLIKED") {
				let $count = $btn.find(".like-count");
				let current = parseInt($count.text()) || 0;
				$count.text(current > 0 ? current - 1 : 0);
			}
		});
	});

	loadComments();

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

			const depth = c.commentDepth || 0;
			const margin = depth * 20;

			html += "<li class='comment-item' data-id='" + c.commentId + "' style='margin-left:" + margin + "px'>";

			html += "  <div class='comment-info'>";
			html += "    <span class='writer'>" + (c.frstRegisterId || "익명") + "</span>";
			html += "    <span class='date'>" + (c.frstRegistPnttm || "") + "</span>";
			html += "  </div>";

			html += "  <div class='comment-cn'>" + c.commentCn + "</div>";

			html += "  <div class='comment-actions'>";
			html += "    <button class='btn-reply'>답글</button>";

			// 좋아요 버튼 추가
			html += "    <button class='btn-like' data-id='" + c.commentId + "'>";
			html += "      👍 <span class='like-count'>" + (c.likeCnt || 0) + "</span>";
			html += "    </button>";

			if (loginUserId && loginUserId === c.frstRegisterId) {
				html += "    <button class='btn-edit'>수정</button>";
				html += "    <button class='btn-delete'>삭제</button>";
			}

			html += "  </div>";

			html += "</li>";
		});

		$("#commentList").html(html);
	}

	// 답글 버튼 클릭 → 해당 댓글 아래에 입력창 생성
	$("#commentList").on("click", ".btn-reply", function() {

		$(".reply-area").remove(); // 기존 입력창 제거

		const $item = $(this).closest(".comment-item");
		const parentId = $item.data("id");
		const depth = parseInt($item.css("margin-left")) || 0;

		const replyHtml =
			"<div class='reply-area' style='margin-left:" + (depth + 20) + "px'>" +
			"  <textarea class='reply-text' placeholder='답글을 입력하세요'></textarea>" +
			"  <div class='reply-btns'>" +
			"    <button class='btn-reply-save'>등록</button>" +
			"    <button class='btn-reply-cancel'>취소</button>" +
			"  </div>" +
			"</div>";

		$item.after(replyHtml);
		$(".reply-area").data("parentId", parentId);
	});

	// 답글 등록
	$("#commentList").on("click", ".btn-reply-save", function() {

		const $area = $(this).closest(".reply-area");
		const parentId = $area.data("parentId");
		const commentCn = $area.find(".reply-text").val().trim();

		if (commentCn === "") {
			alert("내용을 입력하세요.");
			return;
		}

		$.post("/bbs/cmt/insert.do", {
			nttId: nttId,
			commentCn: commentCn,
			parentId: parentId
		}, function() {
			loadComments();
		});
	});

	// 답글 취소
	$("#commentList").on("click", ".btn-reply-cancel", function() {
		$(".reply-area").remove();
	});

	// 삭제
	$("#commentList").on("click", ".btn-delete", function() {

		if (!confirm("댓글을 삭제하시겠습니까?")) return;

		const commentId = $(this).closest(".comment-item").data("id");

		$.post("/bbs/cmt/delete.do", {
			commentId: commentId,
			frstRegisterId: loginUserId
		}, function() {
			loadComments();
		});
	});

	// 수정
	$("#commentList").on("click", ".btn-edit", function() {

		const $item = $(this).closest(".comment-item");
		const content = $item.find(".comment-cn").text();

		$item.find(".comment-cn").hide();
		$item.find(".comment-actions").hide();

		const editHtml =
			"<div class='comment-edit-area'>" +
			"<textarea>" + content + "</textarea>" +
			"<button class='btn-save'>저장</button>" +
			"<button class='btn-cancel'>취소</button>" +
			"</div>";

		$item.append(editHtml);
	});

	// 수정 저장
	$("#commentList").on("click", ".btn-save", function() {

		console.log("저장 클릭");

		const $item = $(this).closest(".comment-item");
		const commentId = $item.data("id");
		const newContent = $item.find("textarea").val().trim();

		if (newContent === "") {
			alert("내용을 입력하세요.");
			return;
		}

		$.post("/bbs/cmt/update.do", {
			commentId: commentId,
			commentCn: newContent,
			frstRegisterId: loginUserId
		}, function() {
			loadComments();
		});
	});

	$("#commentList").on("click", ".btn-cancel", function() {
		loadComments();
	});

});
