package by.itechart.libmngmt.util.emailScheduler;

import org.stringtemplate.v4.ST;

public class LetterTemplater {
    private static LetterTemplater instance;

    public static LetterTemplater getInstance() {
        if(instance == null){
            instance = new LetterTemplater();
        }
        return instance;
    }

    public String getMessageSevenDaysBefore(String bookTitle, String readerName) {
        ST message = new ST("Hello, <readerName>! You borrowed a book from the library(<bookTitle>)." +
                " Return period expires in 7 days. You can contact library by the phone +375(17)1231231 or by email: library@library.com");
        message.add("readerName", readerName);
        message.add("bookTitle", bookTitle);
        return message.render();
    }

    public String getMessageOneDayBefore(String bookTitle, String readerName) {
        ST message = new ST("Hello, <readerName>! You borrowed a book from the library(<bookTitle>)." +
                " Return period expires in 1 day. You can contact library by the phone +375(17)1231231 or by email: library@library.com");
        message.add("readerName", readerName);
        message.add("bookTitle", bookTitle);
        return message.render();
    }

    public String getMessageOneDayAfter(String bookTitle, String readerName) {
        ST message = new ST("Hello, <readerName>! You borrowed a book from the library(<bookTitle>)." +
                " Return period expired. Please, contact library by the phone +375(17)1231231 or by email: library@library.com.");
        message.add("readerName", readerName);
        message.add("bookTitle", bookTitle);
        return message.render();
    }
}
