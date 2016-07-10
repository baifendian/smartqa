var Menu = function() {

	return {

		init : function() {
			$("div[class*='menu_item']").each(function() {
				$(this).click(function() {
					//alert($(this).attr("data-tab"));
					tab($(this).attr("data-tab"));
				});
			});
		}

	};

}();

function tab(target) {
	if(target == 'hot')
	{
		Hot.refreshHotList();
	}
	
	$("div[class*='main_view']").each(function() {
		var active = (target == $(this).attr("data-tab"));
		
		$(this).animate({
			width : active ? '25%' : 0
		});
		$(this).children("div").each(function() {
			if (active) {
				$(this).show();
			} else {
				$(this).hide();
			}
		});
	});
}