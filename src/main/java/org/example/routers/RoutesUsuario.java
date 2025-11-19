package org.example.routers;

import io.javalin.Javalin;
import org.example.controller.UsuarioController;
import org.example.config.AuthMiddleware;

public class RoutesUsuario {
    private final UsuarioController controller;

    public RoutesUsuario(UsuarioController controller) {
        this.controller = controller;
    }

    public void register(Javalin app) {
        app.post("/registro", controller::registrar);
        app.post("/login", controller::login);
        app.post("/recuperar", controller::recuperarPassword);
        app.post("/encriptar-password", controller::encriptarPassword);

        app.get("/perfil/{id}", controller::obtenerPerfil);
        app.put("/perfil/{id}", controller::editarPerfil);

        app.get("/cliente/dashboard", ctx -> {
            ctx.json(java.util.Map.of("mensaje", "Bienvenido al dashboard de cliente"));
        });

        app.get("/asesor/dashboard", ctx -> {
            ctx.json(java.util.Map.of("mensaje", "Bienvenido al dashboard de asesor"));
        });

        app.get("/admin/dashboard", ctx -> {
            ctx.json(java.util.Map.of("mensaje", "Bienvenido al dashboard de administrador"));
        });
        app.get("/admin/usuarios", ctx -> {
            ctx.json(java.util.Map.of("mensaje", "Lista de todos los usuarios"));
        });
    }
}