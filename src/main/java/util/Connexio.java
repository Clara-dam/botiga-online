package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexio {
    private static final String URL = "jdbc:mysql://localhost:3306/botiga";
    private static final String USER = "clara";
    private static final String PASSWORD = "User1234";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    public static void testConnexio() {
        try (Connection conn = getConnection()) {
            System.out.println("Connexió correcta a la BD. Benvingut!");
        } catch (Exception e) {
            System.out.println("Error de connexió: " + e.getMessage());
        }
    }
}
    
