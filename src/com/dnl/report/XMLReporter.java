package com.dnl.report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;


import com.dnl.result.StepDefinitionResult;
import com.dnl.result.TestCaseResult;
import com.dnl.result.TestStepResult;

import static com.dnl.Logger.GSLogger.gsLogger;
import static com.dnl.globals.Constants.*;

public final class XMLReporter {
	private static volatile XMLReporter xmlReporter=null;
	private Document doc =null;
	//private String fileName=null;
	
	private String featureFilePath=null;
	private String featureFileName=null;
	
	private String regressionFilePath=null;
	private String regressionFileName=null;
	
	private String filePath=null;
	private int testCount=0;
	private int passCount=0;
	private int failCount=0;
	private int skipCount=0;
	
	private int acceptanceScenario=0;
	
	private String previousTest="";
	private int xmlTestCount=0;
	private int xmlTestPassCount=0;
	private int xmlTestFailCount=0;
	private int xmlTestSkipCount=0;
	
	private XMLReporter(){
		String dateTime=new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		File f=new  File(System.getProperty("user.dir")+"/results/"+dateTime);
		f.mkdirs();
		filePath=f.getAbsolutePath();
		
		regressionFilePath=filePath+"/regression";
		f=new File(regressionFilePath);
		f.mkdirs();
		
		featureFilePath=filePath+"/features";
		f=new File(featureFilePath);
		f.mkdirs();
	}
	
	//Thread safe
	//not using for now
	/*public static XMLReporter getXMLReporter(){
		if(xmlReporter ==null){
			synchronized(XMLReporter.class){
				if(xmlReporter ==null){
					xmlReporter=new XMLReporter();
				}
			}
		}
		return xmlReporter;
	}*/
	
	public static XMLReporter getXMLReporter(){
		if(xmlReporter ==null){
			xmlReporter=new XMLReporter();	
		}
		return xmlReporter;
	}
	
	public String getReportPath(){
		return filePath;
	}
	
	public String getRegressionReportPath(){
		return regressionFilePath;
	}
	
	public String getFeatureReportPath(){
		return featureFilePath;
	}
	
