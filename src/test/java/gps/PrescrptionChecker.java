package gps;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import prop.Properties;

public class PrescrptionChecker {

	WebDriver driver;
	JavascriptExecutor js;
	ArrayList<String> tabs;

	String emailPatient = Properties.emailPatient_gps_web;
	String lastName = Properties.lastName_gps_web;
	String storeId = Properties.storeId;
	String pin = Properties.pin;

	int indexAt = emailPatient.indexOf("@");
	String emailID = emailPatient.substring(0, indexAt);
	String code = "";
	String orderNo = "";

	@BeforeClass
	public void Setup() {

		System.setProperty("webdriver.chrome.driver", "D:\\ChromeDriver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.get("https://www.mailinator.com");
		tabs = new ArrayList<String>(driver.getWindowHandles());
		js = (JavascriptExecutor) driver;
	}

	@AfterClass
	public void Teardown() {
		driver.quit();
	}

	public void GetAuthorisationCode() {

		driver.findElement(By.id("addOverlay")).sendKeys(emailID);
		driver.findElement(By.id("go-to-public")).click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.xpath("//td[contains(text(), 'Your Prescription Details')]")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		 //WebElement frame1=driver.findElement(By.id("html_msg_body"));
         //driver.switchTo().frame(frame1);
		
		driver.switchTo().frame("html_msg_body");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WebElement order = driver.findElement(By.xpath("//p[contains(text(), 'Order Number')]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", order);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		orderNo = driver.findElement(By.xpath("//p[contains(text(), 'Order Number')]//child::span")).getText();

		
		WebElement auth = driver.findElement(By.xpath("//p[contains(text(), 'Prescription Auth Code')]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", auth);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		code = driver.findElement(By.xpath("//p[contains(text(), 'Prescription Auth Code')]//child::span")).getText();

	}

	@Test(priority = 1)
	public void Verify() {

		GetAuthorisationCode();
		
		driver.get("http://pharmacy.alt.thegpservice.com");
		driver.findElement(By.partialLinkText("To dispense a prescription,")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(By.name("authCode")).sendKeys(code);
		driver.findElement(By.name("patientSurname")).sendKeys(lastName);
		
		Select dropdown_day = new Select(driver.findElement(By.name("patientDobDay")));
		dropdown_day.selectByValue("1");
		Select dropdown_month = new Select(driver.findElement(By.name("patientDobMonth")));
		dropdown_month.selectByValue("3");
		Select dropdown_year = new Select(driver.findElement(By.name("patientDobYear")));
		dropdown_year.selectByValue("1990");
		
		driver.findElement(By.name("storeId")).sendKeys(storeId);
		driver.findElement(By.name("storePin")).sendKeys(pin);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(By.xpath("//button[text() = 'Verify']")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		String current_url = driver.getCurrentUrl();
		String expected_url = "https://pharmacy.alt.thegpservice.com/orders/" + orderNo;

		Assert.assertEquals(current_url, expected_url);
		
	}
	
	@Test(priority = 2)
	public void Complete() {
		
		driver.findElement(
				By.xpath("//button[text() = 'Print Prescription']//following-sibling::button[text() = 'Complete']"))
				.click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		driver.findElement(By
				.xpath("//div[@aria-hidden = 'false']//child::form//following-sibling::div//child::input[@name = 'pin']"))
				.sendKeys("0000");
		driver.findElement(By
				.xpath("//div[@aria-hidden = 'false']//child::form//following-sibling::div//child::input[@name = 'name']"))
				.sendKeys("Pharmacist");
		driver.findElement(By
				.xpath("//div[@aria-hidden = 'false']//child::form//following-sibling::div//child::input[@name = 'verifiedID']"))
				.click();
		driver.findElement(
				By.xpath("//div[@aria-hidden = 'false']//child::form//following-sibling::button[text() = 'Complete']"))
				.click();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String status = driver
				.findElement(By
						.xpath("//p[contains(text(), 'The electronic Prescription has been mailed')]//preceding-sibling::strong"))
				.getText();
		
		Assert.assertEquals(status, "Complete");
		
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		driver.findElement(By.xpath("//button[text() = 'Close']")).click();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
