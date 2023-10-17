package org.ogzhnulucay.service;

import org.ogzhnulucay.model.common.StructuredSession;
import org.ogzhnulucay.model.response.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SchedulerService {

    private static final String LIGHTNING = "lightning";
    private static final String MIN = "min";

    // This instance should have been init by a factory class
    // I was gonna add if had more time. But of course this is only
    // Usefull if there would other types other than ScheduleCalculatorBasic
    private final ScheduleCalculator scheduleCalculator;

    @Autowired
    public SchedulerService(final ScheduleCalculator scheduleCalculator) {
        this.scheduleCalculator = scheduleCalculator;
    }

    public Schedule schedule(List<StructuredSession> sessionList) {
        sessionList.forEach(it -> {
                    validateIfSessionNameIsMissing(it);
                    validateIfSessionLengthIsNotEmpty(it);
                    validateIfSessionsHasProperLength(it);
                }
        );

        sessionList.forEach(it -> {
            if (it.getLength().equals(LIGHTNING)) {
                it.setLengthMinutes(5);
            } else {
                it.setLengthMinutes(Integer.parseInt(it.getLength().substring(0, it.getLength().toLowerCase().indexOf(MIN))));
            }
        });

        return scheduleCalculator.createSchedule(sessionList);
    }

    private void validateIfSessionsHasProperLength(StructuredSession it) {
        if (!it.getLength().equalsIgnoreCase(LIGHTNING)) {
            if (!it.getLength().toLowerCase().endsWith(MIN)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A session length format is not proper, please use lighthning or supply min posix after int!");
            }
            final String lenghtWithOnlyIntPart = it.getLength().substring(0, it.getLength().toLowerCase().indexOf(MIN));
            int lenghtInMin;
            try {
                lenghtInMin = Integer.parseInt(lenghtWithOnlyIntPart);
            } catch (NumberFormatException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A session length is not Integer, please fix!");
            }
            if (lenghtInMin >= 480) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A session can't be longer than 8 hours!");
            }
        }
    }

    private void validateIfSessionLengthIsNotEmpty(StructuredSession it) {
        if (it.getLength() == null || it.getLength().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A session's name is missing, please provide!");
        }
    }

    private void validateIfSessionNameIsMissing(StructuredSession it) {
        if (it.getFullDescription() != null && !it.getFullDescription().isBlank()) {
            String sessionName = it.getFullDescription().substring(0, it.getFullDescription().indexOf(it.getLength()));
            if (sessionName == null || sessionName.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A session's name is missing, please provide!");
            }
        }
    }
}
