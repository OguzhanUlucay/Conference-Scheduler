package service;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ogzhnulucay.model.common.StructuredSession;
import org.ogzhnulucay.model.response.Schedule;
import org.ogzhnulucay.model.response.Track;
import org.ogzhnulucay.service.ScheduleCalculator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
class ScheduleCalculatorTest {

    @InjectMocks
    private ScheduleCalculator scheduleCalculator = new ScheduleCalculator();

    @Test
    void givenCorrectSessions_whenSchedule_thenSuccessfullyConvertLengthStringToLengthInt() {
        // Given
        List<StructuredSession> sessions = new ArrayList<>();
        sessions.add(new StructuredSession("Architecting Your Codebase 60min", "60min", 60));
        sessions.add(new StructuredSession("Overdoing it in Python 45min", "45min", 45));
        sessions.add(new StructuredSession("Flavors of Concurrency in Java 30min", "30min", 30));
        sessions.add(new StructuredSession("Ruby Errors from Mismatched Gem Versions 45min", "45min", 45));
        sessions.add(new StructuredSession("JUnit 5 - Shaping the Future of Testing on the JVM 45min", "45min", 45));
        sessions.add(new StructuredSession("Cloud Native Java lightning", "lightning", 5));
        sessions.add(new StructuredSession("Communicating Over Distance 60min", "60min", 60));
        sessions.add(new StructuredSession("AWS Technical Essentials 45min", "45min", 45));
        sessions.add(new StructuredSession("Continuous Delivery 30min", "30min", 30));
        sessions.add(new StructuredSession("Monitoring Reactive Applications 30min", "30min", 30));
        sessions.add(new StructuredSession("Pair Programming vs Noise 45min", "45min", 45));
        sessions.add(new StructuredSession("Rails Magic 60min", "60min", 60));
        sessions.add(new StructuredSession("Microservices \"Just Right\" 60min", "60min", 60));
        sessions.add(new StructuredSession("Clojure Ate Scala (on my project) 45min", "45min", 45));
        sessions.add(new StructuredSession("Perfect Scalability 30min", "30min", 30));
        sessions.add(new StructuredSession("Apache Spark 30min", "30min", 30));
        sessions.add(new StructuredSession("Async Testing on JVM 60min", "60min", 60));
        sessions.add(new StructuredSession("A World Without HackerNews 30min", "30min", 30));
        sessions.add(new StructuredSession("User Interface CSS in Apps 30min", "30min", 30));

        // When
        Schedule schedule = scheduleCalculator.createSchedule(sessions);

        // Then
        assertNotNull(schedule);
        assertNotNull(schedule.getTracks());

        Track firstTrack = schedule.getTracks().get(0);
        assertEquals("track1", firstTrack.getTrackName());
        assertEquals("10:45 Flavors of Concurrency in Java 30min", firstTrack.getTrackSchedule().get(2));
        assertEquals("12:00 lunch", firstTrack.getTrackSchedule().get(3));

        Track secondTrack = schedule.getTracks().get(1);
        assertEquals("track2", secondTrack.getTrackName());
        assertEquals("10:45 Microservices \"Just Right\" 60min", secondTrack.getTrackSchedule().get(2));
        assertEquals("13:00 Clojure Ate Scala (on my project) 45min", secondTrack.getTrackSchedule().get(4));
        assertEquals("15:45 A World Without HackerNews 30min", secondTrack.getTrackSchedule().get(8));
    }

}
