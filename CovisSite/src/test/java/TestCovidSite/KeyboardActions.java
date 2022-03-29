package TestCovidSite;


import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

public class KeyboardActions {
	
	static WebDriver driver;
	
	static {
		System.setProperty("webdriver.chrome.driver", "/Users/venkatvarma/eclipse-workspace/seleni1/driver/chromedriver"); 
		driver = new ChromeDriver();
	    
		driver.get("https://google.com");
	//driver.get("http://demo.guru99.com/test/guru99home/");
	
	}
	
	public static void main(String[] args) {
		
		try {
			
			//robot ();
			actionsExample();
			//clicks();
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	

	}
	
	public static void actionsExample() throws InterruptedException {
		
		/*driver.findElement(By.name("q")).sendKeys("hello world" + Keys.ENTER);
		
		Actions action1 = new Actions(driver);
				
		Action pressthekey = action1.keyDown(Keys.COMMAND).sendKeys("a").build();
		pressthekey.perform(); */
		
	 Actions action = new Actions(driver);
		
	    WebElement search = driver.findElement(By.name("q"));
	    
		//Thread.sleep(3000);
	   
	    action.keyDown(Keys.SHIFT).sendKeys(search,"selenium").keyUp(Keys.SHIFT).sendKeys(" classes" + Keys.ENTER).perform(); 
	  
	    Action keydown1 = action.keyDown(Keys.COMMAND).sendKeys("a").build();
		keydown1.perform(); 
		
		
		
	}
	/*public static void robot() throws InterruptedException, AWTException {
		

			driver.get("https://www.edureka.co");
			driver.findElement(By.linkText("Courses")).click();
			
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_KP_DOWN);
			Thread.sleep(2000);
			robot.mouseMove(30,100);

	}*/
	
	/*public static void clicks() {
		
			WebElement searchBtn = driver.findElement(By.linkText("Sign in"));
			
			Actions action = new Actions(driver);
			
		    WebElement search = driver.findElement(By.name("q"));

		    action.keyDown(Keys.SHIFT).sendKeys(search,"selenium").keyUp(Keys.SHIFT).sendKeys(" classes" + Keys.ENTER).perform(); 

			
			//action.moveToElement(searchBtn).build().perform();
			
			//Java Script Executor 
			((JavascriptExecutor)driver).executeScript("window.scrollBy(0,1000)");*/

	}


//}
