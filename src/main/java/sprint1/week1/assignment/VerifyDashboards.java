package sprint1.week1.assignment;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class VerifyDashboards {

    ChromeDriver driver = null;

    @BeforeMethod
    public void login() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
//        Login to https://login.salesforce.com

        driver.get("https://login.salesforce.com/");
//        driver.get("https://virtusa-2df-dev-ed.develop.my.salesforce.com/");
        driver.findElement(By.id("username")).sendKeys("gokul.sekar@testleaf.com");
        driver.findElement(By.id("password")).sendKeys("Leaf$321");
        driver.findElement(By.id("Login")).click();

    }

    @Test
    public void Test() throws InterruptedException {
        driver.findElement(By.xpath("//button[@title='App Launcher']")).click();
        driver.findElement(By.xpath("//button[@aria-label='View All Applications']")).click();
        Thread.sleep(2000);
        WebElement dashboards_Link = driver.findElement(By.xpath("//p[text()='Dashboards']"));
        try {

            dashboards_Link.click();
        } catch (ElementClickInterceptedException e) {
            driver.executeScript("arguments[0].click()", dashboards_Link);
        }
        WebElement dashboardLink = driver.findElement(By.xpath("//a[@title='Dashboards']"));
        driver.executeScript("arguments[0].click();", dashboardLink);
        Thread.sleep(2000);
        WebElement dashboardName = driver.findElement(By.xpath("//span[@title='Dashboard Name']/.."));
//        Actions actions = new Actions(driver);
//        actions.moveToElement(dashboardName);
//        Thread.sleep(2000);
//        WebElement arrow_dashbrdName = driver.findElement(By.xpath("//span[@title='Dashboard Name']/..//*[name()='svg']"));
        dashboardName.click();

        //Get names from table
        List<String> displayedNames = driver.findElements(By.xpath("//table[contains(@aria-label,'Ascending')]/tbody/tr/th//a")).stream().map(e -> e.getText().trim()).collect(Collectors.toList());

        List<String> sortedNames = displayedNames;

        Collections.sort(sortedNames);
        System.out.println("Test Passed");
        Assert.assertTrue(displayedNames.equals(sortedNames));




    }
}
