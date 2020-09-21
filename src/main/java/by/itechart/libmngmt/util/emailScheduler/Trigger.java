package by.itechart.libmngmt.util.emailScheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class Trigger {
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
            e.printStackTrace();
        }
    }
}
