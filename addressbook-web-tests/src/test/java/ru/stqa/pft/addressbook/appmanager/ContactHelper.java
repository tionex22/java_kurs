package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.List;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void submitContactCreation() {
    click(By.xpath("//div[@id='content']/form/input[21]"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getName());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("address"), contactData.getAddress());
    type(By.name("mobile"), contactData.getMobile());
    type(By.name("email"), contactData.getEmail());

    if (creation) {
      //new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
      new Select(wd.findElement(By.name("new_group"))).selectByIndex(1);
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void addNewContact() {
    click(By.linkText("add new"));
  }

  public void deleteSelectContact() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void selectContact(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }

  public void initContact(int index) {
    wd.findElements(By.xpath("//*[@title=\"Edit\"]")).get(index).click();
  }

  public void initContactById(int id) {
    wd.findElement(By.cssSelector("img[title=\"Edit\"]")).click();
  }

  public void submitContactModification() {
    click(By.name("update"));
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public void create(ContactData contact) {
    addNewContact();
    fillContactForm(contact, true);
    submitContactCreation();
    contactCache = null;
  }

  public void modify(ContactData contact) {
    //selectContactById(contact.getId()); //Селект служит только для удаления записи, редактирование идет по "initContactById"
    selectContact(0);
    initContactById(contact.getId());
    fillContactForm(contact, false);
    submitContactModification();
    contactCache = null;
    goHomePage();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectContact();
    acceptAlert();
    contactCache = null;
    goHomePage();
  }

  public void goHomePage() {
    if (isElementPresent(By.id("maintable"))) {
      return;
    }
    click(By.linkText("home"));
  }

  public void acceptAlert() {
    wd.switchTo().alert().accept();
  }

  public int count() { /*Метод показывает кол-во selected[] в списке*/
    return wd.findElements(By.name("selected[]")).size();
  }

  private Contacts contactCache = null;

  public Contacts all() {
    if (contactCache != null) {
      return new Contacts(contactCache);
    }
    contactCache = new Contacts();
    List<WebElement> elements = wd.findElements(By.xpath("//*[@name=\"entry\"]"));
    for (WebElement element : elements) {
      String name = element.findElement(By.cssSelector("tbody > tr > td + td + td")).getText();
      String lastname = element.findElement(By.cssSelector("tbody > tr > td + td")).getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      contactCache.add(new ContactData().withId(id).withName(name).withLastName(lastname));
      //contacts.add(new GroupData().withId(id).withName("name").withLastName("lastname").withAddress(null).withMobile(null).withEmail(null);
    }
    return new Contacts(contactCache);
  }
}