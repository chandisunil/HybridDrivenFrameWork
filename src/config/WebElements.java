package config;

import static ExecutionEngine.DriverScript.OR;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WebElements {
	
	public static String readProperties(String key){
		System.out.println("Key is" +key +"Values is" +OR.getProperty(key));
		return OR.getProperty(key);
	}
	public static WebElement locatorType(String key){
		key=readProperties(key);
		String locators[]=key.split("=>");
		String locatorType=locators[0];
		String locatorValue=locators[1];
		if(locatorType.equalsIgnoreCase("id"))
			  return ActionKeywords.driver.findElement(By.id(locatorValue));
			else if(locatorType.equalsIgnoreCase("name"))
				return ActionKeywords.driver.findElement(By.name(locatorValue));
			else if(locatorType.equalsIgnoreCase("class"))
				return ActionKeywords.driver.findElement(By.className(locatorValue));
			else if(locatorType.equalsIgnoreCase("xpath"))
				return ActionKeywords.driver.findElement(By.xpath(locatorValue));
			else if(locatorType.equalsIgnoreCase("css"))
				return ActionKeywords.driver.findElement(By.cssSelector(locatorValue));
			else if(locatorType.equalsIgnoreCase("link"))
					return ActionKeywords.driver.findElement(By.linkText(locatorValue));
			else if(locatorType.equalsIgnoreCase("partiallink"))
				return ActionKeywords.driver.findElement(By.linkText(locatorValue));
			else
			return null;
		
	}
 

}
