package crudpoo.model;

import crudpoo.service.Vendible;

public abstract class Producto implements Vendible {

    private int id;
    private String nombre;
    private double precio;
    private static int contador = 1;

    public Producto(String nombre, double precio) {
        this.id = contador++;
        this.nombre = nombre;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        if (id >= contador) {
            contador = id + 1;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public abstract double calcularDescuento();

    @Override
    public String toString() {
        return "Producto{id=" + id + ", nombre='" + nombre + "', precio=" + precio + "}";
    }
}
