package org.ogzhnulucay.controller;

import org.ogzhnulucay.model.common.StructuredSession;
import org.ogzhnulucay.model.response.Schedule;
import org.ogzhnulucay.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/scheduler-service")
public class SchedulerController {

    private final SchedulerService schedulerService;

    @Autowired
    public SchedulerController(final SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @PostMapping("/schedule")
    public Schedule scheduleConference(@RequestBody(required = false) String sessionList) {
        if(sessionList == null || sessionList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please provide sessions in a list!", new IllegalArgumentException());
        }

        Stream<String> lines = sessionList.lines(); // Split the input string by newline

        List<StructuredSession> structuredSessionList = lines.distinct().map(it -> {
            String[] sessionSplit = it.split(" ");
            return new StructuredSession(it, sessionSplit[sessionSplit.length - 1]);
        }).toList();

        return schedulerService.schedule(structuredSessionList);
    }
}
