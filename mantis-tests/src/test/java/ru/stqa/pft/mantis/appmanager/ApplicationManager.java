package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {
  private final Properties properties;
  private WebDriver wd;

  public boolean acceptNextAlert = true;
  private String browser;
  private RegistrationHelper registrationHelper;

  public ApplicationManager(String browser) {
    this.browser = browser;
    properties = new Properties();
  }

  public void init() throws IOException {
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
  }

  public void stop() {
    if(wd != null) {
      wd.quit();
    }
  }

  public HttpSession newSession() {
    return new HttpSession(this);
  }

  public String getProperty(String key) {
    return properties.getProperty(key);
  }

  public RegistrationHelper registration() {
    if(registrationHelper == null) {
      registrationHelper = new RegistrationHelper(this);
    }
    return registrationHelper;
  }

  public WebDriver getDriver() { //Метод для вызова WevDriver
    if(wd == null) {
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
    }
    return wd;
  }
}
