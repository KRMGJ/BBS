(function(win, $) {
	"use strict";

	win.Bm = win.Bm || {};

	win.Bm.submit = function(formSelector, actionUrl) {
		var $form = $(formSelector);
		if (!$form.length) return;
		$form.attr("action", actionUrl);
		$form.trigger("submit");
	};

	win.Bm.setHidden = function(formSelector, name, value) {
		var $form = $(formSelector);
		var $el = $form.find("input[name='" + name + "']");
		if (!$el.length) {
			$el = $("<input/>", { type: "hidden", name: name });
			$form.append($el);
		}
		$el.val(value);
	};

	win.Bm.trim = function(v) {
		return $.trim(v || "");
	};

	win.Bm.require = function(value, message, focusSelector) {
		if (win.Bm.trim(value) === "") {
			alert(message);
			if (focusSelector) $(focusSelector).focus();
			return false;
		}
		return true;
	};

})(window, jQuery);
