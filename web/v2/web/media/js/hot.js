var Hot = function() {

	var handleInit = function() {
		//初始化热门讨论的list页面
		
    }
	
	return {

		init : function() {
			
		},
		
		refreshHotList : function(){
			var hot_init_json = {
			"token" : token,
			"method" : "refreshhotlist",
			"data" : {"count":10}
			};
			Websocket.send(JSON.stringify(hot_init_json));
		},
		
		refreshhotlist_callback : function(data){
			//将数据渲染到页面中
			$('div[data-role="hot_disscussion_list"]').html("");
			console.info(data.disccusions);
			var dislist = data.disccusions.split("[,]");
			$.each(dislist, function(n, dis_str) {
				var dis_split = dis_str.split("[|]");
				var subject = dis_split[1];
				var id = dis_split[0];
				$('div[data-role="hot_disscussion_list"]').append(
					'<div data-hdid=' + id + ' class="hot_disscussion_item">' + subject + '</div>'
				);
			});
			
		},


	};

}();