package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase {

  File photo = new File("src/test/resources/stru.jpg");

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) { //данные из базы (.db().)
      app.createGroupTest1();
      app.contact().create(new ContactData().withName("000").withLastName("000").withAddress("000")
              .withEmail("000").withEmail2("000").withEmail3("000")
              .withHomePhone("000").withMobilePhone("000").withWorkPhone("000").withHomePhone2("000")
              .withPhoto(photo));
      app.contact().gotoHomePage();
    }
  }

  @Test //(enabled = false)
  public void testContactDeletion() {
    Contacts before = app.contact().all();
    ContactData deletedContact = before.iterator().next();
    app.contact().delete(deletedContact);
    assertThat(app.contact().count(), equalTo(before.size() - 1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before.withOut(deletedContact)));
    verifyContactListInUI();
    //app.logOut();
  }
}
