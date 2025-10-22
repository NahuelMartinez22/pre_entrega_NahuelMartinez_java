package crudpoo.model;

public class Categoria {

    private int id;
    private String nombre;
    private static int contador = 1;

    // 🔹 Constructor con solo nombre (autoincrementa)
    public Categoria(String nombre) {
        this.id = contador++;
        this.nombre = nombre;
    }

    // 🔹 Constructor con id fijo (NO altera el contador)
    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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

    @Override
    public String toString() {
        return "Categoria{id=" + id + ", nombre='" + nombre + "'}";
    }
}
