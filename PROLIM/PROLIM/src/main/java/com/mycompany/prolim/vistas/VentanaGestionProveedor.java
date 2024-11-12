package com.mycompany.prolim.vistas;

import com.mycompany.prolim.Proveedor;
import com.mycompany.prolim.controladores.ControladorGestionProveedor;
import java.sql.SQLException;
import java.util.Scanner;

public class VentanaGestionProveedor {
    
    // Controlador para gestionar la lógica relacionada con los proveedores
    private final ControladorGestionProveedor controlador;
    private final Scanner sc;
    
    // Constructor de la clase VentanaGestionProveedor
    public VentanaGestionProveedor (Scanner sc){
        this.sc = sc; 
        // Inicializa el controlador y pasa la referencia de esta vista
        this.controlador = new ControladorGestionProveedor(this);
    }
    
    // Método que muestra el menú de gestión de proveedores
    public void menu() throws SQLException {
        boolean salir = true; // Variable para controlar el bucle del menú
        int opciones; // Variable para almacenar la opción seleccionada por el usuario
        do{
            // Muestra las opciones del menú
            System.out.println("/********/Menu Proveedores/********/");
            System.out.println("1- Registrar nuevo proveedor.");
            System.out.println("2- Modificar proveedor.");
            System.out.println("3- Buscar proveedor.");
            System.out.println("4- Eliminar proveedor.");
            System.out.println("5- Registrar nuevo proveedor alternativo.");
            System.out.println("6- Eliminar proveedor alternativo.");
            System.out.println("7- Sugerir proveedores alternativos.");
            System.out.println("8- Listar todos los proveedores.");
            System.out.println("0- Volver al menu.");

            // Verifica que la opción ingresada sea un entero
            while(!sc.hasNextInt()){
                System.out.println("Error: Opción no válida.");
                sc.next(); // Limpia la entrada
                System.out.println("Por favor ingrese una opción valida.");
            }

            opciones = sc.nextInt(); // Captura la opción seleccionada
            sc.nextLine();

            // Maneja la opción seleccionada
            switch(opciones){
                case 1 ->{
                    // Registra un nuevo proveedor
                    controlador.registrarInformacionProveedor
                    (solicitarNombrePantalla("Ingrese el nombre del proveedor."), 
                     solicitarApellidoPantalla("Ingrese el apellido del proveedor."), 
                     solicitarTelefonoPantalla(), 
                     solicitarMailPantalla(), 
                     solicitarProductoPantalla("Ingrese el producto que entrega el proveedor."));
                }
                case 2 ->{
                    // Modifica la información de un proveedor existente
                    Proveedor proveedorBuscado = controlador.buscarProveedorPorNombreCompleto
                    (solicitarNombrePantalla("Ingrese el nombre del proveedor que desea modificar."), 
                     solicitarApellidoPantalla("Ingrese el apellido del proveedor que desea modificar"));

                    if(proveedorBuscado != null ){
                        Proveedor proveedorModificado = new Proveedor
                        (solicitarNombrePantalla("Ingrese el nuevo nombre del proveedor."), 
                         solicitarApellidoPantalla("Ingrese el nuevo apellido del proveedor."), 
                         solicitarTelefonoPantalla(), 
                         solicitarMailPantalla());
                        // Solicita confirmación antes de modificar
                        if (solicitarConfirmacionOperacion().equals("si"))
                            controlador.modificarInformacionProveedor(proveedorModificado, proveedorBuscado);                     
                        else
                            System.out.println("Operación cancelada.");
                    } else
                        System.out.println("No existe el proveedor que desea modificar.");
                }
                case 3 ->{
                    // Busca un proveedor por su nombre completo
                    Proveedor proveedorBuscado = controlador.buscarProveedorPorNombreCompleto
                    (solicitarNombrePantalla("Ingrese el nombre del proveedor que desea buscar."), 
                     solicitarApellidoPantalla("Ingrese el apellido del proveedor que desea buscar"));
                    if(proveedorBuscado != null)
                        controlador.mostrarCoincidencia(proveedorBuscado);
                    else
                        System.out.println("No existe el proveedor ingresado.");
                }
                case 4 ->{
                    // Elimina un proveedor
                    Proveedor proveedorBuscado = controlador.buscarProveedorPorNombreCompleto
                    (solicitarNombrePantalla("Ingrese el nombre del proveedor que desea eliminar."), 
                     solicitarApellidoPantalla("Ingrese el apellido del proveedor que desea eliminar"));    
                    if(proveedorBuscado != null )
                        // Solicita confirmación antes de realizar la eliminación
                        if (solicitarConfirmacionOperacion().equals("si")) {
                            controlador.eliminarProveedor(proveedorBuscado);
                        } else {
                            System.out.println("Operación cancelada.");
                        }
                    else
                        System.out.println("No existe el proveedor que desea eliminar.");
                }
                case 5 ->{
                    // Registra un nuevo proveedor alternativo
                    controlador.registrarProveedorAlternativo(solicitarNombrePantalla("Ingrese el nombre del proveedor."), 
                     solicitarApellidoPantalla("Ingrese el apellido del proveedor."), 
                     solicitarProductoPantalla("Ingrese el producto que entrega el proveedor."));
                }
                case 6 ->{
                    // Elimina un proveedor alternativo
                    controlador.eliminarProveedorAlternativo(solicitarNombrePantalla("Ingrese el nombre del proveedor alternativo que desea eliminar."), 
                        solicitarApellidoPantalla("Ingrese el apellido del proveedor alternativo que desea eliminar"),
                        solicitarProductoPantalla("Ingrese el producto que entregaba el proveedor."));
                }
                case 7 ->{
                    // Sugiere proveedores alternativos basados en un producto
                    controlador.mostrarProveedoresPorProducto(solicitarProductoPantalla("Ingrese el nombre del producto de los prveedores alternativos."));   
                }
                case 8 ->{
                    controlador.listarProveedores();
                }
                case 0 ->{
                    // Sale del menú
                    salir = false;
                }
                default ->{
                    // Maneja opciones no válidas
                    System.out.println("Por favor ingrese una opción valida.");
                }
            }
        }while(salir); // Continúa mostrando el menú hasta que el usuario decida salir
    }
    
