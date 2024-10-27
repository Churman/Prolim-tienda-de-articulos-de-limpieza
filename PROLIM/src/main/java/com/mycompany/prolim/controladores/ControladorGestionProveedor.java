package com.mycompany.prolim.controladores;

import com.mycompany.prolim.modelos.Proveedor;
import com.mycompany.prolim.modelos.ProveedoresAlternativos;
import com.mycompany.prolim.vistas.VentanaGestionProveedor;
import java.util.ArrayList;

public class ControladorGestionProveedor {
    // Ventana de gestión de proveedores asociada al controlador
    private VentanaGestionProveedor ventana;
    // Lista que almacena los proveedores
    private ArrayList<Proveedor> proveedores;
    // Lista que almacena los proveedores alternativos
    private ArrayList<ProveedoresAlternativos> proveedoresAlternativos; 

    // Constructor del controlador, inicializa la ventana y las listas de proveedores
    public ControladorGestionProveedor(VentanaGestionProveedor ventana){
        this.ventana = ventana;
        this.proveedores = new ArrayList<>();
        this.proveedoresAlternativos = new ArrayList<>();
    }

    // Método para registrar un nuevo proveedor
    public void registrarInformacionProveedor(String nombre, String apellido, String telefono, String mail, String producto) {
        // Verifica si el proveedor ya existe antes de agregarlo
        if(buscarProveedorPorNombreCompleto(nombre, apellido) != -1)
            System.out.println("Proveedor ya existente!");
        else{        
            // Captura y devuelve un nuevo proveedor
            this.proveedores.add(new Proveedor(nombre, apellido, telefono, mail, producto));
            ventana.mostrarConfirmacionOperacion(true);
        }
    }
    
    // Método para buscar un proveedor por su nombre completo
    public int buscarProveedorPorNombreCompleto(String nombreProvABuscar, String apellidoProvABuscar){
        for (int i = 0; i < proveedores.size(); i++)
            // Compara nombres y apellidos sin importar mayúsculas o minúsculas
            if (proveedores.get(i).getNombre().equalsIgnoreCase(nombreProvABuscar) 
                    && proveedores.get(i).getApellido().equalsIgnoreCase(apellidoProvABuscar))
                return i; // Retorna la posición del proveedor encontrado
        return -1; // Retorna -1 si no se encuentra el proveedor
    }

    // Método para modificar la información de un proveedor existente
    public void modificarInformacionProveedor(Proveedor proveedorModificado, int posicionProveedor){
        Proveedor proveedorActual = proveedores.get(posicionProveedor);
        // Actualiza la información del proveedor
        proveedorActual.setNombre(proveedorModificado.getNombre());
        proveedorActual.setApellido(proveedorModificado.getApellido());
        proveedorActual.setTelefono(proveedorModificado.getTelefono());
        proveedorActual.setMail(proveedorModificado.getMail());
        ventana.mostrarConfirmacionOperacion(true);
    }
    
    // Método para mostrar la información de un proveedor en base a su posición en la lista
    public void mostrarCoincidencia(int posicionActual){
        proveedores.get(posicionActual).mostrarInformacion();
    }
    
    // Método para eliminar un proveedor por su posición
    public void eliminarProveedor(int posicionBuscado) {
        if(proveedores.remove(posicionBuscado)!=null)
             ventana.mostrarConfirmacionOperacion(true);
        else
             ventana.mostrarConfirmacionOperacion(false);
    }
    
    // Método para buscar proveedores alternativos por producto
    public int buscarProveedorAlternativoSegunProducto(String nombreProducto){
        for (int i = 0; i < proveedoresAlternativos.size(); i++)
            if(proveedoresAlternativos.get(i).getProducto().equalsIgnoreCase(nombreProducto))
                return i; // Retorna la posición del proveedor alternativo encontrado
        return -1; // Retorna -1 si no se encuentra el proveedor alternativo
    }
    
    // Método para registrar un nuevo proveedor alternativo
    public void registrarProveedorAlternativo(Proveedor proveedorARegistrar){
        int posicion = buscarProveedorAlternativoSegunProducto(proveedorARegistrar.getProducto());
        if (posicion != -1) 
            // Si existe, agrega el proveedor al proveedor alternativo encontrado
            ventana.mostrarConfirmacionOperacion
                (proveedoresAlternativos.get(posicion).agregarProveedor(proveedorARegistrar)); // Muestra confirmación de operación
        else {
            // Si no existe, crea un nuevo proveedor alternativo y lo agrega a la lista
            proveedoresAlternativos.add(new ProveedoresAlternativos(proveedorARegistrar.getProducto())); // Agrega el nuevo proveedor alternativo a la lista
            ventana.mostrarConfirmacionOperacion
                (proveedoresAlternativos.getLast().agregarProveedor(proveedorARegistrar)); // Muestra confirmación
        } 
    }

    // Método para eliminar un proveedor alternativo
    public void eliminarProveedorAlternativo(int posicionBuscado, String nombre, String apellido) {
        ventana.mostrarConfirmacionOperacion
        (proveedoresAlternativos.get(posicionBuscado).eliminarProveedorAlternativo(nombre, apellido));
    }

    // Método para verificar si la lista de proveedores alternativos está vacía para un producto específico
    public boolean verificarListaAlternativaVaciaPorProducto(int posicionProducto) {
        return proveedoresAlternativos.get(posicionProducto).estaVacio();
    }

    // Método para mostrar información de proveedores alternativos
    public void mostrarInformacion(int posicionProducto) {
        proveedoresAlternativos.get(posicionProducto).mostrarProveedoresAlternativos();
    }
}