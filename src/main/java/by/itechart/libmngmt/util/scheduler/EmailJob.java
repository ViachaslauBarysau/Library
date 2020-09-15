package by.itechart.libmngmt.util.scheduler;

import by.itechart.libmngmt.entity.ReaderCardEntity;
import by.itechart.libmngmt.service.ReaderCardService;
import by.itechart.libmngmt.service.impl.ReaderCardServiceImpl;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

public class EmailJob implements Job {

    public static final int SEVEN_DAYS_BEFORE = 7;
    public static final int ONE_DAY_BEFORE = 1;
    public static final int ONE_DAY_AFTER = -1;

    private final ReaderCardService readerCardService = ReaderCardServiceImpl.getInstance();
    private final LetterTemplater letterTemplater = LetterTemplater.getInstance();
    private final EmailSender emailSender = EmailSender.getInstance();

    public void execute(JobExecutionContext context)
            throws JobExecutionException {

        List<ReaderCardEntity> sevenDaysBeforeReaderCards = readerCardService.getExpiringReaderCards(SEVEN_DAYS_BEFORE);
        if (sevenDaysBeforeReaderCards != null) {        for (ReaderCardEntity readerCard : sevenDaysBeforeReaderCards
        ) {
            emailSender.sendEmail(readerCard.getReaderEmail(),
                    letterTemplater.getMessageSevenDaysBefore(readerCard.getBookTitle(), readerCard.getReaderName()));
        }}


        List<ReaderCardEntity> oneDayBeforeReaderCards = readerCardService.getExpiringReaderCards(ONE_DAY_BEFORE);
        if (oneDayBeforeReaderCards != null) {
            for (ReaderCardEntity readerCard : oneDayBeforeReaderCards
            ) {
                emailSender.sendEmail(readerCard.getReaderEmail(),
                        letterTemplater.getMessageOneDayBefore(readerCard.getBookTitle(), readerCard.getReaderName()));
            }
        }



        List<ReaderCardEntity> oneDayAfterReaderCards = readerCardService.getExpiringReaderCards(ONE_DAY_AFTER);
        if (oneDayAfterReaderCards != null) {
            for (ReaderCardEntity readerCard : oneDayAfterReaderCards
            ) {
                emailSender.sendEmail(readerCard.getReaderEmail(),
                        letterTemplater.getMessageOneDayAfter(readerCard.getBookTitle(), readerCard.getReaderName()));
            }
        }

    }
}
