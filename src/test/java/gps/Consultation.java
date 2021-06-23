package gps;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import prop.Properties;

public class Consultation {

	WebDriver driver;
	JavascriptExecutor js;
	ArrayList<String> tabs;
	WebDriverWait wait;

	String emailPatient = Properties.emailPatient_gps_web;
	String passwordPatient = Properties.password;
	String firstName = Properties.firstName;
	String lastName = Properties.lastName_gps_web;
	String phone = Properties.phone_gps_web;
	//String time = Properties.time;
	String date = Properties.date;
	String FitnoteFromDate = Properties.FitnoteFromDate();
	String FitnoteExpirationDate = Properties.FitnoteExpirationDate;
	String emailDoctor = Properties.emailDoctor;
	String passwordDoctor = Properties.passwordDoctor;
	String storeId = Properties.storeId;
	String pin = Properties.pin;
	
	String fullName = firstName + " " + lastName;
	int indexAt = emailPatient.indexOf("@");
	String emailID = emailPatient.substring(0, indexAt);
	WebElement timeSlot;
	String code = "";
	String orderNo = "";
	String time = "";

	@BeforeClass
	public void Setup() {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
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

	@Test(priority = 1)
	public void SignUpPatient() {

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
	public void LogoutPatient() {

		driver.findElement(By.id("menuLogout")).click();

		String current_url = driver.getCurrentUrl();
		String expected_url = "https://app.alt.thegpservice.com/register/login";

		Assert.assertEquals(current_url, expected_url);
	}

	@Test(priority = 3)
	public void SignInPatient() {

		// driver.findElement(By.xpath("//a[@routerlink=
		// '/faqs']//parent::li//following-sibling::li//following-sibling::li//child::a[@routerlink=
		// '/register/login']")).click();
		driver.findElement(By.id("email")).sendKeys(emailPatient);
		driver.findElement(By.id("password")).sendKeys(passwordPatient);
		driver.findElement(By.xpath("//button[contains(text(),'SIGN IN')]")).click();

		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String current_url = driver.getCurrentUrl();
		String expected_url = "https://app.alt.thegpservice.com/appointments";

		Assert.assertEquals(current_url, expected_url);
	}

	@Test(priority = 4)
	public void BookConsultationPatient() {

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
				.xpath("//a[@routerlink= '/faqs']//parent::li//following-sibling::li//following-sibling::li//child::a[text() = 'My Account']"))
				.click();

		boolean status = driver.findElement(By.xpath("//span[text() = 'Booked']")).isDisplayed();
		Assert.assertTrue(status);

		/*
		 * String status = driver.findElement(By.
		 * xpath("//td[text() = 'Consultation (10/05/2021 1:30 pm)']//preceding-sibling::td//following-sibling::td//child::span"
		 * )).getText(); Assert.assertEquals(status, "Booked");
		 */

		/*
		 * String current_url = driver.getCurrentUrl(); String expected_url =
		 * "https://app.alt.thegpservice.com/order/thankyou";
		 * 
		 * Assert.assertEquals(current_url, expected_url);
		 */

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	@Test(priority = 5)
	public void JoinConsultationPatient() {

		// driver.findElement(By.xpath("//a[@routerlink=
		// '/faqs']//parent::li//following-sibling::li//following-sibling::li//child::a[@routerlink=
		// '/my-account']")).click();
		// driver.findElement(By.xpath("//td[text() = 'Consultation (07/05/2021
		// 5:00
		// pm)']//following-sibling::td//child::app-join-video-call//child::a")).click();

		driver.findElement(By.xpath("//a[contains(text(),'Join')]")).click();

		wait = new WebDriverWait(driver, 60000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Join Call')]")));

		driver.findElement(By.xpath("//button[contains(text(),'Join Call')]")).click();

		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		boolean endcall = driver.findElement(By.xpath("//button[contains(text(),'End Call')]")).isDisplayed();
		Assert.assertTrue(endcall);
	}

	@Test(priority = 6)
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

		String current_url = driver.getCurrentUrl();
		String expected_url = "https://doctor.alt.thegpservice.com/orders";

		Assert.assertEquals(current_url, expected_url);
	}

	@Test(priority = 7)
	public void JoinConsultationDoctor() {

		driver.findElement(By.xpath("//a[contains(text(),'Video Consultations')]")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Booked')]")).click();
		driver.findElement(By.xpath("//h4[contains(text(), '" + date + "')]//parent::div")).click();
		// driverDoc.findElement(By.xpath("//a[contains(text(),'Join
		// Consultation')]")).click();

		js.executeScript("window.scrollBy(0,500)");
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

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

		driver.findElement(By.id("recommendedActions")).sendKeys("Recommended actions");
		driver.findElement(By.id("additionalNotes")).sendKeys("Notes for patient record");
		driver.findElement(By.id("patientReferredDetails")).sendKeys("Referral details");

		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Test(priority = 8)
	public void sendMessageDoctor() {

		driver.findElement(By.xpath("//input[@placeholder='Enter a message']")).sendKeys("Hello patient");
		driver.findElement(By.xpath("//button[contains(text(), 'Send')]")).click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.switchTo().window(tabs.get(0));

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		boolean message = driver.findElement(By.xpath("//div[contains(text(), 'Hello patient')]")).isDisplayed();
		Assert.assertTrue(message);
	}

	@Test(priority = 9)
	public void sendMessagePatient() {

		driver.findElement(By.xpath("//input[@placeholder='Enter a message...']")).sendKeys("Hello doctor");
		driver.findElement(By.xpath("//button[contains(text(), 'Send')]")).click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		js.executeScript("window.scrollBy(0,-200)");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.switchTo().window(tabs.get(1));

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		boolean message = driver.findElement(By.xpath("//div[contains(text(), 'Hello doctor')]")).isDisplayed();
		Assert.assertTrue(message);
	}

	@Test(priority = 10)
	public void sendImagePatient() {

		driver.switchTo().window(tabs.get(0));

		driver.findElement(By.id("select-files")).sendKeys("D:/images/img.jpg");

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.switchTo().window(tabs.get(1));

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		boolean message = driver.findElement(By.xpath("//p[contains(text(),'Patient sent an image')]")).isDisplayed();
		Assert.assertTrue(message);

		driver.findElement(By.xpath("//p[contains(text(),'Patient sent an image')]")).click();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(2));
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.close();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 11)
	public void sendDocumentPatient() {

		driver.switchTo().window(tabs.get(0));

		driver.findElement(By.id("select-files")).sendKeys("D:/pdfs/dummy.pdf");

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.switchTo().window(tabs.get(1));

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		boolean message = driver.findElement(By.xpath("//p[contains(text(),'Patient sent a document')]")).isDisplayed();
		Assert.assertTrue(message);

		driver.findElement(By.xpath("//p[contains(text(),'Patient sent a document')]")).click();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(2));
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.close();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 12)
	public void proposePrescriptionDoctor() {

		tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));

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

	@Test(priority = 13)
	public void acceptPrescriptionPatient() {

		driver.switchTo().window((String) tabs.get(0));

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// js.executeScript("window.scrollBy(0,100)");
		driver.findElement(By.xpath("//button[contains(text(),'Accept')]")).click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		boolean acceptPrescription = driver.findElement(By.xpath("//div[text() = 'Prescription accepted']"))
				.isDisplayed();
		Assert.assertTrue(acceptPrescription);
	}

	@Test(priority = 14)
	public void fillPrescriptionDoctor() {

		driver.switchTo().window((String) tabs.get(1));

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

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

	@Test(priority = 15)
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

	@Test(priority = 16)
	public void acceptFitNotePatient() {

		driver.switchTo().window((String) tabs.get(0));

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// js.executeScript("window.scrollBy(0,100)");
		driver.findElement(By.xpath("//button[contains(text(),'Accept')]")).click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		boolean acceptFitNote = driver.findElement(By.xpath("//div[text() = 'Fit Note accepted']")).isDisplayed();
		Assert.assertTrue(acceptFitNote);
	}

	@Test(priority = 17)
	public void fillFitNoteDoctor() {

		driver.switchTo().window((String) tabs.get(1));

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

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

	@Test(priority = 18)
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

	@Test(priority = 19)
	public void acceptReferralLetterPatient() {

		driver.switchTo().window((String) tabs.get(0));

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// js.executeScript("window.scrollBy(0,100)");
		driver.findElement(By.xpath("//button[contains(text(),'Accept')]")).click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		boolean acceptReferralLetter = driver.findElement(By.xpath("//div[text() = 'Referral Letter accepted']"))
				.isDisplayed();
		Assert.assertTrue(acceptReferralLetter);
	}

	@Test(priority = 20)
	public void fillReferralLetterDoctor() {

		driver.switchTo().window((String) tabs.get(1));

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

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

	@Test(priority = 21)
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

	@Test(priority = 22)
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

		WebElement patient = driver.findElement(By.xpath("//span[contains(text(),'" + fullName + "')]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", patient);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		boolean completeConsultation = driver.findElement(By.xpath("//span[contains(text(),'" + fullName + "')]"))
				.isDisplayed();
		Assert.assertTrue(completeConsultation);

	}

	@Test(priority = 23)
	public void viewFitnoteDoctor() {

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
		driver.switchTo().window(tabs.get(2));

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

	@Test(priority = 24)
	public void viewReferralLetterDoctor() {

		tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
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
		driver.switchTo().window(tabs.get(2));

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

	@Test(priority = 25)
	public void viewPrescriptionDoctor() {

		tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));

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
		driver.switchTo().window(tabs.get(2));

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

	@Test(priority = 26)
	public void editNotesDoctor() {

		tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));

		WebElement history = driver.findElement(By.linkText("History"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", history);
		driver.findElement(By.linkText("History")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.xpath("//td[contains(text(), '" + fullName
				+ "')]//following-sibling::td[@class = 'alert alert-info']//following-sibling::td//child::a[text() = 'Edit Notes']"))
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

		driver.findElement(By.xpath("//td[contains(text(), '" + fullName
				+ "')]//following-sibling::td[@class = 'alert alert-info']//following-sibling::td//child::a[text() = 'View Patient']"))
				.click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String alerts = driver
				.findElement(By.xpath("//strong[contains(text(), 'Key message alerts: ')]//following-sibling::span"))
				.getText();
		String presentingComplaint[] = driver
				.findElement(By.xpath("//strong[text() = 'Presenting complaint']//parent::p")).getText().split("\\n");
		String recommendedActions[] = driver
				.findElement(By.xpath("//strong[text() = 'Recommended actions']//parent::p")).getText().split("\\n");
		String additionalNotes[] = driver
				.findElement(By.xpath("//strong[text() = 'Notes for patient record']//parent::p")).getText()
				.split("\\n");
		String patientReferredDetails[] = driver
				.findElement(By.xpath("//strong[text() = 'Referral details']//parent::p")).getText().split("\\n");

		int count = 0;

		if (alerts.equals("Key message alerts edited")) {
			count++;
		}
		if (presentingComplaint[1].equals("Presenting complaint edited")) {
			count++;
		}
		if (recommendedActions[1].equals("Recommended actions edited")) {
			count++;
		}
		if (additionalNotes[1].equals("Notes for patient record edited")) {
			count++;
		}
		if (patientReferredDetails[1].equals("Referral details edited")) {
			count++;
		}

		Assert.assertEquals(count, 5);

	}

	@Test(priority = 27)
	public void endCallPatient() {

		tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window((String) tabs.get(0));

		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(By.xpath("//button[contains(text(),'End Call')]")).click();

		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		js.executeScript("window.scrollBy(0,-100)");
		driver.findElement(By
				.xpath("//a[@routerlink= '/faqs']//parent::li//following-sibling::li//following-sibling::li//child::a[@routerlink= '/my-account']"))
				.click();

		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		boolean status = driver.findElement(By.xpath("//span[text() = 'Sent to Pharmacy']")).isDisplayed();
		Assert.assertTrue(status);
	}

	@Test(priority = 28)
	public void ViewFitnotePatient() {

		/*
		 * WebElement view = driver.findElement(By.xpath(
		 * "//span[text() = 'Complete']//parent::td//following-sibling::td//following-sibling::td//child::a[text() = 'View']"
		 * )); ((JavascriptExecutor)
		 * driver).executeScript("arguments[0].scrollIntoView(true);", view);
		 * try { Thread.sleep(5000); } catch (InterruptedException e) {
		 * e.printStackTrace(); } driver.findElement(By
		 * .xpath("//span[text() = 'Complete']//parent::td//following-sibling::td//following-sibling::td//child::a[text() = 'View']"
		 * )) .click();
		 */
		driver.findElement(By.xpath("//a[text() = 'View']")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement fit = driver.findElement(By.linkText("View fit note"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", fit);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.linkText("View fit note")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement pain = driver.findElement(By.xpath("//p[contains(text(),'I assessed your case on')]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", pain);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		boolean condition = driver.findElement(By.xpath("//strong[text() = 'Abdominal pain']")).isDisplayed();
		Assert.assertTrue(condition);

		WebElement print = driver.findElement(By.xpath("//button[text() = 'Print']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", print);
		driver.findElement(By.xpath("//button[text() = 'Print']")).click();
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.navigate().back();
	}

	@Test(priority = 29)
	public void ViewReferralLetterPatient() {

		WebElement ref = driver.findElement(By.linkText("View Referral Letter"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ref);
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
		driver.navigate().back();

	}

	@Test(priority = 30)
	public void ViewInvoicePatient() {

		WebElement invoice = driver.findElement(By.linkText("View Invoice"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", invoice);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.linkText("View Invoice")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String totalInside = driver.findElement(By.xpath("//td[@class = 'basket_total']//child::span")).getText();
		driver.navigate().back();
		driver.findElement(By.linkText("Back to my orders")).click();

		/*
		 * WebElement viewInvoice = driver.findElement(By.xpath(
		 * "//span[text() = 'Complete']//parent::td//following-sibling::td//following-sibling::td//child::a[text() = 'View Invoice']"
		 * )); ((JavascriptExecutor)
		 * driver).executeScript("arguments[0].scrollIntoView(true);",
		 * viewInvoice); try { Thread.sleep(5000); } catch (InterruptedException
		 * e) { e.printStackTrace(); } driver.findElement(By
		 * .xpath("//span[text() = 'Complete']//parent::td//following-sibling::td//following-sibling::td//child::a[text() = 'View Invoice']"
		 * )) .click();
		 */
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.xpath("//a[text() = 'View Invoice']")).click();
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

		String totalOutside = driver.findElement(By.xpath("//td[@class = 'basket_total']//child::span")).getText();
		Assert.assertEquals(totalInside, totalOutside);

		driver.navigate().back();
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

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

		// WebElement frame1=driver.findElement(By.id("html_msg_body"));
		// driver.switchTo().frame(frame1);

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

	public void Verify() {

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

	}

	@Test(priority = 31)
	public void PrescrptionChecker() {

		driver.switchTo().window((String) tabs.get(1));
		js.executeScript("window.open('https://www.mailinator.com', '_blank');");
		tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window((String) tabs.get(2));

		GetAuthorisationCode();
		Verify();

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

		String statusPharmacy = driver
				.findElement(By
						.xpath("//p[contains(text(), 'The electronic Prescription has been mailed')]//preceding-sibling::strong"))
				.getText();

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

		driver.close();

		tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window((String) tabs.get(1));

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.navigate().refresh();
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.linkText("History")).click();
		try {
			Thread.sleep(12000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		String statusDoctor = driver.findElement(By.xpath(
				"//td[contains(text(), '" + fullName + "')]//following-sibling::td[@class = 'alert alert-success']"))
				.getText();

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

		String statusPatient = driver.findElement(By.xpath("//span[@class = 'status status--success']")).getText();
		
		if( (statusPharmacy.equals(statusDoctor)) && (statusPatient.equals("Complete"))){
			Assert.assertTrue(true);
		}
		else{
			Assert.assertTrue(false);
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

	@Test(priority = 32)
	public void CancelPatientLessThan3hrs() {
		
		driver.findElement(By.partialLinkText("SEE A DOCTOR")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

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

	@Test(priority = 33)
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

	@Test(priority = 34)
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
	
	@Test(priority = 35)
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
		driver.switchTo().window((String) tabs.get(1));
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
	
	@Test(priority = 36)
	public void LogoutDoctor() {

		driver.switchTo().window((String) tabs.get(1));
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		driver.findElement(By.xpath("//a[@aria-haspopup='true']")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();

		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String current_url = driver.getCurrentUrl();
		String expected_url = "https://doctor.alt.thegpservice.com/login";

		Assert.assertEquals(current_url, expected_url);

	}
	
}
