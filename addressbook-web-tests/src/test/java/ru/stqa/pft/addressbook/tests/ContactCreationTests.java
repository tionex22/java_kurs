package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContacts() throws IOException {
    List<Object[]> list = new ArrayList<Object[]>();
    File photo = new File("src/test/resources/stru.jpg"); //Добавляем аттач (относительный путь)
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.csv")));
    String line = reader.readLine();
    while (line != null) {
      String[] split = line.split(";");
      list.add(new Object[] {new ContactData().withName(split[0]).withLastName(split[1]).withAddress(split[2])
              .withEmail(split[3]).withEmail2(split[4]).withEmail3(split[5])
              .withHomePhone(split[6]).withMobilePhone(split[7]).withWorkPhone(split[8]).withHomePhone2(split[9])
              .withPhoto(photo)});
      line = reader.readLine();
    }

    /*list.add(new Object[] {new ContactData().withName("name 1").withLastName("lastname 1").withAddress("address 1")
            .withEmail("111.222@").withEmail2("11-11.222@").withEmail3("22-22@af")
            .withHomePhone("+7 (222)").withMobilePhone("22-22").withWorkPhone("33 33 33").withHomePhone2("+7 (333)")
            .withPhoto(photo)});
    list.add(new Object[] {new ContactData().withName("name 2").withLastName("lastname 2").withAddress("address 2")
            .withEmail("111.222@").withEmail2("11-11.222@").withEmail3("22-22@af")
            .withHomePhone("+7 (222)").withMobilePhone("22-22").withWorkPhone("33 33 33").withHomePhone2("+7 (333)")
            .withPhoto(photo)});
    list.add(new Object[] {new ContactData().withName("name 3").withLastName("lastname 3").withAddress("address 3")
            .withEmail("111.222@").withEmail2("11-11.222@").withEmail3("22-22@af")
            .withHomePhone("+7 (222)").withMobilePhone("22-22").withWorkPhone("33 33 33").withHomePhone2("+7 (333)")
            .withPhoto(photo)});*/
    return list.iterator();
  }

  @Test (dataProvider = "validContacts")
  public void testContactCreation(ContactData contact) {
      app.createGroupTest1();
      app.contact().gotoHomePage();
      Contacts before = app.contact().all();
      app.contact().create(contact); //Создание контакта
      app.contact().gotoHomePage();
      assertThat(app.contact().count(), equalTo(before.size() + 1));
      Contacts after = app.contact().all();
      assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
      //app.logOut();
  }

  @Test (enabled = false)
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
