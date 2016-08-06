package com.dnl.report;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import freemarker.template.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import com.google.common.io.Files;

import static com.dnl.Logger.GSLogger.gsLogger;
import static com.dnl.report.XMLReporter.getXMLReporter;

public class HTMLReporter {
	private static XMLReporter xmlReporter;
	private static String reportDir;
	
	static{
		xmlReporter=getXMLReporter();
	}
	
	public static void createHTMLReport(){
		createReportDir();
		copyFilesToReportDir();
		createLeftTreeHTML();
		createOverviewHTML();
		createTestHTML();
		createFeatureHTML();
	}
	

	private static void createReportDir(){
		File f=new  File(xmlReporter.getReportPath()+"/html");
		f.mkdir();
		
		reportDir=f.getAbsolutePath();
	}
	
	private static void copyFilesToReportDir(){
		try{
			Files.copy(new File(System.getProperty("user.dir")+"/reporttemplate/reportng.css"), new File(xmlReporter.getReportPath()+"/html/reportng.css"));
			Files.copy(new File(System.getProperty("user.dir")+"/reporttemplate/reportng.js"), new File(xmlReporter.getReportPath()+"/html/reportng.js"));
			Files.copy(new File(System.getProperty("user.dir")+"/reporttemplate/sorttable.js"), new File(xmlReporter.getReportPath()+"/html/sorttable.js"));
			Files.copy(new File(System.getProperty("user.dir")+"/reporttemplate/index.html"), new File(xmlReporter.getReportPath()+"/html/index.html"));
		}catch(Exception e){
			gsLogger.writeERROR("Exception occured-> HTMLReporter -> copyFilesToReportDir() -> ", e);
			//e.printStackTrace();
		}
	}
	
