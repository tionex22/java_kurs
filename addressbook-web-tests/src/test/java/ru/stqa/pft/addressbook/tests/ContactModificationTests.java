package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {
    app.getContactHelper().selectContact();
    app.getContactHelper().editContact();
    app.getContactHelper().fillContactForm(new ContactData("Dima", "Petrov", "Moscow", "+79776190404", "Dima@mail.ru"));
    app.getContactHelper().submitContactModification();
    app.getNavigationHelper().returnHomePage();
    app.logOut();
  }
}
