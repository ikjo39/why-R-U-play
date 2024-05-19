package whyruplay.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import whyruplay.domain.Reservation;

public record ReservationCreate(String name, String date, String time) {

    public Reservation toEntity() {
        LocalDate newDate = LocalDate.parse(date);
        LocalTime newTime = LocalTime.parse(time);
        return new Reservation(null, name, newDate, newTime);
    }
}
