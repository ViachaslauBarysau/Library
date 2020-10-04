package by.itechart.libmngmt.util.emailScheduler;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Provides method for sending emails.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailSender {
    private static final Logger LOGGER = LogManager.getLogger(EmailSender.class.getName());
    private static final String LIBRARY_EMAIL = "librarystlab@gmail.com";
    private static final String EMAIL_SUBJECT = "Library notification.";
    private static final String AUTHENTICATION_USERNAME = "librarystlab@gmail.com";
    private static final String AUTHENTICATION_PASSWORD = "123123nnn";
    private static final String HOST_NAME = "smtp.googlemail.com";
    private static final int PORT = 465;
    private static EmailSender instance;

    public static synchronized EmailSender getInstance() {
        EmailSender localInstance = instance;
        if (localInstance == null) {
            synchronized (EmailSender.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new EmailSender();
                }
            }
        }
        return localInstance;
    }

    /**
     * Sends email using properties and incoming parameters.
     *
     * @param readerEmail reader's email
     * @param message     message
     */
    public void sendEmail(String readerEmail, String message) {
        try {
            Email email = new SimpleEmail();
            email.setHostName(HOST_NAME);
            email.setSmtpPort(PORT);
            email.setAuthenticator(new DefaultAuthenticator(AUTHENTICATION_USERNAME, AUTHENTICATION_PASSWORD));
            email.setSSLOnConnect(true);
            email.setFrom(LIBRARY_EMAIL);
            email.setSubject(EMAIL_SUBJECT);
            email.setMsg(message);
            email.addTo(readerEmail);
            email.send();
        } catch (EmailException e) {
            LOGGER.debug("Email sending error.", e);
        }
    }
}