	private static void createOverviewHTML(){
		try{
			Configuration cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(new File(System.getProperty("user.dir")+"/reporttemplate"));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
		    cfg.setDefaultEncoding("UTF-8");
		    
		    //Don't know what these 2 lines do. Copied from example
		    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
	        //cfg.setIncompatibleImprovements(new Version(2, 3, 20));
			
	        List<String> xmlReports=getRegressionXMLReports();
			Iterator<String> i=xmlReports.iterator();
			
			//There will be one suite xml because we create one suite node in one testng xml
			//For now we do not pass multiple xmls to testng
			while(i.hasNext()){
				String report=i.next();
				
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.parse(report);
				
				doc.getDocumentElement().normalize();
				
				//Element root=doc.getDocumentElement();
				
				// Get the suite name
				Node suite = doc.getFirstChild();
				
				NamedNodeMap attr = suite.getAttributes();
				Node name= attr.getNamedItem("name");
				Node suitePassCnt=attr.getNamedItem("pass");
				Node suiteFailCnt=attr.getNamedItem("fail");
				Node suiteSkipCnt=attr.getNamedItem("skipped");
				//Node suiteTotalCnt=attr.getNamedItem("tests");
				
				float passCnt=new Float(suitePassCnt.getNodeValue());
				float failCnt=new Float(suiteFailCnt.getNodeValue());
				float skipCnt=new Float(suiteSkipCnt.getNodeValue());
			
				float total=passCnt+failCnt+skipCnt;
				
				
				float passRate;
				if(total>0){
					passRate=(passCnt/total)*100;
				}else{
					passRate=0;
				}
				DecimalFormat df=new DecimalFormat("###.##");
				// Create a data-model
				Map<String,Object> suiteMap= new HashMap<String,Object>();
				
				suiteMap.put("suiteName", name.getNodeValue());
				suiteMap.put("suitePassCnt", suitePassCnt.getNodeValue());
				suiteMap.put("suiteSkipCnt", suiteSkipCnt.getNodeValue());
				suiteMap.put("suiteFailCnt", suiteFailCnt.getNodeValue());
				//suiteMap.put("suiteTotalCnt", suiteTotalCnt.getNodeValue());
				suiteMap.put("suitePassPercent", df.format(passRate));
				
				//Get the test name and status
				NodeList testList = doc.getElementsByTagName("test");
				
				//Create data-model
				List<Object> xmlTests=new ArrayList<Object>();
				suiteMap.put("xmlTests", xmlTests);
				
				for (int n = 0; n < testList.getLength(); n++) {
					Map<String,String> xmlTestMap= new HashMap<String,String>();
					
					Node xmlTest=testList.item(n);
					
					attr = xmlTest.getAttributes();
					
					Node testName= attr.getNamedItem("name");
					Node testPassCnt=attr.getNamedItem("pass");
					Node testFailCnt=attr.getNamedItem("fail");
					Node testSkipCnt=attr.getNamedItem("skipped");
					
					passCnt=new Integer(testPassCnt.getNodeValue());
					failCnt=new Integer(testFailCnt.getNodeValue());
					skipCnt=new Integer(testSkipCnt.getNodeValue());
					
					total=passCnt+failCnt+skipCnt;
					
					if(total>0){
						passRate=(passCnt/total)*100;
					}else{
						passRate=0;
					}
					
					//System.out.println("Pass rate="+passRate);
					//System.out.println("Pass rate="+df.format(passRate));
					
					xmlTestMap.put("testName", testName.getNodeValue());
					xmlTestMap.put("testPassCnt", testPassCnt.getNodeValue());
					xmlTestMap.put("testSkipCnt", testSkipCnt.getNodeValue());
					xmlTestMap.put("testFailCnt", testFailCnt.getNodeValue());
					xmlTestMap.put("testPassPercent", df.format(passRate));
					
					xmlTests.add(xmlTestMap);
				}
				
				
				List<String> xmlAcceptanceReports=getFeatureXMLReports();
				Iterator<String> j=xmlAcceptanceReports.iterator();
				
				//This is to avoid freemarker template error if there are no feature xmls
				if(j.hasNext()){
					suiteMap.put("acceptanceTest", "1");
				}else{
					suiteMap.put("acceptanceTest", "0");
				}
				
				List<Object> xmlFeatures=new ArrayList<Object>();
				
				suiteMap.put("xmlFeatures",xmlFeatures);
				
				float totalScenarioPassCount=0;
				float totalScenarioFailCount=0;
				float totalScenarioSkipCount=0;
				
				while(j.hasNext()){
					report=j.next();
					
					Map<String,String> xmlFeatureMap=new HashMap<String,String>();
					
					docFactory = DocumentBuilderFactory.newInstance();
					docBuilder = docFactory.newDocumentBuilder();
					doc = docBuilder.parse(report);
					
					doc.getDocumentElement().normalize();
					
					// Get the feature node
					Node feature = doc.getFirstChild();
					
					attr = feature .getAttributes();
					name= attr.getNamedItem("name");
					
					
					Node scenarioTotalCnt = attr.getNamedItem("scenarios");
					Node scenarioFailCnt=attr.getNamedItem("fail");
					
					
					float scenarioCnt=new Float(scenarioTotalCnt.getNodeValue());
					failCnt=new Float(scenarioFailCnt.getNodeValue());
					//No skip will happen in acceptance tests
					skipCnt=0;
					//So pass count is total scenarios - failed scenarios
					passCnt=scenarioCnt-failCnt;
					
					totalScenarioPassCount=totalScenarioPassCount+passCnt;
					totalScenarioFailCount=totalScenarioFailCount+failCnt;
					totalScenarioSkipCount=totalScenarioSkipCount+skipCnt;
				
					if(scenarioCnt>0){
						passRate=(passCnt/scenarioCnt)*100;
					}else{
						passRate=0;
					}
					
					//System.out.println("Pass rate="+passRate);
					//System.out.println("Pass rate="+df.format(passRate));
					
					xmlFeatureMap.put("featureName", name.getNodeValue());
					xmlFeatureMap.put("scenarioPassCnt", new Integer((int) passCnt).toString());
					//No skip for acceptance tests
					xmlFeatureMap.put("scenarioSkipCnt", "0");
					xmlFeatureMap.put("scenarioFailCnt", scenarioFailCnt.getNodeValue());
					xmlFeatureMap.put("scenarioPassPercent", df.format(passRate));
					
					xmlFeatures.add(xmlFeatureMap);
					
					
				}
				
				float totalScenarioCount=totalScenarioPassCount+totalScenarioFailCount+totalScenarioSkipCount;
				
				
				if(totalScenarioCount>0){
					passRate=(totalScenarioPassCount/totalScenarioCount)*100;
				}else{
					passRate=0;
				}
				
				suiteMap.put("totalScenarioPassCnt", totalScenarioPassCount);
				suiteMap.put("totalScenarioFailCnt", totalScenarioFailCount);
				suiteMap.put("totalScenarioSkipCnt", totalScenarioSkipCount);
				//suiteMap.put("suiteTotalCnt", suiteTotalCnt.getNodeValue());
				suiteMap.put("totalScenarioPassPercent", df.format(passRate));
				
				/* Get the template */
		        Template template = cfg.getTemplate("overview.ftl");
		        
		        /* Merge data-model with template */
		        File file = new File(reportDir+"/overview.html");
		        Writer out = new OutputStreamWriter(new FileOutputStream(file));
		        template.process(suiteMap, out);
			
			}
		}catch(Exception e){
			//e.printStackTrace();
			gsLogger.writeERROR("Exception occured-> HTMLReporter -> createOverviewHTML() -> ", e);
		}
	}
	
