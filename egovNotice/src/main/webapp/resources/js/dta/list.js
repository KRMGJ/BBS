function dtaGoPage(pageNo) {
	var form = $('#dta-search-form');
	form.find('input[name="pageIndex"]').val(pageNo);
	form.submit();
}
