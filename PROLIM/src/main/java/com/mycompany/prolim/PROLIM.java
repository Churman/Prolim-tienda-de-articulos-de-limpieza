package com.mycompany.prolim;

import com.mycompany.prolim.vistas.MenuPrincipalProlim;
import java.util.Scanner;

public class PROLIM {
    public static void main(String[] args) {
        
        // Crea una instancia del menú principal
        MenuPrincipalProlim menuPrincipal = new MenuPrincipalProlim();
        
        // Crea un objeto Scanner para capturar la entrada del usuario
        Scanner sc = new Scanner(System.in);
        
        // Llama al método del menú principal para mostrar las opciones
        menuPrincipal.menu(sc);
        
        // Cierra el scanner para liberar recursos
        sc.close();
    }
}