package org.example.repository;

import org.example.config.DBconfig;
import org.example.model.Pago;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class PagoRepository {

    private final DataSource dataSource;


    public PagoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Pago registrarPago(Pago pago) throws SQLException {
        String sql = """
            INSERT INTO pago(idCita, monto, idTransaccion, idEstadoPago, idMetodoPago)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBconfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, pago.getIdCita());
            stmt.setBigDecimal(2, pago.getMonto());
            stmt.setString(3, pago.getIdTransaccion());
            stmt.setInt(4, pago.getIdEstadoPago());
            stmt.setInt(5, pago.getIdMetodoPago());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    pago.setIdPago(rs.getInt(1));
                }
            }

            return pago;
        }
    }

    public List<Pago> listarTodos() throws SQLException {
        String sql = "SELECT * FROM pago ORDER BY fechaPago DESC";
        List<Pago> pagos = new ArrayList<>();

        try (Connection conn = DBconfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                pagos.add(mapResultSetToPago(rs));
            }
        }
        return pagos;
    }

    private Pago mapResultSetToPago(ResultSet rs) throws SQLException {
        Pago pago = new Pago();
        pago.setIdPago(rs.getInt("idPago"));
        pago.setIdCita(rs.getInt("idCita"));
        pago.setMonto(rs.getBigDecimal("monto"));
        pago.setIdTransaccion(rs.getString("idTransaccion"));
        pago.setIdEstadoPago(rs.getInt("idEstadoPago"));
        pago.setIdMetodoPago(rs.getInt("idMetodoPago"));
        pago.setFechaPago(rs.getTimestamp("fechaPago"));
        return pago;
    }
}