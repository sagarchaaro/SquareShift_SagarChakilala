package testcases;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utilities.Constants;
import utilities.Log;
import utilities.Utilities;


public class TC03_ChannelNewsAsia_SingaporeWeather {
	static WebDriver driver;
	public static final String element_AllSections="//span[text()='+ All Sections']";
	public static final String element_Weather="(//a[text()='Weather'])[3]";
	public static final String element_weatherCondition="//div[@class='info-today__condition']";
	public static final String element_MaxTemp="//span[@class='info-today__temp--max']";
	public static final String element_MinTemp="//span[@class='info-today__temp--min']";
	
	@BeforeClass
	public void startup() {
		String projectPath=System.getProperty("user.dir");
		DOMConfigurator.configure(projectPath+"//src//test//resources//Log4j.xml");
		
		String className=Thread.currentThread().getStackTrace()[1].getClassName().substring(Thread.currentThread().getStackTrace()[1].getClassName().indexOf('.')+1);
		
		Log.startTestCase(className);
		Log.info("Project Path is :"+projectPath);
	}
	
	@BeforeMethod
	public void launchBrowser() throws Exception{
		driver=Utilities.getDriver("Chrome");
		
		driver.get(Constants.url_InternationalEdition);
		Utilities.waitForAjaxToComplete(driver);
	}
	
	@Test
	public void test_channelNewsAsia() throws Exception{
		
		driver.findElement(By.xpath(element_AllSections)).click();
		Log.info("All Sections link is clicked");
		
		Utilities.waitForAjaxToComplete(driver);
		
		
		driver.findElement(By.xpath(element_Weather)).click();
		Log.info("Weather link from the menu is clicked");
		
		//Max Temp
		String maxTemp=driver.findElement(By.xpath(element_MaxTemp)).getText();
		Log.info("Max Temp is :"+maxTemp);
		
		//Min Temp
		String minTemp=driver.findElement(By.xpath(element_MinTemp)).getText();
		Log.info("Min Temp is :"+minTemp);
		
		//Weather Condition
		String weatherCondition=driver.findElement(By.xpath(element_weatherCondition)).getText();
		Log.info("Weather Condition is :"+weatherCondition);
				
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
		Log.info("Browser is closed...!!!");
		Log.endTestCase();
	}
}
