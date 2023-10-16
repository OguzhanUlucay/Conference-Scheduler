package controller;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ogzhnulucay.controller.SchedulerController;
import org.ogzhnulucay.model.common.StructuredSession;
import org.ogzhnulucay.service.SchedulerService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
class SchedulerControllerTest {

    @InjectMocks
    private SchedulerController schedulerController;
    @Mock
    private SchedulerService schedulerService;

    @Test
    void givenCorrectSessions_whenSchedule_thenSuccessfullyConvertLengthStringToLengthInt() {
        // Given
        String sessionListRaw = "Architecting Your Codebase 60min\n" +
                "Overdoing it in Python 45min\n" +
                "Flavors of Concurrency in Java 30min\n" +
                "Ruby Errors from Mismatched Gem Versions 45min\n" +
                "JUnit 5 - Shaping the Future of Testing on the JVM 45min\n" +
                "Cloud Native Java lightning\n";

        ArgumentCaptor<List<StructuredSession>> argument = ArgumentCaptor.forClass(List.class);

        // When
        schedulerController.scheduleConference(sessionListRaw);

        // Then
        verify(schedulerService).schedule(argument.capture());
        List<StructuredSession> sessionList = argument.getValue();
        assertNotNull(sessionList);
        assertEquals(6, sessionList.size());
        assertEquals("JUnit 5 - Shaping the Future of Testing on the JVM 45min", sessionList.get(4).getFullDescription());
        assertEquals("45min", sessionList.get(3).getLength());
        assertEquals("lightning", sessionList.get(5).getLength());
    }

}
