package whyruplay;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import whyruplay.dao.ReservationDao;
import whyruplay.dto.ReservationCreate;

public class HelloWorld {

    private static final ReservationDao reservationDao = new ReservationDao();

    public static void main(String[] args) {
        staticFiles.location("/public");

        ObjectMapper objectMapper = new ObjectMapper();
        VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "/templates/admin/index2.html");
        }, velocityTemplateEngine);

        get("/admin/reservation", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "/templates/admin/reservation-legacy.html");
        }, velocityTemplateEngine);

        get("/reservations", (req, res) -> {

            List<String> results = reservationDao.readAll().stream()
                    .map(reservation -> {
                        try {
                            return objectMapper.writeValueAsString(reservation);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .toList();
            res.type("application/json");
            return results;
        });

        post("/reservations", (req, res) -> {
            String body = req.body();
            ReservationCreate reservationCreate = objectMapper.readValue(body, ReservationCreate.class);
            reservationDao.create(reservationCreate.toEntity());
            res.status(201);
            return body;
        });

        delete("/reservations/:id", (req, res) -> {
            String id = req.params(":id");
            reservationDao.delete(Long.parseLong(id));
            res.status(204);
            return id;
        });
    }
}
