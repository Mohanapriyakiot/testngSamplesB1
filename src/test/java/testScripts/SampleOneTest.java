package testScripts;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.fasterxml.jackson.databind.annotation.JsonAppend.Prop;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SampleOneTest {
	
	WebDriver driver;
  	Properties prop;
    
	@BeforeMethod
	public void setup() {
//		WebDriverManager.chromedriver().setup();
//		driver=new ChromeDriver();
//		driver.manage().window().maximize();

	}
  @Test(alwaysRun=true, dependsOnMethods = "seleniumSearchTest")
  public void javaSearchTest() throws IOException {
	  
//	  System.setProperty("webdriver.chrome.driver", "E:\\Zuci\\chromedriver_win32\\chromedriver.exe");
//		WebDriver driver = new ChromeDriver();
		
	  String path=System.getProperty("user.dir")+"E:\\SDET Training WS\\testngSamplesB1\\src\\test\\resources\\configFiles\\config.properties";
		prop =new Properties();
		FileInputStream fileIn=new FileInputStream(path);
		prop.load(fileIn);
	    driver.get(prop.getProperty("url"));
		//driver.get("https://www.google.com/");
		WebElement searchBox = driver.findElement(By.name("q"));
		searchBox.sendKeys("Java Tutorial");
		searchBox.submit();
		Assert.assertEquals(driver.getTitle(), "Java Tutorial - Google Search");
		
  }


@Test // to setting sequence order to execute
public void seleniumSearchTest() {
	  System.setProperty("webdriver.chrome.driver", "E:\\Zuci\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		driver.get("https://www.google.com/");
		WebElement searchBox = driver.findElement(By.name("q"));
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(driver.getTitle(),"Google Page");
		searchBox.sendKeys("Selenium Tutorial");
		searchBox.submit();
		Assert.assertEquals(driver.getTitle(), "Selenium Tutorial - Google Search Page");
		softAssert.assertAll();
}

@Test(enabled=false)  // to ignore the test
public void cucumberSearchTest() {
	  
//	  System.setProperty("webdriver.chrome.driver", "E:\\Zuci\\chromedriver_win32\\chromedriver.exe");
//		WebDriver driver = new ChromeDriver();
		
		driver.get("https://www.google.com/");
		WebElement searchBox = driver.findElement(By.name("q"));
		searchBox.sendKeys("cucumber");
		searchBox.submit();
		Assert.assertEquals(driver.getTitle(), "cucumber - Google Search");
		
}

@Parameters("browser")
@BeforeMethod

public void setup(String strBrowser) {
	if(strBrowser.equalsIgnoreCase("chrome")) {
			 WebDriverManager.chromedriver().setup();
			 driver = new ChromeDriver();
}
     driver.manage().window().maximize();
}
//@AfterMethod
//public void teardown() {
//driver.close();
//}
}





