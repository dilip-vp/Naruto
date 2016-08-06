package com.dnl.result;

import java.util.ArrayList;
import java.util.List;

public final class TestCaseResult
{
	private String testName;
	private String testcaseName;
	private long ellapsedTimeMillSec;
	private String status;
	private static volatile TestCaseResult testcaseResult=null;
	
	private static List<TestStepResult> testStepResult;
	
	private TestCaseResult(){
		
	}
	
	public static TestCaseResult newInstance(){
		testcaseResult=new TestCaseResult();
		testStepResult=new ArrayList<TestStepResult>();
		return testcaseResult;
	}
	
	public static TestCaseResult geTestCaseResult(){
		if(testcaseResult ==null){
			testcaseResult=newInstance();	
		}
		return testcaseResult;
	}
	
	public void setTestcaseName(String testcaseName){
		this.testcaseName=testcaseName;
	}
	
	public String getTestcaseName(){
		return testcaseName;
	}
	
	
	public void setXMLTestName(String testName){
		this.testName=testName;
	}
	
	public String getXMLTestName(){
		return testName;
	}
	
	public void setTestStatus(String testStatus){
		this.status=testStatus;
	}
	
	public String getTestStatus(){
		return status;
	}
	
	public void setTestEllapsedTime(long timeMilliSec){
		this.ellapsedTimeMillSec=timeMilliSec;
		
	}
	
	public long getTestEllapsedTime(){
		return ellapsedTimeMillSec;
	}
	
	public void addTestStepResult(TestStepResult testStepResult){
		this.testStepResult.add(testStepResult);
	}
	
	public List<TestStepResult> getTestStepsResult(){
		return testStepResult;
	}
}