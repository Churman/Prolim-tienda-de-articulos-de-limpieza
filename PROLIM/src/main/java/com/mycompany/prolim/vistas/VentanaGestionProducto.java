package com.mycompany.prolim.vistas;

import com.mycompany.prolim.modelos.Producto;
import com.mycompany.prolim.controladores.ControladorGestionProducto;
import java.util.Scanner;

public class VentanaGestionProducto {
    // Atributo que almacena el controlador para gestionar la lógica de productos
    private ControladorGestionProducto controlador;

    // Constructor de la clase VentanaGestionProducto
    public VentanaGestionProducto() {
        this.controlador = new ControladorGestionProducto(this); // Inicializa el controlador con la referencia de la vista
    }

    // Método que despliega el menú de gestión de productos
    public void menu(Scanner sc) {
        boolean salir = true; // Controla la salida del menú
        int opciones; // Variable para almacenar la opción seleccionada

        do {
            System.out.println("/********/Menu Productos/********/");
            System.out.println("1- Registrar nuevo producto.");
            System.out.println("2- Modificar producto.");
            System.out.println("3- Buscar producto.");
            System.out.println("4- Eliminar producto.");
            System.out.println("0- Volver al menu.");

            // Validación de entrada para asegurar que se ingresa un número
            while (!sc.hasNextInt()) {
                System.out.println("Error: Opción no válida.");
                sc.next(); // Descartar entrada no válida
                System.out.println("Por favor ingrese una opción válida.");
            }

            opciones = sc.nextInt(); // Captura la opción elegida

            switch (opciones) {
                case 1 -> { // Opción para registrar un nuevo producto
                    controlador.registrarInformacionProducto(
                        solicitarNombrePantalla("Ingrese el nombre del producto.", sc), 
                        solicitarCategoriaPantalla(sc), 
                        solicitarPrecioPantalla(sc)
                    );
                }
                case 2 -> { // Opción para modificar un producto
                    int posicionBuscado = controlador.buscarProductoPorNombre(
                        solicitarNombrePantalla("Ingrese el nombre del producto que desea modificar.", sc)
                    );

                    // Verifica si el producto existe antes de modificarlo
                    if (posicionBuscado != -1) {
                        Producto productoModificado = new Producto(
                            solicitarNombrePantalla("Ingrese el nombre del producto.", sc), 
                            solicitarCategoriaPantalla(sc), 
                            solicitarPrecioPantalla(sc)
                        );

                        // Solicita confirmación antes de realizar la modificación
                        if (solicitarConfirmacionOperacion(sc).equals("si")) {
                            controlador.modificarInformacionProducto(productoModificado, posicionBuscado);
                        } else {
                            System.out.println("Operación cancelada.");
                        }
                    } else {
                        System.out.println("No existe el producto que desea modificar.");
                    }
                }
                case 3 -> { // Opción para buscar un producto
                    int posicionBuscado = controlador.buscarProductoPorNombre(
                        solicitarNombrePantalla("Ingrese el nombre del producto que desea buscar.", sc)
                    );
                    // Muestra la información del producto si se encuentra
                    if (posicionBuscado != -1) {
                        controlador.mostrarCoincidencia(posicionBuscado);
                    } else {
                        System.out.println("No existe el producto ingresado.");
                    }
                }
                case 4 -> { // Opción para eliminar un producto
                    int posicionBuscado = controlador.buscarProductoPorNombre(
                        solicitarNombrePantalla("Ingrese el nombre del producto que desea eliminar.", sc)
                    );
                    // Verifica si el producto existe antes de eliminarlo
                    if (posicionBuscado != -1) {
                        // Solicita confirmación antes de realizar la eliminación
                        if (solicitarConfirmacionOperacion(sc).equals("si")) {
                            controlador.eliminarProducto(posicionBuscado);
                        } else {
                            System.out.println("Operación cancelada.");
                        }
                    } else {
                        System.out.println("No existe el producto que desea eliminar.");
                    }
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
    private String solicitarNombrePantalla(String mensaje, Scanner sc) {
        System.out.println(mensaje); // Muestra el mensaje al usuario
        return sc.next(); // Captura y retorna el nombre ingresado
    }
    
    // Método privado para solicitar la categoría del producto
    private String solicitarCategoriaPantalla(Scanner sc) {
        System.out.println("Ingrese la categoría del producto."); // Mensaje al usuario
        return sc.next(); // Captura y retorna la categoría ingresada
    }
    
    // Método privado para solicitar el precio del producto
    private float solicitarPrecioPantalla(Scanner sc) {
        System.out.println("Ingrese el precio del producto."); // Mensaje al usuario
        // Validación para asegurarse que el precio es un dato válido
        while (!sc.hasNextFloat()) {
            System.out.println("Error: Dato no válido.");
            sc.next(); // Descartar entrada no válida
            System.out.println("Por favor ingrese el precio nuevamente.");
        }
        return sc.nextFloat(); // Captura y retorna el precio ingresado
    }
    
    // Método privado para solicitar confirmación de la operación
    private String solicitarConfirmacionOperacion(Scanner sc) {
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