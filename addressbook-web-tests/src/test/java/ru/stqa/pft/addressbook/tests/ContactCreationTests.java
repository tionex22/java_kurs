package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

  @Test //(enabled = false)
  public void testContactCreation() {
    app.createGroupTest1();
    app.contact().goHomePage();
    List<ContactData> before = app.contact().list();
    ContactData contact = new ContactData().withName("111").withLastName("111");
    //ContactData contact = new ContactData().withName("111").withLastName("111").withAddress(null).withMobile(null).withEmail(null);
    app.contact().create(contact);
    app.contact().goHomePage();
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size() + 1);

    before.add(contact);
    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
    //app.logOut();
  }
}
