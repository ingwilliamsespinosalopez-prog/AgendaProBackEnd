package org.example.service;

import org.example.model.Usuario;
import org.example.repository.UsuarioRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UsuarioService {
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public void registrar(Usuario usuario) throws SQLException {
        if (usuario.getIdRol() != 1) {
            throw new IllegalArgumentException("Error al registrar usuario Solo clientes");
        }
        if (repository.verificarCorreo(usuario.getCorreo())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }
        String hashed = BCrypt.hashpw(usuario.getContrasena(), BCrypt.gensalt());
        usuario.setContrasena(hashed);
        repository.registrarUsuario(usuario);
    }

    public Optional<Usuario> getCorreo(String correo) throws SQLException {
        return repository.getCorreo(correo);
    }

    public Optional<Usuario> getId(int id) throws SQLException {
        return repository.getId(id);
    }

    public void editarPerfil(Usuario usuario) throws SQLException {
        repository.updatePerfil(usuario);
    }

    public void recuperarPassword(String correo, String nuevaPassword) throws SQLException {
        if (!repository.verificarCorreo(correo)) {
            throw new IllegalArgumentException("Correo no registrado");
        }
        String hashed = BCrypt.hashpw(nuevaPassword, BCrypt.gensalt());
        repository.updatePassword(correo, hashed);
    }
}
