package crudpoo.model;

public class Articulo extends Producto {

    private Categoria categoria;
    private int stock;

    public Articulo(String nombre, double precio, Categoria categoria, int stock) {
        super(nombre, precio);
        this.categoria = categoria;
        this.stock = stock;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public double calcularDescuento() {
        return getPrecio() * 0.9;
    }

    @Override
    public String toString() {
        return super.toString() +
                " | Categoría: " + categoria.getNombre() +
                " | Stock: " + stock;
    }
}
