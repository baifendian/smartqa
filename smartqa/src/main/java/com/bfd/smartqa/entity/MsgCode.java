package com.bfd.smartqa.entity;

public enum MsgCode {
	SUCCES(0),NotAuth(1),ParamWrong(2),Exception(100); 
	
	private int code;
	private MsgCode(int code){  
        this.code=code;   
     }  
	public int getCode()
	{
		return this.code;
	}

	
}
