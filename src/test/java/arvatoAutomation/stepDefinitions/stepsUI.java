package arvatoAutomation.stepDefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.net.MalformedURLException;
import java.net.URL;

public class stepsUI {

    public static WebDriver driver;
    public static String url = "https://ac.myafterpay.com/en-se";
    public static String chromeDriver = "http://browser:4444/wd/hub";


    @Before("@UIFeatures")
    public static void setWebDriver() throws MalformedURLException {
        // Setting up chrome driver before running the scenarios in UIFeatures
        // WebDriverManager.chromedriver().setup();
        // driver = new ChromeDriver();

       ChromeOptions options = new ChromeOptions();
       driver = new RemoteWebDriver(new URL(chromeDriver), options);

    }

    @After("@UIFeatures")
    public void tearDown() {
        // Closing the browser after all tests are completed
        driver.quit();
    }

    @Given("customer is in a login page")
    public void customerLoginPage() throws InterruptedException {
        // Opening the customer login page and maximizing the window
        driver.get(url);
        driver.manage().window().maximize();

        // Waiting for the cookies pop-up which is inside single shadow DOM, and selecting "Accept all cookies" option
        Thread.sleep(1000);
        SearchContext shadow = driver.findElement(By.cssSelector("#usercentrics-root")).getShadowRoot();
        Thread.sleep(1000);
        shadow.findElement(By.cssSelector(".sc-gsDKAQ.jdEkck")).click();

    }
    @When("customer selects create account link")
    public void selectCreateAccountLink() {
        // Locating "Create account" link and clicking on it
        WebElement createAccountLink = driver.findElement(By.xpath("//a[normalize-space()='Create account']"));
        createAccountLink.click();
    }
    @When("customer inserts {string} e-mail address")
    public void insertEmailAddress(String emailAddress) {
        // Locating email field and inserting the email address and clicking submit button
        WebElement email = driver.findElement(By.xpath("//input[@id='NewAccountEmail']"));
        email.sendKeys(emailAddress);
        WebElement submit = driver.findElement(By.xpath("//button[@id='SubmitCreateAccount']"));
        submit.click();

    }
    @Then("customer is redirected to the {string} page")
    public void customerIsRedirectedToPage(String redirectedPage) {
        // Asserting the customer redirected page
        Assert.assertEquals(url + redirectedPage, driver.getCurrentUrl());
    }
    @Then("{string} message is shown")
    public void confirmMessage(String message) {
        // Asserting the expected message after the action
        Assert.assertTrue(driver.getPageSource().contains(message));
    }

    @When("customer selects login with ’e-mail and password’ option")
    public void customerSelectsLoginWithEMailAndPasswordOption() {
        // Locating "Email and Password" option and clicking on it
        WebElement emailPasswordOption = driver.findElement(By.xpath("//div[@id='welcome']//button[@id='emailandpasswordTabLink']"));
        emailPasswordOption.click();
    }

    @And("customer inserts invalid email address")
    public void customerInsertsInvalidEmailAddress() {
        // Locating email field and inserting invalid email
        WebElement emailField = driver.findElement(By.xpath("//input[@id='EmailAndPasswordModel_Email']"));
        emailField.sendKeys("invalidEmail");
        emailField.sendKeys(Keys.ENTER);
    }

    @Then("error validation message is shown")
    public void errorValidationMessageIsShown() {
        // Asserting that validation error message is shown
        Assert.assertTrue(driver.getPageSource().contains("Sorry, this does not seem to be a valid emailaddress, please try again"));
    }

    @And("customer inserts {string} email address and {string} password")
    public void customerInsertsEmailAddressAndPassword(String email, String password) {
        // Locating the email and password fields and inserting email and password passed from the scenario step
        WebElement emailField = driver.findElement(By.xpath("//input[@id='EmailAndPasswordModel_Email']"));
        emailField.sendKeys(email);
        WebElement passwordField = driver.findElement(By.xpath("//input[@id='EmailAndPasswordModel_Password']"));
        passwordField.sendKeys(password);
        // Locating login button and clicking on it
        WebElement loginButton = driver.findElement(By.xpath("//button[@id='EmailAndPasswordBtn']"));
        loginButton.click();
    }

    @And("error message is shown")
    public void errorMessageIsShown() {
        // Asserting that error message is shown
        Assert.assertTrue(driver.getPageSource().contains("The specified credentials are incorrect, make sure you typed them correctly"));
    }
}
