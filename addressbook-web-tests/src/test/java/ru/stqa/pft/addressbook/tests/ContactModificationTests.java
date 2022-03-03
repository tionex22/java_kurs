package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.contact().all().size() == 0) {
      app.createGroupTest1();
      app.contact().create(new ContactData().withName("111").withLastName("111"));
      app.contact().gotoHomePage();
    }
  }

  @Test //(enabled = false)
  public void testContactModification() {
    Contacts before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData()
            .withId(modifiedContact.getId()).withName("333").withLastName("333");
    //.withId(before.get(index).getId()).withName("222").withLastName("222").withAddress(null).withMobile(null).withEmail(null);
    app.contact().modify(contact);
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.contact().all();
    System.out.println("AFTER " + after);
    System.out.println("BEFORE " + before.withOut(modifiedContact).withAdded(contact));
    System.out.println("modifiedContact " + modifiedContact);
    System.out.println("contact " + contact);
    assertThat(after, equalTo(before.withOut(modifiedContact).withAdded(contact)));
    //app.logOut();
  }
}
