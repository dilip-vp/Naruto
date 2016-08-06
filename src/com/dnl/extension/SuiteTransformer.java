package com.dnl.extension;

/*import java.io.File;
import java.util.Iterator;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;*/

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.testng.ISuite;
import org.testng.ISuiteListener;

import com.dnl.report.HTMLReporter;
import com.dnl.report.XMLReporter;
import com.dnl.util.FrameWorkConfig;

import static com.dnl.report.XMLReporter.*;
import static com.dnl.util.DriverFactory.*;

public class SuiteTransformer implements ISuiteListener{
	private XMLReporter xmlReporter;
	@Override
	public void onFinish(ISuite suite) {
		closeDriver();
		xmlReporter.setSuiteEndTime(new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()));
		HTMLReporter.createHTMLReport();
	}

	@Override
	public void onStart(ISuite suite) {
		FrameWorkConfig.readConfig();
		xmlReporter=getXMLReporter();
		xmlReporter.createReport(suite.getName(),new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()));
		/*File suiteFile=new File(suite.getXmlSuite().getFileName());
		SAXReader reader = new SAXReader();
		
		try {
			Document document = reader.read(suiteFile);
			Element suiteElement=document.getRootElement();
			
			String suiteType=suiteElement.attributeValue("Type");
			
			if(suiteType.endsWith("Web")){
				//Its a web ui test case
			}
			
			
		} catch (DocumentException e) {
			// Consume for now
		}
		System.exit(0);*/
	}

}
