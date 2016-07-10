var username;
var token = null;

var methods = {
	login_callback : function(data) {
		Login.login_callback(data);
	},
	recommenduser_callback : function(data) {
		Question.recommend_user_callback(data);
	},
	recommenddiscussion_callback : function(data) {
		Question.recommend_discussion_callback(data);
	},
	createchatroom_callback : function(data) {
		Question.creat_chat_room_callback(data);
	},
	chat_callback : function(data) {
		Chat.chat_callback(data);
	},
	joinchatroom_callback : function(data) {
		Chat.joinchatroom_callback(data);
	},
	refreshhotlist_callback:function(data){
		Hot.refreshhotlist_callback(data);
	},
	close_callback : function(data) {
		Chat.close_chat_room_callback(data);
	}
}