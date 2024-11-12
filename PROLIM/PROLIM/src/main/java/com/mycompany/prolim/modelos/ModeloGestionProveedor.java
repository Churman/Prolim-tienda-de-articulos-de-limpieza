package com.mycompany.prolim.modelos;

import com.mycompany.prolim.Proveedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModeloGestionProveedor {
    // Atributo que almacena la conexion a la base de datos
    private final Connection conexion;
    
    // Consultas SQL como constantes
    private static final String CONSULTA_PROVEEDOR_ID = "SELECT id_proveedor FROM proveedor WHERE nombre = ? AND apellido = ?";
    private static final String CONSULTA_PRODUCTO_ID = "SELECT id_producto FROM producto WHERE nombre = ?";
    private static final String CONSULTA_PROVEEDOR_NOMBRE_COMPLETO = "SELECT * FROM proveedor WHERE nombre = ? AND apellido = ?";
    private static final String CONSULTA_PROVEEDORES = "SELECT * FROM proveedor ORDER BY apellido ASC";
    private static final String CONSULTA_PROVEEDOR_PRODUCTO = "SELECT * FROM proveedor_producto WHERE id_proveedor = ? AND id_producto = ?";
    private static final String CONSULTA_PROVEEDOR_MEDIANTE_PROVEEDOR_PRODUCTO = "SELECT p.nombre, p.apellido, p.telefono, p.mail FROM proveedor p JOIN proveedor_producto pp ON p.id_proveedor = pp.id_proveedor WHERE pp.id_producto = ?";
    private static final String INSERTAR_PROVEEDOR = "INSERT INTO proveedor (nombre, apellido, telefono, mail) VALUES (?, ?, ?, ?)";
    private static final String INSERTAR_PROVEEDOR_PRODUCTO = "INSERT INTO proveedor_producto (id_proveedor, id_producto) VALUES (?, ?)";
    private static final String ACTUALIZAR_PROVEEDOR = "UPDATE proveedor SET nombre = ?, apellido = ?, telefono = ?, mail = ? WHERE nombre = ? AND apellido = ?";
    private static final String ELIMINAR_PROVEEDOR_PRODUCTO_IDPROVEEDOR = "DELETE FROM proveedor_producto WHERE id_proveedor = ?";
    private static final String ELIMINAR_PROVEEDOR_ID = "DELETE FROM proveedor WHERE id_proveedor = ?";
    private static final String ELIMINAR_PROVEEDOR_PRODUCTO_IDS = "DELETE FROM proveedor_producto WHERE id_proveedor = ? AND id_producto = ?";
    
    // Constructor
    public ModeloGestionProveedor(Connection conexion) {
        this.conexion = conexion;
    }

    // Método para obtener Id del proveedor de la base de datos
    // Retorna el valor del id, o en caso que no exista el proveedor retorna -1
    private int obtenerIdProveedor(String nombre, String apellido) throws SQLException {
        String consultaSQL = CONSULTA_PROVEEDOR_ID;
        try (PreparedStatement sentencia = conexion.prepareStatement(consultaSQL)) {
            sentencia.setString(1, nombre);
            sentencia.setString(2, apellido);
            ResultSet resultSet = sentencia.executeQuery();
            return resultSet.next() ? resultSet.getInt("id_proveedor") : -1;
        }
    }
    
    // Método para obtener Id del producto de la base de datos
    // Retorna el valor del id, o en caso que no exista el producto retorna -1
    private int obtenerIdProducto(String nombreProducto) throws SQLException {
        String consultaSQL = CONSULTA_PRODUCTO_ID;
        try (PreparedStatement sentencia = conexion.prepareStatement(consultaSQL)) {
            sentencia.setString(1, nombreProducto);
            ResultSet resultSet = sentencia.executeQuery();
            return resultSet.next() ? resultSet.getInt("id_producto") : -1;
        }
    }
    
    // Método para asignar los datos a un objeto Proveedor
    private Proveedor guardarInformacionProveedor(ResultSet resultSet) throws SQLException {
        return new Proveedor(
                resultSet.getString("nombre"),
                resultSet.getString("apellido"),
                resultSet.getString("telefono"),
                resultSet.getString("mail")
        );
    }

    // Método para obtener un proveedor por su nombre completo
    public Proveedor consultarProveedorPorNombreCompleto(String nombre, String apellido) {
        String consultaSQL = CONSULTA_PROVEEDOR_NOMBRE_COMPLETO;
        try (PreparedStatement sentencia = conexion.prepareStatement(consultaSQL)) {
            sentencia.setString(1, nombre);
            sentencia.setString(2, apellido);
            ResultSet resultSet = sentencia.executeQuery();
            if (resultSet.next()) 
                return guardarInformacionProveedor(resultSet);
        } catch (SQLException e) {
            System.err.println("Error al consultar proveedor: " + e.getMessage());
        }
        return null;
    }
    
    // Método para registrar un proveedor en la base de datos
    public boolean registrarProveedor(Proveedor proveedor, String nombreProducto) {
        String consultaProveedorSQL = INSERTAR_PROVEEDOR;
        String consultaRelacionSQL = INSERTAR_PROVEEDOR_PRODUCTO;
        
        try (PreparedStatement sentenciaProveedor = conexion.prepareStatement(consultaProveedorSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            sentenciaProveedor.setString(1, proveedor.getNombre());
            sentenciaProveedor.setString(2, proveedor.getApellido());
            sentenciaProveedor.setString(3, proveedor.getTelefono());
            sentenciaProveedor.setString(4, proveedor.getMail());
            sentenciaProveedor.executeUpdate();

            ResultSet generatedKeys = sentenciaProveedor.getGeneratedKeys();
            if (generatedKeys.next()) {
                int proveedorId = generatedKeys.getInt(1);
                int productoId = obtenerIdProducto(nombreProducto);

                if (productoId != -1) {
                    try (PreparedStatement sentenciaRelacion = conexion.prepareStatement(consultaRelacionSQL)) {
                        sentenciaRelacion.setInt(1, proveedorId);
                        sentenciaRelacion.setInt(2, productoId);
                        sentenciaRelacion.executeUpdate();
                    }
                } else {
                    System.err.println("Producto no encontrado. Operación cancelada.");
                    return false;
                }
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Error al registrar proveedor: " + e.getMessage());
            return false;
        }
    }
    
    // Método para modificar la información de un proveedor en la base de datos
    public boolean modificarInformacion(Proveedor proveedorModificado, Proveedor proveedorActual) {
        String consultaSQL = ACTUALIZAR_PROVEEDOR;
        try (PreparedStatement sentencia = conexion.prepareStatement(consultaSQL)) {
            sentencia.setString(1, proveedorModificado.getNombre());
            sentencia.setString(2, proveedorModificado.getApellido());
            sentencia.setString(3, proveedorModificado.getTelefono());
            sentencia.setString(4, proveedorModificado.getMail());
            sentencia.setString(5, proveedorActual.getNombre());
            sentencia.setString(6, proveedorActual.getApellido());

            return sentencia.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al modificar información del proveedor: " + e.getMessage());
        }
        return false;
    }

    // Método para eliminar un proveedor en la base de datos y sus relaciones en proveedor_producto
    public boolean eliminarProveedor(Proveedor proveedorAEliminar) throws SQLException {
        int proveedorId = obtenerIdProveedor(proveedorAEliminar.getNombre(), proveedorAEliminar.getApellido());
        if (proveedorId == -1) {
            return false;
        }

        try (PreparedStatement sentenciaEliminarRelacion = conexion.prepareStatement(ELIMINAR_PROVEEDOR_PRODUCTO_IDPROVEEDOR);
             PreparedStatement sentenciaEliminarProveedor = conexion.prepareStatement(ELIMINAR_PROVEEDOR_ID)) {
            
            sentenciaEliminarRelacion.setInt(1, proveedorId);
            sentenciaEliminarRelacion.executeUpdate();
            
            sentenciaEliminarProveedor.setInt(1, proveedorId);
            sentenciaEliminarProveedor.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar proveedor: " + e.getMessage());
        }
        return false;
    }

    // Método para obtener todos los proveedores en ordden alfabético según el apellido
    public ArrayList<Proveedor> obtenerProveedores() {
        ArrayList<Proveedor> proveedores = new ArrayList<>();
        String consultaSQL = CONSULTA_PROVEEDORES;
        try (PreparedStatement sentencia = conexion.prepareStatement(consultaSQL)) {
            ResultSet resultSet = sentencia.executeQuery();
            while (resultSet.next())
                proveedores.add(guardarInformacionProveedor(resultSet));
        } catch (SQLException e) {
            System.err.println("Error al obtener proveedores: " + e.getMessage());
        }
        return proveedores;
    }
    
    // Método para consultar por un proveedor en base a un producto
    // Retorna P sí el proveedor no exite
    // Retorna T sí el producto no exite
    // Retorna R sí el proveedor ya entrega ese producto
    // Retorna N sí el exite el proveedor y el producto pero no la relacion
    // Retorna E sí el hubo algún error al hacer las consultas
    public char consultarProveedorAlternativo(String nombreProveedor, String apellidoProveedor, String nombreProducto) {
        try {
            int idProveedor = obtenerIdProveedor(nombreProveedor, apellidoProveedor);
            if (idProveedor == -1) return 'P';

            int idProducto = obtenerIdProducto(nombreProducto);
            if (idProducto == -1) return 'T';

            try (PreparedStatement sentencia = conexion.prepareStatement(CONSULTA_PROVEEDOR_PRODUCTO)) {
                sentencia.setInt(1, idProveedor);
                sentencia.setInt(2, idProducto);
                return sentencia.executeQuery().next() ? 'R' : 'N';
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar proveedor alternativo: " + e.getMessage());
        }
        return 'E';
    }

    // Método para registrar una nueva relacion entre un producto y un proveedor ya existente
    // Es decir queda registrado que ese proveedor puede entregar dicho producto
    // Retorna verdadero en el caso en que se haya podido registrar, de lo contrario retorna falso
    public boolean registrarProveedorAlternativo(String nombreProveedor, String apellidoProveedor, String nombreProducto) throws SQLException {
        int idProveedor = obtenerIdProveedor(nombreProveedor, apellidoProveedor);
        int idProducto = obtenerIdProducto(nombreProducto);

        if (idProveedor == -1 || idProducto == -1) {
            return false;
        }

        try (PreparedStatement sentencia = conexion.prepareStatement( INSERTAR_PROVEEDOR_PRODUCTO)) {
            sentencia.setInt(1, idProveedor);
            sentencia.setInt(2, idProducto);
            sentencia.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al registrar proveedor alternativo: " + e.getMessage());
        }
        return false;
    }

    // Método para eliminar la relación entre un proveedor y un producto 
    // Es decir el proveedor ya no entregaría más ese producto
    // Retorna verdadero en el caso en que se haya podido eliminar, de lo contrario retorna falso
    public boolean eliminarProveedorAlternativo(String nombreProveedor, String apellidoProveedor, String nombreProducto) throws SQLException {
        int idProveedor = obtenerIdProveedor(nombreProveedor, apellidoProveedor);
        int idProducto = obtenerIdProducto(nombreProducto);

        if (idProveedor == -1 || idProducto == -1) {
            return false;
        }

        try (PreparedStatement sentencia = conexion.prepareStatement(ELIMINAR_PROVEEDOR_PRODUCTO_IDS)) {
            sentencia.setInt(1, idProveedor);
            sentencia.setInt(2, idProducto);
            sentencia.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar proveedor alternativo: " + e.getMessage());
        }
        return false;
    }

    // Método que retornar una lista con todos los proveedores en la base de datos
    // que se encargan de entregar el producto pasado por parámetro
    public ArrayList<Proveedor> obtenerProveedoresAlternativos(String nombreProducto) throws SQLException {
        ArrayList<Proveedor> proveedores = new ArrayList<>();
        int idProducto = obtenerIdProducto(nombreProducto);
        if (idProducto == -1) 
            return proveedores;
        String consultaSQL = CONSULTA_PROVEEDOR_MEDIANTE_PROVEEDOR_PRODUCTO;
        try (PreparedStatement sentencia = conexion.prepareStatement(consultaSQL)) {
            sentencia.setInt(1, idProducto);
            ResultSet resultSet = sentencia.executeQuery();
            while (resultSet.next())
                proveedores.add(guardarInformacionProveedor(resultSet));
        } catch (SQLException e) {
            System.err.println("Error al obtener proveedores alternativos: " + e.getMessage());
        }
        return proveedores;
    }
}