package by.itechart.libmngmt.util.emailScheduler;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Provides method for managing scheduler.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SchedulerTrigger {
    private static final Logger LOGGER = LogManager.getLogger(EmailSender.class.getName());
    private static volatile SchedulerTrigger instance;

    public static synchronized SchedulerTrigger getInstance() {
        SchedulerTrigger localInstance = instance;
        if (localInstance == null) {
            synchronized (MailTemplate.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new SchedulerTrigger();
                }
            }
        }
        return localInstance;
    }

    /**
     * Creates trigger and job and after creates scheduler using this
     * objects and starts this scheduler.
     */
    public static void startScheduler() {
        JobDetail job = JobBuilder.newJob(EmailJob.class).build();
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 00 10 * * ?"))
                .build();
        try {
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            LOGGER.debug("Scheduler error.", e);
        }
    }
}
