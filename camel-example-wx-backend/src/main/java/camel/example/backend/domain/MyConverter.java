package camel.example.backend.domain;

import org.apache.camel.Converter;

import java.util.Date;

@Converter
public class MyConverter {
    private MyConverter() {
        // Helper class
    }
    
    @Converter
    public static AttendeeInfo toAttendeeInfo(Attendee attendee) {
        return new AttendeeInfo(attendee.getName(), new Date());
    }
}
