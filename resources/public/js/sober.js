var url = window.location;
// Will only work if string in href matches with location
$('ul.nav a[href="'+ url +'"]').parent().addClass('active');

// Will also work for relative and absolute hrefs
$('ul.nav a').filter(function() {
    return this.href == url;
}).parent().addClass('active');

// button 
$('#addOffer').on('click', function () {
    window.location.href ="offer/add";
})

// swithc 插件设置
$.fn.bootstrapSwitch.defaults.size = 'mini';
$.fn.bootstrapSwitch.defaults.onColor = 'success';
$("[name='user-enable-checkbox']").bootstrapSwitch();

$('#reviewModal').on('show.bs.modal', function(event) {
	var button = $(event.relatedTarget);
	var datajson = button.data('whatever');
	// If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
	// Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
	var modal = $(this)
	  modal.find('.modal-title').text('管理员审核：' + datajson.review_text)
	//  modal.find('.modal-body input').val(userDesc)
	
	////jquery 修改action提交地址
	$("#review-form").attr("action", datajson.action);
	
	//添加隐藏字段 id
    var hidden_filed="<input type='hidden' name='id' value='" +  datajson.review_target + "'>";
    $("#review-form").append(hidden_filed);
	
})

$('#reviewModal').on('shown.bs.modal', function() {
	$('#review-textarea').focus();
})