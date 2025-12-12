package dao;

/**
 *
 * @author clara
 */
import java.sql.*;
import util.Connexio;
import model.Producte;
import java.util.ArrayList;
import java.util.List;

public class ProducteDAO {

    // Afegir producte
    public static void afegirProducte(String nom, double preu, int estoc) {
        String sql = "INSERT INTO Producte (nom, preu, estoc) VALUES (?, ?, ?)";
        try (Connection conn = Connexio.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            pstmt.setDouble(2, preu);
            pstmt.setInt(3, estoc);
            pstmt.executeUpdate();
            System.out.println("✅ Producte afegit correctament!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Eliminar producte per ID
    public static void eliminarProducte(int id) {
        String sql = "DELETE FROM Producte WHERE id = ?";
        try (Connection conn = Connexio.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int files = pstmt.executeUpdate();
            if (files > 0) {
                System.out.println("Producte amb id " + id + " eliminat correctament!");
            } else {
                System.out.println("No s'ha trobat cap producte amb id " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Actualitzar preu per ID
    public static void actualitzarPreu(int id, double nouPreu) {
        String sql = "UPDATE Producte SET preu = ? WHERE id = ?";
        try (Connection conn = Connexio.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, nouPreu);
            pstmt.setInt(2, id);
            int files = pstmt.executeUpdate();
            if (files > 0) {
                System.out.println("Preu del producte amb id " + id + " actualitzat a " + nouPreu + " €");
            } else {
                System.out.println("No s'ha trobat cap producte amb id " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Llistar productes
    public static void llistarProductes() {
        String sql = "SELECT * FROM Producte";
        try (Connection conn = Connexio.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("%d - %s (%.2f €) Estoc: %d%n",
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getDouble("preu"),
                        rs.getInt("estoc"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static double getPreu(int id) {
        String sql = "SELECT preu FROM Producte WHERE id = ?";
        try (Connection conn = Connexio.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("preu");
            }
        } catch (SQLException ex) {
        }
        return 0;
    }
    /* Exemple d’ús
    public static void main(String[] args) {
        afegirProducte("Impressora", 120.0, 3);
        actualitzarPreu(1, 750.0);
        eliminarProducte(2);
    } */
}
