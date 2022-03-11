package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

  File photo = new File("src/test/resources/stru.jpg");

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0 | app.db().groups().size() == 0) { //данные из базы (.db().)
      app.createGroupTest1();
      app.contact().create(new ContactData().withName("000").withLastName("000").withAddress("000")
              .withEmail("000").withEmail2("000").withEmail3("000")
              .withHomePhone("000").withMobilePhone("000").withWorkPhone("000").withHomePhone2("000")
              .withPhoto(photo));
      app.contact().gotoHomePage();
    }
  }

  @Test //(enabled = false)
  public void testContactModification() {
    Contacts before = app.db().contacts(); //данные из базы (.db().)
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData()
            .withId(modifiedContact.getId()).withName("333").withLastName("333").withAddress("333")
            .withEmail("333").withEmail2("333").withEmail3("333")
            .withHomePhone("333").withMobilePhone("333").withWorkPhone("333").withHomePhone2("333")
            .withPhoto(photo);
    //.withId(before.get(index).getId()).withName("222").withLastName("222").withAddress(null).withMobile(null).withEmail(null);
    app.contact().modify(contact);
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.db().contacts(); //данные из базы (.db().)
    assertThat(after, equalTo(before.withOut(modifiedContact).withAdded(contact)));
    verifyContactListInUI();
    //app.logOut();
  }
}
