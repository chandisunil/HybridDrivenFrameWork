package ExecutionEngine;
import config.ActionKeywords;
import config.WebElements;
import config.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.log4j.xml.DOMConfigurator;

import utility.ExcelUtilities;
import utility.Log;
public class DriverScript {
	public static Properties OR;
	public static String locatorValue;
	public static String testData;
	public static ActionKeywords action;
	public static String sAction;
	public static Method method[];
	public static boolean bResult=true;
	public static String exceptionMsg = "";
	public static String sTestCaseID;
	public static String sRunMode;
	public static int iTestStep;
	public static int iTestLastStep;
	
	public DriverScript(){
		action=new ActionKeywords();
		method=action.getClass().getMethods();
		DOMConfigurator.configure("log.xml");
	}

	public static void main(String[] args) throws Exception {
		WebElements element=new WebElements();
		DriverScript ds =new DriverScript();
		String sPath=Constants.filePath;
		FileInputStream fis=new FileInputStream(new File(Constants.OR_Path));
		OR=new Properties();
		OR.load(fis);
		ExcelUtilities.setExcelFile(sPath, Constants.sheetName_TS);
		ds.execute_TestCases();
	    

	}
	public void execute_TestCases() throws Exception
	{
		int iTotalTestCases= ExcelUtilities.getRowCount(Constants.sheetName_TC);
		for (int iTestCase = 1; iTestCase <iTotalTestCases; iTestCase++) {
			
		 sTestCaseID= ExcelUtilities.getCellData(iTestCase, Constants.col_TestCaseId, Constants.sheetName_TC);
	 	 sRunMode= ExcelUtilities.getCellData(iTestCase, Constants.Col_RunMode, Constants.sheetName_TC);
	 	 if(sRunMode.equalsIgnoreCase("Yes"))
	 	 {
	 		Log.startTestCase(sTestCaseID);
	 		 iTestStep= ExcelUtilities.getRowContains(sTestCaseID, Constants.col_TestCaseId, Constants.sheetName_TS);
	 		iTestLastStep= ExcelUtilities.getTestStepsCount(Constants.sheetName_TS, sTestCaseID, iTestStep);
	 		
	 		for (; iTestStep < iTestLastStep;iTestStep++) {
				
	 			sAction=ExcelUtilities.getCellData(iTestStep, Constants.col_ActionKeyWords, Constants.sheetName_TS);
	 			locatorValue=ExcelUtilities.getCellData(iTestStep, Constants.col_LocatorValue, Constants.sheetName_TS);
	 			testData=ExcelUtilities.getCellData(iTestStep, Constants.col_TestData, Constants.sheetName_TS);
	 			execute_Actions();
	 			if (bResult == false) {

					ExcelUtilities.setCellData(Constants.Keyword_Fail, iTestCase, Constants.col_Result,
							Constants.sheetName_TC);

					Log.endTestCase(sTestCaseID);

					break;
				}

			}
	 	 }
	 	if (bResult == true) {
			ExcelUtilities.setCellData(Constants.Keyword_Pass, iTestCase, Constants.col_Result,
					Constants.sheetName_TC);
			Log.endTestCase(sTestCaseID);
		}
		}
		
	}
	
	public static void execute_Actions() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		//System.out.println("Total no of methods:"+method.length);
		try
		{
		for (int i = 0; i < method.length; i++) {
			if(method[i].getName().equals(sAction)){
				method[i].invoke(action,locatorValue,testData);
				if (bResult == true) {
					ExcelUtilities.setCellData(Constants.Keyword_Pass, iTestStep, Constants.col_TestStepResult,
							Constants.sheetName_TS);
					break;
				} else {
					ExcelUtilities.setCellData(Constants.Keyword_Fail, iTestStep, Constants.col_TestStepResult,
							Constants.sheetName_TS);
					ExcelUtilities.setCellData(exceptionMsg, iTestStep, Constants.col_TestStepException,
							Constants.sheetName_TS);
					exceptionMsg = "";
					break;
				}
				}
		
	}
		}catch(Exception e){
			Log.error("Expection occured while exectuing the action methods:" + e.toString());
		}

}
}	
