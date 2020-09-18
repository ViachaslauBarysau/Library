package by.itechart.libmngmt.util.emailScheduler;

import by.itechart.libmngmt.dto.ReaderCardDto;
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

        List<ReaderCardDto> sevenDaysBeforeReaderCards = readerCardService.getExpiringReaderCards(SEVEN_DAYS_BEFORE);
        if (sevenDaysBeforeReaderCards != null) {
            for (ReaderCardDto readerCard : sevenDaysBeforeReaderCards
        ) {
            emailSender.sendEmail(readerCard.getReaderEmail(),
                    letterTemplater.getMessageSevenDaysBefore(readerCard.getBookTitle(), readerCard.getReaderName()));
        }}


        List<ReaderCardDto> oneDayBeforeReaderCards = readerCardService.getExpiringReaderCards(ONE_DAY_BEFORE);
        if (oneDayBeforeReaderCards != null) {
            for (ReaderCardDto readerCard : oneDayBeforeReaderCards
            ) {
                emailSender.sendEmail(readerCard.getReaderEmail(),
                        letterTemplater.getMessageOneDayBefore(readerCard.getBookTitle(), readerCard.getReaderName()));
            }
        }



        List<ReaderCardDto> oneDayAfterReaderCards = readerCardService.getExpiringReaderCards(ONE_DAY_AFTER);
        if (oneDayAfterReaderCards != null) {
            for (ReaderCardDto readerCard : oneDayAfterReaderCards
            ) {
                emailSender.sendEmail(readerCard.getReaderEmail(),
                        letterTemplater.getMessageOneDayAfter(readerCard.getBookTitle(), readerCard.getReaderName()));
            }
        }

    }
}