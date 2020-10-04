package by.itechart.libmngmt.util.emailScheduler;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

/**
 * Provides method for creating email text using templates.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MailTemplate {
    private STGroup stGroup = new STGroupFile("templates.stg");
    private static volatile MailTemplate instance;

    public static synchronized MailTemplate getInstance() {
        MailTemplate localInstance = instance;
        if (localInstance == null) {
            synchronized (MailTemplate.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new MailTemplate();
                }
            }
        }
        return localInstance;
    }

    /**
     * Creates message using template and incoming parameters.
     *
     * @param bookTitle  book title
     * @param readerName reader name
     * @return message
     */
    public String getMessageSevenDaysBefore(String bookTitle, String readerName) {
        ST message = stGroup.getInstanceOf("sevenDaysBeforeTemplate");
        return createMessage(message, bookTitle, readerName);
    }

    /**
     * Creates message using template and incoming parameters.
     *
     * @param bookTitle  book title
     * @param readerName reader name
     * @return message
     */
    public String getMessageOneDayBefore(String bookTitle, String readerName) {
        ST message = stGroup.getInstanceOf("oneDayBeforeTemplate");
        return createMessage(message, bookTitle, readerName);
    }

    /**
     * Creates message using template and incoming parameters.
     *
     * @param bookTitle  book title
     * @param readerName reader name
     * @return message
     */
    public String getMessageOneDayAfter(String bookTitle, String readerName) {
        ST message = stGroup.getInstanceOf("oneDayAfterTemplate");
        return createMessage(message, bookTitle, readerName);
    }

    private String createMessage(ST message, String bookTitle, String readerName) {
        message.add("readerName", readerName);
        message.add("bookTitle", bookTitle);
        return message.render();
    }
}
