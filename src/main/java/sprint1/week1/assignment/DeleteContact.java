package sprint1.week1.assignment;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class DeleteContact {

    ChromeDriver driver;

    @BeforeMethod
    public void Login() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
//        Login to https://login.salesforce.com

        driver.get("https://virtusa-2df-dev-ed.develop.my.salesforce.com/");
        driver.findElement(By.id("username")).sendKeys("shankar@sdet.com");
        driver.findElement(By.id("password")).sendKeys("Shkr@9497");
        driver.findElement(By.id("Login")).click();
    }


    @Test
    public void Test() throws InterruptedException {
        driver.findElement(By.xpath("//button[@title='App Launcher']")).click();
        driver.findElement(By.xpath("//button[@aria-label='View All Applications']")).click();
        Thread.sleep(3000);
//        Click Contacts

        Thread.sleep(3000);
        WebElement element = driver.findElement(By.xpath("//p[text()='Contacts']"));
        driver.executeScript("arguments[0].click();", element);

//        Contacts
        Thread.sleep(3000);

        createNewContact(driver);

//        list of contacts
        List<WebElement> elements = driver.findElements(By.xpath("//table[@*='Recently Viewed']/tbody/tr"));

        int size = elements.size();
        System.out.println(size);
        //        search for a name
        WebElement txt_SearchBox = null;

        txt_SearchBox = driver.findElement(By.xpath("//input[@name='Contact-search-input']"));
        txt_SearchBox.sendKeys("SHANKAR K");
        txt_SearchBox = driver.findElement(By.xpath("//input[@name='Contact-search-input']"));

        String name = txt_SearchBox.getAttribute("value");
        System.out.println("Entered name in search box is " + name);

        //getText

//        click on dropdown
       Thread.sleep(2000);
        driver.findElement(By.xpath("(//table[@aria-label='Recently Viewed']//td[last()]//a)[1]")).click();
        //Click on Delete
        driver.findElement(By.xpath("//a[@title='Delete']")).click();
        //Click on Delete button
        driver.findElement(By.xpath("//button[@title='Delete']")).click();
        Thread.sleep(2000);
        //Toast msg
        String deletedName = driver.findElement(By.xpath("//div[contains(@id,'toast')]/span")).getText();
        System.out.println(deletedName);

        Assert.assertTrue(deletedName.contains(name));
        //


    }

    public void createNewContact(RemoteWebDriver driver) throws InterruptedException {
        driver.findElement(By.cssSelector("a[title=New]")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement firstName = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='firstName']")));

        firstName.sendKeys("SHANKAR");
        driver.findElement(By.xpath("//input[@name='lastName']")).sendKeys("K");
        Thread.sleep(3000);
        driver.findElement(By.xpath("//button[@name='SaveEdit']")).click();
        //Click On Contact at the top
        Thread.sleep(3000);
        WebElement ContactsHeadingLink = driver.findElement(By.xpath("//a[@title='Contacts']"));
        try {
            ContactsHeadingLink.click();
        } catch (Exception e) {
            driver.executeScript("arguments[0].click()", ContactsHeadingLink);
        }
    }
}
