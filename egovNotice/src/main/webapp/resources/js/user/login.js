$(function() {
	$("#btnLogin").on("click", function() {
		var userId = $("#userId").val();
		var password = $("#password").val();

		if (!userId || !password) {
			$("#msg").text("아이디/비밀번호를 입력하세요.");
			return;
		}

		$.ajax({
			url: "/bbs/user/login.do",
			type: "POST",
			dataType: "json",
			data: { userId: userId, password: password },
			success: function(res) {
				if (res && res.result === "OK") {
					location.href = "/notice/list.do";
					return;
				}
				$("#msg").text("로그인 실패");
			},
			error: function(xhr) {
				if (xhr.responseJSON && xhr.responseJSON.error && xhr.responseJSON.error.message) {
					$("#msg").text(xhr.responseJSON.error.message);
					return;
				}
				$("#msg").text("로그인 실패");
			}
		});
	});
});
