package gps;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import prop.Properties;

public class SignUpPatient {

	WebDriver driver;
	JavascriptExecutor js;
	
	String emailPatient = Properties.emailPatient_gps_web;
	String passwordPatient = Properties.password;
	String firstName = Properties.firstName;
	String lastName = Properties.lastName_gps_web;
	String phone = Properties.phone_gps_web;
	//String time = Properties.time;
	
	@BeforeClass
	public void Setup() {

		System.setProperty("webdriver.chrome.driver", "D:\\ChromeDriver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.get("https://app.alt.thegpservice.com");
		js = (JavascriptExecutor) driver;
	}

	@AfterClass
	public void Teardown() {
		driver.quit();
	}

	@Test(priority = 1)
	public void SignUp_Patient() {

		driver.findElement(By
				.xpath("//a[@routerlink= '/faqs']//parent::li//following-sibling::li//following-sibling::li//following-sibling::li//child::a[@routerlink= '/register']"))
				.click();
		driver.findElement(By.id("email")).sendKeys(emailPatient);
		driver.findElement(By.id("telnumber-field")).sendKeys(phone);
		driver.findElement(By.id("rpassword")).sendKeys(passwordPatient);
		driver.findElement(By.id("cpassword")).sendKeys(passwordPatient);
		js.executeScript("window.scrollBy(0,500)");
		driver.findElement(By.xpath("//button[contains(text(),'NEXT')]")).click();
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(By.id("firstName")).sendKeys(firstName);
		driver.findElement(By.id("lastName")).sendKeys(lastName);
		Select dropdown_day = new Select(driver.findElement(By.name("day")));
		dropdown_day.selectByValue("1: 1");
		Select dropdown_month = new Select(driver.findElement(By.name("month")));
		dropdown_month.selectByValue("3: 3");
		Select dropdown_year = new Select(driver.findElement(By.name("year")));
		dropdown_year.selectByValue("91: 1990");
		js.executeScript("window.scrollBy(0,1000)");
		driver.findElement(By.xpath("//button[contains(text(),'Female')]")).click();
		driver.findElement(By.name("patientPostcodeInput")).sendKeys("LE11AA");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(By.name("patientPostcodeInput")).sendKeys(Keys.RETURN);
		// driver.findElement(By.xpath("//span[contains(text(),'LE1
		// 1AA')]")).click();
		Select dropdown_addr = new Select(driver.findElement(By.id("userAddressInput")));
		dropdown_addr.selectByValue("3: Object");
		js.executeScript("window.scrollBy(0,500)");
		driver.findElement(By.xpath("//button[contains(text(),'NEXT')]")).click();

		driver.findElement(By.xpath("//button[contains(text(),'NO')]")).click();
		driver.findElement(By.xpath("//button[contains(text(),'SIGN UP')]")).click();

		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String current_url = driver.getCurrentUrl();
		String expected_url = "https://app.alt.thegpservice.com/appointments";

		Assert.assertEquals(current_url, expected_url);

	}

	@Test(priority = 2)
	public void BookConsultation_Patient() {

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

		/*String current_url = driver.getCurrentUrl();
		String expected_url = "https://app.alt.thegpservice.com/order/thankyou";

		Assert.assertEquals(current_url, expected_url);*/

	}

}
