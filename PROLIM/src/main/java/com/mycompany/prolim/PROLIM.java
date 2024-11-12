package com.mycompany.prolim;

import com.mycompany.prolim.vistas.MenuPrincipalProlim;
import java.sql.SQLException;

public class PROLIM {
    public static void main(String[] args) throws SQLException {
        
        // Crea una instancia del menú principal
        MenuPrincipalProlim menuPrincipal = new MenuPrincipalProlim();
        
        // Llama al método del menú principal para mostrar las opciones
        menuPrincipal.menu();
        
    }
}