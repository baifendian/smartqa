var Chat = function() {
	
	var chat = function(roomid, msg) {
		var chat_json = {
			"token" : token,
			"method" : "chat",
			"data" : {}
		};
		chat_json.data.roomid = roomid;
		chat_json.data.msg = msg;
		Websocket.send(JSON.stringify(chat_json));
	};
	
	var chat_callback = function(data) {
		var groupid = data.roomid;
		var mine = data.isme;
		var name = data.sendername;
		var msg = data.msg;
		if (mine == "1") {
			$('div[data-role="blob_background"][data-groupid="' + groupid + '"]').append(
				'<div class="blob">\
					<table class="blob_view" data-mine="true">\
						<tbody>\
							<tr>\
								<td class="blob_fill"></td>\
								<td class="blob_text">' + msg + '</td>\
								<td class="blob_border"></td>\
								<td class="blob_user">\
									<div class="blob_user">' + name + '</div>\
								</td>\
								<td class="blob_border"></td>\
							</tr>\
						</tbody>\
					</table>\
				</div>'
			);
		} else {
			$('div[data-role="blob_background"][data-groupid="' + groupid + '"]').append(
				'<div class="blob">\
					<table class="blob_view" data-mine="false">\
						<tbody>\
							<tr>\
								<td class="blob_border"></td>\
								<td class="blob_user">\
									<div class="blob_user">' + name + '</div>\
								</td>\
								<td class="blob_border"></td>\
								<td class="blob_text">' + msg + '</td>\
								<td class="blob_fill"></td>\
							</tr>\
						</tbody>\
					</table>\
				</div>'
			);
			new_msg_ui(groupid);
		}
		var tar_div = $('div[data-role="blob_background"][data-groupid="' + groupid + '"]');
		tar_div.scrollTop(tar_div[0].scrollHeight);
	};
	
	var new_msg_ui = function(groupid) {
		if ($('div[data-role="blob_background"][data-groupid="' + groupid + '"]').css("display") == "none") {
			var local = $('div[class="group_btn"][data-groupid="' + groupid + '"]').children("div");
			if (local.find("span").length == 0) {
				local.append('<span class="badge badge-important">1</span>');
			} else {
				local.find("span").html(Number(local.find("span").html()) + 1);
			}
		}
		audio.play();
	};
	
	var joinchatroom_callback = function(data) {
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
		audio.play();
		alert(data.msg);
	}

	return {

		init : function() {

			$('#close_btn').click(function() {
				chat_callback({
					roomid : "-1",
					isme : "0",
					sendername : "邝俊玮",
					msg : "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈"
				});
			});
			
			$('button[data-type="send"]').click(function() {
				var roomid = $("#close_btn").attr("data-groupid");
				var msg = $("input[name='chat_send_text']").val();
				if (msg == "" || msg == null) {
					return;
				}
				chat(roomid, msg);
				$("input[name='chat_send_text']").val("");
			});
			
			$(document).on("click", 'div[class="group_btn"]', function() {
				var groupid = $(this).attr("data-groupid");
				var title = $(this).attr("data-title");
				$("#close_btn").attr("data-groupid", groupid);
				$('div[class="chat_room_title"]').html(title);
				$('div[data-role="blob_background"][data-groupid!=' + groupid + ']').each(function() {
					$(this).hide();
				});
				$('div[data-role="blob_background"][data-groupid=' + groupid + ']').each(function() {
					$(this).show();
				});
				$('div[data-role="menber_list"][data-groupid!=' + groupid + ']').each(function() {
					$(this).hide();
				});
				$('div[data-role="menber_list"][data-groupid=' + groupid + ']').each(function() {
					$(this).show();
				});
				if ($(this).find("span").length != 0) {
					var tar_div = $('div[data-role="blob_background"][data-groupid="' + groupid + '"]');
					tar_div.scrollTop(tar_div[0].scrollHeight);
				}
				$(this).find("span").remove();
			});
			
			$("input[name='chat_send_text']").keypress(function(e) {
				if (e.which == 13) {
					$('button[data-type="send"]').click();
				}
			});

		},

		chat_callback : function(data) {
			chat_callback(data);
		},

		joinchatroom_callback : function(data) {
			joinchatroom_callback(data);
		}

	};

}();