$(function() {
	$("#btnCheckId").on("click", function() {
		var $msg = $("#msg");
		$msg.removeClass("ok err").text("");

		var userId = $("#userId").val();
		if (!userId) {
			$msg.addClass("err").text("아이디를 입력하세요.");
			return;
		}

		$.get("/bbs/user/checkUserId.do", { userId: userId }, function(res) {
			if (res.duplicated) {
				$msg.addClass("err").text("이미 사용 중인 아이디입니다.");
			} else {
				$msg.addClass("ok").text("사용 가능한 아이디입니다.");
			}
		}, "json");
	});

	$("#btnJoin").on("click", function() {
		$.ajax({
			url: "/bbs/user/join.do",
			type: "POST",
			dataType: "json",
			data: {
				userId: $("#userId").val(),
				password: $("#password").val(),
				userNm: $("#userNm").val(),
				email: $("#email").val(),
				mobile: $("#mobile").val()
			},
			success: function(res) {
				if (res.result === "OK") {
					alert("회원가입이 완료되었습니다.");
					location.href = "/bbs/user/loginView.do";
				}
			},
			error: function(xhr) {
				if (xhr.responseJSON && xhr.responseJSON.error) {
					$msg.addClass("err").text(xhr.responseJSON.error.message);
				} else {
					$msg.addClass("err").text("회원가입 실패");
				}
			}
		});
	});

});
