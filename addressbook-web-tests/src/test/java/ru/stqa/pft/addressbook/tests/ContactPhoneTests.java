package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneTests extends TestBase{

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.contact().all().size() == 0) {
      app.createGroupTest1();
      app.contact().create(new ContactData().withName("111").withLastName("111"));
      app.contact().gotoHomePage();
    }
  }

  @Test //(enabled = false)
  public void testContactPhones() {
    app.contact().gotoHomePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

    /*System.out.println("Address " + contact.getAddress());
    System.out.println("Address2 " + cleaned(contactInfoFromEditForm.getAddress()));

    System.out.println("Email2 " + contact.getEmail2());
    System.out.println("Email2.2 " + contactInfoFromEditForm.getEmail2());

    System.out.println("MobilePhone " + contact.getMobilePhone());
    System.out.println("MobilePhone2 " + cleaned(contactInfoFromEditForm.getMobilePhone()));*/

    assertThat(contact.getAddress(), equalTo((contactInfoFromEditForm.getAddress())));
    assertThat(contact.getEmail(), equalTo((contactInfoFromEditForm.getEmail())));
    assertThat(contact.getEmail2(), equalTo((contactInfoFromEditForm.getEmail2())));
    assertThat(contact.getEmail3(), equalTo((contactInfoFromEditForm.getEmail3())));
    assertThat(contact.getHomePhone(), equalTo(cleaned(contactInfoFromEditForm.getHomePhone())));
    assertThat(contact.getMobilePhone(), equalTo(cleaned(contactInfoFromEditForm.getMobilePhone())));
    assertThat(contact.getWorkPhone(), equalTo(cleaned(contactInfoFromEditForm.getWorkPhone())));
  }

  public String cleaned(String phone) {
    return phone.replaceAll("\\s", "").replaceAll("[-()]", ""); //RegEx
  }
}
