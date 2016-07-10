jQuery(document).ready(function() {

	App.init();
	Websocket.init();
	Login.init();
	Menu.init();
	Question.init();
	Chat.init();
	Hot.init();
	
	$.backstretch([
		"media/image/bg/1.jpg",
		"media/image/bg/2.jpg",
		"media/image/bg/3.jpg",
		"media/image/bg/4.jpg"
	], {
		fade : 1000,
		duration : 8000
	});

	$('body').on('click', '.close', function(){
		$(this).parent().remove();
	});

});