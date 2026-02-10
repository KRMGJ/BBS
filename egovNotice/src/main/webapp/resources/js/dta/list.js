function dtaGoPage(pageNo) {
	var form = $('#dta-search-form');
	form.find('input[name="pageIndex"]').val(pageNo);
	form.submit();
}

$(function() {
	$('#btnReg').on('click', function() {
		location.href = '/bbs/dta/form.do';
	});
});

