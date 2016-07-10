<!DOCTYPE html>
<html class="full_page">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<title>SmartQA</title>
<link href="<?PHP echo $theme; ?>/media/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="<?PHP echo $theme; ?>/media/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="<?PHP echo $theme; ?>/media/css/style-metro.css" rel="stylesheet" type="text/css" />
<link href="<?PHP echo $theme; ?>/media/css/style.css" rel="stylesheet" type="text/css" />
<link href="<?PHP echo $theme; ?>/media/css/login-soft.css" rel="stylesheet" type="text/css" />
<link href="<?PHP echo $theme; ?>/media/css/index.css" rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="favicon.ico" />
</head>
<body class="login full_page">
	<div id="login_div" class="">
		<div class="logo">
			<img src="<?PHP echo $theme; ?>/media/image/logo-big.png" alt="" />
		</div>
		<div class="content">
			<form name="form" class="form-vertical login-form">
				<h3 class="form-title">登录</h3>
				<div id="login_failed"></div>
				<div class="alert alert-error hide">
					<button class="close" data-dismiss="alert"></button>
					<span>Email和密码不能为空！</span>
				</div>
				<div class="control-group">
					<label class="control-label visible-ie8 visible-ie9">用户名</label>
					<div class="controls">
						<div class="input-icon left">
							<i class="icon-user"></i>
							<input class="m-wrap placeholder-no-fix" type="text" placeholder="用户名" name="username" />
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label visible-ie8 visible-ie9">密码</label>
					<div class="controls">
						<div class="input-icon left">
							<i class="icon-lock"></i>
							<input class="m-wrap placeholder-no-fix" type="password" placeholder="密码" name="password" />
						</div>
					</div>
				</div>
				<div class="form-actions">
					<button data-type="submit" type="button" class="btn blue pull-right">
						登录<i class="m-icon-swapright m-icon-white"></i>
					</button>
				</div>
			</form>
		</div>
	</div>
	<div id="main_div" class="full_page hide">
		<div class="page-container full_page">
			<div class="main_page full_page">
				<div class="row-fluid menu_bar">
					<div class="menu_item btn blue" data-tab="question">
						<i class="icon-question-sign"></i>
						<div>提问</div>
					</div>
					<div class="menu_item btn green" data-tab="group">
						<i class="icon-group"></i>
						<div>讨论组</div>
					</div>
					<div class="menu_item btn yellow" data-tab="hot">
						<i class="icon-fire"></i>
						<div>热门</div>
					</div>
					<div class="menu_item btn purple" data-tab="user">
						<i class="icon-user"></i>
						<div>我</div>
					</div>
				</div>
				<div class="row-fluid full_page base_view overflow">
					<div class="full_page base_view width">
						<div data-tab="question" class="main_view full_page default">
							<div class="control-group row-fluid">
								<label class="control-label visible-ie8 visible-ie9"></label>
								<div class="controls">
									<div class="controls question">
										<input data-type="question_search" class="m-wrap span8 placeholder-no-fix question" type="text" placeholder="请输入问题..." name="question" />
										<button data-role="question_search" type="button" class="btn blue">
											搜索<i class="m-icon-swapright m-icon-white"></i>
										</button>
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span12"></div>
								<div class="span12"></div>
							</div>
							<div class="full_page">
								<div class="recommend_user_list" >
									<label class="float_label">推荐人选：</label>
									<button data-role="new_group" type="button" class="btn blue float_btn">
										组成讨论组<i class="m-icon-swapright m-icon-white"></i>
									</button>
									<div id="recommend_user_list"></div>
								</div>
								<div class="recommend_chat_list">
									<label class="float_label">相关讨论记录：</label>
									<div id="recommend_chat_list"></div>
								</div>
							</div>
						</div>
						<div data-tab="group" class="main_view full_page">
							<div class="full_page">
								<div class="group_list"></div>
								<div class="chat_view">
									<div class="chat_text">
										<div class="chat_menu_bar">
											<div class="toolbar">
												<div id="close_btn" class="btn mini red toolitem" data-groupid="" style="display: none;">
													<i class="icon-remove"></i>关闭
												</div>
											</div>
											<div class="chat_room_title"></div>
										</div>
										<div id="blob_background_list" class="full_page"></div>
									</div>
									<div class="chat_send">
										<div class="chat_send_text">
											<input class="m-wrap chat_send_text" type="text" name="chat_send_text" />
										</div>
										<div class="chat_send_btn">
											<button data-type="send" type="button" class="btn blue">
												发送<i class="m-icon-swapright m-icon-white"></i>
											</button>
											<audio id='audio' src='media/voice/msg.mp3'/>
										</div>
									</div>
								</div>
								<div class="member_list">
									<div class="member_title">讨论组成员</div>
									<div class="member_list_view"></div>
								</div>
							</div>
						</div>
						<div data-tab="hot" class="main_view full_page">
							<div class="hot_view">
								<div class="recommend_disccusion">
									<label class="hot_view_lable">热门主题：</label>
									<div data-role="hot_disscussion_list" class="hot_discussion_list"></div>
								</div>
							</div>
						</div>
						<div data-tab="user" class="main_view full_page">
							<div class="user_view">
							<div class="recommend_disccusion">
								<label class="user_view_lable">我的标签：</label>
								<div data-role="user_view_list" class="hot_disscussion_list">
									 大数据操作系统(200) 大数据操作系统(200) 
								</div>
							</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="<?PHP echo $theme; ?>/media/js/jquery.min.js" type="text/javascript"></script>
	<script src="<?PHP echo $theme; ?>/media/js/jquery.validate.min.js" type="text/javascript"></script>
	<script src="<?PHP echo $theme; ?>/media/js/jquery.backstretch.min.js" type="text/javascript"></script>
	<script src="<?PHP echo $theme; ?>/media/js/main.js" type="text/javascript"></script>
	<script src="<?PHP echo $theme; ?>/media/js/websocket.js" type="text/javascript"></script>
	<script src="<?PHP echo $theme; ?>/media/js/app.js" type="text/javascript"></script>
	<script src="<?PHP echo $theme; ?>/media/js/login.js" type="text/javascript"></script>
	<script src="<?PHP echo $theme; ?>/media/js/menu.js" type="text/javascript"></script>
	<script src="<?PHP echo $theme; ?>/media/js/question.js" type="text/javascript"></script>
	<script src="<?PHP echo $theme; ?>/media/js/chat.js" type="text/javascript"></script>
	<script src="<?PHP echo $theme; ?>/media/js/hot.js" type="text/javascript"></script>
	<script src="<?PHP echo $theme; ?>/media/js/callback.js" type="text/javascript"></script>
</body>
</html>