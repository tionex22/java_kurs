package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {
    if (!app.getContactHelper().isThereAContact()) {
      app.createGroupTest1();
      app.getContactHelper().createContact(new ContactData("Dima", null, null, "+79776190404", "Dima@mail.ru"));
      app.getNavigationHelper().returnHomePage();
    }
    app.getContactHelper().selectContact();
    app.getContactHelper().editContact();
    app.getContactHelper().fillContactForm(new ContactData("Dima", "Petrov", "Moscow", "+79776190404", "Dima@mail.ru"), false);
    app.getContactHelper().submitContactModification();
    app.logOut();
  }
}