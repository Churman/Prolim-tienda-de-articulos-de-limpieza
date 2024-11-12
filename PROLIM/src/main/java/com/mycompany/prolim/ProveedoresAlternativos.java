/*package com.mycompany.prolim;

import com.mycompany.prolim.Proveedor;
import java.util.ArrayList;

public class ProveedoresAlternativos {
    // Atributos de la clase ProveedoresAlternativos
    private String producto;// Nombre del producto para el cual se almacenan proveedores alternativos
    private ArrayList<Proveedor> proveedoresAlternativos; // Lista de proveedores alternativos

    // Constructor de la clase ProveedoresAlternativos
    public ProveedoresAlternativos(String nombreProducto) {
        this.producto = nombreProducto; // Inicializa el nombre del producto
        this.proveedoresAlternativos = new ArrayList<>(); // Inicializa la lista de proveedores alternativos
    }

    // Método para obtener el nombre del producto
    public String getProducto() {
        return producto; // Retorna el nombre del producto
    }

    // Método para obtener la lista de proveedores alternativos
    public ArrayList<Proveedor> getProveedoresAlternativos() {
        return proveedoresAlternativos; // Retorna la lista de proveedores alternativos
    }
    
    // Método para mostrar la información de los proveedores alternativos
    public void mostrarProveedoresAlternativos() {
        // Recorre la lista y muestra por pantalla la información de cada proveedor
        for (int i = 0; i < proveedoresAlternativos.size(); i++) {
            System.out.println("Nombre: " + proveedoresAlternativos.get(i).getNombre());
            System.out.println("Apellido: " + proveedoresAlternativos.get(i).getApellido());
            System.out.println("Teléfono: " + proveedoresAlternativos.get(i).getTelefono());
            System.out.println("Mail: " + proveedoresAlternativos.get(i).getMail() + "\n");
        }
    }
    
    // Método para agregar un proveedor alternativo
    public boolean agregarProveedor(Proveedor proveedor) {
        boolean agregado = false;    // Variable para verificar si se agregó el proveedor        
        // Busca si el proveedor ya existe en la lista
        if(buscarProveedorAlternativoPorNombreCompleto(proveedor.getNombre(), proveedor.getApellido()) != null)
                System.out.println("Proveedor ya existente!"); // Muestra por pantalla un mensaje
        // Si no se encontró, se agrega el proveedor a la lista
        else{
            proveedoresAlternativos.add(proveedor);
            agregado = true; // Marca como agregado
        }
        return agregado; // Retorna true si se agregó, false si ya existía
    }

    // Método para eliminar un proveedor alternativo por nombre y apellido
    public boolean eliminarProveedorAlternativo(String nombre, String apellido) {
        boolean eliminado = false; // Variable para verificar si se eliminó el proveedor
        Proveedor proveedorEncontrado = buscarProveedorAlternativoPorNombreCompleto(nombre, apellido);
        // Busca el proveedor en la lista para eliminarlo
        if( proveedorEncontrado != null){
                proveedoresAlternativos.remove(proveedorEncontrado); // Elimina el proveedor de la lista
                eliminado = true; // Marca como eliminado
        }
        return eliminado; // Retorna true si se eliminó, false si no se encontró
    }
    
    // Método para verificar si la lista de proveedores alternativos está vacía
    public boolean estaVacio() {
        return proveedoresAlternativos.isEmpty(); // Retorna true si la lista está vacía, false si contiene elementos
    }
    
    // Método para buscar un proveedor segun su nombre y apellido entre los proveedores alternativos.
    private Proveedor buscarProveedorAlternativoPorNombreCompleto (String nombre, String apellido){
        Proveedor proveedorEncontrado = null;
        int i = 0;
        //Bucle que recorre la lista de proveedores alternativos.
        while (proveedorEncontrado == null && i < proveedoresAlternativos.size()) {
            if (proveedoresAlternativos.get(i).getNombre().equalsIgnoreCase(nombre) 
                    && proveedoresAlternativos.get(i).getApellido().equalsIgnoreCase(apellido))
                proveedorEncontrado = proveedoresAlternativos.get(i);
            i++;
        }
        //Devuelve null si no se encontro y en caso que se haya encontrado devuelve el mismo objeto.
        return proveedorEncontrado;
    }
    
}*/