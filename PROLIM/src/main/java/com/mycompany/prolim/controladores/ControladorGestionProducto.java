package com.mycompany.prolim.controladores;

import com.mycompany.prolim.modelos.Producto;
import com.mycompany.prolim.vistas.VentanaGestionProducto;
import java.util.ArrayList;

public class ControladorGestionProducto {
    // Atributo que almacena la vista asociada a este controlador
    private VentanaGestionProducto ventana;
    // Lista de productos gestionados por este controlador
    private ArrayList<Producto> productos;

    // Constructor del controlador, inicializa la vista y la lista de productos
    public ControladorGestionProducto(VentanaGestionProducto ventana) {
        this.ventana = ventana; // Asocia la vista al controlador
        this.productos = new ArrayList<>(); // Inicializa la lista de productos
    }
    
    // Método para registrar un nuevo producto
    public void registrarInformacionProducto(String nombre, String categoria, float precio) {
        // Verifica si el producto ya existe
        if (buscarProductoPorNombre(nombre) != -1) {
            System.out.println("Producto ya existente! Operación cancelada."); // Mensaje de error
        } else {
            // Agrega el nuevo producto a la lista
            this.productos.add(new Producto(nombre, categoria, precio));
            ventana.mostrarConfirmacionOperacion(true); // Confirma la operación exitosa
        }
    }
    
    // Método para buscar un producto por su nombre
    public int buscarProductoPorNombre(String productoABuscar) {
        for (int i = 0; i < productos.size(); i++) {
            // Compara el nombre del producto sin considerar mayúsculas/minúsculas
            if (productos.get(i).getNombre().equalsIgnoreCase(productoABuscar)) {
                return i; // Retorna la posición del producto encontrado
            }
        }
        return -1; // Retorna -1 si no se encuentra el producto
    }
    
    // Método para modificar la información de un producto existente
    public void modificarInformacionProducto(Producto productoModificado, int posicionProducto) {
        Producto productoActual = productos.get(posicionProducto); // Obtiene el producto actual
        // Actualiza los atributos del producto
        productoActual.setNombre(productoModificado.getNombre());
        productoActual.setCategoria(productoModificado.getCategoria());
        productoActual.setPrecio(productoModificado.getPrecio());
        ventana.mostrarConfirmacionOperacion(true); // Confirma la operación exitosa
    }

    // Método para mostrar la información de un producto en base a su posición
    public void mostrarCoincidencia(int posicionActual) {
        productos.get(posicionActual).mostrarInformacion(); // Muestra la información del producto
    }
    
    // Método para eliminar un producto de la lista
    public void eliminarProducto(int posicionBuscado) {
        // Intenta eliminar el producto y confirma la operación
        if (productos.remove(posicionBuscado) != null) {
            ventana.mostrarConfirmacionOperacion(true); // Confirmación de éxito
        } else {
            ventana.mostrarConfirmacionOperacion(false); // Confirmación de fallo
        }
    }
}