	private static void createLeftTreeHTML(){
		try{
			Configuration cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(new File(System.getProperty("user.dir")+"/reporttemplate"));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
		    cfg.setDefaultEncoding("UTF-8");
		    
		    //Don't know what these 2 lines do. Copied from example
		    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
	        //cfg.setIncompatibleImprovements(new Version(2, 3, 20));

			
		    List<String> xmlReports=getRegressionXMLReports();
			Iterator<String> i=xmlReports.iterator();
			
			
			//There will be one suite xml because we create one suite node in one testng xml
			//For now we do not pass multiple xmls to testng
			while(i.hasNext()){
				String report=i.next();
				
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.parse(report);
				
				doc.getDocumentElement().normalize();
				
				//Element root=doc.getDocumentElement();
				
				// Get the suite name
				Node suite = doc.getFirstChild();
				
				NamedNodeMap attr = suite.getAttributes();
				Node name= attr.getNamedItem("name");
				
				// Create a data-model
				Map<String,Object> suiteMap= new HashMap<String,Object>();
				suiteMap.put("suiteName", name.getNodeValue());
				
				//Create data-model
				List<Object> xmlTests=new ArrayList<Object>();
				
				suiteMap.put("xmlTests", xmlTests);
				
				//Get the test name and status
				NodeList testList = doc.getElementsByTagName("test");
				
				for (int n = 0; n < testList.getLength(); n++) {	
					Map<String,Object> xmlTestMap= new HashMap<String,Object>();
					
					Node xmlTest=testList.item(n);
					
					attr = xmlTest.getAttributes();
					
					name=attr.getNamedItem("name");
					Node status=attr.getNamedItem("testStatus");
							
					xmlTestMap.put("testStatus", status.getNodeValue());
					xmlTestMap.put("testName", name.getNodeValue());
					
					//Get the child testcases and status
					//NodeList testcaseList = doc.getElementsByTagName("testcase");
					NodeList testcaseList = xmlTest.getChildNodes();
					
					//Create data-model
					List<Object> testCases=new ArrayList<Object>();
					
					xmlTestMap.put("testCases", testCases);
					for (int j = 0; j < testcaseList.getLength(); j++) {
						Map<String,String> testcaseMap= new HashMap<String,String>();
						
						Node testcase=testcaseList.item(j);
						
						if(!(testcase.getNodeType()==Node.ELEMENT_NODE))
							continue;
						
						attr = testcase.getAttributes();
						
						name=attr.getNamedItem("name");
						
						//This is not testcase node if name is null. 
						//It is teststep node
						if(name == null)
							continue;
						
						status=attr.getNamedItem("status");
						
						testcaseMap.put("testcaseStatus", status.getNodeValue());
						testcaseMap.put("testcaseName", name.getNodeValue());
						
						testCases.add(testcaseMap);
					}
					
					xmlTests.add(xmlTestMap);
				}
			
				//Create tree for feature xmls
				xmlReports=getFeatureXMLReports();
				i=xmlReports.iterator();
				
				//This is to avoid freemarker template error if there are no feature xmls
				if(i.hasNext()){
					suiteMap.put("acceptanceTest", "1");
				}else{
					suiteMap.put("acceptanceTest", "0");
				}
				
				//Create data-model
				List<Object> xmlFeatures=new ArrayList<Object>();
				
				while(i.hasNext()){
					report=i.next();
					
					docFactory = DocumentBuilderFactory.newInstance();
					docBuilder = docFactory.newDocumentBuilder();
					doc = docBuilder.parse(report);
					
					doc.getDocumentElement().normalize();
					
					//Get the feature and status
					NodeList featureList = doc.getElementsByTagName("feature");
					
					suiteMap.put("xmlFeatures", xmlFeatures);
					
					for (int n = 0; n < featureList.getLength(); n++) {
						Map<String,Object> xmlFeatureMap= new HashMap<String,Object>();
						
						Node xmlFeature=featureList.item(n);
						
						attr = xmlFeature.getAttributes();
						
						name=attr.getNamedItem("name");
						Node status=attr.getNamedItem("featureStatus");
								
						xmlFeatureMap.put("status", status.getNodeValue());
						xmlFeatureMap.put("featureName", name.getNodeValue());
						
						//Get the scenarios and status
						NodeList testScenarioList = doc.getElementsByTagName("scenario");
						
						//Create data-model
						List<Object> testScenarios=new ArrayList<Object>();
						
						xmlFeatureMap.put("testScenarios", testScenarios);
						for (int j = 0; j < testScenarioList.getLength(); j++) {
							Map<String,String> testScenarioMap= new HashMap<String,String>();
							
							Node testScenario=testScenarioList.item(j);
							
							attr = testScenario.getAttributes();
							
							name=attr.getNamedItem("name");
							status=attr.getNamedItem("scenarioStatus");
							
							testScenarioMap.put("scenarioStatus", status.getNodeValue());
							testScenarioMap.put("testScenarioName", name.getNodeValue());
							
							testScenarios.add(testScenarioMap);
						}
						
						xmlFeatures.add(xmlFeatureMap);
					}					
				}
								
				/* Get the template */
		        Template template = cfg.getTemplate("suites.ftl");
		        
		        /* Merge data-model with template */
		        File file = new File(reportDir+"/suites.html");
		        Writer out = new OutputStreamWriter(new FileOutputStream(file));
		        template.process(suiteMap, out);
				
			}
		}catch(Exception e){
			//e.printStackTrace();
			gsLogger.writeERROR("Exception occured-> HTMLReporter -> createSuitesHTML() -> ", e);
		}
	}
	
