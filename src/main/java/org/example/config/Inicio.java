package org.example.config;

import javax.sql.DataSource;

import org.example.routers.RoutesUsuario;
import org.example.routers.RoutesCita;
import org.example.routers.RoutePago;
import org.example.routers.RouteBlog;

import org.example.repository.UsuarioRepository;
import org.example.repository.CitaRepository;
import org.example.repository.PagoRepository;
import org.example.repository.BlogRepository;

import org.example.service.UsuarioService;
import org.example.service.CitaService;
import org.example.service.PagoService;
import org.example.service.BlogService;

import org.example.controller.UsuarioController;
import org.example.controller.CitaController;
import org.example.controller.PagoController;
import org.example.controller.BlogController;

import static org.example.config.DBconfig.getDataSource;

public class Inicio {

    private final DataSource dataSource;

    public Inicio(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    CitaRepository citaRepository = new CitaRepository();
    CitaService citaService = new CitaService(citaRepository);
    CitaController citaController = new CitaController(citaService);
    RoutesCita citaRoutes = new RoutesCita(citaController);

    public RoutesUsuario inicioUsuario() {
        DataSource dataSource = getDataSource();
        UsuarioRepository usuarioRepository = new UsuarioRepository(dataSource);
        UsuarioService usuarioService = new UsuarioService(usuarioRepository);
        UsuarioController usuarioController = new UsuarioController(usuarioService);
        return new RoutesUsuario(usuarioController);
    }

    public static RoutePago inicioPago() {

        DataSource dataSource = getDataSource();
        PagoRepository pagoRepository = new PagoRepository(dataSource);
        CitaRepository citaRepository = new CitaRepository();
        CitaService citaService = new CitaService(citaRepository);
        PagoService pagoService = new PagoService(pagoRepository, citaService);
        PagoController pagoController = new PagoController(pagoService);
        RoutePago routes = new RoutePago(pagoController);

        return routes;
    }

    public RouteBlog inicioBlog() {
        DataSource dataSource = getDataSource();
        BlogRepository blogRepository = new BlogRepository(dataSource);
        BlogService blogService = new BlogService(blogRepository);
        BlogController blogController = new BlogController(blogService);
        return new RouteBlog(blogController);
    }

    public RoutesCita inicioCita() {
        CitaRepository citaRepository = new CitaRepository();
        CitaService citaService = new CitaService(citaRepository);
        CitaController citaController = new CitaController(citaService);
        return new RoutesCita(citaController);

    }
}
