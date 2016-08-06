package com.dnl.result;

import java.util.ArrayList;
import java.util.List;

public class StepDefinitionResult {
	private String stepDefinitionKeyword;
	private String stepDefinition;
	private long ellapsedTimeMillSec;
	//private String status;
	private static volatile StepDefinitionResult stepDefinitionResult=null;
	
	private static List<TestStepResult> testStepResult;
	
	private StepDefinitionResult(){
		
	}
	
	public static StepDefinitionResult newInstance(){
		stepDefinitionResult=new StepDefinitionResult();
		testStepResult=new ArrayList<TestStepResult>();
		return stepDefinitionResult;
	}
	
	public static StepDefinitionResult geStepDefinitionResult(){
		if(stepDefinitionResult ==null){
			stepDefinitionResult=newInstance();	
		}
		return stepDefinitionResult;
	}

	public void setStepDefiniton(String stepDefinition){
		this.stepDefinition=stepDefinition;
	}
	
	public String getStepDefiniton(){
		return stepDefinition;
	}
	
	public void setStepDefinitonKeyword(String stepDefinitionKeyword){
		this.stepDefinitionKeyword=stepDefinitionKeyword;
	}
	
	public String getStepDefinitonKeyword(){
		return stepDefinitionKeyword;
	}
	
	/*public void setTestStatus(String status){
		this.status=status;
	}
	
	public String getTestStatus(){
		return status;
	}*/
	
	public void setStepEllapsedTime(long timeMilliSec){
		this.ellapsedTimeMillSec=timeMilliSec;
		
	}
	
	public long getStepEllapsedTime(){
		return ellapsedTimeMillSec;
	}
	
	public void addTestStepResult(TestStepResult testStepResult){
		this.testStepResult.add(testStepResult);
	}
	
	public List<TestStepResult> getTestStepsResult(){
		return testStepResult;
	}
}
