package crudpoo;

import java.util.ArrayList;

// ============================================================================
// CrudProductos
// ----------------------------------------------------------------------------
// - Implementa el CRUD CONCRETO para Productos, reusando la plantilla de
//   CrudConsola.
// - Mantiene las listas en memoria (ArrayList) y toda la interacción de consola
//   relacionada con productos y categorías.
// - Esta separación deja a "Main" muy limpio: sólo orquesta el flujo.
// ============================================================================
public class CrudProductos extends CrudConsola<Producto> {

    private final ArrayList<Producto> productos;   // almacenamiento en memoria
    private final ArrayList<Categoria> categorias; // catálogo de categorías

    // Constructor: recibe las listas que compartiremos con Main
    public CrudProductos(ArrayList<Producto> productos, ArrayList<Categoria> categorias) {
        this.productos = productos;   // guardamos la referencia a la lista de productos
        this.categorias = categorias; // guardamos la referencia a la lista de categorías
    }

    // ------------------------- Crear ----------------------------------
    @Override
    public void crear() {
        System.out.println("1) Crear Artículo");
        System.out.println("2) Crear Servicio");
        int op = leerEntero("Elige una opción: ");

        if (op == 1) {
            // Datos comunes
            String nombre = leerTexto("Nombre: ");
            double precio = leerDouble("Precio: ");

            // Elegir categoría
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

            if (seleccionada != null) {
                productos.add(new Articulo(nombre, precio, seleccionada));
                System.out.println("Artículo creado.");
            } else {
                System.out.println("Categoría inválida.");
            }

        } else if (op == 2) {
            String nombre = leerTexto("Nombre: ");
            double precio = leerDouble("Precio: ");
            int duracion = leerEntero("Duración (horas): ");
            productos.add(new Servicio(nombre, precio, duracion));
            System.out.println("Servicio creado.");

        } else {
            System.out.println("Opción inválida.");
        }
    }

    // ------------------------- Listar ---------------------------------
    @Override
    public void listar() {
        if (productos.isEmpty()) {
            System.out.println("(sin productos)");
        } else {
            for (Producto p : productos) {
                System.out.println(p); // Polimorfismo: imprime toString() de cada subclase
            }
        }
    }

    // ------------------------- Actualizar ------------------------------
    @Override
    public void actualizar() {
        int id = leerEntero("Id del producto: ");
        for (Producto p : productos) {
            if (p.getId() == id) {
                String nuevoNombre = leerTexto("Nuevo nombre: ");
                double nuevoPrecio = leerDouble("Nuevo precio: ");
                p.setNombre(nuevoNombre);
                p.setPrecio(nuevoPrecio);
                System.out.println("Actualizado: " + p);
                return;
            }
        }
        System.out.println("No se encontró producto con id " + id);
    }

    // ------------------------- Eliminar --------------------------------
    @Override
    public void eliminar() {
        int id = leerEntero("Id del producto a eliminar: ");
        boolean eliminado = productos.removeIf(p -> p.getId() == id);
        System.out.println(eliminado ? "Producto eliminado." : "No existía ese id.");
    }
}
