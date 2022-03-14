package ru.stqa.pft.addressbook.tests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.sql.*;
import java.util.List;

public class HbConnectionTest {

  private SessionFactory sessionFactory;

  @BeforeClass
  protected void setUp() throws Exception {
    // A SessionFactory is set up once for an application!
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure() // configures settings from hibernate.cfg.xml
            .build();
    try {
      sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
    }
    catch (Exception e) {
      e.printStackTrace();
      // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
      // so destroy it manually.
      StandardServiceRegistryBuilder.destroy( registry );
    }
  }

  @Test (enabled = false)
  public void testHbConnection() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<GroupData> result = session.createQuery("from GroupData").list();
    session.getTransaction().commit();
    session.close();
    for (GroupData group : result) {
      System.out.println(group);
      System.out.println("Contacts " + group.getContacts());
    }
  }

  @Test //(enabled = false)
  public void testContactHbConnection() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<ContactData> result = session.createQuery("from ContactData join address_in_groups where group_id = 473").list();
    session.getTransaction().commit();
    session.close();
    for (ContactData contact : result) {
      System.out.println(contact);
      System.out.println(contact.getGroups());
    }
  }

  @Test
  public void verifyAddInGroup() {
    Connection conn = null;
    try {
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressbook?user=root&password=");
      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery("select addressbook.id, firstname, lastname, address, home, mobile" +
              ", work, phone2, email, email2, email3 from addressbook " +
              "join address_in_groups ON addressbook.id=address_in_groups.id");
      Contacts contacts = new Contacts();
      while (rs.next()) {
        contacts.add(new ContactData().withId(rs.getInt("id")).withName(rs.getString("firstname")).withLastName(rs.getString("lastname"))
                .withAddress(rs.getString("address")).withHomePhone(rs.getString("home")).withMobilePhone(rs.getString("mobile"))
                .withWorkPhone(rs.getString("work")).withHomePhone2(rs.getString("phone2"))
                .withEmail(rs.getString("email")).withEmail2(rs.getString("email2")).withEmail3(rs.getString("email3")));
      }
      rs.close();
      st.close();
      conn.close();

      System.out.println(contacts);


      // Do something with the Connection

    } catch (SQLException ex) {
      // handle any errors
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }
  }
}
