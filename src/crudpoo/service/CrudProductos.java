package crudpoo.service;

import crudpoo.model.Articulo;
import crudpoo.model.Categoria;
import crudpoo.model.Producto;
import crudpoo.model.Servicio;

import java.util.ArrayList;

public class CrudProductos extends CrudConsola<Producto> {

    private final ArrayList<Producto> productos;
    private final ArrayList<Categoria> categorias;

    public CrudProductos(ArrayList<Producto> productos, ArrayList<Categoria> categorias) {
        this.productos = productos;
        this.categorias = categorias;
    }

    @Override
    public void mostrarOpciones() {
        System.out.println("\n=== Menú Productos ===");
        System.out.println("1) Crear");
        System.out.println("2) Listar");
        System.out.println("3) Actualizar");
        System.out.println("4) Eliminar");
        System.out.println("0) Volver");
    }

    @Override
    public void crear() {
        System.out.println("\n¿Qué tipo de producto querés crear?");
        System.out.println("1) Artículo");
        System.out.println("2) Servicio");
        int tipo = leerEntero("Elegí una opción: ");

        String nombre = leerTexto("Nombre del producto: ");
        double precio = leerDouble("Precio: ");

        Producto nuevoProducto;

        if (tipo == 1) {
            System.out.println("Categorías disponibles:");
            for (Categoria c : categorias) {
                System.out.println(c.getId() + ") " + c.getNombre());
            }
            int idCat = leerEntero("Elegí id de categoría: ");

            Categoria seleccionada = null;
            for (Categoria c : categorias) {
                if (c.getId() == idCat) {
                    seleccionada = c;
                    break;
                }
            }

            if (seleccionada == null) {
                System.out.println("Categoría inválida.");
                return;
            }

            nuevoProducto = new Articulo(nombre, precio, seleccionada);

        } else if (tipo == 2) {
            nuevoProducto = new Servicio(nombre, precio, 0);
        } else {
            System.out.println("Tipo inválido.");
            return;
        }

        productos.add(nuevoProducto);
        System.out.println("Producto creado: " + nuevoProducto);
        ArchivoProductosHelper.guardarProductos(productos);
    }

    @Override
    public void listar() {
        if (productos.isEmpty()) {
            System.out.println("(sin productos)");
        } else {
            for (Producto p : productos) {
                System.out.println(p);
            }
        }
    }

    @Override
    public void actualizar() {
        System.out.println("\n=== Lista de productos ===");
        listar();

        int id = leerEntero("\nId del producto que querés actualizar: ");
        for (Producto p : productos) {
            if (p.getId() == id) {
                System.out.println("\nProducto actual: " + p.getNombre() + " | Precio actual: " + p.getPrecio());

                String nuevoNombre = leerTexto("Nuevo nombre (" + p.getNombre() + "): ");
                if (nuevoNombre.isBlank()) nuevoNombre = p.getNombre();

                double nuevoPrecio = leerDouble("Nuevo precio (" + p.getPrecio() + "): ");

                p.setNombre(nuevoNombre);
                p.setPrecio(nuevoPrecio);

                System.out.println("Producto actualizado: " + p);
                ArchivoProductosHelper.guardarProductos(productos);
                return;
            }
        }
        System.out.println("No se encontró producto con id " + id);
    }

    @Override
    public void eliminar() {
        System.out.println("\n=== Lista de productos ===");
        listar();

        int id = leerEntero("\nIngrese el ID del producto a eliminar: ");

        Producto productoAEliminar = productos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);

        if (productoAEliminar == null) {
            System.out.println("No se encontró ningún producto con ese ID.");
            return;
        }

        System.out.printf("Seleccionó: [ID: %d] %s%n", productoAEliminar.getId(), productoAEliminar.getNombre());
        String confirmacion = leerTexto("¿Está seguro que desea eliminar este producto? (s/n): ").toLowerCase();

        if (confirmacion.equals("s")) {
            productos.remove(productoAEliminar);
            ArchivoProductosHelper.guardarProductos(productos);
            System.out.println("Producto eliminado correctamente.");
        } else {
            System.out.println("Operación cancelada. No se eliminó ningún producto.");
        }
    }

    public void menu() {
        int opcion;
        do {
            mostrarOpciones();
            opcion = leerEntero("Elegí una opción: ");

            switch (opcion) {
                case 1 -> crear();
                case 2 -> listar();
                case 3 -> actualizar();
                case 4 -> eliminar();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }
}
