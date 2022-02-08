package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    app.getContactHelper().createContact(new ContactData("Dima", null, null, "+79776190404", "Dima@mail.ru", "test1"), true);
    app.getNavigationHelper().returnHomePage();
    app.logOut();
  }

}
