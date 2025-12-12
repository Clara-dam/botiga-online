/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Comanda;
import model.LiniaComanda;
import util.Connexio;

/**
 *
 * @author clara
 */
public class ComandaDAO {

    private Connection conn;

    public ComandaDAO() {
        try {
            this.conn = Connexio.getConnection();
            this.conn.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("Error obrint connexió: " + e.getMessage());
        }
    }

    /* ==================================================
       1. Crear comanda principal (amb transacció)
    =================================================== */
    public boolean crearComanda(Comanda comanda, List<LiniaComanda> linies) {

        try {
            conn.setAutoCommit(false);

            // 1. Inserir comanda (sense total encara)
            int idComanda = inserirComanda(comanda);

            // 2. Inserir línies + comprovar estoc + actualitzar estoc
            double subtotal = inserirLiniesComanda(idComanda, linies);

            // 3. Crear savepoint abans de descompte
            Savepoint sp = conn.setSavepoint("SP_DESCOMPTE");

            // 4. Intentar aplicar descompte
            boolean aplicat = aplicarDescompte(idComanda, subtotal);

            // 5. Si no hi ha descompte o falla → rollback al savepoint
            if (!aplicat) {
                conn.rollback(sp);
                actualitzarTotal(idComanda, subtotal);
            }

            conn.commit();
            return true;

        } catch (SQLException ex) {
            System.err.println("ERROR: rollback total de la comanda.");
            System.err.println("Motiu: " + ex.getMessage());
            ex.printStackTrace();   // opcional, però molt útil per veure la línia exacta
            try {
                conn.rollback();
            } catch (SQLException e) {
            }
            return false;

        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ignored) {
            }
            try {
                conn.close();
            } catch (SQLException ignored) {
            }
        }
    }

    /* ==================================================
       2. Inserir comanda (sense total)
    =================================================== */
    private int inserirComanda(Comanda c) throws SQLException {

        String sql = "INSERT INTO Comanda (client_id, data, total) VALUES (?, ?, 0)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, c.getClient_id());
            ps.setTimestamp(2, c.getData());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }

    /* ==================================================
       3. Inserir línies + validar estoc
    =================================================== */
    private double inserirLiniesComanda(int idComanda, List<LiniaComanda> linies) throws SQLException {

        double subtotal = 0;

        String sqlLinia = "INSERT INTO LiniaComanda (comanda_id, producte_id, quantitat, preuUnitari) VALUES (?, ?, ?, ?)";
        String sqlEstoc = "UPDATE Producte SET estoc = estoc - ? WHERE id = ? AND estoc >= ?";

        for (LiniaComanda l : linies) {

            // Validació d’estoc
            try (PreparedStatement psEstoc = conn.prepareStatement(sqlEstoc)) {
                psEstoc.setInt(1, l.getQuantitat());
                psEstoc.setInt(2, l.getProducte_id());
                psEstoc.setInt(3, l.getQuantitat());

                int rows = psEstoc.executeUpdate();
                if (rows == 0) {
                    throw new SQLException("Estoc insuficient del producte " + l.getProducte_id());
                }
            }

            // Inserció de línia
            try (PreparedStatement ps = conn.prepareStatement(sqlLinia)) {
                ps.setInt(1, idComanda);
                ps.setInt(2, l.getProducte_id());
                ps.setInt(3, l.getQuantitat());
                ps.setDouble(4, l.getPreuUnitari());
                ps.executeUpdate();
            }

            subtotal += l.getQuantitat() * l.getPreuUnitari();
        }

        return subtotal;
    }

    /* ==================================================
       4. Aplicar descompte (si existeix)
    =================================================== */
    private boolean aplicarDescompte(int idComanda, double subtotal) throws SQLException {

        // Buscar descompte per producte dins la comanda
        String sql = """
            SELECT d.tipus, d.valor 
            FROM Descompte d
            JOIN LiniaComanda l ON l.producte_id = d.producte_id
            WHERE l.Comanda_id = ?
            LIMIT 1;
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idComanda);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                return false; // No hi ha descompte
            }

            String tipus = rs.getString("tipus");
            double valor = rs.getDouble("valor");

            double totalFinal = calcularDescompte(subtotal, tipus, valor);

            actualitzarTotal(idComanda, totalFinal);

            return true;

        } catch (SQLException ex) {
            System.err.println("FALLA aplicació descompte ⇒ rollback(savepoint)");
            return false;
        }
    }

    /* ==================================================
       4.1 Càlcul del descompte
    =================================================== */
    private double calcularDescompte(double subtotal, String tipus, double valor) {
        if (tipus.equals("%")) {
            return subtotal - (subtotal * valor / 100.0);
        }
        if (tipus.equals("€")) {
            return subtotal - valor;
        }
        return subtotal;
    }

    /* ==================================================
       5. Actualitzar total a la comanda
    =================================================== */
    private void actualitzarTotal(int idComanda, double total) throws SQLException {

        String sql = "UPDATE Comanda SET total = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, total);
            ps.setInt(2, idComanda);
            ps.executeUpdate();
        }
    }

    /* ==================================================
       6. Mostrar Comandes per Client
    =================================================== */
    public List<Comanda> getComandesByClient(int clientId) {
        List<Comanda> comandes = new ArrayList<>();

        String sql = "SELECT c.id AS comanda_id, c.data, c.total, "
                + "l.id AS linia_id, l.quantitat, l.preuUnitari, "
                + "p.id AS producte_id, p.nom AS producteNom, "
                + "COALESCE(d.valor,0) AS descompte "
                + "FROM Comanda c "
                + "JOIN LiniaComanda l ON c.id = l.comanda_id "
                + "JOIN Producte p ON l.producte_id = p.id "
                + "LEFT JOIN Descompte d ON p.id = d.producte_id "
                + "WHERE c.client_id = ? "
                + "ORDER BY c.id";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, clientId);
            ResultSet rs = ps.executeQuery();

            Comanda comandaActual = null;
            int ultimaComandaID = -1;

            while (rs.next()) {
                int comandaId = rs.getInt("comanda_id");

                if (comandaId != ultimaComandaID) {
                    comandaActual = new Comanda(
                            comandaId,
                            clientId,
                            rs.getTimestamp("data"),
                            rs.getDouble("total")
                    );
                    comandes.add(comandaActual);
                    ultimaComandaID = comandaId;
                }

                LiniaComanda linia = new LiniaComanda(
                        rs.getInt("linia_id"),
                        comandaId,
                        rs.getInt("producte_id"),
                        rs.getString("producteNom"),
                        rs.getDouble("descompte"),
                        rs.getInt("quantitat"),
                        rs.getDouble("preuUnitari")
                );

                comandaActual.getLinies().add(linia);
            }

        } catch (SQLException e) {
            System.err.println("Error obtenint comandes: " + e.getMessage());
            e.printStackTrace();
        }

        return comandes;
    }

}
