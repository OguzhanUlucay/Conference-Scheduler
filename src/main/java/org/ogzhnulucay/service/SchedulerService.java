package org.ogzhnulucay.service;

import org.ogzhnulucay.model.common.StructuredSession;
import org.ogzhnulucay.model.response.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchedulerService {

    private final ScheduleCalculator scheduleCalculator;

    @Autowired
    public SchedulerService(final ScheduleCalculator scheduleCalculator) {
        this.scheduleCalculator = scheduleCalculator;
    }

    public Schedule schedule(List<StructuredSession> sessionList) {

        sessionList.forEach(it -> {
            if (it.getLength().equals("lightning")) {
                it.setLengthMinutes(5);
            } else {
                it.setLengthMinutes(Integer.parseInt(it.getLength().substring(0, it.getLength().indexOf("min"))));
            }
        });

        return scheduleCalculator.createSchedule(sessionList);
    }
}
