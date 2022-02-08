package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() {
    if (!app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("Dima", null, null, "+79776190404", "Dima@mail.ru", "test1"), true);
      app.getNavigationHelper().returnHomePage();
    }
    app.getContactHelper().selectContact();
    app.getContactHelper().deleteSelectContact();
    app.acceptAlert();
    app.logOut();
  }

}
