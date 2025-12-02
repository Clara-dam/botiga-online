/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mysql.cj.xdevapi.Result;
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
public class ClientDAO {

    // Afegir Client
    public static void afegirClient(String nom, String correu) {
        String sql = "INSERT INTO Client (nom, correu) VALUES (?, ?)";
        try (Connection conn = Connexio.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nom);
            pstmt.setString(2, correu);
            pstmt.executeUpdate();
            System.out.println("✅ Client afegit correctament!");
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }

    // Eliminar Client per ID
    public static void eliminarClient(int id) {
        String sql = "DELETE FROM Client WHERE id = ?";
        try (Connection conn = Connexio.getConnection();) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            int files = pstmt.executeUpdate();
            if (files > 0) {
                System.out.println("Client amb id " + id + " eliminat correctament!");
            } else {
                System.out.println("No s'ha trobat cap Client amb id " + id);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Actualitzar correu Client
    public static void actualitzarDades (int id, String nom, String correu) {
        String sql = "Update Client SET nom = ?, SET correu = ? WHERE ID = ?";
        try (Connection conn = Connexio.getConnection();) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nom);
            pstmt.setString(2, correu);
            pstmt.setInt(3, id);
            int files = pstmt.executeUpdate();
            if (files > 0) {
                System.out.println("Client amb id " + id + 
                        ": \nNom actualitzat a " + nom + 
                        "\nCorreu actualitzar a " + correu);
            } else {
                System.out.println("No s'ha trobat cap Client amb id " + id);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Llistar Clients
    public static void llistarClients() {
        String sql = "SELECT * FROM Client";
        try {
            Connection conn = Connexio.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.printf("%d - %s. Correu: %s\n",
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("correu"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
