<?PHP
	function isMobile() {
	  //�ж��ֻ����͵Ŀͻ��˱�־
	  if(isset($_SERVER['HTTP_USER_AGENT'])) {
		$userAgent = strtolower($_SERVER['HTTP_USER_AGENT']);
		$clientkeywords = array(
		  'nokia', 'sony', 'ericsson', 'mot', 'samsung', 'htc', 'sgh', 'lg', 'sharp', 'sie-'
		  ,'philips', 'panasonic', 'alcatel', 'lenovo', 'iphone', 'ipod', 'blackberry', 'meizu', 
		  'android', 'netfront', 'symbian', 'ucweb', 'windowsce', 'palm', 'operamini', 
		  'operamobi', 'opera mobi', 'openwave', 'nexusone', 'cldc', 'midp', 'wap', 'mobile'
		);
		// ��HTTP_USER_AGENT�в����ֻ�������Ĺؼ���
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