var socket;
var url = "ws://localhost:10001/ws";

function realsend(message) {
	if (socket.readyState == WebSocket.OPEN) {
		socket.send(message);
	} else {
		alert("连接建立不成功");
	}
};

var Websocket = function() {

	var send = function(message) {
		if (!window.WebSocket) {
			return;
		}
		if (socket.readyState == WebSocket.OPEN) {
			socket.send(message);
		} else {
			socket.close();
			socket = new WebSocket(url);
			socket.onmessage = function(event) {
				console.log(event.data);
				var output = JSON.parse(event.data);
				if (output.code == 0) {
					dispather(output);
				} else {
					alert(output.msg);
				}
			};

			if (socket.readyState == WebSocket.OPEN) {
				socket.send(message);
			} else {
				setTimeout("realsend('" + message + "')", 1000)
			}

		}
	};

	var dispather = function(data) {
		methods[data.method + "_callback"](data.data);
	};

	return {

		init : function() {

			if (!window.WebSocket) {
				window.WebSocket = window.MozWebSocket;
			}

			if (window.WebSocket) {
				socket = new WebSocket(url);
				socket.onmessage = function(event) {
					console.log(event.data);
					var output = JSON.parse(event.data);
					if (output.code == 0) {
						dispather(output);
					} else {
						alert(output.msg);
					}
				};
			} else {
				alert("你的浏览器不支持 WebSocket！");
			}

		},

		send : function(message) {
			send(message);
		},

		dispather : function(data) {
			dispather(data);
		}

	};

}();