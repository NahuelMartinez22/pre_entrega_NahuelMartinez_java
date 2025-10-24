package crudpoo.model;

import java.util.ArrayList;

public class Pedido {
    private int id;
    private double total;
    private ArrayList<LineaPedido> lineas;

    public Pedido(int id) {
        this.id = id;
        this.total = 0.0;
        this.lineas = new ArrayList<>();
    }

    public Pedido(int id, double total) {
        this.id = id;
        this.total = total;
        this.lineas = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotal() {
        if (total == 0 && lineas != null && !lineas.isEmpty()) {
            for (LineaPedido lp : lineas) {
                total += lp.getSubtotal();
            }
        }
        return total;
    }

    public ArrayList<LineaPedido> getLineas() {
        return lineas;
    }

    public void agregarLinea(Producto producto, int cantidad) {
        lineas.add(new LineaPedido(
                producto.getId(),
                producto.getNombre(),
                cantidad,
                producto.getPrecio()
        ));
        total += producto.getPrecio() * cantidad;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Pedido #%d - Total: $%.2f\n", id, getTotal()));
        for (LineaPedido lp : lineas) {
            sb.append("  ").append(lp).append("\n");
        }
        return sb.toString();
    }
}
