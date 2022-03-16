package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

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
    type(By.name("mobile"), contactData.getMobilePhone());
    type(By.name("email"), contactData.getEmail());
    type(By.name("email2"), contactData.getEmail2());
    type(By.name("email3"), contactData.getEmail3());
    type(By.name("home"), contactData.getHomePhone());
    type(By.name("phone2"), contactData.getHomePhone2());
    type(By.name("work"), contactData.getWorkPhone());
    attach(By.name("photo"), contactData.getPhoto()); //Отдельный метод для аттача


    if (creation) {
      //new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
      new Select(wd.findElement(By.name("new_group"))).selectByIndex(1);
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void fillContactFormWithoutGroup(ContactData contactData) {
    type(By.name("firstname"), contactData.getName());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("address"), contactData.getAddress());
    type(By.name("mobile"), contactData.getMobilePhone());
    type(By.name("email"), contactData.getEmail());
    type(By.name("email2"), contactData.getEmail2());
    type(By.name("email3"), contactData.getEmail3());
    type(By.name("home"), contactData.getHomePhone());
    type(By.name("phone2"), contactData.getHomePhone2());
    type(By.name("work"), contactData.getWorkPhone());
    attach(By.name("photo"), contactData.getPhoto()); //Отдельный метод для аттача
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

  private void selectGroup(int index) {
    wd.findElements(By.cssSelector("select[name='group'] > option")).get(index).click();
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

  public void createWithoutGroup(ContactData contact) {
    addNewContact();
    fillContactFormWithoutGroup(contact);
    submitContactCreation();
    contactCache = null;
  }

  public ContactData addInGroup() {
    selectGroup(1);
    selectContact(0);
    clickAddTo();
    gotoContactInGroupPage();
    //DbHelper.verifyAddInGroup();
    return new ContactData();
  }

  private void gotoContactInGroupPage() {
    if (isElementPresent(By.id("maintable"))) {
      return;
    }
    click(By.cssSelector("i > a[href]"));
  }

  public void deleteFromGroup() {
    selectGroup(2);
    selectContact(0);
    clickRemove();
    gotoHomePage();
  }

  private void clickRemove() {
    click(By.name("remove"));
  }

  private void clickAddTo() {
    click(By.cssSelector("input[name=add]"));
  }

  public void modify(ContactData contact) {
    //selectContactById(contact.getId()); //Селект служит только для удаления записи, редактирование идет по "initContactById"
    selectContact(0);
    //initContactById(contact.getId());
    initContactModificationById(contact.getId());
    fillContactForm(contact, false);
    submitContactModification();
    contactCache = null;
    gotoHomePage();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectContact();
    acceptAlert();
    contactCache = null;
    gotoHomePage();
  }

  public void gotoHomePage() {
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

  public Contacts all() { //Получает данные с начальной страницы HomePage
    if (contactCache != null) {
      return new Contacts(contactCache);
    }
    contactCache = new Contacts();
    List<WebElement> elements = wd.findElements(By.xpath("//*[@name=\"entry\"]"));
    for (WebElement element : elements) {
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      String name = element.findElement(By.cssSelector("tbody > tr > td + td + td")).getText();
      String lastname = element.findElement(By.cssSelector("tbody > tr > td + td")).getText();
      String address = element.findElement(By.cssSelector("tbody > tr > td + td + td +td")).getText();
      String allEmails = element.findElement(By.cssSelector("tbody > tr > td + td + td +td + td")).getText();
      String allPhones = element.findElement(By.cssSelector("tbody > tr > td + td + td + td + td + td")).getText();
      contactCache.add(new ContactData().withId(id).withName(name).withLastName(lastname).withAddress(address)
              .withAllEmails(allEmails)
              .withAllPhones(allPhones));
      //contacts.add(new GroupData().withId(id).withName("name").withLastName("lastname").withAddress(null).withMobile(null).withEmail(null);
    }
    return new Contacts(contactCache);
  }

  public ContactData infoFromEditForm(ContactData contact) { //5.9 Получает данные из формы редактирования
    initContactModificationById(contact.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String home2 = wd.findElement(By.name("phone2")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    wd.navigate().back();
    return new ContactData().withId(contact.getId()).withName(firstname).withLastName(lastname)
            .withAddress(address)
            .withEmail(email).withEmail2(email2).withEmail3(email3)
            .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work).withHomePhone2(home2);
  }

  private void initContactModificationById(int id) { //5.9
    WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']", id)));
    WebElement row = checkbox.findElement(By.xpath("./../.."));
    List<WebElement> cells = row.findElements(By.tagName("td"));
    cells.get(7).findElement(By.tagName("a")).click();
  }

  public void selectGroup(Contacts contactData) {
    if (contactData.iterator().next().getGroups().size() > 1) {
      Assert.assertTrue(contactData.iterator().next().getGroups().size() == 1);
      new Select(wd.findElement(By.name("group"))).selectByVisibleText(contactData.iterator().next().getGroups().iterator().next().getName());
    }
  }

  public void selectGroup(GroupData group) {
    wd.findElement(By.xpath(String.format("//select[@name='to_group']/option[@value='%s']", group.getId()))).click();
  }
  public void selectContact(ContactData contact) {
    click(By.xpath(String.format("//input[@type='checkbox']", contact.getId())));
  }

  public void selectContactWithoutGroup(ContactData contact) {
    new Select(wd.findElement(By.name("group"))).selectByVisibleText("[none]");
    click(By.xpath(String.format("//input[@type='checkbox']", contact.getId())));
  }

  public void removeContactFromGroup() {
    click(By.name("remove"));
    contactCache = null;
    gotoHomePage();
  }

  public void getGroupData(GroupData groupData) {
    click(By.xpath(String.format("//select[@name='group']/option[text() = '%s']", groupData.getName())));;
  }

  public void addContactToGroup() {
    click(By.name("add"));
    contactCache = null;
    gotoHomePage();
  }
}