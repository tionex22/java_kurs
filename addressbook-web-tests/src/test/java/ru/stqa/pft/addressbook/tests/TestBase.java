package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBase {

  Logger logger = LoggerFactory.getLogger(TestBase.class);

  protected static final ApplicationManager app
          = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME)); //-ea -Dbrowser=chrome

  @BeforeSuite(alwaysRun = true)
  public void setUp() throws IOException {
    app.init();
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() {
    app.stop();
  }

  @BeforeMethod
  public void logTestStart (Method m, Object[] p) {
    logger.info("Start test " + m.getName() + "with parameters " + Arrays.asList(p));
  }

  @AfterMethod(alwaysRun = true)
  public void logTestStop(Method m) {
    logger.info("Stop test " + m.getName());
  }

  public void verifyGroupListInUI() { //Проверка списков из UI (вкл/выкл)
    if (Boolean.getBoolean("verifyUI")) { //Включение в - Edit Configurations "VM Options = -ea -DverifyUI=true"
      Groups dbGroups = app.db().groups(); //данные из базы (.db().)
      Groups uiGroups = app.group().all(); //Данные из UI
      assertThat(uiGroups, equalTo(dbGroups.stream() //Сравниваем только ID и имена
              .map((g) -> new GroupData().withId(g.getId()).withName(g.getName()))
              .collect(Collectors.toSet())));
    }
  }

  public void verifyContactListInUI() { //Проверка списков из UI (вкл/выкл)
    if (Boolean.getBoolean("verifyUI")) { //Включение - в Edit Configurations "VM Options = -ea -DverifyUI=true"
      Contacts dbContacts = app.db().contacts(); //данные из базы (.db().)
      Contacts uiContacts = app.contact().all(); //Данные из UI
      assertThat(uiContacts, equalTo(dbContacts.stream() //Сравниваем только ID, имена и адрес
              .map((c) -> new ContactData().withId(c.getId()).withName(c.getName()).withLastName(c.getLastname())
              .withAddress(c.getAddress()))
              .collect(Collectors.toSet())));
    }
  }
}
