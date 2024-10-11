import com.example.eventmanagementdemo.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class EventTest {

    private Event event;

    @BeforeEach
    void setUp() {
        event = new Event("event1", "host1", "23/10/2024", "venue1", "123456", "$23");
    }

    @Test
    void testId() {
        event.setId(1);
        assertEquals(1, event.getId());
    }

    @Test
    void setId() {
    }

    @Test
    void getEventName() {
        assertEquals("event1", event.getEventName());
    }

    @Test
    void setEventName() {
        event.setEventName("event2");
        assertEquals("event2", event.getEventName());
    }

    @Test
    void getHostedBy() {
        assertEquals("host1", event.getHostedBy());
    }

    @Test
    void setHostedBy() {
        event.setHostedBy("host2");
        assertEquals("host2", event.getHostedBy());
    }

    @Test
    void getDate() {
        assertEquals("23/10/2024", event.getDate());
    }

    @Test
    void setDate() {
        event.setDate("24/11/2024");
        assertEquals("24/11/2024", event.getDate());
    }

    @Test
    void getVenue() {
        assertEquals("venue1", event.getVenue());
    }

    @Test
    void setVenue() {
        event.setVenue("venue2");
        assertEquals("venue2", event.getVenue());
    }

    @Test
    void getPhone() {
        assertEquals("123456", event.getPhone());
    }

    @Test
    void setPhone() {
        event.setPhone("phone2");
        assertEquals("phone2", event.getPhone());
    }
}