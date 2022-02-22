package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.devtools.v85.domsnapshot.model.StringIndex;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

  @Test //(enabled = false)
  public void testContactCreation() {
    app.createGroupTest1();
    app.getContactHelper().returnHomePage();
    List<ContactData> before = app.getContactHelper().getContactList();
    ContactData contact = new ContactData("111", "111", null, null, null);
    app.getContactHelper().createContact(contact);
    app.getContactHelper().returnHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size() + 1);

    before.add(contact);
    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
    //app.logOut();
  }
}
