$(document).ready(function() {

	$('.dta-btn-delete').on('click', function(e) {
		if (!confirm('삭제하시겠습니까?')) {
			e.preventDefault();
		}
	});

});