	private static void copyDefaultXML(File srcFile, File destFile) {
		try{
			Files.copy(srcFile, destFile);
		}catch(Exception e){
			gsLogger.writeERROR("Exception occured-> HTMLReporter -> copyDefaultXML() -> ", e);
			//e.printStackTrace();
		}
	}

	private static void createTestHTML(){
		try{
			Configuration cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(new File(System.getProperty("user.dir")+"/reporttemplate"));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
		    cfg.setDefaultEncoding("UTF-8");
		    
		    //Don't know what these 2 lines do. Copied from example
		    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
	        //cfg.setIncompatibleImprovements(new Version(2, 3, 20));

			
			List<String> xmlReports=getRegressionXMLReports();
			Iterator<String> i=xmlReports.iterator();
			
			while(i.hasNext()){
				//Hack. Running counter to create unique id.
				//Assign not working as expected in freemarker template
				int cnt=0;
				
				String report=i.next();
				
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.parse(report);
				
				doc.getDocumentElement().normalize();
				
				//get the test element
				NodeList xmlTestList = doc.getElementsByTagName("test");
				
				for (int n = 0; n < xmlTestList.getLength(); n++) {
					// Create a data-model
					Map<String,Object> xmlTestMap= new HashMap<String,Object>();
					
					Node xmlTestcase=xmlTestList.item(n);
					
					NamedNodeMap attr = xmlTestcase.getAttributes();
					
					Node name=attr.getNamedItem("name");
					
					xmlTestMap.put("testName", name.getNodeValue());
					String xmlTestName = name.getNodeValue();
					//name.getNodeValue()
					//Create data-model
					List<Object> testCases=new ArrayList<Object>();
					
					xmlTestMap.put("testCases", testCases);
					
				    NodeList testcaseList = xmlTestcase.getChildNodes();
				    
				    //System.out.println("Testcase cnt="+testcaseList.getLength());

					
					for (int j = 0; j < testcaseList.getLength(); j++) {
						
						Map<String,Object> testcaseMap= new HashMap<String,Object>();
						
						Node testcase=testcaseList.item(j);
						//System.out.println("Got "+testcase.getNodeType());
						
						if(!(testcase.getNodeType()==Node.ELEMENT_NODE))
							continue;
						
						attr = testcase.getAttributes();
						
						name=attr.getNamedItem("name");
						Node status=attr.getNamedItem("status");
						Node duration=attr.getNamedItem("ellapsedtime");
						
						testcaseMap.put("testcaseStatus", status.getNodeValue());
						testcaseMap.put("testcaseName", name.getNodeValue());
						testcaseMap.put("testcaseDuration", duration.getNodeValue());
						
						NodeList teststepList = testcase.getChildNodes();
						
						//Create data-model
						List<Object> testSteps=new ArrayList<Object>();
						
						testcaseMap.put("testSteps", testSteps);
						for (int k = 0; k < teststepList.getLength(); k++) {
							cnt++;
							
							Map<String,String> teststepMap= new HashMap<String,String>();
							
							Node teststep=teststepList.item(k);
							
							if(!(teststep.getNodeType()==Node.ELEMENT_NODE))
								continue;
							
							
							attr = teststep.getAttributes();
							Node step=attr.getNamedItem("step");
							status=attr.getNamedItem("status");
							Node screenshot=attr.getNamedItem("screenshot");
							Node error=attr.getNamedItem("error");
							Node trace=attr.getNamedItem("stacktrace");
							
							teststepMap.put("stepName", step.getNodeValue());
							if(error.getNodeValue().length()>15){
								teststepMap.put("stepError", error.getNodeValue().substring(0,14));
							}
							else{
								teststepMap.put("stepError", error.getNodeValue());
							}
							String fullError="Error Msg: "+error.getNodeValue();
							fullError=fullError+" Stacktrace: "+trace.getNodeValue();
							
							fullError=fullError.replaceAll("\"", "'");
							fullError=fullError.replaceAll("'", "\\'");
							fullError=fullError.replaceAll("\n", " ");
							
							teststepMap.put("stepFullError", fullError);
							teststepMap.put("stepScreenshot", screenshot.getNodeValue());
							teststepMap.put("stepStatus", status.getNodeValue());
							teststepMap.put("errorCounter", new Integer(cnt).toString());
							testSteps.add(teststepMap);
						}
						testCases.add(testcaseMap);
					}
					
					/* Get the template */
			        Template template = cfg.getTemplate("xmlTest.ftl");
			        
			        /* Merge data-model with template */
			        File file = new File(reportDir+"/"+xmlTestName+".html");
			        Writer out = new OutputStreamWriter(new FileOutputStream(file));
			        template.process(xmlTestMap, out);
				}
				
			}
		}catch(Exception e){
			//e.printStackTrace();
			gsLogger.writeERROR("Exception occured-> HTMLReporter -> createTestHTML() -> ", e);
		}

	}
	
