package utilities;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Utilities {
	
	public static WebDriver getDriver(String browser) {
		WebDriver driver=null;
		if(browser.equalsIgnoreCase("Chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}else if(browser.equalsIgnoreCase("Firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}else if(browser.equalsIgnoreCase("InternetExplorer") || browser.equalsIgnoreCase("IE") || browser.equalsIgnoreCase("Edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}else if(browser.equalsIgnoreCase("Opera") ) {
			WebDriverManager.operadriver().setup();
			driver = new OperaDriver();
		}
		driver.manage().window().maximize();
		Log.info("Browser window maximized");
		
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		
		return driver;
	}
	
	public static void waitForPageLoad(WebDriver driver) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);
			       
		wait.until(new Function<WebDriver, Boolean>() {
		     public Boolean apply(WebDriver driver) {
		       return ((JavascriptExecutor)driver).executeScript("return document.readyState").toString().equalsIgnoreCase("complete");
		     }
		});
		
		Log.info("Complete webpage is loaded");
	}
	
	public static void waitForAjaxToComplete(WebDriver driver) throws Exception{
		JavascriptExecutor js = (JavascriptExecutor)driver;
		String jQueryActiveScript="return jQuery.active";
		
		long activeQueries=100L;
		int i=0;
		do {
			i++;
			try {
				activeQueries =(Long) js.executeScript(jQueryActiveScript);
				
			}catch (Exception e) {
				activeQueries=100L;
				i=i+5;
			}
			Thread.sleep(1000);
			
		}while(activeQueries>0 && i<30);
	}
	
}
