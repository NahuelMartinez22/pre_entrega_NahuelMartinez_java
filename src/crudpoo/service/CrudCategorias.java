package crudpoo.service;

import crudpoo.model.Categoria;

import java.util.ArrayList;

public class CrudCategorias extends CrudConsola<Categoria> {

    private final ArrayList<Categoria> categorias;

    public CrudCategorias(ArrayList<Categoria> categorias) {
        this.categorias = categorias;
    }

    @Override
    public void mostrarOpciones() {
        System.out.println("\n=== Menú Categorías ===");
        System.out.println("1) Crear");
        System.out.println("2) Listar");
        System.out.println("3) Actualizar");
        System.out.println("4) Eliminar");
        System.out.println("0) Volver");
    }

    @Override
    public void crear() {
        String nombre = leerTexto("Nombre de la nueva categoría: ");
        Categoria c = new Categoria(nombre);
        categorias.add(c);
        System.out.println("Categoría creada: " + c);
    }

    @Override
    public void listar() {
        if (categorias.isEmpty()) {
            System.out.println("(No hay categorías registradas)");
            return;
        }
        System.out.println("Categorías disponibles:");
        for (Categoria c : categorias) {
            System.out.println(c);
        }
    }

    @Override
    public void actualizar() {
        int id = leerEntero("ID de la categoría a actualizar: ");
        for (Categoria c : categorias) {
            if (c.getId() == id) {
                String nuevoNombre = leerTexto("Nuevo nombre: ");
                c.setNombre(nuevoNombre);
                System.out.println("Actualizado: " + c);
                return;
            }
        }
        System.out.println("No se encontró categoría con ID " + id);
    }

    @Override
    public void eliminar() {
        int id = leerEntero("ID de la categoría a eliminar: ");
        boolean eliminado = categorias.removeIf(c -> c.getId() == id);
        System.out.println(eliminado ? "Categoría eliminada." : "No existe categoría con ese ID.");
    }
}
