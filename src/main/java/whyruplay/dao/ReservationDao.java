package whyruplay.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import whyruplay.domain.Reservation;

public class ReservationDao {

    private Long sequence = 1L;
    private static final Map<Long, Reservation> REPOSITORY = new HashMap<>();

    public void create(Reservation reservation) {
        reservation = new Reservation(sequence, reservation.getName(), reservation.getDate(), reservation.getTime());
        REPOSITORY.put(sequence++, reservation);
    }

    public List<Reservation> readAll() {
        return new ArrayList<>(REPOSITORY.values());
    }

    public void delete(long id) {
        REPOSITORY.remove(id);
    }
}
