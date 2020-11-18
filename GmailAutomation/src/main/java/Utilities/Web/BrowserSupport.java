package Utilities.Web;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class BrowserSupport {

	public static WebDriver driver;
	
	public static HashMap <String, String> appPropsHM = new HashMap<String, String>();
	
	public enum WebDriverType{
		Firefox,
		Chrome,
		Iexplorer;
	}
	

	
	public BrowserSupport() {
		Properties appProps = new Properties(); 
		FileInputStream objfile;
		try {
			objfile = new FileInputStream(System.getProperty("user.dir")+"\\application.properties");
			appProps.load(objfile);
			appPropsHM.put("implicitWait",appProps.getProperty("implicitlyWait"));
			appPropsHM.put("pageLoadWait", appProps.getProperty("PageLoadWait"));
			appPropsHM.put("appURL", appProps.getProperty("appURL"));
			appProps = null;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
		
			e.printStackTrace();
			System.exit(1);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		
	}
	
	public static WebDriver getNewDriver(WebDriverType BrowserType) {
		
		switch (BrowserType) {
			case Firefox:
				return new FirefoxDriver();
			case Chrome:
			return new ChromeDriver();
			case Iexplorer:
				return new InternetExplorerDriver();
			default:
				throw new InvalidParameterException("You must choose one of the defined driver types");
		
		}
	}
	
	public void pageRefresh() {
		
		driver.navigate().refresh();
	}
	
	public void browserClose() {
		driver.close();
	}
	
	public void pageLoad() {
	
		long pageWait = Long.parseLong(appPropsHM.get("PageLoadWait"));
		driver.manage().timeouts().pageLoadTimeout(pageWait, TimeUnit.SECONDS);

	}
	
	public void implicitWait() {
		
		long impWait = Long.parseLong(appPropsHM.get("implicitlyWait"));
		driver.manage().timeouts().implicitlyWait(impWait, TimeUnit.SECONDS);
	}
	
}
