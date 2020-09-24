package by.itechart.libmngmt.util.emailScheduler;

import org.stringtemplate.v4.ST;

public class MailTemplate {
    final private String SEVEN_DAYS_BEFORE_MESSAGE = "Hello, <readerName>! You borrowed a book from the " +
            "library(<bookTitle>). Return period expires in 7 days. You can contact library by the phone" +
            " +375(17)1231231 or by email: library@library.com";
    final private String ONE_DAY_BEFORE_MESSAGE = "Hello, <readerName>! You borrowed a book from the" +
            " library(<bookTitle>). Return period expires in 1 day. You can contact library by the phone" +
            " +375(17)1231231 or by email: library@library.com";
    final private String ONE_DAY_AFTER_MESSAGE = "Hello, <readerName>! You borrowed a book from the" +
            " library(<bookTitle>). Return period expired. Please, contact library by the phone" +
            " +375(17)1231231 or by email: library@library.com.";

    private static MailTemplate instance;
    public static synchronized MailTemplate getInstance() {
        if(instance == null){
            instance = new MailTemplate();
        }
        return instance;
    }

    public String getMessageSevenDaysBefore(String bookTitle, String readerName) {
        ST message = new ST(SEVEN_DAYS_BEFORE_MESSAGE);
        message.add("readerName", readerName);
        message.add("bookTitle", bookTitle);
        return message.render();
    }

    public String getMessageOneDayBefore(String bookTitle, String readerName) {
        ST message = new ST(ONE_DAY_BEFORE_MESSAGE);
        message.add("readerName", readerName);
        message.add("bookTitle", bookTitle);
        return message.render();
    }

    public String getMessageOneDayAfter(String bookTitle, String readerName) {
        ST message = new ST(ONE_DAY_AFTER_MESSAGE);
        message.add("readerName", readerName);
        message.add("bookTitle", bookTitle);
        return message.render();
    }
}
