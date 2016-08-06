package com.dnl.extension;



import java.lang.reflect.Method;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener2;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.internal.ConstructorOrMethod;

import com.dnl.annotation.RunContext;
import com.dnl.globals.TestType;
import com.dnl.report.XMLReporter;
import com.dnl.result.TestCaseResult;
import com.dnl.util.FrameWorkConfig;

import static com.dnl.report.XMLReporter.*;
import static com.dnl.result.TestCaseResult.*;
import static com.dnl.globals.Constants.*;


public class MethodListener implements IInvokedMethodListener2{
	private TestCaseResult testcaseResult;
	private XMLReporter xmlReporter;
	
	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		
	}

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult,
			ITestContext testContext) {
		
		int status;
	//	testContext.getCurrentXmlTest().
		if(method.isTestMethod() && TestType.getTestType().equals(FUNCTIONAL_TEST)){
			status=testResult.getStatus();
			if(status==ITestResult.SUCCESS){
				testcaseResult.setTestStatus(PASS);
			}else if(status==ITestResult.FAILURE){
				testcaseResult.setTestStatus(FAIL);
			}else if(status==ITestResult.SKIP){
				testcaseResult.setTestStatus(SKIPPED);
			}
			testcaseResult.setTestEllapsedTime(testResult.getEndMillis()-testResult.getStartMillis());
			xmlReporter=getXMLReporter();
			xmlReporter.writeTestCaseReport(testcaseResult);
		}
		
	}

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult,
			ITestContext testContext) {
		// TODO Auto-generated method stub
		
		if(method.isTestMethod()){
			
			ITestNGMethod testNGMethod= testResult.getMethod();
			
			ConstructorOrMethod constructorOrMethod=testNGMethod.getConstructorOrMethod();
			Method m= constructorOrMethod.getMethod();
			
			//Check if Context is set for execution
			if(m.isAnnotationPresent(RunContext.class)){
				RunContext runContext=m.getAnnotation(RunContext.class);
				
				String methodContext=runContext.value();
				
				if(methodContext==null)
					methodContext=METHODCONTEXT_WEB; //default to Web
				
				if(methodContext.equalsIgnoreCase(METHODCONTEXT_MOBILE)){
					
					if(FrameWorkConfig.getPlatform().equals(PLATFORM_WINDOWS)){
						//We are trying to run test meant for Mobile on Web
						//Skip the test
						throw new SkipException("Cannot run test meant for mobile on web");
					}
					
					
				}else if(methodContext.equalsIgnoreCase(METHODCONTEXT_WEB)){
					//Default to Web Context
					
					if((FrameWorkConfig.getPlatform().equals(PLATFORM_ANDROID)) || (FrameWorkConfig.getPlatform().equalsIgnoreCase(PLATFORM_IOS))){
						//We are trying to run test meant for Web on Mobile
						//Skip the test
						throw new SkipException("Cannot run test meant for web on mobile");
					}
				}
			}
			
			if(method.toString().startsWith("RunAcceptanceTest.run_cukes")){
				TestType.setTestType(ACCEPTANCE_TEST);
			}else{
				TestType.setTestType(FUNCTIONAL_TEST);
				testcaseResult=newInstance();
				testcaseResult.setTestcaseName(method.getTestMethod().getMethodName());
				testcaseResult.setXMLTestName(testContext.getCurrentXmlTest().getName());
			}
		}
	}
}