	public static void createFeatureHTML(){
		try{
			Configuration cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(new File(System.getProperty("user.dir")+"/reporttemplate"));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
		    cfg.setDefaultEncoding("UTF-8");
		    
		    //Don't know what these 2 lines do. Copied from example
		    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
		    
		    List<String> xmlReports=getFeatureXMLReports();
			Iterator<String> i=xmlReports.iterator();
			
			while(i.hasNext()){
				//Hack. Running counter to create unique id.
				//Assign not working as expected in freemarker template
				int cnt=0;
				
				String report=i.next();
				
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.parse(report);
				
				doc.getDocumentElement().normalize();
				
				//get the test element
				NodeList xmlFeatureList = doc.getElementsByTagName("feature");
				
				Map<String,Object> xmlFeatureMap= new HashMap<String,Object>();
				
				//There is only one feature per xml
				Node xmlFeature=xmlFeatureList.item(0);
				
				NamedNodeMap attr = xmlFeature.getAttributes();
				
				Node name=attr.getNamedItem("name");
				
				xmlFeatureMap.put("featureName", name.getNodeValue());
				String xmlFeatureName = name.getNodeValue();
				
				//Create data-model
				List<Object> testScenarios=new ArrayList<Object>();
				
				xmlFeatureMap.put("testScenarios", testScenarios);
				
			    NodeList xmlScenarioList = xmlFeature.getChildNodes();
				
			    for (int j = 0; j < xmlScenarioList.getLength(); j++) {
					
					Map<String,Object> testScenarioMap= new HashMap<String,Object>();
					
					Node xmlScenario=xmlScenarioList.item(j);
					//System.out.println("Got "+testcase.getNodeType());
					
					if(!(xmlScenario.getNodeType()==Node.ELEMENT_NODE))
						continue;
					
					attr = xmlScenario.getAttributes();
					
					name=attr.getNamedItem("name");
					Node status=attr.getNamedItem("scenarioStatus");
					//Node duration=attr.getNamedItem("ellapsedtime");
					
					testScenarioMap.put("scenarioStatus", status.getNodeValue());
					testScenarioMap.put("scenarioName", name.getNodeValue());
					//Duration not calculated yet
					testScenarioMap.put("scenarioDuration", "");
					
					NodeList xmlStepDefinitionList = xmlScenario.getChildNodes();
					
					List<Object> stepDefintionList=new ArrayList<Object>();
					testScenarioMap.put("stepDefinitions", stepDefintionList);
					
					String stepDefinition="";
					for (int k = 0; k < xmlStepDefinitionList.getLength(); k++) {
						Map<String,Object> stepDefinitionMap= new HashMap<String,Object>();
						
						Node xmlStepDefinition=xmlStepDefinitionList.item(k);
						//System.out.println("Got "+testcase.getNodeType());
							
						if(!(xmlStepDefinition.getNodeType()==Node.ELEMENT_NODE))
							continue;
							
						attr = xmlStepDefinition.getAttributes();
							
						name=attr.getNamedItem("name");
						//This is not stepdefinition node if name is null. 
						//It is teststep node
						if(name == null)
							continue;
						
						
						Node stepDefinitionStatus=attr.getNamedItem("status");
						
						stepDefinitionMap.put("stepName", name.getNodeValue());
						stepDefinition=stepDefinition+name.getNodeValue()+"<br>";
						stepDefinitionMap.put("stepDefinitionStatus",stepDefinitionStatus.getNodeValue());
						
						NodeList xmlExecutionStepList = xmlStepDefinition.getChildNodes();
						
						List<Object> executionStepList=new ArrayList<Object>();
						stepDefinitionMap.put("testSteps", executionStepList);
						for(int l = 0; l < xmlExecutionStepList.getLength(); l++){
							cnt++;
							
							Map<String,String> executionStepMap= new HashMap<String,String>();
							
							Node xmlExecutionStep=xmlExecutionStepList.item(l);
							//System.out.println("Got "+testcase.getNodeType());
								
							if(!(xmlExecutionStep.getNodeType()==Node.ELEMENT_NODE))
								continue;
							
							attr = xmlExecutionStep.getAttributes();
							
							Node step=attr.getNamedItem("step");
							status=attr.getNamedItem("status");
							Node screenshot=attr.getNamedItem("screenshot");
							Node error=attr.getNamedItem("error");
							Node trace=attr.getNamedItem("stacktrace");
							
							executionStepMap.put("step", step.getNodeValue());
							if(error.getNodeValue().length()>15){
								executionStepMap.put("stepError", error.getNodeValue().substring(0,14));
							}
							else{
								executionStepMap.put("stepError", error.getNodeValue());
							}
							String fullError="Error Msg: "+error.getNodeValue();
							fullError=fullError+" Stacktrace: "+trace.getNodeValue();
							
							fullError=fullError.replaceAll("\"", "'");
							fullError=fullError.replaceAll("'", "\\'");
							fullError=fullError.replaceAll("\n", " ");
							
							executionStepMap.put("stepFullError", fullError);
							executionStepMap.put("stepScreenshot", screenshot.getNodeValue());
							executionStepMap.put("status", status.getNodeValue());
							executionStepMap.put("errorCounter", new Integer(cnt).toString());
							
							executionStepList.add(executionStepMap); 
						}
						
						stepDefintionList.add(stepDefinitionMap);
							
					}
					
					testScenarioMap.put("stepDefinition", stepDefinition);
					testScenarios.add(testScenarioMap);
			    }
			    /* Get the template */
		        Template template = cfg.getTemplate("xmlFeature.ftl");
		        
		        /* Merge data-model with template */
		        File file = new File(reportDir+"/"+xmlFeatureName+".html");
		        Writer out = new OutputStreamWriter(new FileOutputStream(file));
		        template.process(xmlFeatureMap, out);
			}
		}catch(Exception e){
			//e.printStackTrace();
			gsLogger.writeERROR("Exception occured-> HTMLReporter -> createFeatureHTML() -> ", e);
		}
		
	}

	private static List<String> getRegressionXMLReports(){
		List<String> xmlReports=new ArrayList<String>();
		
		File reportDir=new File(xmlReporter.getRegressionReportPath());
		
		File[] listOfFiles=reportDir.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		    	  if(listOfFiles[i].getAbsolutePath().endsWith(".xml"))
		    		  xmlReports.add(listOfFiles[i].getAbsolutePath());
		      }
		}
		
		return xmlReports;
	}
	
	private static List<String> getFeatureXMLReports(){
		List<String> xmlReports=new ArrayList<String>();
		
		File reportDir=new File(xmlReporter.getFeatureReportPath());
		
		File[] listOfFiles=reportDir.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		    	  if(listOfFiles[i].getAbsolutePath().endsWith(".xml"))
		    		  xmlReports.add(listOfFiles[i].getAbsolutePath());
		      }
		}
		
		return xmlReports;
	}
}