package org.example.repository;

import org.example.model.Pago;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class PagoRepository {

    private final DataSource dataSource;

    public PagoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Pago registrarPago(Pago pago) throws Exception {

        String sql = """
            INSERT INTO pago(idCita, monto, idTransaccion, idEstadoPago, idMetodoPago)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, pago.getIdCita());
            stmt.setDouble(2, pago.getMonto());
            stmt.setString(3, pago.getIdTransaccion());
            stmt.setInt(4, pago.getIdEstadoPago());
            stmt.setInt(5, pago.getIdMetodoPago());

            stmt.executeUpdate();

            var rs = stmt.getGeneratedKeys();
            if (rs.next()) pago.setIdPago(rs.getInt(1));

            return pago;
        }
    }
}
