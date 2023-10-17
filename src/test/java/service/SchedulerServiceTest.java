package service;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ogzhnulucay.model.common.StructuredSession;
import org.ogzhnulucay.service.ScheduleCalculatorBasic;
import org.ogzhnulucay.service.SchedulerService;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
class SchedulerServiceTest {

    @InjectMocks
    private SchedulerService schedulerService;
    @Mock
    private ScheduleCalculatorBasic scheduleCalculator;

    @Test
    void givenCorrectSessions_whenSchedule_thenSuccessfullyConvertLengthStringToLengthInt() {
        // Given
        List<StructuredSession> sessions = new ArrayList<>();
        sessions.add(new StructuredSession("Architecting Your Codebase 60MIN", "60MIN"));
        sessions.add(new StructuredSession("Cloud Native Java lightning", "lightning"));
        ArgumentCaptor<ArrayList<StructuredSession>> argument = ArgumentCaptor.forClass(ArrayList.class);

        // When
        schedulerService.schedule(sessions);

        // Then
        verify(scheduleCalculator).createSchedule(argument.capture());
        List<StructuredSession> sessionList = argument.getValue();
        assertNotNull(sessionList);
        assertEquals(2, sessionList.size());
        assertEquals(60, sessionList.get(0).getLengthMinutes());
        assertEquals(5, sessionList.get(1).getLengthMinutes());
    }

    @Test
    void givenBlankSessionName_whenSchedule_thenReturnError() {
        // Given
        List<StructuredSession> sessions = new ArrayList<>();
        sessions.add(new StructuredSession("Architecting Your Codebase 60min", "60min"));
        sessions.add(new StructuredSession("lightning", "lightning"));

        // When
        final Executable executable = () -> schedulerService.schedule(sessions);

        // Then
        assertThrows(ResponseStatusException.class,executable);
    }

    @Test
    void givenNoneSessionName_whenSchedule_thenReturnError() {
        // Given
        List<StructuredSession> sessions = new ArrayList<>();
        sessions.add(new StructuredSession("Architecting Your Codebase 60min", "60min"));
        sessions.add(new StructuredSession(null, null));

        // When
        final Executable executable = () -> schedulerService.schedule(sessions);

        // Then
        assertThrows(ResponseStatusException.class, executable);
    }

    @Test
    void givenIncorrectlightning_whenSchedule_thenReturnError() {
        // Given
        List<StructuredSession> sessions = new ArrayList<>();
        sessions.add(new StructuredSession("Architecting Your Codebase 60min", "60min"));
        sessions.add(new StructuredSession("Cloud Native Java ligning", "ligning"));

        // When
        final Executable executable = () -> schedulerService.schedule(sessions);

        // Then
        assertThrows(ResponseStatusException.class,executable);
    }

    @Test
    void givenIncorrectMin_whenSchedule_thenReturnError() {
        // Given
        List<StructuredSession> sessions = new ArrayList<>();
        sessions.add(new StructuredSession("Architecting Your Codebase 60ming", "60ming"));
        sessions.add(new StructuredSession("Cloud Native Java lightning", "lightning"));

        // When
        final Executable executable = () -> schedulerService.schedule(sessions);

        // Then
        assertThrows(ResponseStatusException.class,executable);
    }

    @Test
    void givenIncorrectInt_whenSchedule_thenReturnError() {
        // Given
        List<StructuredSession> sessions = new ArrayList<>();
        sessions.add(new StructuredSession("Architecting Your Codebase sc6min", "sc6min"));
        sessions.add(new StructuredSession("Cloud Native Java lightning", "lightning"));

        // When
        final Executable executable = () -> schedulerService.schedule(sessions);

        // Then
        assertThrows(ResponseStatusException.class,executable);
    }

    @Test
    void givenMoreThen8Hour_whenSchedule_thenReturnError() {
        // Given
        List<StructuredSession> sessions = new ArrayList<>();
        sessions.add(new StructuredSession("Architecting Your Codebase 481min", "481min"));
        sessions.add(new StructuredSession("Cloud Native Java lightning", "lightning"));

        // When
        final Executable executable = () -> schedulerService.schedule(sessions);

        // Then
        assertThrows(ResponseStatusException.class,executable);
    }

}
