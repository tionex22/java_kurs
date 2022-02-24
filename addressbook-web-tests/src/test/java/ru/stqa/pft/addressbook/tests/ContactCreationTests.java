package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

public class ContactCreationTests extends TestBase {

  @Test //(enabled = false)
  public void testContactCreation() {
    app.createGroupTest1();
    app.contact().goHomePage();
    Set<ContactData> before = app.contact().all();
    ContactData contact = new ContactData().withName("111").withLastName("111");
    //ContactData contact = new ContactData().withName("111").withLastName("111").withAddress(null).withMobile(null).withEmail(null);
    app.contact().create(contact);
    app.contact().goHomePage();
    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(), before.size() + 1);

    contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt());
    before.add(contact);
    Assert.assertEquals(before, after);
    //app.logOut();
  }
}
