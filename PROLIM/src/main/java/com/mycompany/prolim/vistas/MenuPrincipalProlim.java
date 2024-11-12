package com.mycompany.prolim.vistas;

import com.mycompany.prolim.ConexionDB;
import java.sql.SQLException;
import java.util.Scanner;

public class MenuPrincipalProlim {
    
    // Ventana de gestión de productos
    private final VentanaGestionProducto ventanaProd;
    // Ventana de gestión de proveedores
    private final VentanaGestionProveedor ventanaProv;
    private final Scanner sc;
    
    // Constructor del menú principal, inicializa las ventanas de gestión
    public MenuPrincipalProlim(){
        this.sc = new Scanner(System.in);
        this.ventanaProd = new VentanaGestionProducto(sc);
        this.ventanaProv = new VentanaGestionProveedor(sc);
        
    }
    
    // Método para mostrar el menú principal y manejar las opciones del usuario
    public void menu() throws SQLException {
        try (sc) {
            boolean salir = true; // Controla el bucle para salir del menú
            int opciones; // Almacena la opción seleccionada por el usuario
            
            do {
                // Muestra las opciones del menú
                System.out.println("/********/Menu/********/");
                System.out.println("1- Ingresar al menu de productos.");
                System.out.println("2- Ingresar al menu de proveedores.");
                System.out.println("0- Salir.");
                
                // Verifica que la entrada sea un entero válido
                while(!sc.hasNextInt()){
                    System.out.println("Error: Opción no válida.");
                    sc.next(); // Captura y descarta la entrada inválida
                    System.out.println("Por favor ingrese una opción valida.");
                }
                // Captura y devuelve la opción seleccionada
                opciones = sc.nextInt();
                
                // Manejo de las opciones del menú
                switch(opciones){
                    case 1 -> {
                        ventanaProd.menu(); // Llama al menú de gestión de productos
                    }
                    case 2 -> {
                        ventanaProv.menu(); // Llama al menú de gestión de proveedores
                    }
                    case 0 -> {
                        ConexionDB.cerrarConexion();
                        salir = false; // Cambia la variable para salir del bucle
                    }
                    default -> {
                        System.out.println("Por favor ingrese una opción valida."); // Mensaje de error para opción no válida
                    }
                }
            } while(salir); // Repite el bucle hasta que el usuario elija salir
        } // Controla el bucle para salir del menú
    }
}