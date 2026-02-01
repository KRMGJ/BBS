(function(win, $) {
	"use strict";

	function getUrl(name) {
		return $("#bmModForm").data(name);
	}

	$(function() {
		$("#btnCancel").on("click", function() {
			Bm.submit("#bmModForm", getUrl("detail-url"));
		});

		$("#btnSave").on("click", function() {
			if (!Bm.require($("#bbsNm").val(), "게시판명은 필수입니다.", "#bbsNm")) return;
			Bm.submit("#bmModForm", getUrl("update-url"));
		});
	});

})(window, jQuery);
