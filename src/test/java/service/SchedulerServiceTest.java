package service;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ogzhnulucay.model.common.StructuredSession;
import org.ogzhnulucay.service.ScheduleCalculator;
import org.ogzhnulucay.service.SchedulerService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
class SchedulerServiceTest {

    @InjectMocks
    private SchedulerService schedulerService;
    @Mock
    private ScheduleCalculator scheduleCalculator;

    @Test
    void givenCorrectSessions_whenSchedule_thenSuccessfullyConvertLengthStringToLengthInt() {
        // Given
        List<StructuredSession> sessions = new ArrayList<>();
        sessions.add(new StructuredSession("Architecting Your Codebase 60min", "60min"));
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

}
