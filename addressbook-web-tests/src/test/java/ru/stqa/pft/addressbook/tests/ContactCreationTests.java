package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase1 {

  @Test
  public void testContactCreation() {
    app.getContactHelper().addNewContact();
    app.getContactHelper().initContactCreation(new ContactData("Dima", "Petrov", "Moscow", "+79776190404", "Dima@mail.ru"));
    app.getContactHelper().submitContactCreation();
    app.getNavigationHelper1().returnHomePage();
    app.logOut();
  }

}
