package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {
  private final Properties properties;
  WebDriver wd;

  private SessionHelper sessionHelper;
  private NavigationHelper navigationHelper;
  private GroupHelper groupHelper;
  private ContactHelper contactHelper;
  public boolean acceptNextAlert = true;
  private String browser;

  public ApplicationManager(String browser) {
    this.browser = browser;
    properties = new Properties();
  }

  public void init() throws IOException {
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));

    if (browser.equals(BrowserType.CHROME)) {
      System.setProperty("webdriver.chrome.driver", "D:/chromedriver.exe");
      wd = new ChromeDriver();
    } else if (browser.equals(BrowserType.FIREFOX)) {
      System.setProperty("webdriver.gecko.driver", "D:/geckodriver.exe");
      wd = new FirefoxDriver();
    } else if (browser.equals(BrowserType.OPERA)) {
      System.setProperty("webdriver.chrome.driver", "D:/operadriver.exe");
      wd = new ChromeDriver();
    } else if (browser.equals(BrowserType.EDGE)) {
      System.setProperty("webdriver.edge.driver", "D:/msedgedriver.exe");
      wd = new EdgeDriver();
    }
    wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    wd.get(properties.getProperty("web.baseUrl"));
    groupHelper = new GroupHelper(wd);
    contactHelper = new ContactHelper(wd);
    navigationHelper = new NavigationHelper(wd);
    sessionHelper = new SessionHelper(wd);
    sessionHelper.login(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPassword"));
  }

  public void logOut() {
    wd.findElement(By.linkText("Logout")).click();
  }

  public void stop() {
    wd.quit();
  }

  public GroupHelper group() {
    return groupHelper;
  }

  public ContactHelper contact() {
    return contactHelper;
  }

  public NavigationHelper goTo() {
    return navigationHelper;
  }

  public void createGroupTest1() {
    goTo().groupPage();
    if (group().all().size() == 0) {
      group().create(new GroupData().withName("test1"));
    }
  }
}
