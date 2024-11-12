package com.mycompany.prolim.controladores;

import com.mycompany.prolim.ConexionDB;
import com.mycompany.prolim.Producto;
import com.mycompany.prolim.modelos.ModeloGestionProducto;
import com.mycompany.prolim.vistas.VentanaGestionProducto;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ControladorGestionProducto {
    // Atributo que almacena la vista asociada a este controlador
    private VentanaGestionProducto ventana;  
    // Atributo que almacena el modelo asociado a este controlador
    private ModeloGestionProducto modelo;
    
    // Constructor del controlador, inicializa la ventana y el modelo
    public ControladorGestionProducto(VentanaGestionProducto ventana) {
        this.ventana = ventana; // Asocia la vista al controlador
        try {
            Connection conexion = ConexionDB.obtenerConexion();
            this.modelo = new ModeloGestionProducto(conexion);
        } catch (SQLException e) {
            System.err.println("Error de conexión a la base de datos: " + e.getMessage());
        }
    }
    
    // Método para registrar un nuevo producto
    public void registrarInformacionProducto(String nombre, String categoria, float precio) throws SQLException {
        Producto productoExistente = buscarProductoPorNombre(nombre);
        if (productoExistente != null) {
            System.out.println("Producto ya existente! Operación cancelada.");
        } else {
            Producto productoRegistrar = new Producto(nombre, categoria, precio);
            ventana.mostrarConfirmacionOperacion(modelo.registrarProducto(productoRegistrar)); // Confirma la operación exitosa
        }
    }
    
    // Método para buscar un producto por su nombre
    public Producto buscarProductoPorNombre(String productoABuscar) {
        return modelo.consultarProductoPorNombre(productoABuscar);
    }
    
    // Método para modificar la información de un producto existente
    public void modificarInformacionProducto(Producto productoModificado, Producto productoActual) {
        Boolean modificacion = modelo.modificarInformacion(productoModificado, productoActual);
        ventana.mostrarConfirmacionOperacion(modificacion);
    }

    // Método para mostrar la información de un producto 
    public void mostrarCoincidencia(Producto productoActual) {
        productoActual.mostrarInformacion();
    }
    
    // Método para eliminar un producto
    public void eliminarProducto(Producto productoActual) {
        ventana.mostrarConfirmacionOperacion(modelo.eliminarProducto(productoActual));
    }
    
    public void listarProductos() {
        ArrayList<Producto> productos = modelo.obtenerProductos();
        if(productos.isEmpty())
            System.out.println("No hay productos registrados.");
        else
            for(int i = 0; i < productos.size(); i++)
                productos.get(i).mostrarInformacion();
    }
}