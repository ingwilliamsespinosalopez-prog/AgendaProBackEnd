package org.example.repository;

import org.example.config.DBconfig;
import org.example.model.Usuario;

import java.sql.*;
import java.util.Optional;

public class UsuarioRepository {

    public void registrarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (nombreUsuario, nombre, apellido, segundoApellido, rfc, curp, telefono, correo, contrasena, rutaImagen, idRol) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBconfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombreUsuario());
            stmt.setString(2, usuario.getNombre());
            stmt.setString(3, usuario.getApellido());
            stmt.setString(4, usuario.getSegundoApellido());
            stmt.setString(5, usuario.getRfc());
            stmt.setString(6, usuario.getCurp());
            stmt.setString(7, usuario.getTelefono());
            stmt.setString(8, usuario.getCorreo());
            stmt.setString(9, usuario.getContrasena());
            stmt.setString(10, usuario.getRutaImagen());
            stmt.setInt(11, usuario.getIdRol());

            stmt.executeUpdate();
        }
    }

    public boolean verificarCorreo(String correo) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuario WHERE correo = ?";
        try (Connection conn = DBconfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, correo);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    public Optional<Usuario> getCorreo(String correo) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE correo = ?";
        try (Connection conn = DBconfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, correo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapearUsuario(rs));
            }
        }
        return Optional.empty();
    }

    public Optional<Usuario> getId(int id) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE idUsuario = ?";
        try (Connection conn = DBconfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapearUsuario(rs));
            }
        }
        return Optional.empty();
    }

    public void updatePerfil(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuario SET nombre=?, apellido=?, segundoApellido=?,rfc =?, rutaImagen=?, telefono=?, correo=?, contrasena=?, idRol=? WHERE idUsuario=?";
        try (Connection conn = DBconfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getSegundoApellido());
            stmt.setString(4, usuario.getRutaImagen());
            stmt.setString(5, usuario.getTelefono());
            stmt.setString(6, usuario.getCorreo());
            stmt.setString(7, usuario.getContrasena());
            stmt.setInt(8, usuario.getIdRol());
            stmt.setInt(9, usuario.getId());

            int filas = stmt.executeUpdate();
            if (filas == 0) {
                throw new IllegalArgumentException("No se encontró el usuario con ID " + usuario.getId());
            }
        }
    }

    public void updatePassword(String correo, String nuevaPasswordEncriptada) throws SQLException {
        String sql = "UPDATE usuario SET contrasena = ? WHERE correo = ?";
        try (Connection conn = DBconfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nuevaPasswordEncriptada);
            stmt.setString(2, correo);
            stmt.executeUpdate();
        }
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setIdUsuario(rs.getInt("idUsuario"));
        u.setNombre(rs.getString("nombre"));
        u.setApellido(rs.getString("apellido"));
        u.setSegundoApellido(rs.getString("segundoApellido"));
        u.setTelefono(rs.getString("telefono"));
        u.setCorreo(rs.getString("correo"));
        u.setContrasena(rs.getString("contrasena"));
        u.setRutaImagen(rs.getString("rutaImagen"));
        u.setIdRol(rs.getInt("IdRol"));
        return u;
    }
}