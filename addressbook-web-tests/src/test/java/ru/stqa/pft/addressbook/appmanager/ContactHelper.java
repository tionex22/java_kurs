package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
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

  public void initContact(int index) {
    wd.findElements(By.xpath("//*[@title=\"Edit\"]")).get(index).click();
  }

  public void submitContactModification() {
    click(By.name("update"));
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public void createContact(ContactData contact) {
    addNewContact();
    fillContactForm(contact, true);
    submitContactCreation();
  }

  public List<ContactData> getContactList() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> elements = wd.findElements(By.xpath("//*[@name=\"entry\"]"));
    for (WebElement element : elements) {
      String name = element.findElement(By.cssSelector("tbody > tr > td + td + td")).getText();
      String lastname = element.findElement(By.cssSelector("tbody > tr > td + td")).getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      ContactData contact = new ContactData(id, name, lastname, null, null, null);
      contacts.add(contact);
    }
    return contacts;
  }

  public int getContactCount() {
    return wd.findElements(By.name("selected[]")).size();
  }
}
