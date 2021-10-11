package testcases;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utilities.Constants;
import utilities.Log;
import utilities.Utilities;


public class TC01_ChannelNewsAsia_ValidateTitle {
	static WebDriver driver;
	public static final String element_headline_title="//div[@class='feature-card feature-card--']//a[contains(@class,'feature-card')]";
	public static final String element_Second_ReadFullStory="(//a[contains(text(),'Expand to read the full story')])[2]";
	
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
		
		driver.get(Constants.url);
		Utilities.waitForAjaxToComplete(driver);
	}
	
	@Test
	public void test_channelNewsAsia() throws Exception{
		
		JavascriptExecutor js = (JavascriptExecutor)driver;
		
		String sHeadLineTxt=driver.findElement(By.xpath(element_headline_title)).getText();
		Log.info("Headline Title is :"+sHeadLineTxt);
		
		//Validating Headline new item title
		Assert.assertEquals(sHeadLineTxt, Constants.expectedHeadLineText);
		
		driver.findElement(By.xpath(element_headline_title)).click();
		Log.info("Healine new item title is clicked");
		
		Utilities.waitForPageLoad(driver);
		
		String sHeadLinetitle=driver.getTitle();
		Assert.assertTrue(sHeadLinetitle.contains(Constants.expectedHeadLineText));
		Log.info("Expected Headline new item title page is loaded");
		
		//1st time ScrollDown
		Utilities.waitForAjaxToComplete(driver);
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Log.info("Scrolled Down till the bottom of the page");
		
		//2nd time ScrollDown
		Utilities.waitForAjaxToComplete(driver);
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Log.info("Scrolled Down till the bottom of the page");
		
		Utilities.waitForAjaxToComplete(driver);
		js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(element_Second_ReadFullStory)));
		
		driver.findElement(By.xpath(element_Second_ReadFullStory)).click();
		Log.info("Second New Item - Expand to read the full Story link is clicked");
		
		String articleTitle=driver.getTitle();
		Log.info("Expected Title is :"+Constants.expectedArticleTitle);
		Log.info("Actual Title is :"+articleTitle);
		driver.navigate().refresh();
		Utilities.waitForAjaxToComplete(driver);
		
		Assert.assertTrue(articleTitle.contains(Constants.expectedArticleTitle));
		Log.info("Verified the Second News Item title");
		
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
		Log.info("Browser is closed...!!!");
		Log.endTestCase();
	}
}
