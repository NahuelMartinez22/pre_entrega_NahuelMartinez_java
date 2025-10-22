package crudpoo.main;

import crudpoo.model.Categoria;
import crudpoo.model.Producto;
import crudpoo.service.ArchivoProductosHelper;
import crudpoo.service.CrudProductos;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ArrayList<Categoria> categorias = new ArrayList<>();
        categorias.add(new Categoria(1, "Placa de video"));
        categorias.add(new Categoria(2, "Procesador"));

        ArrayList<Producto> productos = ArchivoProductosHelper.cargarProductos(categorias);

        CrudProductos crudProductos = new CrudProductos(productos, categorias);

        int opcion;
        do {
            System.out.println("\n=== Menú Principal ===");
            System.out.println("1) Productos");
            System.out.println("2) Pedidos");
            System.out.println("0) Salir");

            opcion = crudProductos.leerEntero("Opción: ");

            switch (opcion) {
                case 1 -> crudProductos.menu();
                case 2 -> System.out.println("Gestión de pedidos (pendiente de implementar).");
                case 0 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);

        ArchivoProductosHelper.guardarProductos(productos);
    }
}
