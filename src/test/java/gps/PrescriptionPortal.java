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

public class PrescriptionPortal{
	
	WebDriver driver;
	JavascriptExecutor js;

	String firstName = Properties.firstName;
	String lastName = Properties.lastName_gps_web;
	String birthDate = Properties.birthDate;
	String emailDoctorNonGPS = Properties.emailDoctorNonGPS;
	String emailVet = Properties.emailVet;
	String emailPhamacist = Properties.emailPhamacist;
	String password = Properties.password;
	
	String fullName = firstName + " " + lastName;
	
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
	}

	@AfterClass
	public void Teardown() {
		driver.quit();
	}

	@Test(priority = 1)
	public void SignInDoctor() {

		driver.findElement(By.id("username")).sendKeys(emailDoctorNonGPS);
		driver.findElement(By.id("password")).sendKeys(password);
		System.out.println(firstName);
		
		driver.findElement(By.xpath("//button[contains(text(),'Submit')]")).click();

		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String current_url = driver.getCurrentUrl();
		String expected_url = "https://doctor.alt.thegpservice.com/prescriptions/list";

		Assert.assertEquals(current_url, expected_url);
	}

	@Test(priority = 2)
	public void CreatePrescriptionDoctor() {

		

		driver.findElement(By.xpath("//button[text() = 'Create Prescription (Patient)']")).click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(By.name("firstName")).sendKeys(firstName);
		driver.findElement(By.name("lastName")).sendKeys(lastName);
		driver.findElement(By.name("dateOfBirth")).sendKeys(birthDate);

		driver.findElement(By.xpath("//div[@class = 'form-group col-xs-2 pl-1 pr-1 search-btn']//child::button"))
				.click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(By.xpath("//button[contains(text(),'Confirm')]")).click();
		driver.findElement(By.xpath("//button[text() = 'Clear']//following-sibling::button[text() = 'Continue']"))
				.click();
		driver.findElement(By.name("postcode")).sendKeys("LE11AA");
		driver.findElement(By.xpath("//button[contains(text(),'Find Pharmacies')]")).click();
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		js.executeScript("window.scrollBy(0,100)");
		driver.findElement(By.xpath("//strong[contains(text(),'GPS QA Pharmacy')]")).click();
		driver.findElement(By.xpath("//button[text() = 'Back']//following-sibling::button[text() = 'Continue']"))
				.click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.name("medicationSearch")).sendKeys("Acarbose");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.name("medicationSearch")).sendKeys(Keys.RETURN);
		Select formulation = new Select(driver.findElement(By.name("formulation")));
		formulation.selectByValue("Tablets");
		Select strength = new Select(driver.findElement(By.id("strength")));
		strength.selectByValue("100mg");
		driver.findElement(By.id("dosage")).sendKeys("Dosage instructions");
		driver.findElement(By.id("quantity")).sendKeys("25");
		driver.findElement(By.id("unit")).sendKeys("unit");
		driver.findElement(
				By.xpath("//label[contains(text(),'Yes') and @class = 'radio-label']//following-sibling::input"))
				.click();
		driver.findElement(By.xpath("//button[contains(text(),'Add')]")).click();
		driver.findElement(By.id("additionalPatientNotes")).sendKeys("Additional patient notes");
		driver.findElement(By.xpath("//button[contains(text(),'Complete Prescription')]")).click();
		driver.findElement(By
				.xpath("//label[text() = 'Confirm your prescription by entering your pin']//following-sibling::input"))
				.sendKeys("0000");
		;
		driver.findElement(By.xpath("//button[text() = 'Change']//following-sibling::button[text() = 'Confirm']"))
				.click();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(By.xpath("//td[contains(text(), '" + fullName
				+ "' )]//following-sibling::td[text()='Sent to Pharmacy']//following-sibling::td//child::a")).click();
		String medication = driver
				.findElement(By
						.xpath("//th[text() = 'Medication']//parent::thead//following-sibling::tbody//child::tr//child::td"))
				.getText();
		Assert.assertEquals(medication, "Acarbose");

		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 3)
	public void AddFormulary() {

		driver.findElement(By.xpath("//a[text() = 'Formulary']")).click();
		driver.findElement(
				By.xpath("//input[@placeholder='ID, name, formulation or strength']//preceding-sibling::button"))
				.click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.name("name")).sendKeys("Medication");
		driver.findElement(By.name("formulation")).sendKeys("Formulation");
		driver.findElement(By.name("strength")).sendKeys("10mg");
		driver.findElement(By.xpath("//button[text() = 'OK']")).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		boolean message = driver.findElement(By.xpath("//div[@aria-label = 'Formulary Updated Successfully']"))
				.isDisplayed();
		Assert.assertTrue(message);

		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 4)
	public void SearchFormulary() {

		driver.findElement(By.xpath("//input[@placeholder='ID, name, formulation or strength']"))
				.sendKeys("Medication");
		driver.findElement(By.xpath("//span[@class='glyphicon glyphicon-search']")).click();

		String results = driver
				.findElement(By
						.xpath("//th[text()='ID']//parent::tr//parent::thead//following-sibling::tbody//child::tr//child::td//following-sibling::td"))
				.getText();
		Assert.assertEquals(results, "Medication");
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 5)
	public void EditFormulary() {

		driver.findElement(By
				.xpath("//th[text()='ID']//parent::tr//parent::thead//following-sibling::tbody//child::tr//child::td[@class='col-md-1 ng-star-inserted']//child::button[@class='btn btn-sm btn-primary']"))
				.click();
		driver.findElement(By.name("name")).clear();
		driver.findElement(By.name("name")).sendKeys("MedicationX");
		driver.findElement(By.name("formulation")).clear();
		driver.findElement(By.name("formulation")).sendKeys("FormulationX");
		driver.findElement(By.name("strength")).clear();
		driver.findElement(By.name("strength")).sendKeys("10mgX");
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(By.xpath("//button[text() = 'OK']")).click();
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String results = driver
				.findElement(By
						.xpath("//th[text()='ID']//parent::tr//parent::thead//following-sibling::tbody//child::tr//child::td//following-sibling::td"))
				.getText();
		Assert.assertEquals(results, "MedicationX");
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 6)
	public void DeleteFormulary() {

		driver.findElement(By
				.xpath("//th[text()='ID']//parent::tr//parent::thead//following-sibling::tbody//child::tr//child::td[@class='col-md-1 ng-star-inserted']//child::button[@class='btn btn-sm btn-danger']"))
				.click();

		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.xpath("//button[text() = 'Yes']")).click();

		boolean message = driver.findElement(By.xpath("//div[@aria-label = 'Formulary Updated Successfully']"))
				.isDisplayed();
		Assert.assertTrue(message);

		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 7)
	public void LogoutDoctor() {

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

	@Test(priority = 8)
	public void SignInVet() {

		driver.findElement(By.id("username")).sendKeys(emailVet);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.xpath("//button[contains(text(),'Submit')]")).click();

		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String current_url = driver.getCurrentUrl();
		String expected_url = "https://doctor.alt.thegpservice.com/prescriptions/list";

		Assert.assertEquals(current_url, expected_url);
	}

	@Test(priority = 9)
	public void CreatePrescriptionVet() {

		

		driver.findElement(By.xpath("//button[text() = 'Create Prescription (Pet)']")).click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(By.name("firstName")).sendKeys(firstName);
		driver.findElement(By.name("lastName")).sendKeys(lastName);
		driver.findElement(By.name("dateOfBirth")).sendKeys(birthDate);

		driver.findElement(By.xpath("//div[@class = 'form-group col-xs-2 pl-1 pr-1 search-btn']//child::button"))
				.click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(By.xpath("//button[contains(text(),'Confirm')]")).click();
		driver.findElement(By
				.xpath("//div[@aria-expanded='true' and @class='panel-collapse collapse in show']//child::div//child::div//child::button[text() = 'Continue']"))
				.click();
		driver.findElement(By.name("name")).sendKeys("Timmy");
		driver.findElement(By.name("species")).sendKeys("Dog");
		driver.findElement(By.name("pet")).click();
		driver.findElement(By
				.xpath("//button[text() = 'Back']//following-sibling::button[text() = 'Clear']//following-sibling::button[text() = 'Continue']"))
				.click();
		driver.findElement(By.name("postcode")).sendKeys("LE11AA");
		driver.findElement(By.xpath("//button[contains(text(),'Find Pharmacies')]")).click();
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		js.executeScript("window.scrollBy(0,100)");
		driver.findElement(By.xpath("//strong[contains(text(),'GPS QA Pharmacy')]")).click();
		driver.findElement(By
				.xpath("//label[contains(text(),'Enter your postcode')]//parent::div//parent::app-pharmacy-picker//parent::div//child::button[text()='Continue']"))
				.click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.name("medicationSearch")).sendKeys("Acarbose");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.name("medicationSearch")).sendKeys(Keys.RETURN);
		Select formulation = new Select(driver.findElement(By.name("formulation")));
		formulation.selectByValue("Tablets");
		Select strength = new Select(driver.findElement(By.id("strength")));
		strength.selectByValue("100mg");
		driver.findElement(By.id("dosage")).sendKeys("Dosage instructions");
		driver.findElement(By.id("quantity")).sendKeys("25");
		driver.findElement(By.id("unit")).sendKeys("unit");
		driver.findElement(
				By.xpath("//label[contains(text(),'Yes') and @class = 'radio-label']//following-sibling::input"))
				.click();
		driver.findElement(By.xpath("//button[contains(text(),'Add')]")).click();
		driver.findElement(By.id("additionalPatientNotes")).sendKeys("Additional patient notes");
		driver.findElement(By.xpath("//button[contains(text(),'Complete Prescription')]")).click();
		driver.findElement(By
				.xpath("//label[text() = 'Confirm your prescription by entering your pin']//following-sibling::input"))
				.sendKeys("0000");
		;
		driver.findElement(By.xpath("//button[text() = 'Change']//following-sibling::button[text() = 'Confirm']"))
				.click();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(By.xpath("//td[contains(text(), '" + fullName
				+ "' )]//following-sibling::td[text()='Sent to Pharmacy']//following-sibling::td//child::a")).click();
		String medication = driver
				.findElement(By
						.xpath("//th[text() = 'Medication']//parent::thead//following-sibling::tbody//child::tr//child::td"))
				.getText();
		Assert.assertEquals(medication, "Acarbose");

		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 10)
	public void LogoutVet() {

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

	@Test(priority = 11)
	public void SignInPhamacist() {

		driver.findElement(By.id("username")).sendKeys(emailPhamacist);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.xpath("//button[contains(text(),'Submit')]")).click();

		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String current_url = driver.getCurrentUrl();
		String expected_url = "https://doctor.alt.thegpservice.com/prescriptions/list";

		Assert.assertEquals(current_url, expected_url);
	}

	@Test(priority = 12)
	public void CreatePrescriptionPhamacist() {


		driver.findElement(By.xpath("//button[text() = 'Create Prescription (Patient)']")).click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(By.name("firstName")).sendKeys(firstName);
		driver.findElement(By.name("lastName")).sendKeys(lastName);
		driver.findElement(By.name("dateOfBirth")).sendKeys(birthDate);

		driver.findElement(By.xpath("//div[@class = 'form-group col-xs-2 pl-1 pr-1 search-btn']//child::button"))
				.click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(By.xpath("//button[contains(text(),'Confirm')]")).click();
		driver.findElement(By.xpath("//button[text() = 'Clear']//following-sibling::button[text() = 'Continue']"))
				.click();
		driver.findElement(By.name("postcode")).sendKeys("LE11AA");
		driver.findElement(By.xpath("//button[contains(text(),'Find Pharmacies')]")).click();
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		js.executeScript("window.scrollBy(0,100)");
		driver.findElement(By.xpath("//strong[contains(text(),'GPS QA Pharmacy')]")).click();
		driver.findElement(By.xpath("//button[text() = 'Back']//following-sibling::button[text() = 'Continue']"))
				.click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.name("medicationSearch")).sendKeys("Acetazolamide");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.name("medicationSearch")).sendKeys(Keys.RETURN);
		Select formulation = new Select(driver.findElement(By.name("formulation")));
		formulation.selectByValue("Tablets");
		Select strength = new Select(driver.findElement(By.id("strength")));
		strength.selectByValue("250mg");
		driver.findElement(By.id("dosage")).sendKeys("Dosage instructions");
		driver.findElement(By.id("quantity")).sendKeys("25");
		driver.findElement(By.id("unit")).sendKeys("unit");
		driver.findElement(
				By.xpath("//label[contains(text(),'Yes') and @class = 'radio-label']//following-sibling::input"))
				.click();
		driver.findElement(By.xpath("//button[contains(text(),'Add')]")).click();
		driver.findElement(By.id("additionalPatientNotes")).sendKeys("Additional patient notes");
		driver.findElement(By.xpath("//button[contains(text(),'Complete Prescription')]")).click();
		driver.findElement(By
				.xpath("//label[text() = 'Confirm your prescription by entering your pin']//following-sibling::input"))
				.sendKeys("0000");
		;
		driver.findElement(By.xpath("//button[text() = 'Change']//following-sibling::button[text() = 'Confirm']"))
				.click();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(By.xpath("//td[contains(text(), '" + fullName
				+ "' )]//following-sibling::td[text()='Sent to Pharmacy']//following-sibling::td//child::a")).click();
		String medication = driver
				.findElement(By
						.xpath("//th[text() = 'Medication']//parent::thead//following-sibling::tbody//child::tr//child::td"))
				.getText();
		Assert.assertEquals(medication, "Acetazolamide");

		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 13)
	public void LogoutPhamacist() {

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