	public void createAcceptanceReport(String featureName,String startTime){
		try{
			//featureFileName=filePath+"/"+featureName+".xml";
			featureFileName=featureFilePath+"/"+featureName+".xml";
			acceptanceScenario=0;
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.newDocument();
			
			//Create feature Element
			Element feature = doc.createElement("feature");
			feature.setAttribute("name", featureName);
			feature.setAttribute("scenarios", "0");
			feature.setAttribute("pass", "0");
			feature.setAttribute("fail", "0");
			feature.setAttribute("skipped", "0");
			feature.setAttribute("startTime", startTime);
			feature.setAttribute("endTime", "Running");
			feature.setAttribute("featureStatus", PASS);
			
			doc.appendChild(feature);
			
			//Write the xml report
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
				
			OutputStreamWriter xmlFile = new OutputStreamWriter( new FileOutputStream(featureFileName),Charset.forName("UTF-8").newEncoder());
			
			StreamResult result = new StreamResult(xmlFile);
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT,"yes");	
			transformer.transform(source, result);
			
		}catch(Exception e){
			//e.printStackTrace();
			gsLogger.writeERROR("Exception occured-> XMLReporter -> createAcceptanceReport() -> ", e);
		}
	}
	
	/*public void writeFeatureReport(String featureName,String startTime){
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.parse(featureFileName);
			
			
			
			//Write the xml report
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
				
			OutputStreamWriter xmlFile = new OutputStreamWriter( new FileOutputStream(featureFileName),Charset.forName("UTF-8").newEncoder());
			
			StreamResult result = new StreamResult(xmlFile);
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT,"yes");	
			transformer.transform(source, result);
		}catch(Exception e){
			//e.printStackTrace();
			gsLogger.writeERROR("Exception occured-> XMLReporter -> writeFeatureReport() -> ", e);
		}
	}*/
	
	public void writeScenarioReport(String scenarioName,String startTime){
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.parse(featureFileName);
			
			//create scenario node
			Element scenario= doc.createElement("scenario");
			scenario.setAttribute("name", scenarioName);
			scenario.setAttribute("startTime", startTime);
			scenario.setAttribute("endTime", "Running");
			scenario.setAttribute("scenarioStatus", PASS);
				
			//root.appendChild(scenario);
			
			//Add in the last xml feature
			NodeList xmlFeatureList = doc.getElementsByTagName("feature");
			xmlFeatureList.item(xmlFeatureList.getLength()-1).appendChild(scenario);
			
			//Update the scenarios counters
			acceptanceScenario++;
			Node featureNode=doc.getFirstChild();
			NamedNodeMap attr=featureNode.getAttributes();
			Node tests=attr.getNamedItem("scenarios");
			tests.setNodeValue(new Integer(acceptanceScenario).toString());
			
			//Write the xml report
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
				
			OutputStreamWriter xmlFile = new OutputStreamWriter( new FileOutputStream(featureFileName),Charset.forName("UTF-8").newEncoder());
			
			StreamResult result = new StreamResult(xmlFile);
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT,"yes");	
			transformer.transform(source, result);
		}catch(Exception e){
			//e.printStackTrace();
			gsLogger.writeERROR("Exception occured-> XMLReporter -> writeScenarioReport() -> ", e);
		}
	}
	
	public void createReport(String suiteName,String startTime){
		try{
			regressionFileName=regressionFilePath +"/"+suiteName+".xml";
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.newDocument();
			
			//Create suite Element
			Element suite = doc.createElement("testsuite");
			suite.setAttribute("name", suiteName);
			suite.setAttribute("tests", "0");
			suite.setAttribute("pass", "0");
			suite.setAttribute("fail", "0");
			suite.setAttribute("skipped", "0");
			suite.setAttribute("startTime", startTime);
			suite.setAttribute("endTime", "Running");
			suite.setAttribute("suiteStatus", PASS);
			
			doc.appendChild(suite);
			
			//Write the xml report
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
				
			OutputStreamWriter xmlFile = new OutputStreamWriter( new FileOutputStream(regressionFileName),Charset.forName("UTF-8").newEncoder());
			
			StreamResult result = new StreamResult(xmlFile);
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT,"yes");	
			transformer.transform(source, result);
			
		}catch(Exception e){
			//e.printStackTrace();
			gsLogger.writeERROR("Exception occured-> XMLReporter -> createReport() -> ", e);
		}
	}
	
	public void setSuiteEndTime(String endTime){
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.parse(regressionFileName);
			
			// Get the root element
			Node suite = doc.getFirstChild();
			
			// update endtime attribute
			NamedNodeMap attr = suite.getAttributes();
			Node nodeAttr = attr.getNamedItem("endTime");
			nodeAttr.setNodeValue(endTime);
			//nodeAttr.setNodeValue(arg0)
			//Write the xml report
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			
			OutputStreamWriter xmlFile = new OutputStreamWriter( new FileOutputStream(regressionFileName),Charset.forName("UTF-8").newEncoder());
			
			StreamResult result = new StreamResult(xmlFile);
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT,"yes");	
			transformer.transform(source, result);
			
		}catch(Exception e){
			//e.printStackTrace();
			gsLogger.writeERROR("Exception occured-> XMLReporter -> setSuiteEndTime() -> ", e);
		}
	}
	
	public void writeTestCaseReport(TestCaseResult testcaseResult){
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.parse(regressionFileName);
			
			Element root=doc.getDocumentElement();
			//check if its new xml test
			String xmlTest=testcaseResult.getXMLTestName();
			if(!xmlTest.equalsIgnoreCase(previousTest)){
				previousTest=xmlTest;
				
				xmlTestCount=0;
				xmlTestFailCount=0;
				xmlTestPassCount=0;
				xmlTestSkipCount=0;
				
				//create xml test node
				Element xmlTestCase= doc.createElement("test");
				xmlTestCase.setAttribute("name", previousTest);
				xmlTestCase.setAttribute("tests", "0");
				xmlTestCase.setAttribute("pass", "0");
				xmlTestCase.setAttribute("fail", "0");
				xmlTestCase.setAttribute("skipped", "0");
				//Need to work on start time and end time
				//xmlTestCase.setAttribute("startTime", startTime);
				//xmlTestCase.setAttribute("endTime", "Running");
				xmlTestCase.setAttribute("testStatus", PASS);
				
				root.appendChild(xmlTestCase);
			}
			
			//Create the testcase element
			Element test=doc.createElement("testcase");
			test.setAttribute("name", testcaseResult.getTestcaseName());
			
			test.setAttribute("ellapsedtime", new Long(testcaseResult.getTestEllapsedTime()).toString());
			
			//This is status of testcase as per TestNG.
			//We consider only Skipped status from TestNG.
			//For Pass/Fail we consider status on each step maintained by framework
			//Testcase will be marked Pass if all the steps are Pass
			//testcase will be marked Fail if one or more steps are Fail
			String testStatus=testcaseResult.getTestStatus();
				
			if(!testStatus.equalsIgnoreCase(SKIPPED)){
				//If testcase is not skipped, get details about the steps
				List<TestStepResult> stepResult=testcaseResult.getTestStepsResult();
				Iterator<TestStepResult> i=stepResult.iterator();
			
				while(i.hasNext()){
					TestStepResult testStepResult=i.next();
					Element step=doc.createElement("teststep");
					
					String stepStatus=testStepResult.getStatus();
					//If step is fail,mark the test as fail
					if(stepStatus.equalsIgnoreCase(FAIL)){
						testStatus=FAIL;
					}
					
					step.setAttribute("step",testStepResult.getStep());
					step.setAttribute("status", stepStatus);
					step.setAttribute("error",testStepResult.getErrMsg());
					step.setAttribute("stacktrace",testStepResult.getStacktrace());
					step.setAttribute("screenshot", testStepResult.getErrorScreenshotFile());
					test.appendChild(step);
				}
			}
			
			//Add the test element
			test.setAttribute("status", testStatus);
			
			//Add in the last xml test
			//root.getFirstChild().getLastChild().appendChild(test);
			//root.getLastChild().appendChild(test);
			NodeList xmlTestList = doc.getElementsByTagName("test");
			xmlTestList.item(xmlTestList.getLength()-1).appendChild(test);
			
			//Update the test counters
			testCount++;
			xmlTestCount++;
			Node suiteNode=doc.getFirstChild();
			NamedNodeMap attr=suiteNode.getAttributes();
			Node tests=attr.getNamedItem("tests");
			tests.setNodeValue(new Integer(testCount).toString());
			
			//Node xmlTestNode=doc.getFirstChild().getLastChild();
			//Node xmlTestNode=root.getLastChild();
			Node xmlTestNode=xmlTestList.item(xmlTestList.getLength()-1);
			NamedNodeMap attrXMLTest=xmlTestNode.getAttributes();
			tests=attrXMLTest.getNamedItem("tests");
			tests.setNodeValue(new Integer(xmlTestCount).toString());
			
			if(testStatus.equalsIgnoreCase(SKIPPED)){
				skipCount++;
				xmlTestSkipCount++;
				Node skipped=attr.getNamedItem("skipped");
				skipped.setNodeValue(new Integer(skipCount).toString());
				
				skipped=attrXMLTest.getNamedItem("skipped");
				skipped.setNodeValue(new Integer(xmlTestSkipCount).toString());
			}else if(testStatus.equalsIgnoreCase(PASS)){
				passCount++;
				xmlTestPassCount++;
				Node pass=attr.getNamedItem("pass");
				pass.setNodeValue(new Integer(passCount).toString());
				
				pass=attrXMLTest.getNamedItem("pass");
				pass.setNodeValue(new Integer(xmlTestPassCount).toString());
			}else if(testStatus.equalsIgnoreCase(FAIL)){
				failCount++;
				xmlTestFailCount++;
				Node fail=attr.getNamedItem("fail");
				fail.setNodeValue(new Integer(failCount).toString());
				
				fail=attrXMLTest.getNamedItem("fail");
				fail.setNodeValue(new Integer(xmlTestFailCount).toString());
				
				//mark the suite as failed
				Node suiteStatus=attr.getNamedItem("suiteStatus");
				suiteStatus.setNodeValue(FAIL);
				
				//mark the xml test as failed
				Node xmlTestStatus=attrXMLTest.getNamedItem("testStatus");
				xmlTestStatus.setNodeValue(FAIL);
			}
			
			//Write the xml report
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
				
			OutputStreamWriter xmlFile = new OutputStreamWriter( new FileOutputStream(regressionFileName),Charset.forName("UTF-8").newEncoder());
			
			StreamResult result = new StreamResult(xmlFile);
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT,"yes");	
			transformer.transform(source, result);
		}catch(Exception e){
			//e.printStackTrace();
			gsLogger.writeERROR("Exception occured-> XMLReporter -> writeTestCaseReport() -> ", e);
		}
		
	}

	public void writeStepDefinitionReport(StepDefinitionResult stepDefinitionResult) {
		// TODO Auto-generated method stub
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.parse(featureFileName);
			
			//Create the testcase element
			Element stepDefinition=doc.createElement("stepdefinition");
			stepDefinition.setAttribute("name", stepDefinitionResult.getStepDefinitonKeyword()+" "+stepDefinitionResult.getStepDefiniton());
			stepDefinition.setAttribute("ellapsedtime", new Long(stepDefinitionResult.getStepEllapsedTime()).toString());
			
			List<TestStepResult> stepResult=stepDefinitionResult.getTestStepsResult();
			Iterator<TestStepResult> i=stepResult.iterator();
			
			String stepDefinitionStatus=PASS;
			
			while(i.hasNext()){
				TestStepResult testStepResult=i.next();
				Element step=doc.createElement("teststep");
				
				String stepStatus=testStepResult.getStatus();
				//If step is fail,mark the test as fail
				if(stepStatus.equalsIgnoreCase(FAIL)){
					stepDefinitionStatus=FAIL;
				}
				
				step.setAttribute("step",testStepResult.getStep());
				step.setAttribute("status", stepStatus);
				step.setAttribute("error",testStepResult.getErrMsg());
				step.setAttribute("stacktrace",testStepResult.getStacktrace());
				step.setAttribute("screenshot", testStepResult.getErrorScreenshotFile());
				stepDefinition.appendChild(step);
			}
			
			stepDefinition.setAttribute("status", stepDefinitionStatus);
			
			//Get the last scenario element
			NodeList scenarioList = doc.getElementsByTagName("scenario");
			Node scenario=scenarioList.item(scenarioList.getLength()-1);
			
			if(stepDefinitionStatus.equals(FAIL)){
				//update the scenario status to fail. It is set to pass by default
				//update the fail count in feature tag
				NamedNodeMap attr=scenario.getAttributes();
				Node scenarioStatus=attr.getNamedItem("scenarioStatus");
				scenarioStatus.setNodeValue(FAIL);
				
				//update the failure counter in feature tag
				//Add in the last xml feature
				NodeList featureList = doc.getElementsByTagName("feature");
				Node feature=featureList.item(featureList.getLength()-1);
				
				attr=feature.getAttributes();
				Node fail=attr.getNamedItem("fail");
				int failCount=new Integer(fail.getNodeValue());
				failCount++;
				fail.setNodeValue(new Integer(failCount).toString());
				
				Node featureStatus=attr.getNamedItem("featureStatus");
				featureStatus.setNodeValue(FAIL);
			}
			
			//Add in the last scenario element
			scenario.appendChild(stepDefinition);
			
			//Write the xml report
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
				
			OutputStreamWriter xmlFile = new OutputStreamWriter( new FileOutputStream(featureFileName),Charset.forName("UTF-8").newEncoder());
			
			StreamResult result = new StreamResult(xmlFile);
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT,"yes");	
			transformer.transform(source, result);
			
			
		}catch(Exception e){
			//e.printStackTrace();
			gsLogger.writeERROR("Exception occured-> XMLReporter -> writeStepReport() -> ", e);
		}
		
	}
}
