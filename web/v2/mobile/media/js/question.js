var Question = function() {
	
	var subject;
	
	var recommend_user = function(str) {
		var recommend_user_json = {
			"token" : token,
			"method" : "recommenduser",
			"data" : {}
		};
		recommend_user_json.data.subject = str;
		Websocket.send(JSON.stringify(recommend_user_json));
	};
	
	var recommend_user_callback = function(data) {
		$("#recommend_user_list").html("");
		if (data.userlist == "" || data.userlist == null) {
			return;
		}
		var userlist = data.userlist.split("[,]");
		$.each(userlist, function(n, user_str) {
			var user_split = user_str.split("[|]");
			var username = user_split[1];
			var userid = user_split[0];
			var userexp = user_split[2];
			$("#recommend_user_list").append(
				'<div class="recommend_user">\
					<div data-type="recommend_user_background" data-selected="false" data-name="' + username + '" data-userid="' + userid + '">\
						<div data-type="recommend_user_background">\
							<div class="recommend_user_background" data-type="recommend_user_background">\
								<label>' + username + '（经验值：' + userexp + '）</label>\
							</div>\
						</div>\
					</div>\
				</div>'
			);
		});
	};
	
	var recommend_discussion = function(str) {
		var recommend_discussion_json = {
			"token" : token,
			"method" : "recommenddiscussion",
			"data" : {}
		};
		recommend_discussion_json.data.subject = str;
		Websocket.send(JSON.stringify(recommend_discussion_json));
	};
	
	var recommend_discussion_callback = function(data) {
		$("#recommend_chat_list").html("");
		if (data.discussionlist == "" || data.discussionlist == null) {
			return;
		}
		var chatlist = data.discussionlist.split("[,]");
		$.each(chatlist, function(n, chat_str) {
			var chat_split = chat_str.split("[|]");
			var chat = chat_split[1];
			var chatid = chat_split[0];
			$("#recommend_chat_list").append(
				'<div class="recommend_chat" data-chatid=' + chatid + '>\
					' + chat + '\
				</div>'
			);
		});
	};
	
	var creat_chat_room = function(user_ids, subject) {
		var creat_chat_room_json = {
			"token" : token,
			"method" : "creat_chat_room",
			"data" : {}
		};
		creat_chat_room_json.data.user_ids = user_ids;
		creat_chat_room_json.data.subject = subject;
		Websocket.send(JSON.stringify(creat_chat_room_json));
	};
	
	var creat_chat_room_callback = function(data) {
		var groupid = data.roomid;
		var name = data.roomname;
		var title = data.subject;
		var userlist = data.userlist.split("[,]");
		$('div[class="group_list"]').append(
			'<div class="group_btn" data-groupid="' + groupid + '" data-title="' + title + '">\
				<div class="icon-btn extra">\
					<div class="group_name">' + name + '</div>\
				</div>\
			</div>'
		);
		$('#blob_background_list').append(
			'<div class="blob_background hide" data-role="blob_background" data-groupid="' + groupid + '"></div>'
		);
		var ap_str = '<div data-role="menber_list" data-groupid="' + groupid + '">';
		$.each(userlist, function(n, user_str) {
			var user_split = user_str.split("[|]");
			var userid = user_split[0];
			var name = user_split[1];
			var online = user_split[2];
			ap_str +=
				'<div class="member">\
					<div class="member_view' + ((online == '1') ? ' online' : '') + '" data-userid="' + userid + ' style="display: none;"">\
						<div class="member_background">\
							<div class="member_background">\
								<div class="member_background">\
									<div class="menber_name">' + name + '</div>\
								</div>\
							</div>\
						</div>\
					</div>\
				</div>';
		});
		$('div[class="member_list_view"]').append(ap_str + '</div>');
		$('div[class="group_btn"][data-groupid="' + groupid + '"]').click();
		$('div[data-tab="group"]').click();
	};

	return {

		init : function() {

			$('div').each(function() {
				var this_div = $(this);
				$(this).scroll(function() {
					var top = this_div.scrollTop();
					this_div.find("label").each(function() {
						$(this).css("top", top + "px");
					});
					this_div.find("button").each(function() {
						$(this).css("bottom", "-" + top + "px");
					});
				});
			});

			$('button[data-role="question_search"]').click(function(){
				var str = $('input[data-type="question_search"]').val();
				if (str == "" || str == null) {
					return;
				}
				subject = str;
				recommend_user(str);
				recommend_discussion(str);
			});
			
			$('button[data-role="new_group"]').click(function(){
				var user_ids = "";
				$('div[data-selected="true"]').each(function() {
					user_ids = user_ids + $(this).attr("data-userid") + "|" + $(this).attr("data-name") + ",";
				});
				user_ids = user_ids.substring(0, user_ids.length - 1);
				creat_chat_room(user_ids, subject);
			});

			$(document).on("click", 'div[class="recommend_user"]', function() {
				var background = "url(../media/image/bg-white-lock.png) repeat"
				$(this).children("div").each(function() {
					if ($(this).attr("data-selected") == "false") {
						$(this).css("background", background);
						$(this).find("div").each(function() {
							$(this).css("background", background);
						});
						$(this).attr("data-selected", "true");
					} else {
						$(this).css("background", "");
						$(this).find("div").each(function() {
							$(this).css("background", "");
						});
						$(this).attr("data-selected", "false");
					}
				});
			});
			
			$('input[data-type="question_search"]').keypress(function(e) {
				if (e.which == 13) {
					$('button[data-role="question_search"]').click();
				}
			});

		},

		recommend_user_callback : function(data) {
			recommend_user_callback(data);
		},

		recommend_discussion_callback : function(data) {
			recommend_discussion_callback(data);
		},

		creat_chat_room_callback : function(data) {
			creat_chat_room_callback(data);
		}

	};

}();