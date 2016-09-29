package config;
import config.WebElements;
import utility.Log;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import ExecutionEngine.DriverScript;

public class ActionKeywords {
	public static WebDriver driver;
	public static WebDriverWait wait;
	
	public static void openBrowser(String object,String testData){
		try{
			    System.setProperty("webdriver.chrome.driver", Constants.chormeBrowserPath);
			    driver = new ChromeDriver();
			    wait = new WebDriverWait(driver, Constants.waitTime);
			    /*else if (browserName.equalsIgnoreCase("firefox")) {
			    System.setProperty("webdriver.gecko.driver", Constants.firefoxBrowserPath);
			    driver = new FirefoxDriver();
			    wait = new WebDriverWait(driver, Constants.waitTime);
			   } else if (browserName.equalsIgnoreCase("InternetExplorer")) {
			    System.setProperty("webdriver.ie.driver", Constants.ieBrowserPath);
			    driver = new InternetExplorerDriver();
			    wait = new WebDriverWait(driver, Constants.waitTime);
			   } else {
			    java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
			    System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
			    driver = new HtmlUnitDriver();
			    wait = new WebDriverWait(driver, Constants.waitTime);
			   }*/
		}catch(Exception e){
			Log.error("unable to open Chrome Browser"+e.toString());
			DriverScript.bResult=false;
			DriverScript.exceptionMsg=e.toString();
			
		}
	
		}
	public static void navigate(String object,String testData){
		try
		{
		Log.info("In the navigate action method");
		driver.get(Constants.URL);
		Log.info("Opened the url:"+Constants.URL);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Constants.waitTime, TimeUnit.SECONDS);
		}catch(Exception e){
			Log.error("Unable to navigate the "+Constants.URL+":"+e.toString());
			DriverScript.bResult=false;
			DriverScript.exceptionMsg=e.toString();
		}
	}
	public static void waitFor(String object,String testData){
		try
		{
		Thread.sleep(Long.parseLong(testData));
		Log.info("Wait for Some Time");
		}catch(Exception e){
			Log.error("Exception occured in the wait method:"+e.toString());
			DriverScript.bResult=false;
			DriverScript.exceptionMsg=e.toString();
		}
		}
	public static void element_Click(String object,String testData)
	{
		try
		{
		Log.info("In click action method");
		WebElements.locatorType(object).click();
		}catch(Exception e)
		{
			Log.error("Unable to click the element:"+e.toString());
			DriverScript.bResult=false;
			DriverScript.exceptionMsg=e.toString();
		}
	}
	
	public static void textEnter(String object,String testData)
	{
		try
		{
			Log.info("In the text enter action method");
			WebElements.locatorType(object).sendKeys(testData);
		}
		catch (Exception e) {
			
			Log.error("Unable to enter the text in the web element:"+e.toString());
			DriverScript.bResult=false;
			DriverScript.exceptionMsg=e.toString();
		}
	}
	public static void dropDown_Select(String object, String selectableElement) {

		try {
			WebElement stateDropDwn = WebElements.locatorType(object);
			List<WebElement> drpdwnOptions = stateDropDwn.findElements(By.tagName("li"));

			for (WebElement drpDwnOption : drpdwnOptions) {

				System.out.println("dropdown options are:"+drpDwnOption.getText());
				if (drpDwnOption.getText().equals(selectableElement)) {
					drpDwnOption.click();
				}
			}
		} catch (Exception e) {

			DriverScript.bResult = false;
			System.out.println("Unable to select the Dropdown element :" + selectableElement + ":" + e.toString());
			Log.info("Unable to select the Dropdown element :" + selectableElement + ":" + e.toString());
			DriverScript.exceptionMsg=e.toString();
		}

	}
	public void explicit_WaitForElement(String object,String testData)
	{
		try
		{
		  if(testData.equals("visibility"))	
		  wait.until(ExpectedConditions.visibilityOf(WebElements.locatorType(object)));
		  else if(testData.equals("clickable"))
			  wait.until(ExpectedConditions.elementToBeClickable(WebElements.locatorType(object)));
		  else
			  wait.until(ExpectedConditions.titleIs("SundayKart : Order Online Shop Grocery Food Home Delivery"));
		  
		}
		catch(Exception e)
		{
			Log.error("Expection occured in explicit wait method:"+e.toString());
			DriverScript.exceptionMsg=e.toString();
		}
		
	}
	public static void closeBrowser(String object,String testData){
		try
		{
			String stng=driver.getTitle();
			if(stng.equalsIgnoreCase("SundayKart – Android Apps on Google Play"))
		driver.switchTo().window("SundayKart – Android Apps on Google Play");
		driver.close();
		driver.switchTo().window("Main window title");
		Log.info("Close the Browser");
		driver.quit();
		}catch(Exception e){
			Log.error("Expection occured while calling the quit method:"+e.toString());
			DriverScript.bResult=false;
			DriverScript.exceptionMsg=e.toString();
		}
	}
	public static void elementDisplayed(String object,String testData){
	
		try
		{
			Log.info("At element Displayed method of  locator:"+object);
			WebElement element = WebElements.locatorType(object);
			if(element.isDisplayed())
			  DriverScript.bResult= true;
			else
			{
				DriverScript.bResult=false;
				DriverScript.exceptionMsg="Element not displayed with that locator:"+object;
			}
			
		}
		catch(Exception e)
		{
			DriverScript.bResult=false;
			Log.error("Exception occured at elementDisplayed method:"+e.toString());
			DriverScript.exceptionMsg=e.toString();
		}
		
	}
	public static void closeChatPopup(String object,String testData){
		
		try
		{
			//wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));
			String chatFrame=object.split(";")[0];
	        String chatClose=object.split(";")[1];
			driver.switchTo().frame(1);
			WebElement chatMaximize=WebElements.locatorType(chatFrame);
			chatMaximize.click();
			WebElement chatMinimize=WebElements.locatorType(chatClose);
			chatMinimize.click();
			driver.switchTo().defaultContent();
			
		}catch(Exception e){
			DriverScript.bResult=false;
			Log.error("Exception occured at Close Pop up method:"+e.toString());
			DriverScript.exceptionMsg=e.toString();
		}
		
			
		}
	public static void scrollDown(String object,String testData){
		try
		{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		WebElement element=WebElements.locatorType(object);
		jse.executeScript("window.scrollBy(0,500)", element);
		}catch(Exception e){
			DriverScript.bResult=false;
			Log.error("Exception occured at Scroll Down Method:"+e.toString());
			DriverScript.exceptionMsg=e.toString();
		}
	}
	public static void monthDropDown(String object,String testData){
		WebElement element=WebElements.locatorType(object);
		Select sel=new Select(element);
		sel.selectByVisibleText("February");
		
		
		
	}
		
	
	

}
