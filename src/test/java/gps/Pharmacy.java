package gps;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import prop.Properties;

public class Pharmacy {

	WebDriver driver;
	String id;
	String testDescription = "";

	@BeforeClass
	public void Setup() {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.get("http://pharmacy.alt.thegpservice.com");
	}

	@AfterClass
	public void Teardown() {
		driver.quit();
	}

	@AfterMethod
	public void getResult(ITestResult result) throws Exception {
		
		Properties.test = Properties.extent.createTest(result.getMethod().getDescription()).assignCategory("MyTest").assignAuthor("Thanuji Wijerathna");
		if (result.getStatus() == ITestResult.FAILURE) {
			Properties.test.fail(testDescription);
		} else if (result.getStatus() == ITestResult.SKIP) {
			Properties.test.skip(testDescription);
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			Properties.test.pass(testDescription);
		}
		Properties.extent.flush();
	}
	
	@Test(priority = 1, description = "Sign in to pharmacy app")
	public void SignIn() {
		testDescription = "Verify whether a pharmacist can sign in to the pharmacy app";
		driver.findElement(By.id("username")).sendKeys("thanuji.wijerathna@thegpservice.co.uk");
		driver.findElement(By.id("password")).sendKeys("Thanu@93");
		driver.findElement(By.xpath("//button[text() = 'Submit']")).click();

		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String current_url = driver.getCurrentUrl();
		String expected_url = "https://pharmacy.alt.thegpservice.com/orders";

		Assert.assertEquals(current_url, expected_url);
	}

