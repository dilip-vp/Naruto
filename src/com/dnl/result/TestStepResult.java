package com.dnl.result;

public class TestStepResult {
	private String step;
	private String status;
	private String errMsg;
	private String stackTrace;
	private String errorScreenshotFile;
	
	public void setStep(String step){
		this.step=step;
	}
	
	public String getStep(){
		return step;
	}
	
	public void setStatus(String status){
		this.status=status;
	}
	
	public String getStatus(){
		return status;
	}

	public void setErrMsg(String errMsg){
		this.errMsg=errMsg;
	}
	
	public String getErrMsg(){
		return errMsg;
	}
	
	public void setStackTrace(String stackTrace){
		this.stackTrace=stackTrace;
	}
	
	public String getStacktrace(){
		return stackTrace;
	}
	
	public void setErrorScreenshotFile(String fileName){
		errorScreenshotFile=fileName;
	}
	
	public String getErrorScreenshotFile(){
		return errorScreenshotFile;
	}
}
