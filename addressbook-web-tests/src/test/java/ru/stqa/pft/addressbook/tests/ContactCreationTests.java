package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    app.getContactHelper().addNewContact();
    app.getContactHelper().fillContactForm(new ContactData("Dima", null, null, "+79776190404", "Dima@mail.ru"));
    app.getContactHelper().submitContactCreation();
    app.getNavigationHelper().returnHomePage();
    app.logOut();
  }

}
