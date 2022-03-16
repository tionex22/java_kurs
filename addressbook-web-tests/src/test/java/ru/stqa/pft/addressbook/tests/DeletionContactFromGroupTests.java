package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;
import java.io.IOException;

import static org.testng.AssertJUnit.assertTrue;

public class DeletionContactFromGroupTests extends TestBase {

  File photo = new File("src/test/resources/stru.jpg");

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().verifyContactInGroup().size() == 0) { //данные из базы (.db().)
      app.createGroupTest1();
      app.contact().create(new ContactData().withName("000").withLastName("000").withAddress("000")
              .withEmail("000").withEmail2("000").withEmail3("000")
              .withHomePhone("000").withMobilePhone("000").withWorkPhone("000").withHomePhone2("000")
              .withPhoto(photo));
      app.contact().gotoHomePage();
    }
  }

  @Test
  public void testDeletionContactFromGroup() throws Exception {
    ContactData before = app.db().contactWithGroup();
    GroupData group = before.getGroups().iterator().next();
    app.contact().gotoHomePage();
    app.contact().getGroupData(group);
    app.contact().selectContact(before);
    app.contact().removeContactFromGroup();
    ContactData after = app.db().contactById(before.getId());
    System.out.println("11111 " + after);
    System.out.println("222222 " + group);
    assertTrue(after.getGroups().contains(group));
    verifyContactListInUI();
  }
}