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

public class AfterConsultationDoctor {

	WebDriver driver;
	JavascriptExecutor js;
	ArrayList<String> tabs;
	
	//////////////////////////////////////////////////////////////////////////////////
	String firstName = "Testjun07";
	String date = "2021-06-07";
	////////////////////////////////////////////////////////////////////////////////////
	
	String lastName = "Silva";
	String fullName = firstName + " " + lastName;
	String emailDoctor = "doctortest@mailinator.com";
	String passwordDoctor = "Qwerty123";

	@BeforeClass
	public void Setup() {

		System.setProperty("webdriver.chrome.driver", "D:\\ChromeDriver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.get("https://doctor.alt.thegpservice.com");
		js = (JavascriptExecutor) driver;
		tabs = new ArrayList<String>(driver.getWindowHandles());

	}

	@AfterClass
	public void Teardown() {
		driver.quit();
	}

	@Test(priority = 1)
	public void SignInDoctor() {

		driver.findElement(By.id("username")).sendKeys(emailDoctor);
		driver.findElement(By.id("password")).sendKeys(passwordDoctor);
		driver.findElement(By.xpath("//button[contains(text(),'Submit')]")).click();

		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String current_url = driver.getCurrentUrl();
		String expected_url = "https://doctor.alt.thegpservice.com/orders";

		Assert.assertEquals(current_url, expected_url);
	}

	@Test(priority = 2)
	public void viewFitnoteDoctor() {

		driver.findElement(By.xpath("//a[contains(text(),'Video Consultations')]")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Complete')]")).click();
		driver.findElement(By.xpath("//h4[contains(text(), '" + date + "')]//parent::div")).click();
		// driverDoc.findElement(By.xpath("//a[contains(text(),'Join
		// Consultation')]")).click();

		WebElement patient = driver.findElement(By.xpath("//span[contains(text(),'" + fullName + "')]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", patient);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		driver.findElement(
				By.xpath("//span[contains(text(),'" + fullName + "')]//parent::td//following-sibling::td//child::a"))
				.click();
		driver.findElement(By.xpath("//span[text() = 'Consultation']")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement fit = driver.findElement(By.linkText("View Fit Note"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", fit);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.linkText("View Fit Note")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		boolean condition = driver.findElement(By.xpath("//strong[text() = 'Abdominal pain']")).isDisplayed();
		Assert.assertTrue(condition);

		driver.findElement(By.xpath("//button[text() = 'Print']")).click();
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.close();
	}

	@Test(priority = 3)
	public void viewReferralLetterDoctor() {

		tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(0));
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(By.linkText("View Referral Letter")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement reasonText = driver.findElement(By.xpath("//h4[text() = 'Reason for Referral:']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", reasonText);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String reason = driver
				.findElement(By
						.xpath("//h4[text() = 'Reason for Referral:']//following-sibling::p[text() = 'Reason for referral']"))
				.getText();
		Assert.assertEquals(reason, "Reason for referral");

		WebElement print = driver.findElement(By.xpath("//button[text() = 'Print']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", print);
		driver.findElement(By.xpath("//button[text() = 'Print']")).click();
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.close();
	}

	@Test(priority = 4)
	public void viewPrescriptionDoctor() {

		tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(0));
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(By.linkText("View Prescription")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String medication = driver
				.findElement(By
						.xpath("//th[text() = 'Medication']//parent::thead//following-sibling::tbody//child::tr//child::td"))
				.getText();
		Assert.assertEquals(medication, "Acitretin");

		driver.findElement(By.xpath("//button[text() = 'Print']")).click();
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.close();
	}
	
	@Test(priority = 5)
	public void editNotesDoctor() {

		tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(0));

		WebElement history = driver.findElement(By.linkText("History"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", history);
		driver.findElement(By.linkText("History")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		driver.findElement(By
				.xpath("//td[contains(text(), '" + fullName + "')]//following-sibling::td//following-sibling::td//child::a[text() = 'Edit Notes']"))
				.click();
		
		driver.findElement(By.name("alerts")).clear();
		driver.findElement(By.name("alerts")).sendKeys("Key message alerts edited");
		
		driver.findElement(By.id("presentingComplaint")).clear();
		driver.findElement(By.id("presentingComplaint")).sendKeys("Presenting complaint edited");
		
		driver.findElement(By.id("recommendedActions")).clear();
		driver.findElement(By.id("recommendedActions")).sendKeys("Recommended actions edited");
		
		driver.findElement(By.id("additionalNotes")).clear();
		driver.findElement(By.id("additionalNotes")).sendKeys("Notes for patient record edited");
		
		driver.findElement(By.id("patientReferredDetails")).clear();
		driver.findElement(By.id("patientReferredDetails")).sendKeys("Referral details edited");
		
		driver.findElement(By.xpath("//button[text() = 'Update']")).click();
		
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		driver.findElement(By
				.xpath("//td[contains(text(), '" + fullName + "')]//following-sibling::td//following-sibling::td//child::a[text() = 'View Patient']"))
				.click();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		String alerts = driver.findElement(By.xpath("//strong[contains(text(), 'Key message alerts: ')]//following-sibling::span")).getText();
		String presentingComplaint[] = driver.findElement(By.xpath("//strong[text() = 'Presenting complaint']//parent::p")).getText().split("\\n");
		String recommendedActions[] = driver.findElement(By.xpath("//strong[text() = 'Recommended actions']//parent::p")).getText().split("\\n");
		String additionalNotes[] = driver.findElement(By.xpath("//strong[text() = 'Notes for patient record']//parent::p")).getText().split("\\n");
		String patientReferredDetails[] = driver.findElement(By.xpath("//strong[text() = 'Referral details']//parent::p")).getText().split("\\n");
		
		int count = 0;
		
		if(alerts.equals("Key message alerts edited")){
			count++;
		}
		if(presentingComplaint[1].equals("Presenting complaint edited")){
			count++;
		}
		if(recommendedActions[1].equals("Recommended actions edited")){
			count++;
		}
		if(additionalNotes[1].equals("Notes for patient record edited")){
			count++;
		}
		if(patientReferredDetails[1].equals("Referral details edited")){
			count++;
		}
		System.out.println(count);
		Assert.assertEquals(count, 5);
	
	}
}
