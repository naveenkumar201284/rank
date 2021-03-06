package com.qst.ohrm.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


/*import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.config.xml.*;
*/
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Log {

	// Initialize Log4j logs

	private static Logger loggerObj = Logger.getLogger(Log.class.getName() + "_" + ExtentReportFactory.getCurrentDateAndTime());//
	private static ExtentTest test ;
	private static ExtentReports reports;
	private static BufferedWriter bw = null;
	private static FileWriter fw = null;
	private static String logName = null;


	// This is to print log for the beginning of the test case, as we usually run so many test cases as a test suite

	public static void configureReport(){
		DOMConfigurator.configure("log4j.xml");
		reports = ExtentReportFactory.getInstance();

	}

	public static ExtentTest startReport(String testname){
		test= reports.startTest("Extent Report Started for " +testname);
		try {
			logName = ExtentReportFactory.getLogName(testname);
			fw = new FileWriter(logName,true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		bw = new BufferedWriter(fw);
		startTestCase(testname + "-started");
		return test;

	}
	public static void endReport(){
		
		endTestCase("ending the test -ended");
		reports.endTest(test);
		reports.flush();
		try {
			bw.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	public static void attachScreenShot(String path){
		String impagePath = test.addScreenCapture(path);
		
		//instead of fail changed to pass msg by naveen
		test.log(LogStatus.FAIL, "Test Failed " , impagePath);
		writeToText("---SCREENSHOT---" + impagePath);

	}
	
	public static void startTestCase(String sTestCaseName){

		loggerObj.info("****************************************************************************************");
		loggerObj.info("****************************************************************************************");
		loggerObj.info("$$$$$$$$$$$$$$$$$$$$$                 "+sTestCaseName+ "       $$$$$$$$$$$$$$$$$$$$$$$$$");
		loggerObj.info("****************************************************************************************");
		loggerObj.info("****************************************************************************************");
		writeToText("---Test case execution started---"+ sTestCaseName);

	}

	//This is to print log for the ending of the test case

	public static void endTestCase(String sTestCaseName){

		loggerObj.info("XXXXXXXXXXXXXXXXXXXXXXX             "+"-E---N---D-"+"             XXXXXXXXXXXXXXXXXXXXXX");
		loggerObj.info("X");
		loggerObj.info("X");
		loggerObj.info("X");
		loggerObj.info("X");
		writeToText("---Test case execution End---"+ sTestCaseName);
	}

	// Need to create these methods, so that they can be called  
	public static void pass(String message) {
		loggerObj.info(message);
		System.out.println(message);
		test.log(LogStatus.PASS, message);
		writeToText("---PASS---"+ message);
	}
	public static void fail(String message) {
		loggerObj.info(message);
		System.out.println(message);
		test.log(LogStatus.FAIL, message);
		writeToText("---FAIL---"+message);
		writeToText("Refer HTML Screen shot for the latest failed screen");
		Assert.fail("Test Failed");
	}
	public static void info(String message) {

		loggerObj.info(message);
		System.out.println(message);
		test.log(LogStatus.INFO, message);
		writeToText("---INFO---" + message);
	}

	public static void warn(String message) {

		loggerObj.warn(message);
		System.out.println(message);
		test.log(LogStatus.WARNING, message);
		writeToText("---WARNING---" + message);
	}

	public static void error(String message) {

		loggerObj.error(message);
		System.out.println(message);
		test.log(LogStatus.ERROR, message);
		writeToText("---ERROR---" + message);
	}

	public static void fatal(String message) {

		loggerObj.fatal(message);
		System.out.println(message);
		test.log(LogStatus.FATAL, message);
		writeToText("---FATAL---" + message);
	}

	public static void debug(String message) {

		loggerObj.debug(message);
		System.out.println(message);
		test.log(LogStatus.UNKNOWN, message);
		writeToText("---UNKNOWN---" + message);
	}

	public static void writeToText(String content){
		/*if(bw == null) {
			bw = new BufferedWriter();
		}*/
		try {
			System.out.println(content);
			bw.write(content);
			bw.newLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("error while writing log to text file");
		}
	}

	public static void writeToFailFile(String content){
		FileWriter fw1 = null;
		try {
			//logName = ExtentReportFactory.getLogName(testname);
			fw1= new FileWriter(logName.split("\\.")[0] + ".dif");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedWriter bw1= new BufferedWriter(fw1);
		try {
			bw1.write(content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error while writng error file :");
			e.printStackTrace();
		}
		finally{
			try {
				bw1.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}