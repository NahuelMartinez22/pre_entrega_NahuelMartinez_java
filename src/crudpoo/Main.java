package crudpoo;

import java.util.ArrayList;

// ============================================================================
// Main (limpio y mínimo)
// ----------------------------------------------------------------------------
// - Sólo arranca el programa y delega el trabajo a CrudProductos.
// - Muestra cómo separar responsabilidades: Main NO hace CRUD ni lee teclado;
//   eso queda en CrudConsola/CrudProductos.
// ============================================================================
public class Main {
    public static void main(String[] args) {

        // Listas en memoria que usarán todas las capas
        ArrayList<Producto> productos = new ArrayList<>();
        ArrayList<Categoria> categorias = new ArrayList<>();

        // Precargamos algunas categorías para poder elegir
        categorias.add(new Categoria("Tecnología"));
        categorias.add(new Categoria("Hogar"));
        categorias.add(new Categoria("Libros"));

        // Creamos el CRUD concreto para productos
        CrudProductos crud = new CrudProductos(productos, categorias);

        // Bucle principal de menú (delegado)
        int opcion;
        do {
            crud.mostrarOpciones();                 // menú genérico de la superclase
            opcion = crud.leerEntero("");           // leemos opción con helper reutilizable

            // Según la opción, delegamos en el CRUD concreto
            switch (opcion) {
                case 1 -> crud.crear();
                case 2 -> crud.listar();
                case 3 -> crud.actualizar();
                case 4 -> crud.eliminar();
                case 0 -> System.out.println("¡Hasta luego!");
                default -> System.out.println("Opción inválida");
            }
        } while (opcion != 0);
    }
}
