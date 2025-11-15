package org.example.routers;

import io.javalin.Javalin;
import org.example.controller.UsuarioController;
import org.example.repository.UsuarioRepository;
import org.example.service.UsuarioService;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import io.jsonwebtoken.Claims;
import org.example.config.JwtUtil;

public class RoutesUsuario {
    public void register(Javalin app) {
        UsuarioRepository repository = new UsuarioRepository();
        UsuarioService service = new UsuarioService(repository);
        UsuarioController controller = new UsuarioController(service);

        app.post("/registro", controller::registrar);
        app.post("/login", controller::login);
        app.post("/encriptar-password", controller::encriptarPassword);
        app.post("/recuperar", controller::recuperarPassword);

        Handler requireToken = ctx -> {
            String authHeader = ctx.header("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new UnauthorizedResponse("Token no proporcionado");
            }

            String token = authHeader.substring(7);
            try {
                Claims claims = JwtUtil.validarToken(token);

                Object rawId = claims.get("id");
                int usuarioId = (rawId instanceof Integer) ? (Integer) rawId :
                        (rawId instanceof Number) ? ((Number) rawId).intValue() :
                                (rawId instanceof String) ? Integer.parseInt((String) rawId) : -1;


                ctx.attribute("usuarioId", usuarioId);

                System.out.println("Middleware JWT → usuarioId: " + usuarioId + ", rol: ");
            } catch (Exception e) {
                throw new UnauthorizedResponse("Token inválido o expirado");
            }
        };
        app.before("/perfil/*", requireToken);

        app.put("/perfil/{id}", controller::editarPerfil);
        app.get("/perfil/{id}", controller::obtenerPerfil);
    }
}