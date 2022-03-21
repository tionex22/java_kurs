package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.appmanager.HttpSession;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.Users;
import ru.stqa.pft.mantis.model.UserData;
import java.io.IOException;
import java.util.List;
import static org.testng.AssertJUnit.assertTrue;

public class ChangeUserPasswordTests extends TestBase{

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws IOException {
        app.init();
    }

    @BeforeMethod
    public void startMailServer(){
        app.mail().start();
    }

    @Test
    public void testChangePassword() throws Exception {
        HttpSession session = app.newSession();
        app.admin().login("administrator", "root");
        app.admin().manageUsers();
        Users users = app.db().mantisUser();
        UserData user = users.iterator().next();
        app.admin().reset(user);
        List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
        String conformationLink = app.mail().findConformationLink(mailMessages, user.getEmail());
        String password = "newPassword";
        app.admin().finish(conformationLink, user, password);

        //assertTrue(session.login(user.getUsername(), password)); //Тут
        assertTrue(session.isLoggedInAs(user.getUsername()));
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer(){
        app.mail().stop();
    }
}
