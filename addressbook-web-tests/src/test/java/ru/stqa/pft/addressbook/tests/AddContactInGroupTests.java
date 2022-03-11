package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class AddContactInGroupTests extends TestBase {

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

  @Test
  public void testAddContactInGroup() {
    app.contact().addInGroup();
    Contacts dbContacts = app.db().verifyAddInGroup();
    System.out.println("dbContacts" + dbContacts);
    Contacts uiContacts = app.contact().all();
    System.out.println("uiContacts" + uiContacts);
    assertThat(uiContacts, equalTo(dbContacts.stream() //Сравниваем только ID, имена и адрес
            .map((c) -> new ContactData().withId(c.getId()).withName(c.getName()).withLastName(c.getLastname())
            .withAddress(c.getAddress()))
            .collect(Collectors.toSet())));
  }
}
