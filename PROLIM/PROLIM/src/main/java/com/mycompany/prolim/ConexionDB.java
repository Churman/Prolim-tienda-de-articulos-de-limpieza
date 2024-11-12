package com.mycompany.prolim;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static Connection conexion;

    // Método que devuelve la conexión a la base de datos
    public static Connection obtenerConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/prolim", "root", "SYSTEM");
        }
        return conexion;
    }

    // Método para cerrar la conexión
    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}