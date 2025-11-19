package org.example.repository;

import org.example.model.Cita;
import org.example.config.DBconfig;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CitaRepository {

    public boolean existeEmpalme(int idAsesor, LocalDate fecha, LocalTime hora) throws SQLException {
        String sql = "SELECT COUNT(*) FROM cita WHERE idAsesor = ? AND fechaCita = ? AND horaCita = ?";

        try (Connection con = DBconfig.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idAsesor);
            ps.setDate(2, Date.valueOf(fecha));
            ps.setTime(3, Time.valueOf(hora));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }

        }
        return false;
    }

    public int crear(Cita c) throws SQLException {

        String sql = """
            INSERT INTO cita (
                idCliente, idAsesor, idServicio, idEstado, idModalidad,
                fechaCita, horaCita, pagado, notas
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection con = DBconfig.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, c.getIdCliente());
            ps.setInt(2, c.getIdAsesor());
            ps.setInt(3, c.getIdServicio());
            ps.setInt(4, c.getIdEstado());
            ps.setInt(5, c.getIdModalidad());
            ps.setDate(6, Date.valueOf(c.getFechaCita()));
            ps.setTime(7, Time.valueOf(c.getHoraCita()));
            ps.setBoolean(8, c.getPagado());
            ps.setString(9, c.getNotas());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    public Cita obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM cita WHERE idCita = ?";

        try (Connection con = DBconfig.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    Cita c = new Cita();
                    c.setIdCita(rs.getInt("idCita"));
                    c.setIdCliente(rs.getInt("idCliente"));
                    c.setIdAsesor(rs.getInt("idAsesor"));
                    c.setIdServicio(rs.getInt("idServicio"));
                    c.setIdEstado(rs.getInt("idEstado"));
                    c.setIdModalidad(rs.getInt("idModalidad"));
                    c.setFechaCita(rs.getDate("fechaCita").toLocalDate());
                    c.setHoraCita(rs.getTime("horaCita").toLocalTime());
                    c.setPagado(rs.getBoolean("pagado"));
                    c.setNotas(rs.getString("notas"));
                    return c;
                }
            }
        }
        return null;
    }

    public int actualizarEstado(int id, int estado) throws SQLException {
        String sql = "UPDATE cita SET idEstado = ? WHERE idCita = ?";

        try (Connection con = DBconfig.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, estado);
            ps.setInt(2, id);

            return ps.executeUpdate();
        }
    }

    public boolean asignarAsesor(int idCita, int idAsesor) throws SQLException {
        String sql = "UPDATE cita SET idAsesor = ? WHERE idCita = ?";

        try (Connection con = DBconfig.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idAsesor);
            ps.setInt(2, idCita);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean marcarPagado(int idCita) throws SQLException {
        String sql = "UPDATE cita SET pagado = TRUE WHERE idCita = ?";

        try (Connection con = DBconfig.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCita);
            return ps.executeUpdate() > 0;
        }
    }

    public String cambiarEstado(int idCita, String accion) throws SQLException {
        int estado = accion.equals("aceptar") ? 2 : 3;

        String sql = "UPDATE cita SET idEstado = ? WHERE idCita = ?";

        try (Connection con = DBconfig.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, estado);
            ps.setInt(2, idCita);
            ps.executeUpdate();

            return (estado == 2) ? "Cita aceptada" : "Cita rechazada";
        }
    }

    public List<Cita> listarPorCliente(int idCliente) throws SQLException {
        String sql = "SELECT * FROM cita WHERE idCliente = ?";
        List<Cita> lista = new ArrayList<>();

        try (Connection con = DBconfig.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Cita c = new Cita();
                    c.setIdCita(rs.getInt("idCita"));
                    c.setIdCliente(rs.getInt("idCliente"));
                    c.setIdAsesor(rs.getInt("idAsesor"));
                    c.setIdServicio(rs.getInt("idServicio"));
                    c.setIdEstado(rs.getInt("idEstado"));
                    c.setIdModalidad(rs.getInt("idModalidad"));
                    c.setFechaCita(rs.getDate("fechaCita").toLocalDate());
                    c.setHoraCita(rs.getTime("horaCita").toLocalTime());
                    c.setPagado(rs.getBoolean("pagado"));
                    c.setNotas(rs.getString("notas"));

                    lista.add(c);
                }
            }
        }
        return lista;
    }

    public List<Cita> listarTodas() throws SQLException {
        String sql = "SELECT * FROM cita";
        List<Cita> lista = new ArrayList<>();

        try (Connection con = DBconfig.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cita c = new Cita();
                c.setIdCita(rs.getInt("idCita"));
                c.setIdCliente(rs.getInt("idCliente"));
                c.setIdAsesor(rs.getInt("idAsesor"));
                c.setIdServicio(rs.getInt("idServicio"));
                c.setIdEstado(rs.getInt("idEstado"));
                c.setIdModalidad(rs.getInt("idModalidad"));
                c.setFechaCita(rs.getDate("fechaCita").toLocalDate());
                c.setHoraCita(rs.getTime("horaCita").toLocalTime());
                c.setPagado(rs.getBoolean("pagado"));
                c.setNotas(rs.getString("notas"));

                lista.add(c);
            }
        }
        return lista;
    }

    public List<Cita> listarPorEstado(int idEstado) throws SQLException {
        String sql = "SELECT * FROM cita WHERE idEstado = ?";
        List<Cita> lista = new ArrayList<>();

        try (Connection con = DBconfig.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idEstado);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Cita c = new Cita();
                    c.setIdCita(rs.getInt("idCita"));
                    c.setIdCliente(rs.getInt("idCliente"));
                    c.setIdAsesor(rs.getInt("idAsesor"));
                    c.setIdServicio(rs.getInt("idServicio"));
                    c.setIdEstado(rs.getInt("idEstado"));
                    c.setIdModalidad(rs.getInt("idModalidad"));
                    c.setFechaCita(rs.getDate("fechaCita").toLocalDate());
                    c.setHoraCita(rs.getTime("horaCita").toLocalTime());
                    c.setPagado(rs.getBoolean("pagado"));
                    c.setNotas(rs.getString("notas"));

                    lista.add(c);
                }
            }
        }
        return lista;
    }

    public List<Cita> listarPorAsesor(int idAsesor) throws SQLException {
        String sql = "SELECT * FROM cita WHERE idAsesor = ?";
        List<Cita> lista = new ArrayList<>();

        try (Connection con = DBconfig.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idAsesor);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Cita c = new Cita();
                    c.setIdCita(rs.getInt("idCita"));
                    c.setIdCliente(rs.getInt("idCliente"));
                    c.setIdAsesor(rs.getInt("idAsesor"));
                    c.setIdServicio(rs.getInt("idServicio"));
                    c.setIdEstado(rs.getInt("idEstado"));
                    c.setIdModalidad(rs.getInt("idModalidad"));
                    c.setFechaCita(rs.getDate("fechaCita").toLocalDate());
                    c.setHoraCita(rs.getTime("horaCita").toLocalTime());
                    c.setPagado(rs.getBoolean("pagado"));
                    c.setNotas(rs.getString("notas"));

                    lista.add(c);
                }
            }
        }
        return lista;
    }
    public List<Cita> listarPorEstados(List<Integer> idsEstado) throws SQLException {
        if (idsEstado == null || idsEstado.isEmpty()) return List.of();

        String inClause = String.join(",", java.util.Collections.nCopies(idsEstado.size(), "?"));
        String sql = "SELECT * FROM cita WHERE idEstado IN (" + inClause + ")";
        List<Cita> lista = new ArrayList<>();

        try (Connection con = DBconfig.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (int i = 0; i < idsEstado.size(); i++) {
                ps.setInt(i + 1, idsEstado.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Cita c = new Cita();
                    c.setIdCita(rs.getInt("idCita"));
                    c.setIdCliente(rs.getInt("idCliente"));
                    c.setIdAsesor(rs.getInt("idAsesor"));
                    c.setIdServicio(rs.getInt("idServicio"));
                    c.setIdEstado(rs.getInt("idEstado"));
                    c.setIdModalidad(rs.getInt("idModalidad"));
                    c.setFechaCita(rs.getDate("fechaCita").toLocalDate());
                    c.setHoraCita(rs.getTime("horaCita").toLocalTime());
                    c.setPagado(rs.getBoolean("pagado"));
                    c.setNotas(rs.getString("notas"));

                    lista.add(c);
                }
            }
        }
        return lista;
    }
}