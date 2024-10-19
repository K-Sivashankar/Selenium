package sprint1.week2.assignment;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class CreateContactForCampaign {
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
        WebElement appLauncher = driver.findElement(By.xpath("//button[@title='App Launcher']"));

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(appLauncher)).click();

        WebElement element = driver.findElement(By.xpath("//button[@aria-label='View All Applications']"));
        //view all click
        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait1.until(ExpectedConditions.elementToBeClickable(element)).click();

        //sales
        driver.findElement(By.xpath("//p[.='Sales']")).click();
//        Click on Campaigns tab
        WebElement campaigns = driver.findElement(By.xpath("//a[@title='Campaigns']"));

        try {
            campaigns.click();
        } catch (Exception e) {
            driver.executeScript("arguments[0].click();", campaigns);
        }
//       Click Bootcamp link
        WebElement bootcamp = driver.findElement(By.xpath("//a[@title='Bootcamp']"));
        bootcamp.click();
//       Click on New Contact under Search field
        WebElement NewContact = driver.findElement(By.xpath("//div[@title='New Contact']"));
        NewContact.click();
//       Pick Salutation as 'Mr.'
        driver.findElement(By.xpath("((//span[.='Salutation'])[1]/following::a)[1]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//ul/li/a[@title='Mr.']")).click();
//       Enter first name as <your First Name>
        String name = faker.name().firstName() + " " + faker.name().lastName();
        driver.findElement(By.xpath("//input[@placeholder='First Name']")).sendKeys(name.split(" ")[0]);
        driver.findElement(By.xpath("//input[@placeholder='Last Name']")).sendKeys(name.split(" ")[1]);
//             Enter last name as <your last name>
//             Click Save
        driver.findElement(By.xpath("(//span[.='Save']/..)[last()]")).click();
        String toastMessage = driver.findElement(By.cssSelector("div[id*='toast']>span")).getText().trim();
        driver.findElement(By.xpath("//div[starts-with(@id,'toast')]/ancestor::div/button")).click();
        System.out.println(toastMessage);
//        Click on Add Contact from Campaign member
        Thread.sleep(5000);
        driver.executeScript("document.body.style.zoom='0.5'");
        Thread.sleep(2000);
        WebElement AddContacts = driver.findElement(By.xpath("//div[@title='Add Contacts']"));
       AddContacts.click();

//        new Actions(driver).moveToElement(AddContacts).click().build().perform();


       /* try{
            AddContacts.click();
        }catch (Exception e)
        {
            driver.executeScript("arguments[0].click();", AddContacts);
        }*/

        Thread.sleep(3000);
//        Search name
        WebElement searchCts = driver.findElement(By.xpath("//input[@placeholder='Search Contacts...']"));

        for(char c : name.toCharArray())
        {
            searchCts.sendKeys(String.valueOf(c));
            Thread.sleep(200);
        }
//        searchCts.sendKeys(name);
        searchCts.click();
//        Select the Created Contact
        Thread.sleep(4000);
        String option = "//input[@placeholder='Search Contacts...']/following::a[1]";
//        String option = "//div[@title=\"" + name + "\"]/ancestor::*[2][self::a[@role='option']]";


        WebElement optionElement = driver.findElement(By.xpath(option));
        try {
            Thread.sleep(2000);
            optionElement.click();

        } catch (Exception e) {
            searchCts.clear();
            Thread.sleep(1000);


            new Actions(driver).sendKeys(searchCts, name);
            Thread.sleep(1000);
            driver.executeScript("arguments[0].click();", searchCts);
        }
//        Click Next
        driver.findElement(By.xpath("//button[.='Next']")).click();
//        Click Submit on the Add to Campaign pop up
        Thread.sleep(2000);

        driver.findElement(By.xpath("//button[.='Submit']")).click();
        WebElement closeToastMsg = driver.findElement(By.xpath("//div[contains(.,'was successfully updated.') and starts-with(@id,'toast') ]/ancestor::div//descendant::button//*[name()='svg']"));
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(closeToastMsg)).click();
//        verify the created Contact under Campaign by clicking 'View All'
        Thread.sleep(2000);

        WebElement viewAll = driver.findElement(By.xpath("//div/article[@*='Campaign Members']/../descendant::span[text()='View All']"));
        try {
            viewAll.click();
        } catch (Exception e) {
            driver.executeScript("arguments[0].click();", viewAll);
        }
        driver.executeScript("document.body.style.zoom='1'");

//        Navigate to Contacts tab
        Thread.sleep(2000);

        WebElement Contacts = driver.findElement(By.xpath("//a[@title='Contacts']"));

        try {
            Contacts.click();
        } catch (Exception e) {
            driver.executeScript("arguments[0].click();", Contacts);
        }
//        Search for Cantact with your Name
        driver.findElement(By.xpath("//input[@name='Contact-search-input']")).sendKeys(name + Keys.ENTER);

        //        Expected Result: Contact should be created under Contacts tab and associated to campaign
        String actualText = driver.findElement(By.xpath("//th/span/a[@title='" + name + "']")).getText();
        System.out.println("actual text in contacts :: " + actualText);
        Assert.assertTrue(actualText.equals(name));


    }


}

/*Test Steps:
        1. Login to https://login.salesforce.com
        2. Click on toggle menu button from the left corner
3. Click view All and click Sales from App Launcher
4. Click on Campaigns tab
5. Click Bootcamp link
6. Click on New Contact under Search field
8. Pick Salutation as 'Mr.'
        9. Enter first name as <your First Name>
        10. Enter last name as <your last name>
        12. Click Save
13. Click on Add Contact from Campaign member
14. Select the Created Contact
13. Click Next
14. Click Submit on the Add to Campaign pop up
15. verify the created Contact under Campaign by clicking 'View All'
        16. Navigate to Contacts tab
17. Search for Cantact with your Name
Expected Result: Contact should be created under Contacts tab and associated to campaign
Step Video: https://drive.google.com/file/d/1eEtRdyR3MsgZNqcLFXPtgVk0kyDIxoQH/view?usp=sharing*/
