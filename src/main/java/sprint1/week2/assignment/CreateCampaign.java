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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreateCampaign {


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

        //3. Click view All and click Service Console from App Launcher
        WebElement appLauncher = driver.findElement(By.xpath("//button[@title='App Launcher']"));

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(appLauncher)).click();

        WebElement element = driver.findElement(By.xpath("//button[@aria-label='View All Applications']"));
        //view all click
        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait1.until(ExpectedConditions.elementToBeClickable(element)).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//p[text()='Sales']")).click();

//        4. Click on Campaigns tab
        Thread.sleep(2000);

        WebElement Campaigns = driver.findElement(By.xpath("//a[@title='Campaigns']"));
        driver.executeScript("arguments[0].click();", Campaigns);


//        5. Click on New button
        driver.findElement(By.xpath("//div[@title='New']/..")).click();
//        6. Enter Campaign name as 'Bootcamp',Get the text and Store it
        Thread.sleep(2000);
        String bootcamp = faker.name().firstName() + "Bootcamp";
        driver.findElement(By.xpath("//label/*[text()='Campaign Name']/../following-sibling::input")).sendKeys(bootcamp);

//        7. Choose Start date as Tomorrow
        driver.findElement(By.xpath("//label/*[text()='Start Date']/../following::input[1]")).click();
        Thread.sleep(2000);
        LocalDate localDate = LocalDate.now().plusDays(1);
        String formatted = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        driver.findElement(By.xpath("//div[contains(@aria-label,'Date picker')]//td[@*=\"" + formatted + "\"]")).click();

//        8. End date as Tomorrow+1
        driver.findElement(By.xpath("//label/*[text()='End Date']/../following::input[1]")).click();
        LocalDate localDateTmrwPlus1 = LocalDate.now().plusDays(2);
        String TmrwPlus1 = localDateTmrwPlus1.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        driver.findElement(By.xpath("//div[contains(@aria-label,'Date picker')]//td[@*=\"" + TmrwPlus1 + "\"]")).click();

//        9. click Save and Verify the newly created Campaign
        driver.findElement(By.xpath("(//button[child::span[text()='Save']])[last()]")).click();
        Thread.sleep(500);
//        Expected Result: New Campaign should be created with name as 'Bootcamp'
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        WebElement toast = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                "//div[@role='alert']//div[starts-with(@id,'toast')]/span")));
        String toastMsg = toast.getText().trim();
        String expectedMsg = "Campaign \""+bootcamp+"\" was created.";
        System.out.println("actual:");
        System.out.println(toastMsg);
        System.out.println("expected:");

       Assert.assertEquals(toastMsg,expectedMsg);


    }
}
/*
Test Steps:
        1. Login to https://login.salesforce.com
        2. Click on toggle menu button from the left corner
3. Click view All and click Sales from App Launcher
4. Click on Campaigns tab
5. Click on New button
6. Enter Campaign name as 'Bootcamp',Get the text and Store it
7. Choose Start date as Tomorrow
8. End date as Tomorrow+1
        9. click Save and Verify the newly created Campaign
Expected Result: New Campaign should be created with name as 'Bootcamp'
Step Video: https://drive.google.com/file/d/1Ql0QHpUXmgSGMhNSoq2S062DrMML3YWF/view?usp=sharing*/
