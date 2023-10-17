package org.ogzhnulucay.service;

import org.ogzhnulucay.model.common.StructuredSession;
import org.ogzhnulucay.model.response.Schedule;
import org.ogzhnulucay.model.response.Track;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduleCalculatorBasic implements ScheduleCalculator {

    private static final LocalTime SCHEDULE_START_TIME = LocalTime.of(9, 0);  // 09:00 AM
    private static final LocalTime SCHEDULE_FINISH_TIME = LocalTime.of(17, 0);  // 09:00 AM
    private static final LocalTime LUNCH_START_TIME = LocalTime.of(12, 0);
    private static final LocalTime LUNCH_FINISH_TIME = LocalTime.of(13, 0);

    private static final String SCHEDULED_SESSION_FORMAT = "%s %s";


    // Sorry for not completing this logic properly
    // The logic basically(it's dummy) adds the sessions as they come.
    // If there is session which conflicts with lunch, it put's it after lunch
    // It's missing the networking and a.m p.m requirements.
    // Also sorry for not extracting this long method to
    // smaller parts.
    public Schedule createSchedule(List<StructuredSession> sessionList) {
        // Prepare schedule
        LocalTime nextScheduleTime = SCHEDULE_START_TIME;  // 09:00 AM

        // Individual track preparation
        int trackCount = 1;
        List<Track> tracks = new ArrayList<>();
        List<String> trackSchedule = new ArrayList<>();
        int sessionCount = 1;

        for (StructuredSession session : sessionList) {
            LocalTime currentScheduleTime = nextScheduleTime;

            // Handle session that conflict with lunch time
            if (doesTimeConflictsWithLunchTime(currentScheduleTime, LUNCH_START_TIME, LUNCH_FINISH_TIME)) {
                trackSchedule.add(String.format(SCHEDULED_SESSION_FORMAT, LUNCH_START_TIME, "lunch"));
                currentScheduleTime = LUNCH_FINISH_TIME;
            }

            if (doesTimeConflictsWithLunchTime(currentScheduleTime.plusMinutes(session.getLengthMinutes()), LUNCH_START_TIME, LUNCH_FINISH_TIME)) {
                trackSchedule.add(String.format(SCHEDULED_SESSION_FORMAT, LUNCH_START_TIME, "lunch"));
                currentScheduleTime = LUNCH_FINISH_TIME;
            }

            session.setScheduleTime(currentScheduleTime);

            trackSchedule.add(String.format(SCHEDULED_SESSION_FORMAT, session.getScheduleTime(), session.getFullDescription()));

            // Set next schedule time
            nextScheduleTime = currentScheduleTime.plusMinutes(session.getLengthMinutes());

            // if day is finished
            if (nextScheduleTime.isAfter(SCHEDULE_FINISH_TIME)) {
                nextScheduleTime = SCHEDULE_START_TIME;
                tracks.add(new Track("track" + trackCount, trackSchedule));
                trackSchedule = new ArrayList<>();
                trackCount += 1;
            } else if (sessionList.size() == sessionCount) {
                // If all sessions are finished
                tracks.add(new Track("track" + trackCount, trackSchedule));
            }
            sessionCount++;
        }

        final Schedule schedule = new Schedule();
        schedule.setTracks(tracks);
        return schedule;
    }

    private boolean doesTimeConflictsWithLunchTime(LocalTime currentScheduleTime, LocalTime lunchStartTime, LocalTime lunchFinishTime) {
        return currentScheduleTime.equals(lunchStartTime) || (currentScheduleTime.isAfter(lunchStartTime) && currentScheduleTime.isBefore(lunchFinishTime));
    }
}
