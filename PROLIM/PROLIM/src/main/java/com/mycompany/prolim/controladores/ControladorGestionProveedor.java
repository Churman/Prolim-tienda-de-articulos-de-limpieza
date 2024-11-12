package com.mycompany.prolim.controladores;

import com.mycompany.prolim.ConexionDB;
import com.mycompany.prolim.Proveedor;
import com.mycompany.prolim.modelos.ModeloGestionProveedor;
import com.mycompany.prolim.vistas.VentanaGestionProveedor;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ControladorGestionProveedor {
    // Atributo que almacena la vista asociada a este controlador
    private VentanaGestionProveedor ventana;
    // Atributo que almacena el modelo asociado a este controlador
    private ModeloGestionProveedor modelo;
    
    // Constructor del controlador, inicializa la ventana y el modelo
    public ControladorGestionProveedor(VentanaGestionProveedor ventana){
        this.ventana = ventana; // Asocia la vista al controlador
        try {
            Connection conexion = ConexionDB.obtenerConexion();
            this.modelo = new ModeloGestionProveedor(conexion);
        } catch (SQLException e) {
            System.err.println("Error de conexión a la base de datos: " + e.getMessage());
        }
    }

    // Método para registrar un nuevo proveedor
    public void registrarInformacionProveedor(String nombre, String apellido, String telefono, String mail, String producto) {
        Proveedor proveedorExistente = buscarProveedorPorNombreCompleto(nombre, apellido);
        if (proveedorExistente != null) {
            System.out.println("Proveedor ya existente! Operación cancelada.");
        } else {
            Proveedor proveedorARegistrar = new Proveedor(nombre, apellido, telefono, mail);
            ventana.mostrarConfirmacionOperacion(modelo.registrarProveedor(proveedorARegistrar, producto),""); // Confirma la operación exitosa
        }
    }
    
    // Método para buscar un proveedor por su nombre completo
    public Proveedor buscarProveedorPorNombreCompleto(String nombreProveedor, String apellidoProveedor){
        return modelo.consultarProveedorPorNombreCompleto(nombreProveedor , apellidoProveedor);
    }

    // Método para modificar la información de un proveedor existente
    public void modificarInformacionProveedor(Proveedor proveedorModificado, Proveedor proveedorActual){
        Boolean modificacion = modelo.modificarInformacion(proveedorModificado, proveedorActual);
        ventana.mostrarConfirmacionOperacion(modificacion,"");
    }
    
    // Método para mostrar la información de un proveedor
    public void mostrarCoincidencia(Proveedor proveedorActual){
        proveedorActual.mostrarInformacion();
    }
    
    // Método para eliminar un proveedor
    public void eliminarProveedor(Proveedor proveedorAEliminar) throws SQLException {
        ventana.mostrarConfirmacionOperacion(modelo.eliminarProveedor(proveedorAEliminar),"");
    }
    
    public void listarProveedores() {
        ArrayList<Proveedor> proveedores = modelo.obtenerProveedores();
        if(proveedores.isEmpty())
            System.out.println("No hay proveedores registrados.");
        else
            for(int i = 0; i < proveedores.size(); i++)
                proveedores.get(i).mostrarInformacion();
    }
    
    // Método para buscar proveedores alternativos por producto
    public char buscarProveedorAlternativoSegunProducto(String nombreProveedor, String apellidoProveedor, String nombreProducto){
        return modelo.consultarProveedorAlternativo(nombreProveedor , apellidoProveedor, nombreProducto);
    }
    
    // Método para registrar un nuevo proveedor alternativo
    public void registrarProveedorAlternativo(String nombreProveedor, String apellidoProveedor, String nombreProducto) throws SQLException{
        char estado = buscarProveedorAlternativoSegunProducto(nombreProveedor, apellidoProveedor, nombreProducto);
        switch (estado){
            case 'P' -> ventana.mostrarConfirmacionOperacion(false,"Proveedor no existente, por favor registre el nuevo proveedor.");
            case 'T' -> ventana.mostrarConfirmacionOperacion(false, "Producto no existente, por favor registre el nuevo producto desde el Menu de Productos.");
            case 'R' -> ventana.mostrarConfirmacionOperacion(false, "El proveedor ya entrega este producto.");
            case 'N' -> ventana.mostrarConfirmacionOperacion(modelo.registrarProveedorAlternativo(nombreProveedor, apellidoProveedor, nombreProducto), "");
            case 'E' -> ventana.mostrarConfirmacionOperacion(false, "Ocurrió un error al realizar la consulta.");
        }
    }

    // Método para eliminar un proveedor alternativo
    public void eliminarProveedorAlternativo(String nombreProveedor, String apellidoProveedor, String nombreProducto) throws SQLException {
        char estado = buscarProveedorAlternativoSegunProducto(nombreProveedor, apellidoProveedor, nombreProducto);
        switch (estado){
            case 'P' -> ventana.mostrarConfirmacionOperacion(false,"Proveedor no existente.");
            case 'T' -> ventana.mostrarConfirmacionOperacion(false, "Producto no existente.");
            case 'R' -> ventana.mostrarConfirmacionOperacion(modelo.eliminarProveedorAlternativo(nombreProveedor, apellidoProveedor, nombreProducto), "");
            case 'N' -> ventana.mostrarConfirmacionOperacion(false, "El proveedor  no entrega ese producto.");
            case 'E' -> ventana.mostrarConfirmacionOperacion(false, "Ocurrió un error al realizar la consulta.");
        }
    }

    // Método para mostrar información de proveedores segun el producto pasado por parámetro
    public void mostrarProveedoresPorProducto(String nombreProducto) throws SQLException {
        ArrayList<Proveedor> proveedores = modelo.obtenerProveedoresAlternativos(nombreProducto);
        if(proveedores.isEmpty())
            System.out.println("Este producto no posee proveedores.");
        else
            for(int i = 0; i < proveedores.size(); i++)
                proveedores.get(i).mostrarInformacion();
    }
}