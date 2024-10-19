package sprint1.week2.assignment;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class EditOrder {


//    Test Steps:
//            1. Login to https://login.salesforce.com/?locale=in

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


//            2. Click on menu button from the Left corner


    @Test
    public void test() throws InterruptedException {


//3. Click view All and click Service Console from App Launcher
        WebElement appLauncher = driver.findElement(By.xpath("//button[@title='App Launcher']"));

        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.visibilityOf(appLauncher)).click();

        WebElement element = driver.findElement(By.xpath("//button[@aria-label='View All Applications']"));
        //view all click
        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait1.until(ExpectedConditions.elementToBeClickable(element)).click();


//4. Click on the drop down and select Orders
        Thread.sleep(2000);
        driver.findElement(By.xpath("//p[text()='Service Console']")).click();
        Thread.sleep(2000);
        WebElement Dropdown = driver.findElement(By.xpath("(//span[text()='Service Console'])[last()]//following::button[1]"));
        new WebDriverWait(driver,Duration.ofSeconds(15)).until(ExpectedConditions.visibilityOf(Dropdown)).click();

//        Select Orders from dropdown
        driver.findElement(By.xpath("//li[descendant::a[@data-label='Orders']]//a[1]")).click();


//5.Click drop down near Recently Viewed and Select All Orders
        Thread.sleep(3000);
        WebElement RecentlyViewed_dd = driver.findElement(By.xpath("//div[contains(@aria-label,'Recently Viewed')]//following::button[@role='button' and contains(@title,'Orders')]"));
        new WebDriverWait(driver,Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(RecentlyViewed_dd));
        RecentlyViewed_dd.click();
//select All Orders
        driver.findElement(By.xpath("//li[descendant::a//span[text()='All Orders']]/a")).click();

        Thread.sleep(3000);
        //6. Select first result, click the dropdown of the result and click on Edit
        driver.findElement(By.xpath("//table[@aria-label='All Orders']/tbody/tr[1]/td[last()]//a[@role='button']")).click();
        driver.findElement(By.xpath("//a[@title='Edit']")).click();
//7.Click on the close button form the Account Name Field and  Select Account name as Testing001
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[text()='Account Name']/following::button[1]"))).click();
        Thread.sleep(2000);
        driver.findElements(By.xpath("//div/ul[@*='Recent Accounts']/li[position()>1]")).get(0).click();


//8.Click on the close button form theContract Number field and select Contract number as 00000104

        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait2.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[text()='Contract Number']/following::button[1]"))).click();

        Thread.sleep(2000);
        driver.findElements(By.xpath("//div/ul[@*='Recent Contracts']/li[position()>1]")).get(0).click();

        String contractNumber = driver.findElement(By.xpath("//label[text()='Contract Number']/following::input[1]")).getAttribute("placeholder");
        Thread.sleep(2000);

//            9. Click Save
        driver.findElement(By.xpath("//button[@name='SaveEdit']")).click();
//    Expected Result: New account name and respective Contract number should be saved

        Thread.sleep(500);

        WebElement toastMsg = new WebDriverWait(driver,Duration.ofSeconds(15)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[starts-with(@id,'toast')]/span")));


        String toastMsgText = toastMsg.getText().trim();
        System.out.println("toast Msg :: "+toastMsgText);
        System.out.println("Contract Number :: "+contractNumber);
        Assert.assertTrue(toastMsgText.contains("Order") && toastMsgText.contains("was saved"));

    }

}
