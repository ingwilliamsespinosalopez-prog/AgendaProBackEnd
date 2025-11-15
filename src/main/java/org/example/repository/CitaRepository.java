package org.example.repository;

import org.example.model.Cita;
import org.example.config.DBconfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CitaRepository {

    // ============================
    // VERIFICAR EMPALME (CHOCAR CITA)
    // ============================
    public boolean existeEmpalme(int idAsesor, String fecha, String hora) {
        String sql = "SELECT COUNT(*) FROM cita WHERE idAsesor = ? AND fechaCita = ? AND horaCita = ?";

        try (Connection con = DBconfig.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idAsesor);
            ps.setString(2, fecha);
            ps.setString(3, hora);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    // ============================
    // CREAR CITA (DEVUELVE ID)
    // ============================
    public int crear(Cita c) {

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
            ps.setString(6, c.getFechaCita());
            ps.setString(7, c.getHoraCita());
            ps.setBoolean(8, c.isPagado());
            ps.setString(9, c.getNotas());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }


    // ============================
    // OBTENER CITA POR ID
    // ============================
    public Cita obtenerPorId(int id) {
        String sql = "SELECT * FROM cita WHERE idCita = ?";

        try (Connection con = DBconfig.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Cita c = new Cita();
                c.setIdCita(rs.getInt("idCita"));
                c.setIdCliente(rs.getInt("idCliente"));
                c.setIdAsesor(rs.getInt("idAsesor"));
                c.setIdServicio(rs.getInt("idServicio"));
                c.setIdEstado(rs.getInt("idEstado"));
                c.setIdModalidad(rs.getInt("idModalidad"));
                c.setFechaCita(rs.getString("fechaCita"));
                c.setHoraCita(rs.getString("horaCita"));
                c.setPagado(rs.getBoolean("pagado"));
                c.setNotas(rs.getString("notas"));
                return c;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    // ============================
    // ACTUALIZAR ESTADO
    // ============================
    public int actualizarEstado(int id, int estado) {
        String sql = "UPDATE cita SET idEstado = ? WHERE idCita = ?";

        try (Connection con = DBconfig.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, estado);
            ps.setInt(2, id);

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    // ============================
    // ASIGNAR ASESOR
    // ============================
    public boolean asignarAsesor(int idCita, int idAsesor) {
        String sql = "UPDATE cita SET idAsesor = ? WHERE idCita = ?";

        try (Connection con = DBconfig.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idAsesor);
            ps.setInt(2, idCita);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // ============================
    // MARCAR CITA PAGADA
    // ============================
    public boolean marcarPagado(int idCita) {
        String sql = "UPDATE cita SET pagado = TRUE WHERE idCita = ?";

        try (Connection con = DBconfig.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCita);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // ============================
    // ACEPTAR / RECHAZAR CITA
    // ============================
    public String cambiarEstado(int idCita, String accion) {
        int estado = accion.equals("aceptar") ? 2 : 3; // 2=aceptada, 3=rechazada

        String sql = "UPDATE cita SET idEstado = ? WHERE idCita = ?";

        try (Connection con = DBconfig.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, estado);
            ps.setInt(2, idCita);
            ps.executeUpdate();

            return (estado == 2) ? "Cita aceptada" : "Cita rechazada";

        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }


    // ============================
    // LISTAR CITAS POR CLIENTE
    // ============================
    public List<Cita> listarPorCliente(int idCliente) {

        String sql = "SELECT * FROM cita WHERE idCliente = ?";
        List<Cita> lista = new ArrayList<>();

        try (Connection con = DBconfig.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cita c = new Cita();
                c.setIdCita(rs.getInt("idCita"));
                c.setIdCliente(rs.getInt("idCliente"));
                c.setIdAsesor(rs.getInt("idAsesor"));
                c.setIdServicio(rs.getInt("idServicio"));
                c.setIdEstado(rs.getInt("idEstado"));
                c.setIdModalidad(rs.getInt("idModalidad"));
                c.setFechaCita(rs.getString("fechaCita"));
                c.setHoraCita(rs.getString("horaCita"));
                c.setPagado(rs.getBoolean("pagado"));
                c.setNotas(rs.getString("notas"));

                lista.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}
