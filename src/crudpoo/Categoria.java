package crudpoo;

// ============================================================================
// Clase Categoria (clase simple del dominio)
// ----------------------------------------------------------------------------
// - Encapsula id y nombre.
// - Demuestra variables de clase (static) para un contador simple.
// - Será usada dentro de Articulo (objetos dentro de objetos).
// ============================================================================
public class Categoria {

    private int id;             // id único de la categoría
    private String nombre;      // nombre de la categoría

    private static int contador = 1; // contador compartido

    // Constructor: asigna id autoincremental y guarda el nombre
    public Categoria(String nombre) {
        this.id = contador++;
        this.nombre = nombre;
    }

    // Getters/Setters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // toString para imprimir prolijo en consola
    @Override
    public String toString() {
        return "Categoria{id=" + id + ", nombre='" + nombre + "'}";
    }
}
