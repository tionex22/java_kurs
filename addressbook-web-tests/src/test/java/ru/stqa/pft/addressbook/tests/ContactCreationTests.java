package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import org.openqa.selenium.json.TypeToken;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContactsFromXml() throws IOException {
    File photo = new File("src/test/resources/stru.jpg"); //Добавляем аттач (относительный путь)
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.xml")))) {
      String xml = "";
      String line = reader.readLine();
      while (line != null) {
        xml += line;
      /*String[] split = line.split(";");
      list.add(new Object[] {new ContactData().withName(split[0]).withLastName(split[1]).withAddress(split[2])
              .withEmail(split[3]).withEmail2(split[4]).withEmail3(split[5])
              .withHomePhone(split[6]).withMobilePhone(split[7]).withWorkPhone(split[8]).withHomePhone2(split[9])
              .withPhoto(photo)});*/
        line = reader.readLine();
      }
      XStream xstream = new XStream();
      xstream.processAnnotations(ContactData.class);
      List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);
      return contacts.stream().map((c) -> new Object[] {c}).collect(Collectors.toList()).iterator();
    }
  }

  @DataProvider
  public Iterator<Object[]> validContactsFromJson() throws IOException {
    File photo = new File("src/test/resources/stru.jpg"); //Добавляем аттач (относительный путь)
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))) {
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
      /*String[] split = line.split(";");
      list.add(new Object[] {new ContactData().withName(split[0]).withLastName(split[1]).withAddress(split[2])
              .withEmail(split[3]).withEmail2(split[4]).withEmail3(split[5])
              .withHomePhone(split[6]).withMobilePhone(split[7]).withWorkPhone(split[8]).withHomePhone2(split[9])
              .withPhoto(photo)});*/
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>(){}.getType());
      return contacts.stream().map((c) -> new Object[] {c}).collect(Collectors.toList()).iterator();
    }
  }

  @Test (dataProvider = "validContactsFromJson") //Какой DataProvider используется
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
