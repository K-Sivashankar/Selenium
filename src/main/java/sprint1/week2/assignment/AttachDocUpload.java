package sprint1.week2.assignment;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class AttachDocUpload {

    ChromeDriver driver = null;
    Faker faker = new Faker();

    @BeforeMethod
    public void Login() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(20));
        driver.get("https://login.salesforce.com");
        driver.findElement(By.id("username")).sendKeys("gokul.sekar@testleaf.com");
        driver.findElement(By.id("password")).sendKeys("Leaf$321");
        driver.findElement(By.id("Login")).click();
    }

    @Test
    public void test() throws InterruptedException {

        Thread.sleep(5000);
        WebElement appLauncher = driver.findElement(By.xpath("//button[@title='App Launcher']"));

        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.visibilityOf(appLauncher)).click();

        WebElement element = driver.findElement(By.xpath("//button[@aria-label='View All Applications']"));
        //view all click
        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait1.until(ExpectedConditions.elementToBeClickable(element)).click();
        Thread.sleep(3000);
        driver.executeScript("document.body.style.zoom='0.5'");

        Thread.sleep(3000);
        WebElement SalesLink = driver.findElement(By.xpath("//p[text()='Sales']"));
        SalesLink.click();

//        4. Click on Campaigns tab
        Thread.sleep(2000);

        WebElement Campaigns = driver.findElement(By.xpath("//a[@title='Campaigns']"));
        driver.executeScript("arguments[0].click();", Campaigns);
        Thread.sleep(2000);

        //Click on Bootcamp link
        driver.findElement(By.xpath("//table/tbody/tr[1]/th//a")).click();
        //Click on Upload File
        Thread.sleep(2000);

        WebElement UploadFiles = driver.findElement(By.xpath("//span[contains(text(),'Upload Files')]/preceding::input[1]"));

        UploadFiles.sendKeys(System.getProperty("user.dir")+"/src/main/resources/Test file.pdf");
        Thread.sleep(2000);

            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
            WebElement doneBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[span[contains(text(),'Done')]]")));
            doneBtn.click();
        
        Thread.sleep(3000);
        String text = driver.findElement(By.xpath("//a[contains(.,'Attachments')]/following::a[2]//span[@title]")).getText();
        Assert.assertTrue(text.equals("Test file"));

    }
}
