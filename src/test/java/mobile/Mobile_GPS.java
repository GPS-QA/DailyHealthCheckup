package mobile;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Mobile_GPS {
	
	WebDriver driver;

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	String firstName = "Testjun04";
	String date = "2021-06-04";
	String FitnoteFromDate = "05/25";
	String FitnoteExpirationDate = "06/10/2021";
	String lastName = "Wijerathna";
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
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
	public void JoinConsultationDoctor() {

		driver.findElement(By.xpath("//a[contains(text(),'Video Consultations')]")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Booked')]")).click();
		driver.findElement(By.xpath("//h4[contains(text(), '" + date + "')]//parent::div")).click();
		// driverDoc.findElement(By.xpath("//a[contains(text(),'Join
		// Consultation')]")).click();
		driver.findElement(
				By.xpath("//span[contains(text(),'" + fullName + "')]//parent::td//following-sibling::td//child::a"))
				.click();

		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(By.xpath("//button[contains(text(),'Yes')]")).click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(
				By.xpath("//label[contains(text(),'Are you sure you want to')]//following-sibling::div//child::button"))
				.click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		/*
		 * WebElement we = driver.findElement(By.
		 * xpath("//h4[contains(text(),'Prescribed medication in the past 28 days')]//parent::div//following-sibling::div[@class  = 'modal-footer']//child::button"
		 * )); if(we != null){ we.click(); }
		 */

		driver.findElement(By.xpath("//button[contains(text(),'Join Call')]")).click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		boolean endcall = driver.findElement(By.xpath("//button[@class = 'end_call']")).isDisplayed();
		Assert.assertTrue(endcall);

		fillPatientNote();

	}

	public void fillPatientNote() {
		
		driver.findElement(By.name("alerts")).sendKeys("Key message alerts");

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(By.xpath("//label[@for = 'relevantPastMedicalHistory']//following-sibling::a")).click();
		driver.findElement(By.id("relevantPastMedicalHistory")).sendKeys("Relevant past medical history");

		driver.findElement(By.xpath("//label[@for = 'relevantPastFamilyHistory']//following-sibling::a")).click();
		driver.findElement(By.id("relevantPastFamilyHistory")).sendKeys("Relevant past family history");

		driver.findElement(By.xpath("//label[@for = 'medicationHistory']//following-sibling::a")).click();
		driver.findElement(By.id("medicationHistory")).sendKeys("Drug history (past and present)");

		driver.findElement(By.xpath("//label[@for = 'allergyInformation']//following-sibling::a")).click();
		driver.findElement(By.id("allergyInformation")).sendKeys("Allergy information");

		driver.findElement(By.xpath("//label[@for = 'examinationFindings']//following-sibling::a")).click();
		driver.findElement(By.id("examinationFindings")).sendKeys("Examination Findings");

		driver.findElement(By.xpath("//label[@for = 'patientRegLocationYes']//following-sibling::input")).click();
		driver.findElement(By.name("patientAltLocation")).sendKeys("New patient's location");

		driver.findElement(By.xpath("//label[@for = 'shareDetailsWithGpYes']//preceding-sibling::input")).click();
		driver.findElement(By.id("nhsGPDetails")).sendKeys("Patients local GP details");

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(By.id("presentingComplaint")).sendKeys("Presenting complaint");
		driver.findElement(By.xpath("//label[@for = 'painNo']//child::input")).click();
		driver.findElement(By.id("differentialDiagnosis")).sendKeys("Abdominal pain");

		//driver.findElement(By.id("differentialDiagnosisNotes")).sendKeys("Additional diagnosis notes");
		driver.findElement(By.id("recommendedActions")).sendKeys("Recommended actions");
		driver.findElement(By.id("additionalNotes")).sendKeys("Notes for patient record");
		driver.findElement(By.id("patientReferredDetails")).sendKeys("Referral details");

		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Test(priority = 3)
	public void sendMessageDoctor() {

		driver.findElement(By.xpath("//input[@placeholder='Enter a message']")).sendKeys("Hello patient");
		driver.findElement(By.xpath("//button[contains(text(), 'Send')]")).click();
		
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 4)
	public void proposePrescriptionDoctor() {

		driver.findElement(By
				.xpath("//button[@aria-controls = 'dropdown-dropup']//parent::div//preceding-sibling::div[@class = 'btn-group dropup']//child::button"))
				.click();
		driver.findElement(By.xpath("//a[contains(text(),'Prescription (£7.49)')]")).click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		boolean ProposePrescription = driver
				.findElement(By.xpath("//p[text() = 'Prescription offered at the cost of £7.49']")).isDisplayed();
		Assert.assertTrue(ProposePrescription);
		
	}

	@Test(priority = 5)
	public void fillPrescriptionDoctor() {

		WebDriverWait wait = new WebDriverWait(driver, 500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'action-buttons']//child::button")));
		
		driver.findElement(By.xpath("//div[@class = 'action-buttons']//child::button")).click();
		// Acitretin//Capsules//25mg
		// Acarbose//Tablets//100mg
		// Acetazolamide//Tablets//250mg

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.name("medicationSearch")).sendKeys("Acitretin");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.name("medicationSearch")).sendKeys(Keys.RETURN);
		Select formulation = new Select(driver.findElement(By.name("formulation")));
		formulation.selectByValue("Capsules");
		Select strength = new Select(driver.findElement(By.id("strength")));
		strength.selectByValue("25mg");
		driver.findElement(By.id("dosage")).sendKeys("Dosage instructions");
		driver.findElement(By.id("quantity")).sendKeys("25");
		driver.findElement(By.id("unit")).sendKeys("unit");
		driver.findElement(
				By.xpath("//label[contains(text(),'Yes') and @class = 'radio-label']//following-sibling::input"))
				.click();
		driver.findElement(By.xpath("//button[contains(text(),'Clear')]//following-sibling::button")).click();
		driver.findElement(By.id("additionalPatientNotes")).sendKeys("Additional patient notes");
		driver.findElement(By.xpath("//button[contains(text(),'Complete Prescription')]")).click();
		driver.findElement(By.id("pin")).sendKeys("0000");
		driver.findElement(By.xpath("//button[contains(text(),'Confirm') and @type = 'button']")).click();

		boolean issuePrescription = driver.findElement(By.xpath("//div[text() = 'Prescription issued']")).isDisplayed();
		Assert.assertTrue(issuePrescription);

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 6)
	public void proposeFitNoteDoctor() {

		driver.findElement(By
				.xpath("//button[@aria-controls = 'dropdown-dropup']//parent::div//preceding-sibling::div[@class = 'btn-group dropup']//child::button"))
				.click();
		driver.findElement(By.xpath("//a[contains(text(),'Fit note (£15.00)')]")).click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		boolean ProposeFitNote = driver.findElement(By.xpath("//p[text() = 'Fit Note offered at the cost of £15']"))
				.isDisplayed();
		Assert.assertTrue(ProposeFitNote);
		
	}

	@Test(priority = 7)
	public void fillFitNoteDoctor() {

		WebDriverWait wait = new WebDriverWait(driver, 500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'action-buttons']//child::button//following-sibling::button")));

		driver.findElement(By.xpath("//div[@class = 'action-buttons']//child::button//following-sibling::button"))
				.click();
		// driver.findElement(By.xpath("//div[@class =
		// 'action-buttons']//child::button")).click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(By.name("fit")).click();
		// driver.findElement(By.id("fromDate")).sendKeys("04/30/2021");
		driver.findElement(By.id("fromDate")).sendKeys(FitnoteFromDate);
		driver.findElement(By.id("expirationDate")).sendKeys(FitnoteExpirationDate);
		driver.findElement(By.xpath("//button[contains(text(),'Create')]")).click();
		driver.findElement(By.id("pin")).sendKeys("0000");
		driver.findElement(By.xpath("//button[contains(text(),'Confirm') and @type = 'button']")).click();

		boolean issueFitNote = driver.findElement(By.xpath("//div[text() = 'Fit Note issued']")).isDisplayed();
		Assert.assertTrue(issueFitNote);

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 8)
	public void proposeReferralLetterDoctor() {

		driver.findElement(By
				.xpath("//button[@aria-controls = 'dropdown-dropup']//parent::div//preceding-sibling::div[@class = 'btn-group dropup']//child::button"))
				.click();
		driver.findElement(By.xpath("//a[contains(text(),'Referral letter (£10.00)')]")).click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		boolean ProposeReferralLetter = driver
				.findElement(By.xpath("//p[text() = 'Referral Letter offered at the cost of £10']")).isDisplayed();
		Assert.assertTrue(ProposeReferralLetter);
		
	}

	@Test(priority = 9)
	public void fillReferralLetterDoctor() {

		WebDriverWait wait = new WebDriverWait(driver, 500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'action-buttons']//child::button//following-sibling::button//following-sibling::button")));
		
		driver.findElement(By
				.xpath("//div[@class = 'action-buttons']//child::button//following-sibling::button//following-sibling::button"))
				.click();
		// driver.findElement(By.xpath("//div[@class =
		// 'action-buttons']//child::button")).click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(By.id("reason")).sendKeys("Reason for referral");
		driver.findElement(By.id("speciality")).sendKeys("Referral speciality");

		driver.findElement(By
				.xpath("//label[contains(text(),'Referral speciality')]//parent::div//following-sibling::button[contains(text(),'Add')]"))
				.click();
		driver.findElement(By.id("pin")).sendKeys("0000");
		driver.findElement(By.xpath("//button[contains(text(),'Confirm') and @type = 'button']")).click();

		boolean issueReferralLetter = driver.findElement(By.xpath("//div[text() = 'Referral Letter issued']"))
				.isDisplayed();
		Assert.assertTrue(issueReferralLetter);

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 10)
	public void extendCallDoctor() {

		driver.findElement(By.xpath("//button[@aria-controls = 'dropdown-dropup']")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Extend (£39.99)')]")).click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		boolean extend = driver
				.findElement(By
						.xpath("//div[contains(text(), 'The doctor has added a 10 minute appointment extension at the cost of £39.99')]"))
				.isDisplayed();
		Assert.assertTrue(extend);
	}

	@Test(priority = 11)
	public void endCallDoctor() {

		try {
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(By.className("end_call")).click();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(By.xpath("//button[@aria-controls = 'dropdown-dropup']")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Complete')]")).click();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(By.id("sentPrescription")).click();
		driver.findElement(By.xpath("//button[contains(text(),'Submit')]")).click();

		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(By.xpath("//a[contains(text(),'Video Consultations')]")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Complete')]")).click();
		driver.findElement(By.xpath("//h4[contains(text(), '" + date + "')]//parent::div")).click();
		// driverDoc.findElement(By.xpath("//a[contains(text(),'Join
		// Consultation')]")).click();
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		boolean completeConsultation = driver.findElement(By.xpath("//span[contains(text(),'" + fullName + "')]"))
				.isDisplayed();
		Assert.assertTrue(completeConsultation);

	}

}
