package crudpoo.service;

import crudpoo.model.*;
import java.util.ArrayList;

public class CrudPedidos extends CrudConsola<Pedido> {

    private final ArrayList<Producto> productos;
    private final ArrayList<Pedido> pedidos;

    public CrudPedidos(ArrayList<Producto> productos) {
        this.productos = productos;
        this.pedidos = ArchivoPedidosHelper.cargarPedidos();
    }

    @Override
    public void agregar() {
        if (productos.isEmpty()) {
            System.out.println("No hay productos o servicios disponibles para realizar pedidos.");
            return;
        }

        System.out.println("\n=== Crear nuevo pedido ===");
        Pedido pedido = new Pedido(pedidos.size() + 1);

        while (true) {
            System.out.println("\nProductos y servicios disponibles:");
            for (Producto p : productos) {
                if (p instanceof Articulo a) {
                    System.out.printf("%d) %-20s $%.2f (Stock: %d)\n",
                            p.getId(), p.getNombre(), p.getPrecio(), a.getStock());
                } else if (p instanceof Servicio s) {
                    System.out.printf("%d) %-20s $%.2f (Servicio)\n",
                            p.getId(), p.getNombre(), p.getPrecio());
                }
            }

            int id = leerEntero("ID del producto o servicio (0 para finalizar): ");
            if (id == 0) break;

            Producto seleccionado = null;
            for (Producto p : productos) {
                if (p.getId() == id) {
                    seleccionado = p;
                    break;
                }
            }

            if (seleccionado == null) {
                System.out.println("ID inválido. Intentá nuevamente.");
                continue;
            }

            int cantidad = 1;

            if (seleccionado instanceof Articulo a) {
                cantidad = leerEnteroPositivo("Cantidad deseada: ");
                if (cantidad > a.getStock()) {
                    System.out.println("Stock insuficiente. Disponible: " + a.getStock());
                    continue;
                }
                a.setStock(a.getStock() - cantidad);
            } else if (seleccionado instanceof Servicio) {
                final int idSeleccionado = seleccionado.getId();
                boolean yaExiste = pedido.getLineas().stream()
                        .anyMatch(lp -> lp.getProductoId() == idSeleccionado);

                if (yaExiste) {
                    System.out.println("Este servicio ya fue agregado al pedido. No se puede repetir.");
                    continue;
                }
                cantidad = 1;
            }

            pedido.agregarLinea(seleccionado, cantidad);
            System.out.println("Producto o servicio agregado al pedido.");
        }

        if (pedido.getLineas().isEmpty()) {
            System.out.println("Pedido vacío. No se guardará.");
            return;
        }

        System.out.println("\n=== Pedido a confirmar ===");
        System.out.printf("Pedido #%d\n", pedido.getId());
        for (LineaPedido lp : pedido.getLineas()) {
            System.out.printf("  %-25s (x%d) - Subtotal: $%.2f\n",
                    lp.getNombreProducto(),
                    lp.getCantidad(),
                    lp.getSubtotal());
        }
        System.out.printf("  → Total del pedido: $%.2f\n", pedido.getTotal());

        int confirmar = leerEntero("\n¿Confirmar pedido?\n1) Sí\n2) No\nOpción: ");
        if (confirmar != 1) {
            for (LineaPedido lp : pedido.getLineas()) {
                for (Producto p : productos) {
                    if (p instanceof Articulo a && a.getId() == lp.getProductoId()) {
                        a.setStock(a.getStock() + lp.getCantidad());
                    }
                }
            }
            System.out.println("Pedido cancelado. Stock restaurado.");
            return;
        }

        pedidos.add(pedido);
        ArchivoProductosHelper.guardarProductos(productos);
        ArchivoPedidosHelper.guardarPedidos(pedidos);
        System.out.printf("\nPedido #%d confirmado correctamente.\n", pedido.getId());
    }


    @Override
    public void listar() {
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos registrados.");
            return;
        }

        System.out.println("\n=== Lista de pedidos ===");

        for (Pedido pedido : pedidos) {
            System.out.printf("\nPedido #%d\n", pedido.getId());

            for (LineaPedido lp : pedido.getLineas()) {
                System.out.printf("  %-25s (x%d) - Subtotal: $%.2f\n",
                        lp.getNombreProducto(),
                        lp.getCantidad(),
                        lp.getSubtotal());
            }

            System.out.printf("  → Total del pedido: $%.2f\n", pedido.getTotal());
        }
    }

    public void cancelarPedido() {
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos para cancelar.");
            return;
        }

        listar();
        int id = leerEntero("Ingrese el ID del pedido que desea cancelar: ");

        Pedido pedidoAEliminar = null;
        for (Pedido p : pedidos) {
            if (p.getId() == id) {
                pedidoAEliminar = p;
                break;
            }
        }

        if (pedidoAEliminar == null) {
            System.out.println("No se encontró un pedido con ese ID.");
            return;
        }

        System.out.print("¿Está seguro de que desea cancelar este pedido? (s/n): ");
        String confirmacion = scanner.nextLine().trim().toLowerCase();
        if (!confirmacion.equals("s")) {
            System.out.println("Operación cancelada.");
            return;
        }

        for (LineaPedido lp : pedidoAEliminar.getLineas()) {
            for (Producto pr : productos) {
                if (pr instanceof Articulo a && a.getId() == lp.getProductoId()) {
                    a.setStock(a.getStock() + lp.getCantidad());
                    break;
                }
            }
        }

        pedidos.remove(pedidoAEliminar);
        ArchivoProductosHelper.guardarProductos(productos);
        ArchivoPedidosHelper.guardarPedidos(pedidos);
        System.out.println("Pedido cancelado correctamente y stock restaurado.");
    }

    @Override
    public void actualizar() {
    }

    @Override
    public void eliminar() {
        cancelarPedido();
    }

    @Override
    public void mostrarOpciones() {
        System.out.println("\n=== Menú de pedidos ===");
        System.out.println("1) Crear pedido");
        System.out.println("2) Listar pedidos");
        System.out.println("3) Cancelar pedido");
        System.out.println("0) Volver al menú principal");
        System.out.print("Opción: ");
    }

    @Override
    public void menu() {
        int opcion;
        do {
            mostrarOpciones();
            opcion = leerEntero("");

            switch (opcion) {
                case 1 -> agregar();
                case 2 -> listar();
                case 3 -> cancelarPedido();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida. Intentá nuevamente.");
            }
        } while (opcion != 0);
    }

}
