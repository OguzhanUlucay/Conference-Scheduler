package org.ogzhnulucay.service;

import org.ogzhnulucay.model.common.StructuredSession;
import org.ogzhnulucay.model.response.Schedule;
import org.ogzhnulucay.model.response.Track;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduleCalculator {

    private static final String SCHEDULED_SESSION_FORMAT = "%s %s";
    public Schedule createSchedule(List<StructuredSession> sessionList) {
        // Prepare schedule
        LocalTime scheduleStartTime = LocalTime.of(9, 0);  // 09:00 AM
        LocalTime nextScheduleTime = scheduleStartTime;  // 09:00 AM
        //LocalTime networkingStartTime = LocalTime.of(16, 0);  // 09:00 AM
        LocalTime scheduleFinishTime = LocalTime.of(17, 0);  // 09:00 AM
        LocalTime lunchStartTime = LocalTime.of(12, 0);
        LocalTime lunchFinishTime = LocalTime.of(13, 0);

        // Individual track preparation
        int trackCount = 1;
        List<Track> tracks = new ArrayList<>();
        List<String> trackSchedule = new ArrayList<>();
        int sessionCount = 1;

        for (StructuredSession session : sessionList) {
            LocalTime currentScheduleTime = nextScheduleTime;

            // Handle session that conflict with lunch time
            if (doesTimeConflictsWithLunchTime(currentScheduleTime, lunchStartTime, lunchFinishTime)) {
                trackSchedule.add(String.format(SCHEDULED_SESSION_FORMAT, lunchStartTime, "lunch"));
                currentScheduleTime = lunchFinishTime;
            }

            if (doesTimeConflictsWithLunchTime(currentScheduleTime.plusMinutes(session.getLengthMinutes()), lunchStartTime, lunchFinishTime)) {
                trackSchedule.add(String.format(SCHEDULED_SESSION_FORMAT, lunchStartTime, "lunch"));
                currentScheduleTime = lunchFinishTime;
            }

            session.setScheduleTime(currentScheduleTime);

            trackSchedule.add(String.format(SCHEDULED_SESSION_FORMAT, session.getScheduleTime(), session.getFullDescription()));

            // Set next schedule time
            nextScheduleTime = currentScheduleTime.plusMinutes(session.getLengthMinutes());

            // if day is finished
            if (nextScheduleTime.isAfter(scheduleFinishTime)) {
                nextScheduleTime = scheduleStartTime;
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
