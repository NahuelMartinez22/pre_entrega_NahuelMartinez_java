package crudpoo;

// ============================================================================
// Subclase Servicio
// ----------------------------------------------------------------------------
// - También "Extiende" Producto: Servicio ES-UN Producto.
// - Atributo propio: duracionHoras (sirve para diferenciarlo de Articulo).
// - Sobrescribe calcularDescuento() y toString().
// ============================================================================
public class Servicio extends Producto {

    private int duracionHoras; // atributo propio de Servicio

    // Constructor: usa "super" para nombre/precio y luego setea su propio campo
    public Servicio(String nombre, double precio, int duracionHoras) {
        super(nombre, precio);
        this.duracionHoras = duracionHoras;
    }

    // Getters/Setters
    public int getDuracionHoras() {
        return duracionHoras;
    }

    public void setDuracionHoras(int duracionHoras) {
        this.duracionHoras = duracionHoras;
    }

    // Implementación concreta del descuento para servicios (ejemplo 20%)
    @Override
    public double calcularDescuento() {
        return getPrecio() * 0.8;
    }

    // Imprime la info común + la duración
    @Override
    public String toString() {
        return super.toString() + ", duracion=" + duracionHoras + "h";
    }
}
