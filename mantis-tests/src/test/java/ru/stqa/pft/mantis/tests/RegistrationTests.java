package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import java.util.List;
import static org.testng.Assert.assertTrue;
import javax.mail.MessagingException;

public class RegistrationTests extends TestBase {

    //@BeforeMethod
    public void startMailServer(){
        app.mail().start();
    }

    @Test (enabled = false)
        public void testRegistration() throws Exception, MessagingException {
            long now = System.currentTimeMillis();
            String user = String.format("user%s", now);
            String email = String.format("user%s@localhost", now);
            String password = "password";
            app.registration().start(user, email);
            List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
            String confirmationLink = app.session().findConfirmationLink(mailMessages, email);
            app.registration().finish(confirmationLink, password);
            assertTrue(app.newSession().login(user, password));
        }

    @Test
    public void testRegistrationExtraMail() throws Exception {
        long now = System.currentTimeMillis();
        String user = String.format("user%s", now);
        String email = "user2@localhost.localdomain";
        String password = "password";
        app.james().createUser(user, password);
        app.registration().start(user, email);
        List<MailMessage> mailMessages = app.james().waitForMail(user, password, 60000);
        String confirmationLink = app.session().findConfirmationLink(mailMessages, email);
        app.registration().finish(confirmationLink, password);
        assertTrue(app.newSession().login(user, password));
    }

        //@AfterMethod(alwaysRun = true)
        public void stopMailServer(){
            app.mail().stop();
        }
    }