var Login = function() {

	var login_submit = function() {
		$("button[class='close']").each(function() {
			this.click();
		});
		login(this.form.username.value, this.form.password.value);
	};

	var login = function(username, password) {
		var login_json = {
			"token" : null,
			"method" : "login",
			"data" : {}
		};
		login_json.data.username = username;
		login_json.data.password = password;
		Websocket.send(JSON.stringify(login_json));
	};

	var login_callback = function(data) {
		if (data.login == 0) {
			username = data.username;
			token = data.token;
			$("#login_div").hide();
			$("#main_div").show();
			
			//进行其他页面的初始化工作
			Hot.refreshHotList();
			
		} else {
			$("#login_failed").append(
				'<div class="alert alert-error">\
					<button class="close" data-dismiss="alert"></button>\
					<span>登录失败</span>\
				</div>'
			);
		}
	};

	return {
		// main function to initiate the module
		init : function() {

			$('button[data-type="submit"]').click(function() {
				//login_submit();
				if ($('.login-form').validate().form()) {
					login_submit();
				}
				return false;
			});

			$('.login-form').validate({
				errorElement : 'label', // default input error
				// message container
				errorClass : 'help-inline', // default input
				// error message
				// class
				focusInvalid : false, // do not focus the last
				// invalid input
				rules : {
					username : {
						required : true
					},
					password : {
						required : true
					}
				},

				messages : {
					username : {
						required : "用户名不能为空!"
					},
					password : {
						required : "密码不能为空！"
					}
				},

				invalidHandler : function(event, validator) { // display error alert on form submit
					$('.alert-error', $('.login-form')).show();
				},

				submitHandler : function(form) {
					// alert("提交事件!");
					// form.submit();
					return;
				},

				highlight : function(element) { // hightlight
					// error inputs
					$(element).closest('.control-group').addClass('error'); // set error
					// class to the
					// control group
				},

				success : function(label) {
					label.closest('.control-group').removeClass('error');
					label.remove();
				},

				errorPlacement : function(error, element) {
					error.addClass('help-small no-left-padding').insertAfter(
							element.closest('.input-icon'));
				}

			});

			$('.login-form input').keypress(function(e) {
				if (e.which == 13) {
					if ($('.login-form').validate().form()) {
						login_submit();
					}
					return false;
				}
			});

		},

		login_callback : function(data) {
			login_callback(data);
		}

	};

}();