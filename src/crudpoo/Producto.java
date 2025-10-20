package crudpoo;

// ============================================================================
// Clase abstracta Producto (SUPERCLASE)
// ----------------------------------------------------------------------------
// - Es abstracta porque no se deben crear "productos genéricos" directamente.
// - Reúne atributos y comportamientos COMUNES a sus subclases (Articulo/Servicio).
// - Implementa la interfaz Vendible para obligar a que las subclases indiquen
//   cómo calculan su descuento (polimorfismo por interfaz + herencia).
// - Muestra: herencia, uso de super/this, variables de clase (static), encapsulamiento,
//   constructores y toString.
// ============================================================================
public abstract class Producto implements Vendible {

    // ---------------- Atributos privados (encapsulados) ----------------
    private int id;             // Identidad del producto
    private String nombre;      // Nombre legible
    private double precio;      // Precio base

    // --------------- Variable de CLASE (static) ------------------------
    private static int contador = 1; // Se comparte entre TODAS las instancias

    // -------------------- Constructor ----------------------------------
    public Producto(String nombre, double precio) {
        // "this" se refiere al objeto actual; usamos para asignar campos
        this.id = contador++;   // autoincrementa cada vez que se crea un producto
        this.nombre = nombre;   // guardamos el nombre recibido
        this.precio = precio;   // guardamos el precio recibido
    }

    // -------------------- Getters / Setters ----------------------------
    public int getId() {        // devolvemos el id (no hay setter para mantenerlo fijo)
        return id;
    }

    public String getNombre() { // leemos el nombre
        return nombre;
    }

    public void setNombre(String nombre) { // cambiamos el nombre
        this.nombre = nombre;
    }

    public double getPrecio() { // leemos el precio
        return precio;
    }

    public void setPrecio(double precio) { // cambiamos el precio
        this.precio = precio;
    }

    // ---- Método ABSTRACTO: cada subclase debe dar su propia implementación ---
    public abstract double calcularDescuento();

    // -------------------- toString (sobrescritura) ----------------------
    @Override
    public String toString() {
        // Representación genérica reutilizable desde las subclases con "super.toString()"
        return "Producto{id=" + id + ", nombre='" + nombre + "', precio=" + precio + "}";
    }
}
