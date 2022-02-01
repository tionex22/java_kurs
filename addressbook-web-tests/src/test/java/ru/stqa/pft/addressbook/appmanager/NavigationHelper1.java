package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper1 extends HelperBase1 {

  public NavigationHelper1(WebDriver wd) {
    super (wd);
  }

  public void returnHomePage() {
    click(By.linkText("home page"));
  }
}
