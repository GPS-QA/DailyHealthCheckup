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

public class AfterConsultationPatient {

	WebDriver driver;
	JavascriptExecutor js;

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	String emailPatient = "gpsjun07@mailinator.com";
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	String passwordPatient = "Thanu@93";

	@BeforeClass
	public void Setup() {

		System.setProperty("webdriver.chrome.driver", "D:\\ChromeDriver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.get("https://app.alt.thegpservice.com");
		js = (JavascriptExecutor) driver;

	}

	@AfterClass
	public void Teardown() {
		driver.quit();
	}

	@Test(priority = 1)
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

		String current_url = driver.getCurrentUrl();
		String expected_url = "https://app.alt.thegpservice.com/appointments";

		Assert.assertEquals(current_url, expected_url);
	}

	@Test(priority = 2)
	public void ViewFitnotePatient() {

		driver.findElement(By
				.xpath("//a[@routerlink= '/faqs']//parent::li//following-sibling::li//following-sibling::li//child::a[@routerlink= '/my-account']"))
				.click();

		WebElement view = driver.findElement(By.xpath(
				"//span[text() = 'Complete']//parent::td//following-sibling::td//following-sibling::td//child::a[text() = 'View']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", view);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By
				.xpath("//span[text() = 'Complete']//parent::td//following-sibling::td//following-sibling::td//child::a[text() = 'View']"))
				.click();
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

	@Test(priority = 3)
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

		WebElement reasonText = driver.findElement(By
				.xpath("//h4[text() = 'Reason for Referral:']"));
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

	@Test(priority = 4)
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

		WebElement viewInvoice = driver.findElement(By.xpath(
				"//span[text() = 'Complete']//parent::td//following-sibling::td//following-sibling::td//child::a[text() = 'View Invoice']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", viewInvoice);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By
				.xpath("//span[text() = 'Complete']//parent::td//following-sibling::td//following-sibling::td//child::a[text() = 'View Invoice']"))
				.click();
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

}
