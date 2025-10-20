package crudpoo;

import java.util.Scanner;

// ============================================================================
// Clase abstracta CrudConsola<T>
// ----------------------------------------------------------------------------
// - Provee una "plantilla" para CRUDs de consola (menú y scanner compartidos).
// - Separa la interacción de consola del "Main" para mantenerlo limpio.
// - Demuestra: clases abstractas, métodos abstractos, y uso de generics (T).
// ============================================================================
public abstract class CrudConsola<T> {

    // Scanner protegido: accesible para subclases (encapsulado del resto)
    protected final Scanner scanner = new Scanner(System.in);

    // Métodos abstractos: cada CRUD concreto los implementa a su manera
    public abstract void crear();
    public abstract void listar();
    public abstract void actualizar();
    public abstract void eliminar();

    // Método común para mostrar opciones del menú
    public void mostrarOpciones() {
        System.out.println("\n=== Menú CRUD ===");
        System.out.println("1) Crear");
        System.out.println("2) Listar");
        System.out.println("3) Actualizar");
        System.out.println("4) Eliminar");
        System.out.println("0) Salir");
        System.out.print("Opción: ");
    }

    // Helpers reutilizables para leer datos con validación básica
    protected int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String linea = scanner.nextLine();
                return Integer.parseInt(linea.trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe ser un número entero.");
            }
        }
    }

    protected double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String linea = scanner.nextLine();
                return Double.parseDouble(linea.trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe ser un número (use punto decimal).");
            }
        }
    }

    protected String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }
}
