package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class ContactDeletionTests {
  private WebDriver dw;
  private boolean acceptNextAlert = true;


  @BeforeMethod(alwaysRun = true)
  public void setUp() throws Exception {
    System.setProperty("webdriver.chrome.driver", "D:/chromedriver.exe");
    dw = new ChromeDriver();
    dw.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testContactDeletion() {
    dw.get("http://localhost/addressbook/");
    dw.findElement(By.name("user")).clear();
    dw.findElement(By.name("user")).sendKeys("admin");
    dw.findElement(By.name("pass")).clear();
    dw.findElement(By.name("pass")).sendKeys("secret");
    dw.findElement(By.xpath("//input[@value='Login']")).click();
    dw.findElement(By.xpath("//*[@id=\"maintable\"]/tbody/tr[2]/td[1]")).click();
    dw.findElement(By.xpath("//input[@value='Delete']")).click();
    acceptAlert();
    dw.findElement(By.linkText("Logout")).click();
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() {
    dw.quit();
  }

  private boolean isElementPresent(By by) {
    try {
      dw.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      dw.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String acceptAlert() {
    try {
      Alert alert = dw.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
