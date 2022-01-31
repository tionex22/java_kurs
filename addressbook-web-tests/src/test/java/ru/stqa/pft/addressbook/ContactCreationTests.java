package ru.stqa.pft.addressbook;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class ContactCreationTests {
  private WebDriver webDriver;

  @BeforeClass(alwaysRun = true)
  public void setUp() {
    System.setProperty("webdriver.chrome.driver", "D:/chromedriver.exe");
    webDriver = new ChromeDriver();
    webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    webDriver.get("http://localhost/addressbook/");
    login("admin", "secret");
  }

  private void login(String username, String password) {
    webDriver.findElement(By.name("user")).clear();
    webDriver.findElement(By.name("user")).sendKeys(username);
    webDriver.findElement(By.name("pass")).clear();
    webDriver.findElement(By.name("pass")).sendKeys(password);
    webDriver.findElement(By.xpath("//input[@value='Login']")).click();
  }

  @Test
  public void testUntitledTestCase() {
    addNewContact();
    initContactCreation(new ContactData("Dima", "Petrov", "Moscow", "+79776190404", "Dima@mail.ru"));
    submitContactCreation();
    returnHomePage();
    logOut();
  }

  private void logOut() {
    webDriver.findElement(By.linkText("Logout")).click();
  }

  private void returnHomePage() {
    webDriver.findElement(By.linkText("home page")).click();
  }

  private void submitContactCreation() {
    webDriver.findElement(By.xpath("//div[@id='content']/form/input[21]")).click();
  }

  private void initContactCreation(ContactData contactData) {
    webDriver.findElement(By.name("firstname")).clear();
    webDriver.findElement(By.name("firstname")).sendKeys(contactData.getName());
    webDriver.findElement(By.name("lastname")).clear();
    webDriver.findElement(By.name("lastname")).sendKeys(contactData.getLastname());
    webDriver.findElement(By.name("address")).clear();
    webDriver.findElement(By.name("address")).sendKeys(contactData.getAddress());
    webDriver.findElement(By.name("mobile")).clear();
    webDriver.findElement(By.name("mobile")).sendKeys(contactData.getMobile());
    webDriver.findElement(By.name("email")).clear();
    webDriver.findElement(By.name("email")).sendKeys(contactData.getEmail());
  }

  private void addNewContact() {
    webDriver.findElement(By.linkText("add new")).click();
  }

  @AfterClass(alwaysRun = true)
  public void tearDown() {
    webDriver.quit();
  }

  private boolean isElementPresent(By by) {
    try {
      webDriver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      webDriver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }
}
