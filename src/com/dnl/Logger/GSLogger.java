package com.dnl.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import static com.dnl.globals.Constants.*;
import static com.dnl.report.XMLReporter.getXMLReporter;

import com.dnl.report.XMLReporter;

public class GSLogger {
	public static Logger logger = Logger.getLogger(GSLogger.class);
	public static GSLogger gsLogger = new GSLogger();
	
	public GSLogger(){
		//BasicConfigurator.configure();
		//PropertyConfigurator.configure("mylog4j.properties");	
		//gsLogger.writeINFO("This is my info logger");
		
		//Create and configure Console appender
		ConsoleAppender ca = new ConsoleAppender(); 
		String PATTERN = "%d [%p|%c|%C{1}] %m%n";
		ca.setLayout(new PatternLayout(PATTERN)); 
		ca.setThreshold(Level.INFO);
		ca.activateOptions();		
		//add appender to any Logger (here is root)
		Logger.getRootLogger().addAppender(ca);
	
		//Create and configure File appender
		FileAppender fa = new FileAppender();
		String loggerDirectory = createLoggerDir();
		fa.setFile(loggerDirectory+"/"+LOG_FILE_NAME);
		fa.setLayout(new PatternLayout(PATTERN));
		fa.setThreshold(Level.INFO);
		fa.setAppend(true);
		fa.activateOptions();
		
		//Add appenders to root Logger
		Logger.getRootLogger().addAppender(fa);		
	}
	
	
	/*public void createLogs(){
		try{
			String path=createLoggerDir();
			gsLogger.writeINFO("Path of Destnation direcotory : "+path);
			//FileUtils.copyFile(screenshot,new File(path+"/"+fileName));
			File srcFile = new File("D://GS Enterprise Task//Eclipse GS Workspace//mylog.log");
			File destDir = new File(path);
			FileUtils.copyFileToDirectory(srcFile, destDir, false);
			File srcFile1 = new File("D://GS Enterprise Task//Eclipse GS Workspace//gsenterprise.log");
			
			/*String dateTime=new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			File f=new  File(System.getProperty("user.dir")+"/results/logs/"+dateTime);
			f.mkdirs();*/
		/*}catch(Exception e){
			gsLogger.writeERROR("Exception occured-> Log4jLogger -> createLogs() -> ", e);
		}
	}*/
	
	/*public void deleteLogs(){
		File srcFile1 = new File("D://GS Enterprise Task//Eclipse GS Workspace//gsenterprise.log");
		srcFile1.delete();
		//srcFile1.deleteOnExit();
	}*/
	
	private String createLoggerDir() {
		XMLReporter xmlReporter=getXMLReporter();
		File f=new File(xmlReporter.getReportPath()+"/"+LOGGER_DIR);
		
		if(!f.exists())
			f.mkdir();
		
		return f.getAbsolutePath();
	}

	//Wrapper for info
	public void writeINFO(String message){
		String dateTime=new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		logger.info("["+dateTime+"]: "+ message);
	}

	//Wrapper for warn
	public void writeWARN(String message){
		String dateTime=new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		logger.warn("["+dateTime+"]: "+ message);
	}

	//Wrapper for error
	public void writeERROR(String message,Exception throwable){
		String dateTime=new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		logger.error("["+dateTime+"]: "+message, throwable);
	}
}

