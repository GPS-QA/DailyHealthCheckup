package gps;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import prop.Properties;

public class BookConsultation {

	WebDriver driver;
	JavascriptExecutor js;

	String emailPatient = Properties.emailPatient_gps_web;
	String passwordPatient = Properties.password;
	//String time = Properties.time;
	
	@BeforeClass
	public void Setup() {
		System.setProperty("webdriver.chrome.driver", "D:\\ChromeDriver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.get("https://app.alt.thegpservice.com");
		js = (JavascriptExecutor) driver;
	}

	@AfterClass
	public void Teardown() {
		driver.quit();
	}

	public void SignIn() {

		driver.findElement(By
				.xpath("//a[@routerlink= '/faqs']//parent::li//following-sibling::li//following-sibling::li//child::a[@routerlink= '/register/login']"))
				.click();
		driver.findElement(By.id("email")).sendKeys(emailPatient);
		driver.findElement(By.id("password")).sendKeys(passwordPatient);

		driver.findElement(By.xpath("//button[contains(text(),'SIGN IN')]")).click();
	}

	public void setAttributeValue(WebElement elem, String attr, String value) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute(arguments[1],arguments[2])", elem, attr, value);
	}

	@Test(priority = 1)
	public void Book() {

		SignIn();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		//driver.findElement(By.xpath("//button[contains(text(),'" + time + "')]")).click();
		driver.findElement(By.xpath("//div[contains(@class, 'slot-times')]//button[1]")).click();
		driver.findElement(By.id("presentingComplaint")).sendKeys("Additional details for the doctor");
		driver.findElement(By.name("postcode")).sendKeys("LE11AA");
		driver.findElement(By.xpath("//button[contains(text(),'Find Pharmacies')]")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		js.executeScript("window.scrollBy(0,500)");
		driver.findElement(By.xpath("//strong[contains(text(),'GPS QA Pharmacy')]")).click();
		js.executeScript("window.scrollBy(0,500)");
		driver.findElement(By.xpath("//button[contains(text(),'Confirm Appointment')]")).click();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		/*int size = driver.findElements(By.tagName("iframe")).size();

	    for(int i=0; i<=size; i++){
	    	driver.switchTo().frame(i);
	    	int total=driver.findElements(By.xpath("//input[@name = 'cardnumber']")).size();
	    	System.out.println(total);
	    	driver.switchTo().defaultContent();
	    }*/
		driver.switchTo().frame(1);
		driver.findElement(By.xpath("//input[@name = 'cardnumber']")).sendKeys("4242424242424242");
		driver.findElement(By.xpath("//input[@name = 'exp-date']")).sendKeys("1225");
		driver.findElement(By.xpath("//input[@name = 'cvc']")).sendKeys("123");
		driver.switchTo().defaultContent();
		js.executeScript("window.scrollBy(0,200)");
		driver.findElement(By.xpath("//label[@for = 'agreeMedication']")).click();
		driver.findElement(By.xpath("//label[@for = 'personalUse']")).click();
		driver.findElement(By.xpath("//label[@for = 'agreeFees']")).click();
		
		js.executeScript("window.scrollBy(0,200)");
		driver.findElement(By.xpath("//button[contains(text(),'Confirm Payment')]")).click();

		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(By
				.xpath("//a[@routerlink= '/faqs']//parent::li//following-sibling::li//following-sibling::li//child::a[@routerlink= '/my-account']"))
				.click();

		boolean status = driver.findElement(By.xpath("//span[text() = 'Booked']")).isDisplayed();
		Assert.assertTrue(status);

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	
}
