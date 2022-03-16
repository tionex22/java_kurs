package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;

import static org.testng.AssertJUnit.assertTrue;

public class AddContactInGroupTests extends TestBase {

  File photo = new File("src/test/resources/stru.jpg");

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0 | app.db().groups().size() == 0 | app.db().verifyContactNotInGroup().size() != 0) { //данные из базы (.db().)
      app.createGroupTest1();
      ContactData contact = new ContactData();
      app.contact().createWithoutGroup(contact.withName("000").withLastName("000").withAddress("000")
              .withEmail("000").withEmail2("000").withEmail3("000")
              .withHomePhone("000").withMobilePhone("000").withWorkPhone("000").withHomePhone2("000")
              .withPhoto(photo));
      app.contact().gotoHomePage();
    }
  }

  @Test
  public void testAddContactInGroup() {
    ContactData before = app.db().contactWithoutGroup();
    Groups groups = app.db().groups();
    GroupData group = groups.iterator().next();
    app.contact().gotoHomePage();
    app.contact().selectContactWithoutGroup(before);
    app.contact().selectGroup(group);
    app.contact().addContactToGroup();
    ContactData after = app.db().contactById(before.getId());
    assertTrue(after.getGroups().contains(group));
    verifyContactListInUI();
  }
}
