package org.example.config;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.http.ForbiddenResponse;
import io.jsonwebtoken.Claims;
import org.example.config.JwtUtil;

public class AuthMiddleware {
    public static Handler requireAuth = ctx -> {
        String authHeader = ctx.header("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedResponse("Token no proporcionado");
        }

        String token = authHeader.substring(7);
        try {
            Claims claims = JwtUtil.validarToken(token);

            int usuarioId = JwtUtil.extraerEnteroSeguro(claims.get("id"));
            int rol = JwtUtil.extraerEnteroSeguro(claims.get("rol"));
            String correo = claims.getSubject();

            ctx.attribute("usuarioId", usuarioId);
            ctx.attribute("rol", rol);
            ctx.attribute("correo", correo);

            System.out.println("Usuario autenticado - ID: " + usuarioId + ", Rol: " + rol);
        } catch (Exception e) {
            throw new UnauthorizedResponse("Token inválido o expirado");
        }
    };

    public static Handler requireRole(int... rolesPermitidos) {
        return ctx -> {
            requireAuth.handle(ctx);

            Integer rolUsuario = ctx.attribute("rol");

            if (rolUsuario == null) {
                throw new UnauthorizedResponse("No se pudo determinar el rol del usuario");
            }

            boolean tienePermiso = false;
            for (int rolPermitido : rolesPermitidos) {
                if (rolUsuario == rolPermitido) {
                    tienePermiso = true;
                    break;
                }
            }

            if (!tienePermiso) {
                throw new ForbiddenResponse("No estas autorizado");
            }

            System.out.println("Acceso permitido - Rol: " + rolUsuario);
        };
    }

    public static Handler requireAdmin = requireRole(3);

    public static Handler requireAsesor = requireRole(2);

    public static Handler requireCliente = requireRole(1);

}