package com.mycompany.prolim;

public class Producto {
    // Atributos de la clase Producto
    private String nombre;     // Nombre del producto
    private String categoria;  // Categoría a la que pertenece el producto
    private float precio;      // Precio del producto

    // Constructor de la clase Producto
    public Producto(String nombre, String categoria, float precio) {
        this.nombre = nombre;      // Inicializa el nombre del producto
        this.categoria = categoria; // Inicializa la categoría del producto
        this.precio = precio;      // Inicializa el precio del producto
    }

    // Método para establecer el nombre del producto
    public void setNombre(String nombre) {
        this.nombre = nombre; // Asigna un nuevo nombre al producto
    }

    // Método para establecer la categoría del producto
    public void setCategoria(String categoria) {
        this.categoria = categoria; // Asigna una nueva categoría al producto
    }

    // Método para establecer el precio del producto
    public void setPrecio(float precio) {
        this.precio = precio; // Asigna un nuevo precio al producto
    }

    // Método para obtener el nombre del producto
    public String getNombre() {
        return this.nombre; // Retorna el nombre del producto
    }

    // Método para obtener la categoría del producto
    public String getCategoria() {
        return this.categoria; // Retorna la categoría del producto
    }

    // Método para obtener el precio del producto
    public float getPrecio() {
        return this.precio; // Retorna el precio del producto
    }

    // Método para mostrar la información del producto
    public void mostrarInformacion() {
        // Muestra por pantalla el nombre, categoría y precio del producto en la consola
        System.out.println("Nombre: " + this.nombre);
        System.out.println("Categoria: " + this.categoria);
        System.out.println("Precio: $" + this.precio);
        System.out.println("");
    }
}