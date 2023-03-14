package testScripts;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class OpenAccountExercise {
	WebDriver driver;
	String accCheck;
	@BeforeTest	
	public void start(){
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://parabank.parasoft.com/");
		
	}
	
	@Test(priority=4)
	public void register() throws InterruptedException
	{
		driver.findElement(By.partialLinkText("Register")).click();
		Thread.sleep(3000);
		driver.findElement(By.id("customer.firstName")).sendKeys("priya");
		driver.findElement(By.id("customer.lastName")).sendKeys("C");
		driver.findElement(By.id("customer.address.street")).sendKeys("KG nagar");
		driver.findElement(By.id("customer.address.city")).sendKeys("Salem");
		driver.findElement(By.id("customer.address.state")).sendKeys("tamilnadu");
		driver.findElement(By.id("customer.address.zipCode")).sendKeys("768753");
		driver.findElement(By.id("customer.phoneNumber")).sendKeys("9865424323");
		driver.findElement(By.id("customer.ssn")).sendKeys("1236486");
		driver.findElement(By.id("customer.username")).sendKeys("priya@13");
		driver.findElement(By.id("customer.password")).sendKeys("987654321");
		driver.findElement(By.xpath("//td/input[@id='repeatedPassword']")).sendKeys("987654321");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//td/input[@class='button']")).click();
		Thread.sleep(3000);
		
	}
	 @Test(priority=1)
	    public void customerlogin() {
	  	  driver.findElement(By.name("username")).sendKeys("priya@13");
	  	  driver.findElement(By.name("password")).sendKeys("987654321");
	  	  driver.findElement(By.xpath("//input[@class='button']")).click();
	    }
	
  @Test(priority=2)
  public void OpenAccount() throws InterruptedException
  {
	  Thread.sleep(3000);
	  driver.findElement(By.xpath("//li/a[contains(text(),'Open New Account')]")).click();
	  Select newacc=new Select(driver.findElement(By.xpath("(//select)[1]")));
	  newacc.selectByVisibleText("SAVINGS");
	  Thread.sleep(3000);
	  driver.findElement(By.xpath("//input[@class='button']")).submit();
	  Thread.sleep(3000);
	  Assert.assertEquals("Account Opened!", driver.findElement(By.xpath("//h1")).getText());
	  accCheck=  driver.findElement(By.id("newAccountId")).getText();
	  System.out.println(accCheck);
  }
  
  @Test(priority=3)
  public void overviewAccount() throws InterruptedException
  {
	  driver.findElement(By.xpath("//a[text()='Accounts Overview']")).click();
  	  
	  Thread.sleep(5000);
	 List<WebElement> list= driver.findElements(By.xpath("//td/a[@class='ng-binding']"));
//		 System.out.println(list.size());
  	  Assert.assertEquals(list.get(list.size()-1).getText(), accCheck);
  }
  
  @Test (dependsOnMethods="OpenAccount")
  public void Transferfund() throws InterruptedException {
	  driver.findElement(By.xpath("//li/a[contains(text(),'Fund')]")).click();
	  Thread.sleep(5000);
	  driver.findElement(By.xpath("//form//p/input")).sendKeys("1300");
	Select from=new Select(driver.findElement(By.id("fromAccountId")));
	from.selectByIndex(0);
	Select to=new Select(driver.findElement(By.id("fromAccountId")));
	to.selectByIndex(1);
	driver.findElement(By.xpath("//input[@class='button']")).submit();
	  Thread.sleep(5000);
	  Assert.assertEquals(driver.findElement(By.xpath("//h1")).getText(), "Transfer Complete!");
  }
  
  
/*  @AfterTest
  public void close()
  {
	  driver.close();
  }*/
}
