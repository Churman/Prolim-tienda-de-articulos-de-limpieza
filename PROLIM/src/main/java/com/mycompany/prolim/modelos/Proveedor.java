package com.mycompany.prolim.modelos;

public class Proveedor {
    // Atributos de la clase Proveedor
    private String nombre;     // Nombre del proveedor
    private String apellido;   // Apellido del proveedor
    private String telefono;   // Número de teléfono del proveedor
    private String mail;       // Correo electrónico del proveedor
    private String producto;    // Producto que provee el proveedor

    // Constructor de la clase Proveedor
    public Proveedor(String nombre, String apellido, String telefono, String mail, String producto) {
        this.nombre = nombre;       // Inicializa el nombre del proveedor
        this.apellido = apellido;   // Inicializa el apellido del proveedor
        this.telefono = telefono;   // Inicializa el teléfono del proveedor
        this.mail = mail;           // Inicializa el correo electrónico del proveedor
        this.producto = producto;   // Inicializa el producto que provee el proveedor
    }

    // Método para establecer el nombre del proveedor
    public void setNombre(String nombre) {
        this.nombre = nombre; // Asigna un nuevo nombre al proveedor
    }

    // Método para establecer el apellido del proveedor
    public void setApellido(String apellido) {
        this.apellido = apellido; // Asigna un nuevo apellido al proveedor
    }

    // Método para establecer el teléfono del proveedor
    public void setTelefono(String telefono) {
        this.telefono = telefono; // Asigna un nuevo teléfono al proveedor
    }

    // Método para establecer el correo electrónico del proveedor
    public void setMail(String mail) {
        this.mail = mail; // Asigna un nuevo correo electrónico al proveedor
    }

    // Método para obtener el nombre del proveedor
    public String getNombre() {
        return nombre; // Retorna el nombre del proveedor
    }

    // Método para obtener el apellido del proveedor
    public String getApellido() {
        return apellido; // Retorna el apellido del proveedor
    }

    // Método para obtener el teléfono del proveedor
    public String getTelefono() {
        return telefono; // Retorna el teléfono del proveedor
    }

    // Método para obtener el correo electrónico del proveedor
    public String getMail() {
        return mail; // Retorna el correo electrónico del proveedor
    }
    
    // Método para obtener el producto que provee el proveedor
    public String getProducto() {
        return producto; // Retorna el producto que provee el proveedor
    }
    
    // Método para mostrar la información del proveedor
    public void mostrarInformacion() {
        // Muestra por pantalla el nombre, apellido, teléfono, correo y producto del proveedor
        System.out.println("Nombre: " + this.nombre);
        System.out.println("Apellido: " + this.apellido);
        System.out.println("Telefono: " + this.telefono);
        System.out.println("Mail: " + this.mail);
        System.out.println("Producto: " + this.producto);
    }
}