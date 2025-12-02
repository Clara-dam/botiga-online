/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import util.Connexio;

/**
 *
 * @author clara
 */
public class DescompteDAO {

    // Afegir Descompte
    public static void afegirDescompte(int producte_id, String tipus, double valor) {
        // Validem que el tipus sigui vàlid
        if (!tipus.equals("%") && !tipus.equals("€")) {
            System.out.println("Tipus de descompte no vàlid. Ha de ser '%' o '€'.");
            return;
        }

        String sql = "INSERT INTO Descompte (producte_id, tipus, valor) VALUES (?, ?, ?)";
        try (Connection conn = Connexio.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, producte_id);
            pstmt.setString(2, tipus);
            pstmt.setDouble(3, valor);
            pstmt.executeUpdate();
            System.out.println("✅ Descompte afegit correctament!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Eliminar Descompte per ID
    public static void eliminarDescompte(int id) {
        String sql = "DELETE FROM Descompte WHERE id = ?";
        try (Connection conn = Connexio.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int files = pstmt.executeUpdate();
            if (files > 0) {
                System.out.println("Descompte amb id " + id + " eliminat correctament!");
            } else {
                System.out.println("No s'ha trobat cap descompte amb id " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Actualitzar Descompte per ID
    public static void actualitzarDescompte(int id, String tipus, double valor) {
        // Validem que el tipus sigui vàlid
        if (!tipus.equals("%") && !tipus.equals("€")) {
            System.out.println("Tipus de descompte no vàlid. Ha de ser '%' o '€'.");
            return;
        }
        String sql = "UPDATE Descompte SET tipus = ?, valor = ? WHERE id = ?";
        try (Connection conn = Connexio.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tipus);
            pstmt.setDouble(2, valor);
            pstmt.setInt(3, id);
            int files = pstmt.executeUpdate();
            if (files > 0) {
                System.out.println("Descompte amb id " + id + " actualitzat a " + tipus + " i valor " + valor);
            } else {
                System.out.println("No s'ha trobat cap descompte amb id " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Llistar Descomptes
    public static void llistarDescomptes() {
        String sql = "SELECT * FROM Descompte";
        try (Connection conn = Connexio.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String tipus = rs.getString("tipus");
                System.out.printf("ID: %d | Producte ID: %d | Tipus: %s | Valor: %.2f%n",
                        rs.getInt("id"),
                        rs.getInt("producte_id"),
                        tipus,
                        rs.getDouble("valor"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
