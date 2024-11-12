package com.mycompany.prolim.modelos;

import com.mycompany.prolim.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModeloGestionProducto {
    // Atributo que almacena la conexion a la base de datos
    private final Connection conexion;

    // Consultas SQL como constantes.
    private static final String CONSULTA_PRODUCTO = "SELECT * FROM producto ORDER BY  nombre ASC";
    private static final String CONSULTAR_PRODUCTO_ID = "SELECT id_producto FROM producto WHERE nombre = ?";
    private static final String CONSULTA_PRODUCTO_NOMBRE = "SELECT * FROM producto WHERE nombre = ?";
    private static final String INSERTAR_PRODUCTO = "INSERT INTO producto (nombre, categoria, precio) VALUES (?, ?, ?)";
    private static final String ACTUALIZAR_PRODUCTO = "UPDATE Producto SET nombre = ?, categoria = ?, precio = ? WHERE nombre = ?";
    private static final String ELIMINAR_RELACIONES_PROVEEDOR_PRODUCTO = "DELETE FROM proveedor_producto WHERE id_producto = ?";
    private static final String ELIMINAR_PRODUCTO_ID = "DELETE FROM producto WHERE id_producto = ?";
    
    // Constructor.
    public ModeloGestionProducto(Connection conexion) {
        this.conexion = conexion;
    }

    // Método para asignar los datos a un objeto Producto.
    private Producto guardarInformacionProducto(ResultSet resultSet) throws SQLException {
        // Crea y retorna un producto a partir de los datos obtenidos del ResultSet
        return new Producto(
                resultSet.getString("nombre"),
                resultSet.getString("categoria"),
                resultSet.getFloat("precio")
        );
    }

    // Método para obtener un producto por nombre
    public Producto consultarProductoPorNombre(String nombre) {
        Producto producto = null;
        try (PreparedStatement sentencia = conexion.prepareStatement(CONSULTA_PRODUCTO_NOMBRE)) {
            // Establece el nombre del producto que se desea consultar
            sentencia.setString(1, nombre);
            try (ResultSet resultSet = sentencia.executeQuery()) {
                // Si se encuentra el producto, lo guarda en el objeto producto
                if (resultSet.next()) 
                    producto = guardarInformacionProducto(resultSet);
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar el producto" + e.getMessage());
        }
        return producto;
    }

    // Método para registrar un producto en la base de datos
    public boolean registrarProducto(Producto producto) {
        boolean registrado = false;
        try (PreparedStatement sentencia = conexion.prepareStatement(INSERTAR_PRODUCTO)) {
            // Establece los parámetros del producto que se va a registrar
            sentencia.setString(1, producto.getNombre());
            sentencia.setString(2, producto.getCategoria());
            sentencia.setFloat(3, producto.getPrecio());
            registrado = sentencia.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar el producto" + e.getMessage());
        }
        return registrado;
    }

    // Método para modificar la información de un producto en la base de datos
    public boolean modificarInformacion(Producto productoModificado, Producto productoActual) {
        boolean actualizado = false;
        try (PreparedStatement sentencia = conexion.prepareStatement(ACTUALIZAR_PRODUCTO)) {
            // Establece los parámetros del producto modificado y el producto actual para actualizar
            sentencia.setString(1, productoModificado.getNombre());
            sentencia.setString(2, productoModificado.getCategoria());
            sentencia.setDouble(3, productoModificado.getPrecio());
            sentencia.setString(4, productoActual.getNombre());
            actualizado = sentencia.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al modificar el producto" + e.getMessage());
        }
        return actualizado;
    }

    // Método para eliminar un producto de la base de datos y sus relaciones en proveedor_producto
    public boolean eliminarProducto(Producto productoAEliminar) {
        boolean eliminado = false;
        int idProducto;
        try {
            // Obtener el ID del producto a eliminar
            try (PreparedStatement sentenciaConsultaID = conexion.prepareStatement(CONSULTAR_PRODUCTO_ID)) {
                sentenciaConsultaID.setString(1, productoAEliminar.getNombre());
                ResultSet resultSet = sentenciaConsultaID.executeQuery();
                if (resultSet.next()) {
                    idProducto = resultSet.getInt("id_producto");
                } else {
                    System.err.println("Producto no encontrado en la base de datos.");
                    return false; // Salir si el producto no existe
                }
            }

            // Eliminar relaciones en proveedor_producto antes de eliminar el producto
            try (PreparedStatement sentenciaRelacion = conexion.prepareStatement(ELIMINAR_RELACIONES_PROVEEDOR_PRODUCTO)) {
                sentenciaRelacion.setInt(1, idProducto);
                sentenciaRelacion.executeUpdate();
            }

            // Eliminar el producto
            try (PreparedStatement sentenciaProducto = conexion.prepareStatement(ELIMINAR_PRODUCTO_ID)) {
                sentenciaProducto.setInt(1, idProducto);
                eliminado = sentenciaProducto.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar el producto y sus relaciones" + e.getMessage());
        }
        return eliminado;
    }
    
    // Método para obtener todos los productos en ordden alfabético según el nombre
    public ArrayList<Producto> obtenerProductos() {
        ArrayList<Producto> productos = new ArrayList<>();
        try (PreparedStatement sentenciaProducto = conexion.prepareStatement(CONSULTA_PRODUCTO)) {
            ResultSet resultSetProducto = sentenciaProducto.executeQuery();
            while (resultSetProducto.next()) {
                // Agrega cada producto al listado de productos
                productos.add(guardarInformacionProducto(resultSetProducto));
            }
        }
        catch (SQLException e) {
            System.err.println("Error al obtener los productos: " + e.getMessage());
        }
        return productos;
    }
}