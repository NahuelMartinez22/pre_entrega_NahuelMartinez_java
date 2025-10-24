package crudpoo.model;

public class LineaPedido {
    private int productoId;
    private String nombreProducto;
    private int cantidad;
    private double precioUnitario;

    public LineaPedido(int productoId, String nombreProducto, int cantidad, double precioUnitario) {
        this.productoId = productoId;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public int getProductoId() {
        return productoId;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public double getSubtotal() {
        return precioUnitario * cantidad;
    }

    @Override
    public String toString() {
        return nombreProducto + " (x" + cantidad + ") - Subtotal: $" + String.format("%.2f", getSubtotal());
    }
}
