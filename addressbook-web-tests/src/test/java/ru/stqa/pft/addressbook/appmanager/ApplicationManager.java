package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;

import java.util.concurrent.TimeUnit;

public class ApplicationManager {
  WebDriver wd;

  private SessionHelper sessionHelper;
  private NavigationHelper navigationHelper;
  private GroupHelper groupHelper;
  private ContactHelper contactHelper;
  public boolean acceptNextAlert = true;
  private String browser;

  public ApplicationManager(String browser) {
    this.browser = browser;
  }

  public void init() {
    if (browser == BrowserType.CHROME) {
      System.setProperty("webdriver.chrome.driver", "D:/chromedriver.exe");
      wd = new ChromeDriver();
    } else if (browser == BrowserType.FIREFOX) {
      System.setProperty("webdriver.gecko.driver", "D:/geckodriver.exe");
      wd = new FirefoxDriver();
    } else if (browser == BrowserType.OPERA) {
      System.setProperty("webdriver.chrome.driver", "D:/operadriver.exe");
      wd = new ChromeDriver();
    } else if (browser == BrowserType.EDGE) {
      System.setProperty("webdriver.edge.driver", "D:/msedgedriver.exe");
      wd = new EdgeDriver();
    }
    wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    wd.get("http://localhost/addressbook/");
    groupHelper = new GroupHelper(wd);
    contactHelper = new ContactHelper(wd);
    navigationHelper = new NavigationHelper(wd);
    sessionHelper = new SessionHelper(wd);
    sessionHelper.login("admin", "secret");
  }

  public void logOut() {
    wd.findElement(By.linkText("Logout")).click();
  }

  public void stop() {
    wd.quit();
  }

  public void acceptAlert() {
    wd.switchTo().alert().accept();
  }

  public GroupHelper getGroupHelper() {
    return groupHelper;
  }

  public ContactHelper getContactHelper() {
    return contactHelper;
  }

  public NavigationHelper getNavigationHelper() {
    return navigationHelper;
  }
}
