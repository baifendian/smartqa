package com.bfd.smartqa.biz;

public class BizMapper {
	private BaseBiz biz;
	private String method;
	
	public BizMapper(BaseBiz biz,String method)
	{
		this.biz = biz;
		this.method = method;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public BaseBiz getBiz() {
		return biz;
	}
	public void setBiz(BaseBiz biz) {
		this.biz = biz;
	}
	

}
