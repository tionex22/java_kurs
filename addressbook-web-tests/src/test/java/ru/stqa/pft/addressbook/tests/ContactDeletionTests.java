package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() {
    if (!app.getContactHelper().isThereAContact()) {
      app.createGroupTest1();
      app.getContactHelper().createContact(new ContactData("333", "777", null, null, null));
      app.getNavigationHelper().returnHomePage();
    }
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().selectContact(before.size() - 1);
    app.getContactHelper().deleteSelectContact();
    app.acceptAlert();
    app.getNavigationHelper().returnHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size() - 1);

    before.remove(before.size() - 1);
    Assert.assertEquals(before, after);
    System.out.println("before" + before);
    System.out.println("after" + after);
    app.logOut();
  }

}
