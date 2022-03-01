package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  //public File photo;

  @DataProvider
  public Iterator<Object[]> validContacts() {
    List<Object[]> list = new ArrayList<Object[]>();
    File photo = new File("src/test/resources/stru.jpg"); //Добавляем аттач (относительный путь)
    list.add(new Object[] {new ContactData().withName("name 1").withLastName("lastname 1").withAddress("address 1").withPhoto(photo)});
    list.add(new Object[] {new ContactData().withName("name 2").withLastName("lastname 2").withAddress("address 2").withPhoto(photo)});
    list.add(new Object[] {new ContactData().withName("name 3").withLastName("lastname 3").withAddress("address 3").withPhoto(photo)});
    return list.iterator();
  }

  @Test (dataProvider = "validContacts")
  public void testContactCreation(ContactData contact) {
      app.createGroupTest1();
      app.contact().gotoHomePage();
      Contacts before = app.contact().all();
      //File photo = new File("src/test/resources/stru.jpg"); //Добавляем аттач (относительный путь)
      //ContactData contact = new ContactData().withName(name).withLastName(lastname).withAddress(address).withPhoto(photo);
      //ContactData contact = new ContactData().withName("111").withLastName("111").withAddress(null).withMobile(null).withEmail(null);
      app.contact().create(contact); //Создание контакта
      app.contact().gotoHomePage();
      assertThat(app.contact().count(), equalTo(before.size() + 1));
      Contacts after = app.contact().all();
      assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
      //app.logOut();
  }

  @Test //(enabled = false)
  public void testBadContactCreation() {
    app.createGroupTest1();
    app.contact().gotoHomePage();
    Contacts before = app.contact().all();
    ContactData contact = new ContactData().withName("111'").withLastName("111'");
    //ContactData contact = new ContactData().withName("111").withLastName("111").withAddress(null).withMobile(null).withEmail(null);
    app.contact().create(contact);
    app.contact().gotoHomePage();
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before));
    //app.logOut();
  }
}
