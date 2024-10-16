import com.example.eventmanagementdemo.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
class EventTest {

    private Event event;

    @BeforeEach
    void setUp() {
        event = new Event("event1", "host1", LocalDate.now(), "venue1", "123456", "$23");
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
        assertEquals(LocalDate.now(), event.getDate());
    }

    @Test
    void setDate() {
        event.setDate(LocalDate.now());
        assertEquals(LocalDate.now(), event.getDate());
    }

    @Test
    void getVenue() {
        assertEquals("venue1", event.getVenue());
    }

    @Test
    void setVenue() {
        event.setVenue("venue6");
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

    @Test
    void setPrice() {
        event.setPrice("price2");
        assertEquals("price2", event.getPrice());
    }
}