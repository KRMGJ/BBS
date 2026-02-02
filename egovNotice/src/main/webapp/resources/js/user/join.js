$(function() {
	$("#btnCheckId").on("click", function() {
		var userId = $("#userId").val();
		if (!userId) {
			$("#msg").text("아이디를 입력하세요.");
			return;
		}

		$.get("/user/checkUserId.do", { userId: userId }, function(res) {
			console.log(res);
			if (res.duplicated) {
				$("#msg").text("이미 사용 중인 아이디입니다.");
			} else {
				$("#msg").text("사용 가능한 아이디입니다.");
			}
		}, "json");
	});

	$("#btnJoin").on("click", function() {
		$.ajax({
			url: "/user/join.do",
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
					location.href = "/user/loginView.do";
				}
			},
			error: function(xhr) {
				if (xhr.responseJSON && xhr.responseJSON.error) {
					$("#msg").text(xhr.responseJSON.error.message);
				} else {
					$("#msg").text("회원가입 실패");
				}
			}
		});
	});

});
