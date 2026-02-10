$(function() {
	$("#btnLogin").on("click", function() {
		var userId = $("#userId").val();
		var password = $("#password").val();

		var $msg = $("#msg");
		$msg.removeClass("ok err").text("");

		if (!userId || !password) {
			$msg.addClass("err").text("아이디/비밀번호를 입력하세요.");
			return;
		}

		$.ajax({
			url: "/bbs/user/login.do",
			type: "POST",
			dataType: "json",
			data: { userId: userId, password: password },
			success: function(res) {
				if (res && res.result === "OK") {
					location.href = "/bbs/notice/list.do";
					return;
				}
				$msg.addClass("err").text("로그인 실패");
			},
			error: function(xhr) {
				if (xhr.responseJSON && xhr.responseJSON.error && xhr.responseJSON.error.message) {
					$msg.addClass("err").text(xhr.responseJSON.error.message);
					return;
				}
				$msg.addClass("err").text("로그인 실패");
			}
		});
	});

	$("#btnJoin").on("click", function() {
		location.href = "/bbs/user/joinView.do";
	});
});
