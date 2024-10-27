package com.mycompany.prolim.vistas;

import com.mycompany.prolim.modelos.Proveedor;
import com.mycompany.prolim.controladores.ControladorGestionProveedor;
import java.util.Scanner;

public class VentanaGestionProveedor {
    
    // Controlador para gestionar la lógica relacionada con los proveedores
    private ControladorGestionProveedor controlador;
    
    // Constructor de la clase VentanaGestionProveedor
    public VentanaGestionProveedor (){
        // Inicializa el controlador y pasa la referencia de esta vista
        this.controlador = new ControladorGestionProveedor(this);
    }
    
    // Método que muestra el menú de gestión de proveedores
    public void menu(Scanner sc){
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
            System.out.println("0- Volver al menu.");

            // Verifica que la opción ingresada sea un entero
            while(!sc.hasNextInt()){
                System.out.println("Error: Opción no válida.");
                sc.next(); // Limpia la entrada
                System.out.println("Por favor ingrese una opción valida.");
            }

            opciones = sc.nextInt(); // Captura la opción seleccionada

            // Maneja la opción seleccionada
            switch(opciones){
                case 1 ->{
                    // Registra un nuevo proveedor
                    controlador.registrarInformacionProveedor
                    (solicitarNombrePantalla("Ingrese el nombre del proveedor.", sc), 
                     solicitarApellidoPantalla("Ingrese el apellido del proveedor.", sc), 
                     solicitarTelefonoPantalla(sc), 
                     solicitarMailPantalla(sc), 
                     solicitarProductoPantalla(sc));
                }
                case 2 ->{
                    // Modifica la información de un proveedor existente
                    int posicionBuscado = controlador.buscarProveedorPorNombreCompleto
                    (solicitarNombrePantalla("Ingrese el nombre del proveedor que desea modificar.", sc), 
                     solicitarApellidoPantalla("Ingrese el apellido del proveedor que desea modificar", sc));

                    if(posicionBuscado != -1 ){
                        // Solicita confirmación antes de modificar
                        if (solicitarConfirmacionOperacion(sc).equals("si"))
                            controlador.modificarInformacionProveedor(new Proveedor
                        (solicitarNombrePantalla("Ingrese el nuevo nombre del proveedor.", sc), 
                         solicitarApellidoPantalla("Ingrese el nuevo apellido del proveedor.", sc), 
                         solicitarTelefonoPantalla(sc), 
                         solicitarMailPantalla(sc), 
                         solicitarProductoPantalla(sc)), 
                         posicionBuscado);                     
                        else
                            System.out.println("Operación cancelada.");
                    }
                    else
                        System.out.println("No existe el proveedor que desea modificar.");
                }
                case 3 ->{
                    // Busca un proveedor por su nombre completo
                    int posicionBuscado = controlador.buscarProveedorPorNombreCompleto
                    (solicitarNombrePantalla("Ingrese el nombre del proveedor que desea buscar.", sc), 
                     solicitarApellidoPantalla("Ingrese el apellido del proveedor que desea buscar", sc));
                    if(posicionBuscado != -1)
                        controlador.mostrarCoincidencia(posicionBuscado);
                    else
                        System.out.println("No existe el proveedor ingresado.");
                }
                case 4 ->{
                    // Elimina un proveedor
                    int posicionBuscado = controlador.buscarProveedorPorNombreCompleto
                    (solicitarNombrePantalla("Ingrese el nombre del proveedor que desea eliminar.", sc), 
                     solicitarApellidoPantalla("Ingrese el apellido del proveedor que desea eliminar", sc));    
                    if(posicionBuscado != -1 )
                        controlador.eliminarProveedor(posicionBuscado);
                    else
                        System.out.println("No existe el proveedor que desea eliminar.");
                }
                case 5 ->{
                    // Registra un nuevo proveedor alternativo
                    Proveedor proveedorARegistrar = new Proveedor 
                    (solicitarNombrePantalla("Ingrese el nombre del proveedor.", sc), 
                     solicitarApellidoPantalla("Ingrese el apellido del proveedor.", sc), 
                     solicitarTelefonoPantalla(sc), 
                     solicitarMailPantalla(sc), 
                     solicitarProductoPantalla(sc));
                    controlador.registrarProveedorAlternativo(proveedorARegistrar);
                }
                case 6 ->{
                    // Elimina un proveedor alternativo
                    int posicionBuscado = controlador.buscarProveedorAlternativoSegunProducto(solicitarProductoPantalla(sc));
                    if(posicionBuscado != -1 )
                        controlador.eliminarProveedorAlternativo(posicionBuscado, 
                        solicitarNombrePantalla("Ingrese el nombre del proveedor que desea eliminar.", sc), 
                        solicitarApellidoPantalla("Ingrese el apellido del proveedor que desea eliminar", sc));
                    else
                        System.out.println("No existe el producto ingresado para eliminar el proveedor alternativo.");
                }
                case 7 ->{
                    // Sugiere proveedores alternativos basados en un producto
                    System.out.println("Ingrese el nombre del producto de los proveedores alternativos.");
                    int posicionProducto = controlador.buscarProveedorAlternativoSegunProducto(sc.next());
                    if(posicionProducto == -1)
                        System.out.println("No existen el producto ingresado para mostrar los proveedores alternativos.");
                    else if(controlador.verificarListaAlternativaVaciaPorProducto(posicionProducto))
                        System.out.println("No existen proveedores encargados de ese producto");
                    else
                        controlador.mostrarInformacion(posicionProducto);
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
    public void mostrarConfirmacionOperacion (boolean operacionExitosa){
        if(operacionExitosa)
            System.out.println("Operación realizada exitosamente!");
        else
            System.out.println("Operación fallida.");
    }
    
    // Solicita el nombre al usuario y lo devuelve
    private String solicitarNombrePantalla(String mensaje, Scanner sc){
        System.out.println(mensaje);
        return sc.next(); // Captura y devuelve el nombre
    }
    
    // Solicita el apellido al usuario y lo devuelve
    private String solicitarApellidoPantalla(String mensaje, Scanner sc){
        System.out.println(mensaje);
        return sc.next(); // Captura y devuelve el apellido
    }
    
    // Solicita el número de teléfono al usuario y lo devuelve
    private String solicitarTelefonoPantalla(Scanner sc){
        System.out.println("Ingrese el número de telefono del proveedor.");
        return sc.next(); // Captura y devuelve el teléfono
    }
    
    // Solicita el correo electrónico al usuario y lo devuelve
    private String solicitarMailPantalla(Scanner sc){
        System.out.println("Ingrese la dirección de correo electrónico del proveedor.");
        return sc.next(); // Captura y devuelve el correo electrónico
    }
    
    // Solicita el producto que entrega el proveedor y lo devuelve
    private String solicitarProductoPantalla(Scanner sc) {
        System.out.println("Ingrese el producto que entrega el proveedor.");
        return sc.next(); // Captura y devuelve el producto
    }
    
    // Solicita confirmación de operación y devuelve la respuesta
    private String solicitarConfirmacionOperacion(Scanner sc){
        System.out.println("¿Ésta seguro que desea realizar los cambios? si/no");
        String respuesta = sc.next(); // Captura la respuesta
        while (!respuesta.equals("si") && !respuesta.equals("no")){
            System.out.println("Por favor ingresar una opción válida.");
            respuesta = sc.next(); // Captura y devuelve nuevamente si la respuesta es inválida
        }
        return respuesta; // Devuelve la respuesta
    }
}