    // Muestra un mensaje de confirmación de operación
    public void mostrarConfirmacionOperacion (boolean operacionExitosa, String fallo){
        if(operacionExitosa)
            System.out.println("Operación realizada exitosamente!");
        else
            System.out.println("Operación fallida. " + fallo);
    }
    
    // Solicita el nombre al usuario y lo devuelve
    public String solicitarNombrePantalla(String mensaje){
        System.out.println(mensaje);
        return sc.nextLine(); // Captura y devuelve el nombre
    }
    
    // Solicita el apellido al usuario y lo devuelve
    public String solicitarApellidoPantalla(String mensaje){
        System.out.println(mensaje);
        return sc.nextLine(); // Captura y devuelve el apellido
    }
    
    // Solicita el número de teléfono al usuario y lo devuelve
    public String solicitarTelefonoPantalla(){
        System.out.println("Ingrese el número de telefono del proveedor.");
        return sc.nextLine(); // Captura y devuelve el teléfono
    }
    
    // Solicita el correo electrónico al usuario y lo devuelve
    public String solicitarMailPantalla(){
        System.out.println("Ingrese la dirección de correo electrónico del proveedor.");
        return sc.nextLine(); // Captura y devuelve el correo electrónico
    }
    
    // Solicita el producto que entrega el proveedor y lo devuelve
    public String solicitarProductoPantalla(String mensaje) {
        System.out.println(mensaje);
        return sc.nextLine(); // Captura y devuelve el producto
    }
    
    // Solicita confirmación de operación y devuelve la respuesta
    public String solicitarConfirmacionOperacion(){
        System.out.println("¿Ésta seguro que desea realizar los cambios? si/no");
        String respuesta = sc.next(); // Captura la respuesta
        while (!respuesta.equals("si") && !respuesta.equals("no")){
            System.out.println("Por favor ingresar una opción válida.");
            respuesta = sc.next(); // Captura y devuelve nuevamente si la respuesta es inválida
        }
        return respuesta; // Devuelve la respuesta
    }
}