package gps;

import java.util.ArrayList;
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

public class CancelConsultation {

	WebDriver driver;
	JavascriptExecutor js;
	ArrayList<String> tabs;
	
	String emailPatient = Properties.emailPatient_gps_web;
	String passwordPatient = Properties.password;
	String firstName = Properties.firstName;
	String lastName = Properties.lastName_gps_web;
	String emailDoctor = Properties.emailDoctor;
	String passwordDoctor = Properties.passwordDoctor;
	String date = Properties.date;
	
	String fullName = firstName + " " + lastName;
	WebElement timeSlot;
	String time;
	

	@BeforeClass
	public void Setup() {
		System.setProperty("webdriver.chrome.driver", "D:\\ChromeDriver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.get("https://app.alt.thegpservice.com");
		tabs = new ArrayList<String>(driver.getWindowHandles());
		js = (JavascriptExecutor) driver;
	}

	@AfterClass
	public void Teardown() {
		driver.quit();
	}

	public void SignInPatient() {

		driver.findElement(By
				.xpath("//a[@routerlink= '/faqs']//parent::li//following-sibling::li//following-sibling::li//child::a[@routerlink= '/register/login']"))
				.click();
		driver.findElement(By.id("email")).sendKeys(emailPatient);
		driver.findElement(By.id("password")).sendKeys(passwordPatient);
		driver.findElement(By.xpath("//button[contains(text(),'SIGN IN')]")).click();

		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void SignInDoctor() {

		js.executeScript("window.open('https://doctor.alt.thegpservice.com', '_blank');");
		tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window((String) tabs.get(1));

		driver.findElement(By.id("username")).sendKeys(emailDoctor);
		driver.findElement(By.id("password")).sendKeys(passwordDoctor);
		driver.findElement(By.xpath("//button[contains(text(),'Submit')]")).click();

		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void Book() {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		timeSlot.click();
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
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 1)
	public void CancelPatientLessThan3hrs() {

		SignInPatient();
		timeSlot = driver.findElement(By.xpath("//div[@class = 'slot-times']//button[2]"));
		time = driver.findElement(By.xpath("//div[@class = 'slot-times']//button[2]")).getText();
		Book();

		driver.findElement(
				By.xpath("//td[contains(text(), '" + time + "')]//following-sibling::td//child::a[text() = 'Cancel']"))
				.click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String msg = driver
				.findElement(By.xpath("//button[text() = 'No']//parent::p//preceding-sibling::p[contains(text(), 'As you wish')]"))
				.getText();

		driver.findElement(By.xpath("//button[text() = 'No']//following-sibling::button")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String status = driver
				.findElement(By.xpath("//td[contains(text(), '" + time + "')]//preceding-sibling::td//child::span"))
				.getText();
		
		if (msg.equals(
				"As you wish to cancel with less than 3 hours' notice, you will be charged the full consultation fee. Please click Confirm to proceed with your cancellation.")
				&& status.equals("CANCELLED BY PATIENT")) {
			Assert.assertTrue(true);
		}
		else{
			Assert.assertTrue(false);
		}

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 2)
	public void CancelPatientLessThan24hrs() {

		driver.findElement(By.partialLinkText("SEE A DOCTOR")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		js.executeScript("window.scrollBy(0,200)");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.xpath("//button[contains(text(), 'Show all times')]")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		js.executeScript("window.scrollBy(0,1000)");
		timeSlot = driver.findElement(By.xpath("//div[@class = 'slot-times']//child::div//button[16]"));
		time = driver.findElement(By.xpath("//div[@class = 'slot-times']//child::div//button[16]")).getText();
		Book();

		driver.findElement(
				By.xpath("//td[contains(text(), '" + time + "')]//following-sibling::td//child::a[text() = 'Cancel']"))
				.click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String msg = driver
				.findElement(By.xpath("//button[text() = 'No']//parent::p//preceding-sibling::p[contains(text(), 'You will not be charged')]"))
				.getText();

		driver.findElement(By.xpath("//button[text() = 'No']//following-sibling::button")).click();
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String status = driver
				.findElement(By.xpath("//td[contains(text(), '" + time + "')]//preceding-sibling::td//child::span"))
				.getText();

		System.out.println(msg);
		System.out.println(status);
		if (msg.equals(
				"You will not be charged the full consultation fee, but will be charged a late cancellation fee of £10. Please click Confirm to proceed with your cancellation.")
				&& status.equals("CANCELLED BY PATIENT")) {
			Assert.assertTrue(true);
		}
		else{
			Assert.assertTrue(false);
		}
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 3)
	public void CancelPatientMoreThan24hrs() {

		driver.findElement(By.partialLinkText("SEE A DOCTOR")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		js.executeScript("window.scrollBy(0,200)");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.xpath("//button[contains(text(), 'Choose a different day')]")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.xpath("//div[@class = 'alternative']//child::div[3]//child::button")).click();

		timeSlot = driver.findElement(By.xpath("//div[@class = 'slot-times']//button[5]"));
		time = driver.findElement(By.xpath("//div[@class = 'slot-times']//button[5]")).getText();
		Book();

		driver.findElement(
				By.xpath("//td[contains(text(), '" + time + "')]//following-sibling::td//child::a[text() = 'Cancel']"))
				.click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String msg = driver
				.findElement(By.xpath("//button[text() = 'No']//parent::p//preceding-sibling::p"))
				.getText();

		driver.findElement(By.xpath("//button[text() = 'No']//following-sibling::button")).click();
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String status = driver
				.findElement(By.xpath("//td[contains(text(), '" + time + "')]//preceding-sibling::td//child::span"))
				.getText();
		System.out.println(msg);
		System.out.println(status);
		if (msg.equals(
				"Are you sure you want to cancel this order?")
				&& status.equals("CANCELLED BY PATIENT")) {
			Assert.assertTrue(true);
		}
		else{
			Assert.assertTrue(false);
		}

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority = 4)
	public void CancelDoctor() {

		driver.findElement(By.partialLinkText("SEE A DOCTOR")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		js.executeScript("window.scrollBy(0,200)");
		timeSlot = driver.findElement(By.xpath("//div[@class = 'slot-times']//button[4]"));
		time = driver.findElement(By.xpath("//div[@class = 'slot-times']//button[4]")).getText();
		Book();
		SignInDoctor();
		
		driver.findElement(By.xpath("//a[contains(text(),'Video Consultations')]")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Booked')]")).click();
		driver.findElement(By.xpath("//h4[contains(text(), '" + date + "')]//parent::div")).click();
		
		WebElement patient = driver.findElement(By.xpath("//span[contains(text(),'" + fullName + "')]//parent::td//following-sibling::td//child::a//following-sibling::button"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", patient);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(
				By.xpath("//span[contains(text(),'" + fullName + "')]//parent::td//following-sibling::td//child::a//following-sibling::button"))
				.click();

		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		driver.findElement(By.id("techissues")).click();
		driver.findElement(By.id("cancelCharge")).click();
		driver.findElement(By.id("cancelReason")).sendKeys("Reason for cancellation");
		driver.findElement(By.xpath("//button[text() = 'Ok']")).click();
		
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		driver.navigate().refresh();
		
		driver.findElement(By.xpath("//a[contains(text(),'Cancelled')]")).click();
		driver.findElement(By.xpath("//h4[contains(text(), '" + date + "')]//parent::div")).click();
		
		WebElement cancel = driver.findElement(By.xpath("//span[contains(text(),'" + fullName + "')]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cancel);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		driver.switchTo().window((String) tabs.get(0));
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.navigate().refresh();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String status = driver
				.findElement(By.xpath("//td[contains(text(), '" + time + "')]//preceding-sibling::td//child::span"))
				.getText();
		Assert.assertEquals(status, "CANCELLED BY DOCTOR");
	}

}
