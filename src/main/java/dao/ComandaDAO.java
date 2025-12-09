/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import model.Comanda;
import util.Connexio;

/**
 *
 * @author clara
 */
public class ComandaDAO {

    public static boolean crearComanda(Comanda comanda) {
        String sqlComanda = "INSERT INTO Comanda (client_id, data, total) VALUES (?,?,?)";
        String sqlLinia = "INSERT INTO LiniaComanda (comanda_id, producte_id, quantitat, preUnitari) VALUES (?,?,?,?)";
        String sqlDescompte = "UPDATE Comanda SET total = total - ? WHERE id = ?";

        try (Connection conn = Connexio.getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement ps1 = conn.prepareStatement(sqlComanda);
            ps1.setInt(1, comanda.getClient_id());
            ps1.setTimestamp(2, comanda.getData());
            ps1.setDouble(3, comanda.getTotal());
            
            ResultSet rs = ps1.getGeneratedKeys();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
