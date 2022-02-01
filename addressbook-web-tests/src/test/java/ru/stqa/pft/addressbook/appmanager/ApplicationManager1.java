package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class ApplicationManager1 {
  WebDriver wd;

  private SessionHelper1 sessionHelper1;
  private NavigationHelper1 navigationHelper1;
  private ContactHelper contactHelper;
  public boolean acceptNextAlert = true;

  public void init() {
    System.setProperty("webdriver.chrome.driver", "D:/chromedriver.exe");
    wd = new ChromeDriver();
    wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    wd.get("http://localhost/addressbook/");
    contactHelper = new ContactHelper(wd);
    navigationHelper1 = new NavigationHelper1(wd);
    sessionHelper1 = new SessionHelper1(wd);
    sessionHelper1.login("admin", "secret");
  }


  public void logOut() {
    wd.findElement(By.linkText("Logout")).click();
  }

  public void stop() {
    wd.quit();
  }

  public boolean isElementPresent(By by) {
    try {
      wd.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }


  public String acceptAlert() {
    try {
      Alert alert = wd.switchTo().alert();
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

  public ContactHelper getContactHelper() {
    return contactHelper;
  }

  public NavigationHelper1 getNavigationHelper1() {
    return navigationHelper1;
  }
}
