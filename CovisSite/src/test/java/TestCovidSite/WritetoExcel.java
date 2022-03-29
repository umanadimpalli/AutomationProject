package TestCovidSite;


	import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
	import java.util.*;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
	import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
	import org.openqa.selenium.Keys;
	import org.openqa.selenium.PageLoadStrategy;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.chrome.ChromeOptions;
	import org.openqa.selenium.support.ui.ExpectedConditions;
	import org.openqa.selenium.support.ui.FluentWait;
	import org.openqa.selenium.support.ui.Select;
	import org.openqa.selenium.support.ui.Wait;
	import org.openqa.selenium.support.ui.WebDriverWait;
	import org.openqa.selenium.JavascriptExecutor;
	
	public class WritetoExcel 
	 {
    static WebDriver driver; 
    private static final String FILE_NAME = "/Users/venkatvarma/Desktop/ExcelsheetTypes/CovidWebScrapeagain.xlsx";
		static {
		System.setProperty("webdriver.chrome.driver", "/Users/venkatvarma/eclipse-workspace/seleni1/driver/chromedriver"); 
		driver=new ChromeDriver();
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setImplicitWaitTimeout(Duration.ofSeconds(10));
		chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		chromeOptions.addArguments("start-maximized");
		driver.get("http://office.suratsmartcity.com/SuratCOVID19/Home/COVID19BedAvailabilitydetails");
		}
		
	public static void getHospitalInfo() throws InterruptedException,IOException
		{
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		XSSFSheet sheet = workbook.createSheet("Hospitals");	
		//Selecting HospitalFacility
	    
		Select facility=new Select(driver.findElement(By.id("ddlFacilityType")));
	    facility.selectByIndex(1);
		//ExplicitWait
	    WebDriverWait expliwait = new WebDriverWait(driver, Duration.ofSeconds(20));
		
	    Select zones=new Select(driver.findElement(By.id("ddlZone")));
	    
	    List<WebElement> SelectZones=zones.getOptions();
	   
	   
         for(int i=2;i<=SelectZones.size();i++)
	    {
	    	Thread.sleep(1000);
	    	Select zone1=new Select(driver.findElement(By.id("ddlZone")));
	    	zone1.selectByValue(""+i);
	    	//System.out.println(SelectZones.get(1).getText());
	        //trying to get Selected Zone name---->Not working--->Throwing StaleElementReferenceException
	    	/*WebElement o = zones.getFirstSelectedOption();
	        String selectedoption = o.getText();
	        System.out.println("Selected element: " + selectedoption);*/
	    	
	    	
			
			
		    int noOfCards = driver.findElements(By.xpath("//div[@class='card custom-card']")).size();//10
			int k=0;
			while(k<noOfCards)
			{
			for(int j =1; j<=noOfCards;j++) 
			{
				By hospitalelement = By.xpath("//div[@class='card custom-card']["+j+"]//a");
				List<WebElement> hospitalElements=driver.findElements(hospitalelement);
				expliwait.until(ExpectedConditions.visibilityOfAllElements(hospitalElements));
				String hospitalName =hospitalElements.get(0).getText(); 
				WebElement bedElement = driver.findElement(By.xpath("//div[@class='card custom-card']["+j+"]//div[@class='caption-text']/span"));
				String totalbeds = bedElement.getText();
				System.out.println("Hospital Name : " +hospitalName + "\n"+ " Total Beds : "+totalbeds);
				By BoxElement=By.xpath("//div[@class='card-header']");
				List<WebElement> clickonbox=driver.findElements(BoxElement);
				expliwait.until(ExpectedConditions.visibilityOfAllElements(clickonbox));
				Thread.sleep(200);
				List<WebElement> Ventilators=driver.findElements(By.xpath("//div[contains(text(),'Ventilator')]/following-sibling::div"));
				List<WebElement> O2beds=driver.findElements(By.xpath("//div[contains(text(),'HDU(O2)')]/following-sibling::div"));
				clickonbox.get(k).click();
			    Thread.sleep(1000);
				System.out.println("Ventilators:"+Ventilators.get(k).getText());
				System.out.println("O2beds:"+O2beds.get(k).getText());
			
			    By clickcontact=By.xpath("//span[@class='pl-1']");
				List<WebElement> Contacts=driver.findElements(clickcontact);
				expliwait.until(ExpectedConditions.visibilityOfAllElements(Contacts));
				Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				              .withTimeout(Duration.ofSeconds(30))
							  .pollingEvery(Duration.ofSeconds(1))
							  .ignoring(NoSuchElementException.class);
				Contacts.get(k).click();
				//My Execution stops at this line while getting PhoneNumber of Sangini Hospital at SouthWestZone
				By addresslabel=By.xpath("//div[contains(text(),'Address')]");
				List<WebElement> labelsize=driver.findElements(addresslabel);
				if(labelsize.size()>0) 
				{
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Address')]")));		
				List<WebElement> ContactNo=driver.findElements(By.xpath("//a/span[@id='lblhosCno']"));
				String ContactNumber=ContactNo.get(0).getText();
				System.out.println("Contact Number:"+ContactNumber);
			    List<WebElement> Closebutton=driver.findElements(By.xpath("//button[@class='close']/span"));
			    Closebutton.get(0).click();
			    
			    //Creating Hashmap to store in excel//
			    Map<String,String> data=new LinkedHashMap<String,String>();
				data.put("Hospital Name",""+hospitalName);
				data.put("Available", ""+totalbeds);
				data.put("Ventilators",""+Ventilators.get(k).getText());
				data.put("O2beds",""+O2beds.get(k).getText());
				data.put("Contact",ContactNumber);
				
				int rowno=0;
				
				for(Map.Entry entry:data.entrySet())
				{
					XSSFRow row=sheet.createRow(rowno++);
					row.createCell(0).setCellValue((String)entry.getKey());
					
				}
				
				FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
		        workbook.write(outputStream);
				}
				else {System.out.println("No element found");}
				k++;
				}
			}

			}
         workbook.close();
	        System.out.println("vales are written in sheet");	
		
		}
	    public static void main(String args[]) throws Exception
		{
			System.out.println("Covid site Launched");
		    getHospitalInfo();
		}
		
	}



