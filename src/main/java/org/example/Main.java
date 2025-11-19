package org.example;

import io.javalin.Javalin;
import org.example.config.DBconfig;
import org.example.config.Inicio;

public class Main {

    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(rule -> rule.anyHost());
            });
        }).start(7001);

        Inicio inicio = new Inicio(DBconfig.getDataSource());


        inicio.inicioUsuario().register(app);
        inicio.inicioCita().register(app);
        Inicio.inicioPago().register(app);
        inicio.inicioBlog().register(app);

        System.out.println("🚀 API iniciada en http://localhost:7001");
    }
}
