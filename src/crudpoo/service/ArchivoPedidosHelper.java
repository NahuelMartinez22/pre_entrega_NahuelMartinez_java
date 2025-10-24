package crudpoo.service;

import crudpoo.model.LineaPedido;
import crudpoo.model.Pedido;

import java.io.*;
import java.util.ArrayList;

public class ArchivoPedidosHelper {

    private static final String RUTA_ARCHIVO = "src/crudpoo/data/pedidos.txt";

    public static void guardarPedidos(ArrayList<Pedido> pedidos) {
        try {
            File archivo = new File(RUTA_ARCHIVO);
            archivo.getParentFile().mkdirs();

            try (FileWriter fw = new FileWriter(archivo)) {
                for (Pedido pedido : pedidos) {
                    fw.write("Pedido;" + pedido.getId() + ";" + pedido.getTotal() + "\n");
                    for (LineaPedido lp : pedido.getLineas()) {
                        fw.write("Linea;" + lp.getProductoId() + ";" + lp.getNombreProducto() + ";" +
                                lp.getCantidad() + ";" + lp.getPrecioUnitario() + "\n");
                    }
                    fw.write("END\n");
                }
            }
            System.out.println("Pedidos guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar pedidos: " + e.getMessage());
        }
    }

    public static ArrayList<Pedido> cargarPedidos() {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        File archivo = new File(RUTA_ARCHIVO);

        if (!archivo.exists()) {
            System.out.println("No hay archivo de pedidos, se iniciará vacío.");
            return pedidos;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            Pedido pedidoActual = null;

            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");

                if (partes[0].equalsIgnoreCase("Pedido")) {
                    int id = Integer.parseInt(partes[1]);
                    double total = partes.length > 2 ? Double.parseDouble(partes[2]) : 0.0;
                    pedidoActual = new Pedido(id);
                    pedidoActual.setTotal(total);
                }
                else if (partes[0].equalsIgnoreCase("Linea") && pedidoActual != null) {
                    int idProd = Integer.parseInt(partes[1]);
                    String nombre = partes[2];
                    int cantidad = Integer.parseInt(partes[3]);
                    double precio = Double.parseDouble(partes[4]);
                    pedidoActual.getLineas().add(new LineaPedido(idProd, nombre, cantidad, precio));
                }
                else if (partes[0].equalsIgnoreCase("END") && pedidoActual != null) {
                    pedidos.add(pedidoActual);
                    pedidoActual = null;
                }
            }
            if (pedidoActual != null) {
                pedidos.add(pedidoActual);
            }

            System.out.println("Pedidos cargados correctamente (" + pedidos.size() + ").");

        } catch (IOException e) {
            System.out.println("Error al cargar pedidos: " + e.getMessage());
        }
        return pedidos;
    }
}
