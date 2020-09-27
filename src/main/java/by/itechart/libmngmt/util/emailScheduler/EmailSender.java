package by.itechart.libmngmt.util.emailScheduler;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EmailSender {
    private final static Logger LOGGER = LogManager.getLogger(EmailSender.class.getName());
    private final static String LIBRARY_EMAIL = "librarystlab@gmail.com";
    private final static String EMAIL_SUBJECT = "Library notification.";
    private final static String AUTHENTICATION_USERNAME = "librarystlab@gmail.com";
    private final static String AUTHENTICATION_PASSWORD = "123123nnn";
    private final static String HOST_NAME = "smtp.googlemail.com";
    private final static int PORT = 465;
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
