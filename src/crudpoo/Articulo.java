package crudpoo;

// ============================================================================
// Subclase Articulo
// ----------------------------------------------------------------------------
// - "Extiende" Producto (herencia): Articulo ES-UN Producto.
// - Agrega su atributo propio: Categoria (composición / objeto dentro de objeto).
// - Sobrescribe calcularDescuento() y toString() (polimorfismo).
// - Usa "super" en el constructor para reutilizar inicialización común de Producto.
// ============================================================================
public class Articulo extends Producto {

    private Categoria categoria;  // atributo propio de Articulo

    // Constructor: recibe nombre, precio y una categoría
    public Articulo(String nombre, double precio, Categoria categoria) {
        super(nombre, precio);    // llama al constructor de la SUPERCLASE Producto
        this.categoria = categoria; // guarda la categoría específica
    }

    // Getters/Setters
    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    // Implementación concreta del descuento para artículos (ejemplo 10%)
    @Override
    public double calcularDescuento() {
        return getPrecio() * 0.9;
    }

    // Reutilizamos la parte común de Producto y agregamos el detalle de categoría
    @Override
    public String toString() {
        return super.toString() + ", categoria=" + categoria.getNombre();
    }
}
