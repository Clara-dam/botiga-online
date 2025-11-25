/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import util.Connexio;

/**
 *
 * @author clara
 */
public class ClientDAO {
    // Afegir Client
    public static void afegirClient (String nom, String correu) {
        String sql = "INSERT INTO Client (nom, correu) VALUES (?, ?)";
        try (Connection conn = Connexio.getConnection()){
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
    public static void eliminarClient() {
        try (Connection conn = Connexio.getConnection();) {
            
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }
    
    
}
