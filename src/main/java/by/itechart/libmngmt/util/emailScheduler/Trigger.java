package by.itechart.libmngmt.util.emailScheduler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class Trigger {
    final static Logger LOGGER = LogManager.getLogger(EmailSender.class.getName());

    public static void startScheduler() {
        JobDetail job = JobBuilder.newJob(EmailJob.class).build();
        org.quartz.Trigger trigger = TriggerBuilder
                .newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 10 * * ?"))
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
