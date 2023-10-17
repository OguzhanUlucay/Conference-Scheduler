package org.ogzhnulucay.service;

import org.ogzhnulucay.model.common.StructuredSession;
import org.ogzhnulucay.model.response.Schedule;

import java.util.List;

public interface ScheduleCalculator {

    Schedule createSchedule(List<StructuredSession> sessionList);
}
