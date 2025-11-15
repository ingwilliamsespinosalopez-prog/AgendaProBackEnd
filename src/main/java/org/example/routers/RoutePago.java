package org.example.routers;

import io.javalin.Javalin;
import org.example.controller.PagoController;
import org.example.repository.PagoRepository;
import org.example.service.PagoService;
import org.example.config.DBconfig;

public class RoutePago {

    public static void register(Javalin app) {

        var dataSource = DBconfig.getDataSource();

        var repo = new PagoRepository(dataSource);

        var service = new PagoService(repo);
        var controller = new PagoController(service);

        app.post("/api/pagos", controller::registrar);

    }

}