	@Test(priority = 2, description = "Dispense prescription")
	public void Dispense() {
		testDescription = "Verify whether a pharmacist can dispense a prescription from the prescription table view";
		id = driver.findElement(By.xpath("//tbody/tr[1]/td[1]")).getText();
		driver.findElement(By.xpath("//td[text() = '" + id
				+ "']//following-sibling::td[@class = 'alert alert-info']//following-sibling::td//child::div[@class = 'preload']//following-sibling::button[text() = 'Dispense']"))
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
				.xpath("//div[@aria-hidden = 'false']//child::form//following-sibling::div//child::input[@name = 'reminderID']"))
				.click();
		driver.findElement(By
				.xpath("//div[@aria-hidden = 'false']//child::form//following-sibling::div//child::button[text() = 'Dispense']"))
				.click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String pending = driver
				.findElement(
						By.xpath("//td[text() = '" + id + "']//following-sibling::td[@class = 'alert alert-warning']"))
				.getText();
		driver.findElement(By.linkText("History")).click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String history = driver
				.findElement(
						By.xpath("//td[text() = '" + id + "']//following-sibling::td[@class = 'alert alert-warning']"))
				.getText();
		Assert.assertEquals(pending, history);

	}

	@Test(priority = 3, description = "Complete a dispensed prescription")
	public void Complete() {
		testDescription = "Verify whether a pharmacist can complete a prescription from the prescription table view";
		driver.findElement(By.linkText("Pending")).click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(By.xpath("//td[text() = '" + id
				+ "']//following-sibling::td[@class = 'alert alert-warning']//following-sibling::td//child::div[@class = 'preload']//following-sibling::button"))
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

		driver.findElement(By.linkText("Completed")).click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String completed = driver
				.findElement(
						By.xpath("//td[text() = '" + id + "']//following-sibling::td[@class = 'alert alert-success']"))
				.getText();

		driver.findElement(By.linkText("History")).click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String history = driver
				.findElement(
						By.xpath("//td[text() = '" + id + "']//following-sibling::td[@class = 'alert alert-success']"))
				.getText();
		Assert.assertEquals(completed, history);

	}

	@Test(priority = 4, description = "Reject prescription")
	public void Reject() {
		testDescription = "Verify whether a pharmacist can reject a prescription from the prescription table view";
		driver.findElement(By.linkText("Pending")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		id = driver.findElement(By.xpath("//tbody/tr[1]/td[1]")).getText();

		driver.findElement(By.xpath("//td[text() = '" + id
				+ "']//following-sibling::td[@class = 'alert alert-info']//following-sibling::td//child::div[@class = 'preload']//following-sibling::button[text() = 'Reject']"))
				.click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(
				By.xpath("//div[@aria-hidden = 'false']//child::form//child::input[@value= 'MEDICALLY_NOT_SAFE']"))
				.click();
		driver.findElement(By
				.xpath("//div[@aria-hidden = 'false']//child::form//following-sibling::div//child::input[@name = 'pin']"))
				.sendKeys("0000");
		driver.findElement(By
				.xpath("//div[@aria-hidden = 'false']//child::form//following-sibling::div//child::input[@name = 'name']"))
				.sendKeys("Pharmacist");
		driver.findElement(By.xpath("//div[@aria-hidden = 'false']//child::button[text() = 'Reject']")).click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(By.linkText("History")).click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String history = driver
				.findElement(
						By.xpath("//td[text() = '" + id + "']//following-sibling::td[@class = 'alert alert-danger']"))
				.getText();
		Assert.assertEquals(history, "Rejected by Pharmacy");

	}

	@Test(priority = 5, description = "Dispense prescription - path 2")
	public void ViewDispense() {
		testDescription = "Verify whether a pharmacist can dispense a prescription by navigating to the particular prescription";
		driver.findElement(By.linkText("Pending")).click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		id = driver.findElement(By.xpath("//tbody/tr[1]/td[1]")).getText();
		driver.findElement(By.xpath("//td[text() = '" + id
				+ "']//following-sibling::td[@class = 'alert alert-info']//following-sibling::td//child::app-order-actions//child::button[text() = 'View']"))
				.click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(
				By.xpath("//button[text() = 'Print Prescription']//following-sibling::button[text() = 'Dispense']"))
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
				.xpath("//div[@aria-hidden = 'false']//child::form//following-sibling::div//child::input[@name = 'reminderID']"))
				.click();
		driver.findElement(By
				.xpath("//div[@aria-hidden = 'false']//child::form//following-sibling::div//child::button[text() = 'Dispense']"))
				.click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String status = driver.findElement(By.xpath("//div[@class = 'alert alert-warning']//child::strong")).getText();

		driver.findElement(By.linkText("Prescriptions")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.linkText("History")).click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String history = driver
				.findElement(
						By.xpath("//td[text() = '" + id + "']//following-sibling::td[@class = 'alert alert-warning']"))
				.getText();
		Assert.assertEquals(status, history);

	}

	@Test(priority = 6, description = "Complete prescription - path 2")
	public void ViewComplete() {
		testDescription = "Verify whether a pharmacist can complete a prescription by navigating to the particular prescription";
		driver.findElement(By.linkText("Pending")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(By.xpath("//td[text() = '" + id
				+ "']//following-sibling::td[@class = 'alert alert-warning']//following-sibling::td//child::app-order-actions//child::button[text() = 'View']"))
				.click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

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

		driver.findElement(By.linkText("Prescriptions")).click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(By.linkText("Completed")).click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String completed = driver
				.findElement(
						By.xpath("//td[text() = '" + id + "']//following-sibling::td[@class = 'alert alert-success']"))
				.getText();

		driver.findElement(By.linkText("History")).click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String history = driver
				.findElement(
						By.xpath("//td[text() = '" + id + "']//following-sibling::td[@class = 'alert alert-success']"))
				.getText();

		if ((completed.equals(history)) && status.equals("Complete")) {
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}

	}

	@Test(priority = 7, description = "Reject prescription")
	public void ViewReject() {
		testDescription = "Verify whether a pharmacist can reject a prescription by navigating to the particular prescription";
		driver.findElement(By.linkText("Pending")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		id = driver.findElement(By.xpath("//tbody/tr[1]/td[1]")).getText();

		driver.findElement(By.xpath("//td[text() = '" + id
				+ "']//following-sibling::td[@class = 'alert alert-info']//following-sibling::td//child::app-order-actions//child::button[text() = 'View']"))
				.click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(
				By.xpath("//button[text() = 'Print Prescription']//following-sibling::button[text() = 'Reject']"))
				.click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(
				By.xpath("//div[@aria-hidden = 'false']//child::form//child::input[@value= 'MEDICALLY_NOT_SAFE']"))
				.click();
		driver.findElement(By
				.xpath("//div[@aria-hidden = 'false']//child::form//following-sibling::div//child::input[@name = 'pin']"))
				.sendKeys("0000");
		driver.findElement(By
				.xpath("//div[@aria-hidden = 'false']//child::form//following-sibling::div//child::input[@name = 'name']"))
				.sendKeys("Pharmacist");
		driver.findElement(By.xpath("//div[@aria-hidden = 'false']//child::button[text() = 'Reject']")).click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String status = driver.findElement(By.xpath("//div[@class = 'alert alert-danger']//child::strong")).getText();

		driver.findElement(By.linkText("Prescriptions")).click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.findElement(By.linkText("History")).click();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String history = driver
				.findElement(
						By.xpath("//td[text() = '" + id + "']//following-sibling::td[@class = 'alert alert-danger']"))
				.getText();
		Assert.assertEquals(history, status);

	}

	@Test(priority = 8, description = "Logout form the pharmacy app")
	public void Logout() {
		testDescription = "Verify whether a pharmacist can logout from the pharmacy app";
		driver.findElement(By.xpath("//a[@aria-haspopup='true']")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();

		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String current_url = driver.getCurrentUrl();
		String expected_url = "https://pharmacy.alt.thegpservice.com/login";

		Assert.assertEquals(current_url, expected_url);

	}

}
