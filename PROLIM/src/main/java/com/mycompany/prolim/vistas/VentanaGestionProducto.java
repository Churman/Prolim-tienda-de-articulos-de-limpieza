package com.mycompany.prolim.vistas;

import com.mycompany.prolim.Producto;
import com.mycompany.prolim.controladores.ControladorGestionProducto;
import java.sql.SQLException;
import java.util.Scanner;

public class VentanaGestionProducto {
    // Atributo que almacena el controlador para gestionar la lógica de productos
    private final ControladorGestionProducto controlador;
    private final Scanner sc;
    
    // Constructor de la clase VentanaGestionProducto
    public VentanaGestionProducto(Scanner sc) {
        this.sc = sc;
        this.controlador = new ControladorGestionProducto(this); // Inicializa el controlador con la referencia de la vista
    }

    // Método que despliega el menú de gestión de productos
    public void menu() throws SQLException {
        boolean salir = true; // Controla la salida del menú
        int opciones; // Variable para almacenar la opción seleccionada

        do {
            System.out.println("/********/Menu Productos/********/");
            System.out.println("1- Registrar nuevo producto.");
            System.out.println("2- Modificar producto.");
            System.out.println("3- Buscar producto.");
            System.out.println("4- Eliminar producto.");
            System.out.println("5- Listar productos.");
            System.out.println("0- Volver al menu.");

            // Validación de entrada para asegurar que se ingresa un número
            while (!sc.hasNextInt()) {
                System.out.println("Error: Opción no válida.");
                sc.next(); // Descartar entrada no válida
                System.out.println("Por favor ingrese una opción válida.");
            }

            opciones = sc.nextInt(); // Captura la opción elegida
            sc.nextLine();

            switch (opciones) {
                case 1 -> { // Opción para registrar un nuevo producto
                    controlador.registrarInformacionProducto(
                        solicitarNombrePantalla("Ingrese el nombre del producto."), 
                        solicitarCategoriaPantalla(), 
                        solicitarPrecioPantalla()
                    );
                }
                case 2 -> { // Opción para modificar un producto
                    Producto productoEncontrado = controlador.buscarProductoPorNombre(
                        solicitarNombrePantalla("Ingrese el nombre del producto que desea modificar.")
                    );

                    // Verifica si el producto existe antes de modificarlo
                    if (productoEncontrado != null) {
                        Producto productoModificado = new Producto(
                            solicitarNombrePantalla("Ingrese el nombre del producto."), 
                            solicitarCategoriaPantalla(), 
                            solicitarPrecioPantalla()
                        );

                        // Solicita confirmación antes de realizar la modificación
                        if (solicitarConfirmacionOperacion().equals("si")) {
                            controlador.modificarInformacionProducto(productoModificado, productoEncontrado);
                        } else {
                            System.out.println("Operación cancelada.");
                        }
                    }else {
                        System.out.println("No existe el producto que desea modificar.");
                    }
                }
                case 3 -> { // Opción para buscar un producto
                    Producto productoBuscado = controlador.buscarProductoPorNombre(
                        solicitarNombrePantalla("Ingrese el nombre del producto que desea buscar."));
                    // Muestra la información del producto si se encuentra
                    if (productoBuscado != null) {
                        controlador.mostrarCoincidencia(productoBuscado);
                    } else {
                        System.out.println("No existe el producto ingresado.");
                    }
                }
                case 4 -> { // Opción para eliminar un producto
                    Producto productoBuscado = controlador.buscarProductoPorNombre(
                        solicitarNombrePantalla("Ingrese el nombre del producto que desea eliminar.")
                    );
                    // Verifica si el producto existe antes de eliminarlo
                    if (productoBuscado != null) {
                        // Solicita confirmación antes de realizar la eliminación
                        if (solicitarConfirmacionOperacion().equals("si")) {
                            controlador.eliminarProducto(productoBuscado);
                        } else {
                            System.out.println("Operación cancelada.");
                        }
                    } else {
                        System.out.println("No existe el producto que desea eliminar.");
                    }
                }
                case 5 ->{
                    controlador.listarProductos();
                }
                case 0 -> { // Opción para salir del menú
                    salir = false; // Cambia el estado de salir a false para finalizar el bucle
                }
                default -> { // Manejo de opción no válida
                    System.out.println("Por favor ingrese una opción válida.");
                }
            }
        } while (salir); // Continua mostrando el menú hasta que el usuario elija salir
    }
    
    // Método para mostrar confirmación de operación
    public void mostrarConfirmacionOperacion(boolean operacionExitosa) {
        if (operacionExitosa)
            System.out.println("Operación realizada exitosamente!"); // Mensaje de éxito
        else
            System.out.println("Hubo un error. Operación fallida."); // Mensaje de fallo
    }
    
    // Método privado para solicitar el nombre del producto
    public String solicitarNombrePantalla(String mensaje) {
        System.out.println(mensaje); // Muestra el mensaje al usuario
        return sc.nextLine(); // Captura y retorna el nombre ingresado
    }
    
    // Método privado para solicitar la categoría del producto
    public String solicitarCategoriaPantalla() {
        System.out.println("Ingrese la categoría del producto."); // Mensaje al usuario
        return sc.nextLine(); // Captura y retorna la categoría ingresada
    }
    
    // Método privado para solicitar el precio del producto
    public float solicitarPrecioPantalla() {
        System.out.println("Ingrese el precio del producto."); // Mensaje al usuario
        // Validación para asegurarse que el precio es un dato válido
        while (!sc.hasNextFloat()) {
            System.out.println("Error: Dato no válido.");
            sc.next(); // Descartar entrada no válida
            System.out.println("Por favor ingrese el precio nuevamente.");
        }
        float precio = sc.nextFloat();
        sc.nextLine();
        return precio; // Captura y retorna el precio ingresado
    }
    
    // Método privado para solicitar confirmación de la operación
    public String solicitarConfirmacionOperacion() {
        System.out.println("¿Está seguro que desea realizar los cambios? si/no"); // Mensaje al usuario
        String respuesta = sc.next(); // Captura la respuesta
        // Validación de la respuesta para asegurarse que es 'si' o 'no'
        while (!respuesta.equals("si") && !respuesta.equals("no")) {
            System.out.println("Por favor ingresar una opción válida.");
            respuesta = sc.next(); // Captura nuevamente si la respuesta es inválida
        }
        return respuesta; // Retorna la respuesta válida
    }
}