<?PHP
	function isMobile() {
	  //判断手机发送的客户端标志
	  if(isset($_SERVER['HTTP_USER_AGENT'])) {
		$userAgent = strtolower($_SERVER['HTTP_USER_AGENT']);
		$clientkeywords = array(
		  'nokia', 'sony', 'ericsson', 'mot', 'samsung', 'htc', 'sgh', 'lg', 'sharp', 'sie-'
		  ,'philips', 'panasonic', 'alcatel', 'lenovo', 'iphone', 'ipod', 'blackberry', 'meizu', 
		  'android', 'netfront', 'symbian', 'ucweb', 'windowsce', 'palm', 'operamini', 
		  'operamobi', 'opera mobi', 'openwave', 'nexusone', 'cldc', 'midp', 'wap', 'mobile'
		);
		// 从HTTP_USER_AGENT中查找手机浏览器的关键字
		if(preg_match("/(".implode('|',$clientkeywords).")/i",$userAgent)&&strpos($userAgent,'ipad') === false)
		{
		  return true;
		}
	  }
	  return false;
	}
	$theme = "web";
	$web_root = dirname(__FILE__);
	
	if(isMobile() == true)
	{
		$theme='mobile';
	}
	include($web_root."/".$theme."/index.php");
	
	



?>