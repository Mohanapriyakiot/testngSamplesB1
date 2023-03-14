package testScripts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DanubeExerciseFile {
	WebDriver driver;
	Properties prop;
	 ExtentReports reports;
	 ExtentSparkReporter spark;
	 ExtentTest extentTest;
		
		
	 @BeforeTest
	 public void setupExtent() {
	  reports = new ExtentReports();
	  spark = new ExtentSparkReporter("target\\sparkReport.html");
	  reports.attachReporter(spark);
	 }
	@BeforeTest
	public void launch() throws IOException
	{
		
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		String path=System.getProperty("user.dir")+"\\src\\test\\resources\\ConfigFiles\\config.properties";
		 prop=new Properties();
		FileInputStream obtained = new FileInputStream(path);
		prop.load(obtained);
		driver.get(prop.getProperty("url"));
		driver.get("https://danube-webshop.herokuapp.com");
	}
	// using XL
	@Test(dataProvider="Signup")
	public void signup(String name, String surname, String email, String password, String company) throws InterruptedException, InvalidFormatException, IOException
	{
		extentTest = reports.createTest("Signup Test");
		driver.findElement(By.id(readXLData("Signup"))).click();
		driver.findElement(By.id(readXLData("Name"))).sendKeys(name);
		driver.findElement(By.id(readXLData("Surname"))).sendKeys(surname);
		driver.findElement(By.id(readXLData("Email"))).sendKeys(email);
		driver.findElement(By.id(readXLData("Password"))).sendKeys(password);
		driver.findElement(By.id(readXLData("Company"))).sendKeys(company);
		driver.findElement(By.id(readXLData("Myself"))).click();
		driver.findElement(By.id(readXLData("Promotion"))).click();
		driver.findElement(By.id(readXLData("Policy"))).click();
		driver.findElement(By.xpath(readXLData("Register"))).click();		
	}
	//using XML
	@Test
	public void search() throws InterruptedException, SAXException, IOException, ParserConfigurationException {
//		driver.get(prop.getProperty("url"));
		extentTest = reports.createTest("Search Test");
		driver.findElement(By.name(readXMLData("search"))).sendKeys("Parry Hotter");
		driver.findElement(By.className(readXMLData("buttonsearch"))).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(readXMLData("addtocart"))).click();
		driver.findElement(By.xpath(readXMLData("checkout"))).click();
		Thread.sleep(3000);
		driver.findElement(By.id(readXMLData("Name"))).sendKeys("name");
		driver.findElement(By.id(readXMLData("Surname"))).sendKeys("surname");
		driver.findElement(By.id(readXMLData("Address"))).sendKeys("address");
		driver.findElement(By.id(readXMLData("Zipcode"))).sendKeys("zipcode");
		driver.findElement(By.id(readXMLData("City"))).sendKeys("city");
		driver.findElement(By.id(readXMLData("Company"))).sendKeys("company");
		driver.findElement(By.id(readXMLData("Asap"))).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(readXMLData("buy"))).click();
		Thread.sleep(2000);
	}
	
	public String readXLData(String objName) throws InvalidFormatException, IOException {
		  String objPath="";
		  String path=System.getProperty("user.dir")+"//src//test//resources//testData//danubeData.xlsx";
		  XSSFWorkbook workbook= new XSSFWorkbook(new File(path));
		  XSSFSheet sheet=workbook.getSheet("sheet1");
		  int numRows=sheet.getLastRowNum();
		  for(int i=0; i<=numRows; i++)
		  {
			  XSSFRow row=sheet.getRow(i);
			  if(row.getCell(0).getStringCellValue().equalsIgnoreCase(objName))
				  objPath=row.getCell(1).getStringCellValue();
		  }
		  return objPath;
	}
	public String readXMLData(String tagname) throws SAXException, IOException, ParserConfigurationException {
		  String path=System.getProperty("user.dir")+"//src//test//resources//testData//danubeXML.xml";
		  File file= new File(path);
		  DocumentBuilderFactory factory= DocumentBuilderFactory.newInstance();
		  DocumentBuilder build=factory.newDocumentBuilder();
		  Document document= build.parse(file);
		  NodeList List= document.getElementsByTagName("Danube");
		  Node node1=List.item(0);
		  Element elem=(Element)node1;
		  return elem.getElementsByTagName(tagname).item(0).getTextContent();
	  
	  }
	
	 @DataProvider(name="Signup")
	  public Object[][] getData() throws CsvValidationException, IOException{
		  String path=System.getProperty("user.dir")+"//src//test//resources//testData//danube.csv";
		  String[] cols;
		  CSVReader reader = new CSVReader(new FileReader(path));
		  ArrayList<Object> dataList=new ArrayList<Object>();
		  while((cols=reader.readNext())!=null)
		  {
			  Object[] record= {cols[0], cols[1], cols[2], cols[3], cols[4]};
			  dataList.add(record);
		  }
		  return dataList.toArray(new Object[dataList.size()][]);
		  
	  }
	 @AfterMethod
	    public void teardown(ITestResult result) {
	     if(ITestResult.FAILURE==result.getStatus()) {
	      extentTest.log(Status.FAIL, result.getThrowable().getMessage());
	     }
	     driver.close();
	   }
	    @AfterTest
	    public void finishExtent() {
	     reports.flush();
	    } 
}
